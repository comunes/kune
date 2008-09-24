package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.SocialNetworkRequestResult;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.ImageDescriptor;
import org.ourproject.kune.platf.client.services.ImageUtils;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.MenuItemCollection;
import org.ourproject.kune.platf.client.ui.gridmenu.CustomMenu;
import org.ourproject.kune.platf.client.ui.gridmenu.GridButton;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class SocialNetworkPresenter {

    protected GridButton requestJoin;
    protected GridButton unJoinButton;
    protected MenuItem<GroupDTO> changeToCollabMenuItem;
    protected MenuItem<GroupDTO> removeMemberMenuItem;
    protected MenuItem<GroupDTO> changeToAdminMenuItem;
    protected MenuItem<GroupDTO> acceptJoinGroupMenuItem;
    protected MenuItem<GroupDTO> denyJoinGroupMenuItem;
    protected MenuItem<GroupDTO> gotoMemberMenuItem;
    protected MenuItem<GroupDTO> gotoGroupMenuItem;
    protected MenuItem<GroupDTO> unJoinMenuItem;
    private final I18nUITranslationService i18n;
    private final StateManager stateManager;
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;
    private final Session session;
    private final MenuItemCollection<GroupDTO> otherOperations;
    private final MenuItemCollection<GroupDTO> otherLoggedOperations;
    private final ImageUtils imageUtils;
    private final MenuItemCollection<GroupDTO> otherOperationsUsers;
    private final MenuItemCollection<GroupDTO> otherLoggedOperationsUsers;

    public SocialNetworkPresenter(final I18nUITranslationService i18n, final StateManager stateManager,
	    final ImageUtils imageUtils, final Session session,
	    final Provider<SocialNetworkServiceAsync> snServiceProvider) {
	this.i18n = i18n;
	this.stateManager = stateManager;
	this.imageUtils = imageUtils;
	this.session = session;
	this.snServiceProvider = snServiceProvider;
	createButtons();
	createMenuActions();
	otherOperationsUsers = new MenuItemCollection<GroupDTO>();
	otherLoggedOperationsUsers = new MenuItemCollection<GroupDTO>();
	otherOperations = new MenuItemCollection<GroupDTO>();
	otherLoggedOperations = new MenuItemCollection<GroupDTO>();

    }

    public void addGroupOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	MenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperations : otherOperations;
	collection.add(operation);
    }

    public void addUserOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	MenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperationsUsers : otherOperationsUsers;
	collection.add(operation);
    }

    public void removeGroupOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	MenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperations : otherOperations;
	collection.remove(operation);
    }

    public void removeUserOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	MenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperationsUsers : otherOperationsUsers;
	collection.remove(operation);
    }

    protected GridItem<GroupDTO> createGridItem(final GridGroup groupCategory, final GroupDTO group,
	    final AccessRightsDTO rights, final MenuItem<GroupDTO>... gridMenuItems) {
	final GridItem<GroupDTO> gridItem = createDefMemberMenu(group, groupCategory);
	final CustomMenu<GroupDTO> menu = gridItem.getMenu();
	if (rights.isAdministrable()) {
	    for (final MenuItem<GroupDTO> item : gridMenuItems) {
		menu.addMenuItem(item);
	    }
	}
	return gridItem;
    }

    private void createButtons() {
	requestJoin = new GridButton("images/add-green.gif", i18n.t("Participate"), i18n
		.t("Request to participate in this group"), new Listener<String>() {
	    public void onEvent(final String parameter) {
		Site.showProgressProcessing();
		snServiceProvider.get().requestJoinGroup(session.getUserHash(),
			session.getCurrentState().getStateToken(), new AsyncCallbackSimple<Object>() {
			    public void onSuccess(final Object result) {
				Site.hideProgress();
				final SocialNetworkRequestResult resultType = (SocialNetworkRequestResult) result;
				switch (resultType) {
				case accepted:
				    Site.info(i18n.t("You are now member of this group"));
				    stateManager.reload();
				    break;
				case denied:
				    Site.important(i18n.t("Sorry this is a closed group"));
				    break;
				case moderated:
				    Site.info(i18n.t("Requested. Waiting for admins decision"));
				    break;
				}
			    }
			});
	    }
	});

	unJoinButton = new GridButton("images/del.gif", i18n.t("Unjoin"), i18n
		.t("Don't participate more in this group"), new Listener<String>() {
	    public void onEvent(final String parameter) {
		removeMemberAction();
	    }
	});
    }

    private GridItem<GroupDTO> createDefMemberMenu(final GroupDTO group, final GridGroup gridGroup) {
	final CustomMenu<GroupDTO> menu = new CustomMenu<GroupDTO>(group);
	final String longName = group.getLongName();
	boolean isPersonal = group.getType().equals(GroupType.PERSONAL);
	final String toolTip = i18n.t(isPersonal ? "User nickname: [%s]" : "Group short name: [%s]", group
		.getShortName());
	final String imageHtml = isPersonal ? imageUtils.getImageHtml(ImageDescriptor.personDef) : imageUtils
		.getImageHtml(ImageDescriptor.groupDefIcon);
	final GridItem<GroupDTO> gridItem = new GridItem<GroupDTO>(group, gridGroup, group.getShortName(), imageHtml,
		longName, longName, " ", longName, toolTip, menu);
	if (!isPersonal) {
	    menu.addMenuItemList(otherOperations);
	}
	if (session.isLogged() && !isPersonal) {
	    menu.addMenuItemList(otherLoggedOperations);
	}
	if (isPersonal) {
	    menu.addMenuItemList(otherOperationsUsers);
	}
	if (session.isLogged() && isPersonal) {
	    menu.addMenuItemList(otherLoggedOperationsUsers);
	}

	return gridItem;
    }

    private void createMenuActions() {
	gotoGroupMenuItem = new MenuItem<GroupDTO>("images/group-home.gif", i18n.t("Visit this group homepage"),
		new Listener<GroupDTO>() {
		    public void onEvent(final GroupDTO groupDTO) {
			stateManager.gotoToken(groupDTO.getShortName());
		    }
		});
	gotoMemberMenuItem = new MenuItem<GroupDTO>("images/group-home.gif", i18n.t("Visit this member homepage"),
		new Listener<GroupDTO>() {
		    public void onEvent(final GroupDTO groupDTO) {
			stateManager.gotoToken(groupDTO.getShortName());
		    }
		});
	unJoinMenuItem = new MenuItem<GroupDTO>("images/del.gif", i18n
		.t("Don't participate more as a member in this group"), new Listener<GroupDTO>() {
	    public void onEvent(final GroupDTO groupDTO) {
		removeMemberAction(groupDTO);
	    }
	});
	changeToCollabMenuItem = new MenuItem<GroupDTO>("images/arrow-down-green.gif",
		i18n.t("Change to collaborator"), new Listener<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().setAdminAsCollab(session.getUserHash(),
				session.getCurrentState().getStateToken(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Type of member changed"));
					stateManager.setSocialNetwork(result);
				    }
				});
		    }
		});
	removeMemberMenuItem = new MenuItem<GroupDTO>("images/del.gif", i18n.t("Remove this member"),
		new Listener<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().deleteMember(session.getUserHash(),
				session.getCurrentState().getStateToken(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Member removed"));
					stateManager.reload();
					// in the future, only if I cannot
					// be affected:
					// snService.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
					// result);
				    }
				});
		    }
		});
	changeToAdminMenuItem = new MenuItem<GroupDTO>("images/arrow-up-green.gif", i18n.t("Change to admin"),
		new Listener<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().setCollabAsAdmin(session.getUserHash(),
				session.getCurrentState().getStateToken(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Type of member changed"));
					stateManager.setSocialNetwork(result);
				    }
				});
		    }
		});
	acceptJoinGroupMenuItem = new MenuItem<GroupDTO>("images/accept.gif", i18n.t("Accept this member"),
		new Listener<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().AcceptJoinGroup(session.getUserHash(),
				session.getCurrentState().getStateToken(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Member accepted"));
					stateManager.setSocialNetwork(result);
				    }
				});
		    }
		});
	denyJoinGroupMenuItem = new MenuItem<GroupDTO>("images/cancel.gif", i18n.t("Don't accept this member"),
		new Listener<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().denyJoinGroup(session.getUserHash(),
				session.getCurrentState().getStateToken(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Member rejected"));
					stateManager.setSocialNetwork(result);
				    }
				});
		    }
		});
    }

    private void removeMemberAction() {
	removeMemberAction(session.getCurrentState().getGroup());
    }

    private void removeMemberAction(final GroupDTO groupDTO) {
	Site.showProgressProcessing();
	snServiceProvider.get().unJoinGroup(session.getUserHash(), new StateToken(groupDTO.getShortName()),
		new AsyncCallbackSimple<SocialNetworkResultDTO>() {
		    public void onSuccess(final SocialNetworkResultDTO result) {
			Site.hideProgress();
			Site.info(i18n.t("Removed as member"));
			stateManager.reload();
			// in the future with user info:
			// services.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
			// result);
		    }
		});
    }
}
