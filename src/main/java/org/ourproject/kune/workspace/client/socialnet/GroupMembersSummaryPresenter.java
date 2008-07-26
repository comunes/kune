package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.ImageDescriptor;
import org.ourproject.kune.platf.client.services.ImageUtils;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.gridmenu.GridButton;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;
import org.ourproject.kune.workspace.client.workspace.GroupLiveSearcher;
import org.ourproject.kune.workspace.client.workspace.GroupMembersSummary;

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.signal.Slot;

public class GroupMembersSummaryPresenter extends SocialNetworkPresenter implements GroupMembersSummary {

    protected GridButton addMember;
    private GroupMembersSummaryView view;
    private final I18nUITranslationService i18n;
    private final GridGroup adminCategory;
    private final GridGroup collabCategory;
    private final GridGroup pendigCategory;
    private final Session session;
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;

    public GroupMembersSummaryPresenter(final I18nUITranslationService i18n,
	    final Provider<StateManager> stateManagerProvider, final ImageUtils imageUtils, final Session session,
	    final Provider<SocialNetworkServiceAsync> snServiceProvider,
	    final Provider<GroupLiveSearcher> groupLiveSearcherProvider) {
	super(i18n, stateManagerProvider, imageUtils, session, snServiceProvider);
	this.i18n = i18n;
	this.session = session;
	this.snServiceProvider = snServiceProvider;
	final String adminsTitle = i18n.t("Admins");
	final String collabsTitle = i18n.t("Collaborators");
	final String pendingTitle = i18n.t("Pending");
	adminCategory = new GridGroup(adminsTitle, adminsTitle, i18n.t("People that can admin this group"), true);
	collabCategory = new GridGroup(collabsTitle, collabsTitle, i18n
		.t("Other people that collaborate with this group"), true);
	pendigCategory = new GridGroup(pendingTitle, pendingTitle, i18n
		.t("People pending to be accepted in this group by the admins"), imageUtils
		.getImageHtml(ImageDescriptor.alert), true);
	addMember = new GridButton("images/add-green.gif", i18n.t("Add member"), i18n
		.t("Add a group or a person as member of this group"), new Slot<String>() {
	    public void onEvent(final String parameter) {
		groupLiveSearcherProvider.get().onSelectionAdd(new Slot<LinkDTO>() {
		    public void onEvent(final LinkDTO link) {
			view.confirmAddCollab(link.getShortName(), link.getLongName());
			groupLiveSearcherProvider.get().onSelectionRemove(this);
		    }
		});
		groupLiveSearcherProvider.get().show();
	    }
	});
	super.addGroupOperation(gotoMemberMenuItem, false);
    }

    public void addCollab(final String groupShortName) {
	Site.showProgressProcessing();
	snServiceProvider.get().addCollabMember(session.getUserHash(),
		session.getCurrentState().getGroup().getShortName(), groupShortName,
		new AsyncCallbackSimple<SocialNetworkResultDTO>() {
		    public void onSuccess(final SocialNetworkResultDTO result) {
			Site.hideProgress();
			Site.info(i18n.t("Member added as collaborator"));
			getStateManager().setSocialNetwork(result);
		    }

		});
    }

    public void init(final GroupMembersSummaryView view) {
	this.view = view;
    }

    public void setState(final StateDTO state) {
	if (state.getGroup().getType().equals(GroupDTO.PERSONAL)) {
	    view.setVisible(false);
	} else {
	    setGroupMembers(state.getGroupMembers(), state.getGroupRights());
	}
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	view.setTheme(oldTheme, newTheme);
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
	return userIsAdmin || userIsCollab;
    }

    @SuppressWarnings("unchecked")
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

	view.setDraggable(session.isLogged());

	if (!userIsMember) {
	    view.addButton(requestJoin);
	} else if (userIsAdmin && numAdmins > 1 || userIsCollab) {
	    view.addButton(unJoinButton);
	}

	if (userCanView) {
	    for (final GroupDTO admin : adminsList) {
		view
			.addItem(createGridItem(adminCategory, admin, rights, changeToCollabMenuItem,
				removeMemberMenuItem));
	    }
	    for (final GroupDTO collab : collabList) {
		view
			.addItem(createGridItem(collabCategory, collab, rights, changeToAdminMenuItem,
				removeMemberMenuItem));
	    }
	    for (final GroupDTO pendingCollab : pendingCollabsList) {
		view.addItem(createGridItem(pendigCategory, pendingCollab, rights, acceptJoinGroupMenuItem,
			denyJoinGroupMenuItem));
	    }
	}
	view.setDropDownContentVisible(true);
	view.setVisible(true);
    }

}
