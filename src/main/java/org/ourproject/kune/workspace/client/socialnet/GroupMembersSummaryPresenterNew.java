package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.ImageDescriptor;
import org.ourproject.kune.platf.client.services.ImageUtils;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.gridmenu.GridButton;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenu;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenuItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenuItemCollection;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;
import org.ourproject.kune.workspace.client.workspace.GroupMembersSummary;

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.signal.Slot;

public class GroupMembersSummaryPresenterNew implements GroupMembersSummary {

    private GroupMembersSummaryViewNew view;
    private final I18nTranslationService i18n;
    private final GridGroup adminGroup;
    private final GridGroup collabGroup;
    private final GridGroup pendigGroup;
    private GridMenuItem<GroupDTO> gotoGroupMenuItem;
    private final ImageUtils imageUtils;
    private final Session session;
    private final SocialNetworkServiceAsync snService;
    private final Provider<StateManager> stateManager;
    private GridMenuItem<GroupDTO> changeToCollabMenuItem;
    private GridMenuItem<GroupDTO> removeMemberMenuItem;
    private GridMenuItem<GroupDTO> changeToAdminMenuItem;
    private GridMenuItem<GroupDTO> acceptJoinGroupMenuItem;
    private GridMenuItem<GroupDTO> denyJoinGroupMenuItem;
    private GridButton addMember;
    private GridButton requestJoin;
    private GridButton unJoinButton;
    private GridMenuItemCollection<GroupDTO> otherOperations;
    private GridMenuItemCollection<GroupDTO> otherLoggedOperations;

    public GroupMembersSummaryPresenterNew(final I18nTranslationService i18n,
	    final Provider<StateManager> stateManager, final ImageUtils imageUtils, final Session session,
	    final SocialNetworkServiceAsync snService) {
	this.i18n = i18n;
	this.stateManager = stateManager;
	this.imageUtils = imageUtils;
	this.session = session;
	this.snService = snService;
	final String adminsTitle = i18n.t("Admins");
	final String collabsTitle = i18n.t("Collaborators");
	final String pendingTitle = i18n.t("Pending");
	adminGroup = new GridGroup(adminsTitle, adminsTitle, i18n.t("People that can admin this group"), true);
	collabGroup = new GridGroup(collabsTitle, collabsTitle,
		i18n.t("Other people that collaborate with this group"), true);
	pendigGroup = new GridGroup(pendingTitle, pendingTitle, i18n
		.t("People pending to be accepted in this group by the admins"), imageUtils
		.getImageHtml(ImageDescriptor.alert), true);
	createMenuActions();
	createButtons();
    }

    public void addCollab(final String groupShortName) {
	Site.showProgressProcessing();
	snService.addCollabMember(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
		groupShortName, new AsyncCallbackSimple<SocialNetworkResultDTO>() {
		    public void onSuccess(final SocialNetworkResultDTO result) {
			Site.hideProgress();
			Site.info(i18n.t("Member added as collaborator"));
			getStateManager().setSocialNetwork(result);
		    }

		});
    }

    public void addGroupOperation(final GridMenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	GridMenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperations : otherOperations;
	if (collection == null) {
	    collection = new GridMenuItemCollection<GroupDTO>();
	}
	collection.add(operation);
    }

    public void hide() {
	view.setVisible(false);
    }

    public void init(final GroupMembersSummaryViewNew view) {
	this.view = view;
    }

    public void removeGroupOperation(final GridMenuItem<GroupDTO> operation, final boolean mustBeLogged) {
	GridMenuItemCollection<GroupDTO> collection;
	collection = mustBeLogged ? otherLoggedOperations : otherOperations;
	if (collection != null) {
	    collection.remove(operation);
	}
    }

    public void setState(final StateDTO state) {
	if (state.getGroup().getType() == GroupDTO.PERSONAL) {
	    hide();
	} else {
	    setGroupMembers(state.getGroupMembers(), state.getGroupRights());
	}
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	view.setTheme(oldTheme, newTheme);
    }

    private void createButtons() {
	addMember = new GridButton("images/add-green.gif", i18n.t("Add member"), i18n
		.t("Add a group or a person as member of this group"), new Slot<String>() {
	    public void onEvent(final String parameter) {
		// TODO
		// GroupLiveSearchComponent groupLiveSearchComponent
		// = workspace.getGroupLiveSearchComponent();
		// groupLiveSearchComponent.addListener(listener);
		// groupLiveSearchComponent.show();
	    }
	});

	requestJoin = new GridButton("images/add-green.gif", i18n.t("Participate"), i18n
		.t("Request to participate in this group"), new Slot<String>() {
	    public void onEvent(final String parameter) {
		Site.showProgressProcessing();
		snService.requestJoinGroup(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
			new AsyncCallbackSimple<Object>() {
			    public void onSuccess(final Object result) {
				Site.hideProgress();
				final String resultType = (String) result;
				if (resultType == SocialNetworkDTO.REQ_JOIN_ACEPTED) {
				    Site.info(i18n.t("You are now member of this group"));
				    getStateManager().reload();
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
		Site.showProgressProcessing();
		snService.unJoinGroup(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
			new AsyncCallbackSimple<SocialNetworkResultDTO>() {
			    public void onSuccess(final SocialNetworkResultDTO result) {
				Site.hideProgress();
				Site.info(i18n.t("Removed as member"));
				getStateManager().reload();
				// in the future with user info:
				// services.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
				// result);
			    }
			});
	    }
	});
    }

    private GridItem<GroupDTO> createDefMemberMenu(final GroupDTO group, final GridGroup gridGroup) {
	final GridMenu<GroupDTO> menu = new GridMenu<GroupDTO>(group);
	final String longName = group.getLongName();
	final GridItem<GroupDTO> gridItem = new GridItem<GroupDTO>(group, gridGroup, group.getShortName(), imageUtils
		.getImageHtml(ImageDescriptor.groupDefIcon), longName, longName, " ", longName, i18n.t(
		"User name: [%s]", group.getShortName()), menu);
	menu.addMenuItem(gotoGroupMenuItem);
	if (otherOperations != null) {
	    menu.addMenuItemList(otherOperations);
	}
	if (session.isLogged() && otherOperations != null) {
	    menu.addMenuItemList(otherLoggedOperations);
	}
	return gridItem;
    }

    private void createMenuActions() {
	gotoGroupMenuItem = new GridMenuItem<GroupDTO>("images/group-home.gif", i18n.t("Visit this member homepage"),
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO groupDTO) {
			getStateManager().gotoToken(groupDTO.getShortName());
		    }
		});
	changeToCollabMenuItem = new GridMenuItem<GroupDTO>("images/arrow-down-green.gif", i18n
		.t("Change to collaborator"), new Slot<GroupDTO>() {
	    public void onEvent(final GroupDTO group) {
		Site.showProgressProcessing();
		snService.setCollabAsAdmin(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
			group.getShortName(), new AsyncCallbackSimple<SocialNetworkResultDTO>() {
			    public void onSuccess(final SocialNetworkResultDTO result) {
				Site.hideProgress();
				Site.info(i18n.t("Type of member changed"));
				getStateManager().setSocialNetwork(result);
			    }
			});
	    }
	});
	removeMemberMenuItem = new GridMenuItem<GroupDTO>("images/del.gif", i18n.t("Remove this member"),
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snService.deleteMember(session.getUserHash(), session.getCurrentState().getGroup()
				.getShortName(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Member removed"));
					getStateManager().reload();
					// in the future, only if I cannot
					// be affected:
					// snService.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
					// result);
				    }
				});
		    }
		});
	changeToAdminMenuItem = new GridMenuItem<GroupDTO>("images/arrow-up-green.gif", i18n.t("Change to admin"),
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
			server.addAdminMember(session.getUserHash(), session.getCurrentState().getGroup()
				.getShortName(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Member added as admin"));
					getStateManager().setSocialNetwork(result);
				    }
				});
		    }
		});
	acceptJoinGroupMenuItem = new GridMenuItem<GroupDTO>("images/accept.gif", i18n.t("Accept this member"),
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			snService.AcceptJoinGroup(session.getUserHash(), session.getCurrentState().getGroup()
				.getShortName(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Member accepted"));
					getStateManager().setSocialNetwork(result);
				    }
				});
		    }
		});
	denyJoinGroupMenuItem = new GridMenuItem<GroupDTO>("images/cancel.gif", i18n.t("Don't accept this member"),
		new Slot<GroupDTO>() {
		    public void onEvent(final GroupDTO group) {
			Site.showProgressProcessing();
			final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
			server.denyJoinGroup(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), group.getShortName(),
				new AsyncCallbackSimple<SocialNetworkResultDTO>() {
				    public void onSuccess(final SocialNetworkResultDTO result) {
					Site.hideProgress();
					Site.info(i18n.t("Member rejected"));
					getStateManager().setSocialNetwork(result);
				    }
				});
		    }
		});
    }

    private StateManager getStateManager() {
	return stateManager.get();
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
	return userIsAdmin || userIsCollab;
    }

    private void setGroupMembers(final SocialNetworkDTO socialNetwork, final AccessRightsDTO rights) {
	final AccessListsDTO accessLists = socialNetwork.getAccessLists();

	final List<GroupDTO> adminsList = accessLists.getAdmins().getList();
	final List<GroupDTO> collabList = accessLists.getEditors().getList();
	final List<GroupDTO> pendingCollabsList = socialNetwork.getPendingCollaborators().getList();

	final int numAdmins = adminsList.size();

	boolean userIsAdmin = rights.isAdministrable();
	final boolean userIsCollab = !userIsAdmin && rights.isEditable();
	final boolean userCanView = rights.isVisible();
	boolean userIsMember = isMember(userIsAdmin, userIsCollab);

	view.setDropDownContentVisible(false);
	view.clear();

	if (userIsAdmin) {
	    view.addButton(addMember);
	}

	if (!userIsMember) {
	    view.addButton(requestJoin);
	} else if (userIsAdmin && numAdmins > 1 || userIsCollab) {
	    view.addButton(unJoinButton);
	}

	if (userCanView) {
	    for (final GroupDTO admin : adminsList) {
		final GridItem<GroupDTO> gridItem = createDefMemberMenu(admin, adminGroup);
		final GridMenu<GroupDTO> menu = gridItem.getMenu();
		if (rights.isAdministrable()) {
		    menu.addMenuItem(changeToCollabMenuItem);
		    menu.addMenuItem(removeMemberMenuItem);
		}
		view.addItem(gridItem);
	    }
	    for (final GroupDTO collab : collabList) {
		final GridItem<GroupDTO> gridItem = createDefMemberMenu(collab, collabGroup);
		final GridMenu<GroupDTO> menu = gridItem.getMenu();
		if (rights.isAdministrable()) {
		    menu.addMenuItem(changeToAdminMenuItem);
		    menu.addMenuItem(removeMemberMenuItem);
		}
		view.addItem(gridItem);
	    }
	    for (final GroupDTO pendingCollab : pendingCollabsList) {
		final GridItem<GroupDTO> gridItem = createDefMemberMenu(pendingCollab, pendigGroup);
		final GridMenu<GroupDTO> menu = gridItem.getMenu();
		if (rights.isAdministrable()) {
		    menu.addMenuItem(acceptJoinGroupMenuItem);
		    menu.addMenuItem(denyJoinGroupMenuItem);
		}
		view.addItem(gridItem);
	    }
	}
	view.setDropDownContentVisible(true);
	view.setVisible(true);
    }
}
