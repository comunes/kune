package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.ImageDescriptor;
import org.ourproject.kune.platf.client.services.ImageUtils;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridButton;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenu;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenuItemCollection;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.signal.Slot;

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
    private final GridMenuItemCollection<GroupDTO> otherOperations;
    private final GridMenuItemCollection<GroupDTO> otherLoggedOperations;
    private final ImageUtils imageUtils;
    private final GridMenuItemCollection<GroupDTO> otherOperationsUsers;
    private final GridMenuItemCollection<GroupDTO> otherLoggedOperationsUsers;

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
	otherOperationsUsers = new GridMenuItemCollection<GroupDTO>();
	otherLoggedOperationsUsers = new GridMenuItemCollection<GroupDTO>();
	otherOperations = new GridMenuItemCollection<GroupDTO>();
	otherLoggedOperations = new GridMenuItemCollection<GroupDTO>();

    }

    public void addGroupOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	GridMenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperations : otherOperations;
	collection.add(operation);
    }

    public void addUserOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	GridMenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperationsUsers : otherOperationsUsers;
	collection.add(operation);
    }

    public void removeGroupOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	GridMenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperations : otherOperations;
	collection.remove(operation);
    }

    public void removeUserOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	GridMenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperationsUsers : otherOperationsUsers;
	collection.remove(operation);
    }

    protected GridItem<GroupDTO> createGridItem(final GridGroup groupCategory, final GroupDTO group,
	    final AccessRightsDTO rights, final MenuItem<GroupDTO>... gridMenuItems) {
	final GridItem<GroupDTO> gridItem = createDefMemberMenu(group, groupCategory);
	final GridMenu<GroupDTO> menu = gridItem.getMenu();
	if (rights.isAdministrable()) {
	    for (final MenuItem<GroupDTO> item : gridMenuItems) {
		menu.addMenuItem(item);
	    }
	}
	return gridItem;
    }

    private void createButtons() {
	requestJoin = new GridButton("images/add-green.gif", i18n.t("Participate"), i18n
		.t("Request to participate in this group"), new Slot<String>() {
	    public void onEvent(final String parameter) {
		Site.showProgressProcessing();
		snServiceProvider.get().requestJoinGroup(session.getUserHash(),
			session.getCurrentState().getGroup().getShortName(), new AsyncCallbackSimple<Object>() {
			    public void onSuccess(final Object result) {
				Site.hideProgress();
				final String resultType = (String) result;
				if (resultType == SocialNetworkDTO.REQ_JOIN_ACEPTED) {
				    Site.info(i18n.t("You are now member of this group"));
				    stateManager.reload();
				}
				if (resultType == SocialNetworkDTO.REQ_JOIN_DENIED) {
				    Site.important(i18n.t("Sorry this is a closed group"));
				}
				if (resultType == SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION) {
				    Site.info(i18n.t("Requested. Waiting for admins decision"));
				}
			    }
			});
	    }
	});

	unJoinButton = new GridButton("images/del.gif", i18n.t("Unjoin"), i18n
		.t("Don't participate more as a member in this group"), new Slot<String>() {
	    public void onEvent(final String parameter) {
		removeMemberAction();
	    }
	});
    }

    private GridItem<GroupDTO> createDefMemberMenu(final GroupDTO group, final GridGroup gridGroup) {
	final GridMenu<GroupDTO> menu = new GridMenu<GroupDTO>(group);
	final String longName = group.getLongName();
	final String toolTip = i18n.t(group.getType().equals(GroupDTO.PERSONAL) ? "User nickname: [%s]"
		: "Group short name: [%s]", group.getShortName());
	final GridItem<GroupDTO> gridItem = new GridItem<GroupDTO>(group, gridGroup, group.getShortName(), imageUtils
		.getImageHtml(ImageDescriptor.groupDefIcon), longName, longName, " ", longName, toolTip, menu);
	if (!group.getType().equals(GroupDTO.PERSONAL)) {
	    menu.addMenuItemList(otherOperations);
	}
	if (session.isLogged() && !group.getType().equals(GroupDTO.PERSONAL)) {
	    menu.addMenuItemList(otherLoggedOperations);
	}
	if (group.getType().equals(GroupDTO.PERSONAL)) {
	    menu.addMenuItemList(otherOperationsUsers);
	}
	if (session.isLogged() && group.getType().equals(GroupDTO.PERSONAL)) {
	    menu.addMenuItemList(otherLoggedOperationsUsers);
	}

	return gridItem;
    }

    private void createMenuActions() {
	gotoGroupMenuItem = new MenuItem<GroupDTO>("images/group-home.gif", i18n.t("Visit this group homepage"),
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO groupDTO) {
			stateManager.gotoToken(groupDTO.getShortName());
		    }
		});
	gotoMemberMenuItem = new MenuItem<GroupDTO>("images/group-home.gif", i18n.t("Visit this member homepage"),
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO groupDTO) {
			stateManager.gotoToken(groupDTO.getShortName());
		    }
		});
	unJoinMenuItem = new MenuItem<GroupDTO>("images/del.gif", i18n
		.t("Don't participate more as a member in this group"), new Slot<GroupDTO>() {
	    public void onEvent(final GroupDTO groupDTO) {
		removeMemberAction(groupDTO);
	    }
	});
	changeToCollabMenuItem = new MenuItem<GroupDTO>("images/arrow-down-green.gif",
		i18n.t("Change to collaborator"), new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().setAdminAsCollab(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), group.getShortName(),
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
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().deleteMember(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), group.getShortName(),
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
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().setCollabAsAdmin(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), group.getShortName(),
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
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().AcceptJoinGroup(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), group.getShortName(),
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
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snServiceProvider.get().denyJoinGroup(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), group.getShortName(),
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
	snServiceProvider.get().unJoinGroup(session.getUserHash(), groupDTO.getShortName(),
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
