package org.ourproject.kune.workspace.client.socialnet;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.ParticipationComponent;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ParticipationPresenter implements ParticipationComponent, AbstractPresenter {

    private ParticipationView view;

    public void init(final ParticipationView view) {
	this.view = view;
    }

    public void getParticipation(final String user, final GroupDTO group, final AccessRightsDTO accessRightsDTO) {
	Site.showProgressProcessing();
	final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();

	server.getParticipation(user, group.getShortName(), new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
		Site.hideProgress();
	    }

	    public void onSuccess(final Object result) {
		ParticipationDataDTO participation = (ParticipationDataDTO) result;
		setParticipation(participation, accessRightsDTO);
		Site.hideProgress();
	    }
	});
    }

    public void doAction(final String action, final String group) {
	DefaultDispatcher.getInstance().fire(action, group, this);
    }

    public View getView() {
	return view;
    }

    private void setParticipation(final ParticipationDataDTO participation, final AccessRightsDTO rights) {
	view.setDropDownContentVisible(false);
	view.clear();
	MemberAction[] adminsActions = {
		new MemberAction("Don't participate more in this group", WorkspaceEvents.UNJOIN_GROUP),
		MemberAction.GOTO_GROUP_COMMAND };
	MemberAction[] collabActions = adminsActions;
	MemberAction[] viewerActions = { MemberAction.GOTO_GROUP_COMMAND };
	List groupsIsAdmin = participation.getGroupsIsAdmin();
	List groupsIsCollab = participation.getGroupsIsCollab();
	boolean userIsAdmin = rights.isAdministrable();
	boolean userIsCollab = rights.isEditable();
	boolean userIsMember = isMember(userIsAdmin, userIsCollab);
	int numAdmins = groupsIsAdmin.size();
	int numCollaborators = groupsIsCollab.size();
	if (numAdmins > 0 || numCollaborators > 0) {
	    addParticipants(groupsIsAdmin, groupsIsCollab, numAdmins, numCollaborators, userIsAdmin, userIsMember,
		    adminsActions, collabActions, viewerActions);
	    view.setDropDownContentVisible(true);
	    view.show();
	} else {
	    hide();
	}

    }

    private void hide() {
	view.hide();
    }

    private void addParticipants(final List groupsIsAdmin, final List groupsIsCollab, final int numAdmins,
	    final int numCollaborators, final boolean userIsAdmin, boolean userIsMember,
	    final MemberAction[] adminsActions, final MemberAction[] collabActions, final MemberAction[] viewerActions) {
	MemberAction[] actions;
	if (!userIsMember) {
	    actions = viewerActions;
	} else {
	    if (userIsAdmin) {
		actions = adminsActions;
	    } else {
		actions = collabActions;
	    }
	}
	if (numAdmins > 0) {
	    // i18n
	    view.addCategory("as admin in", "Admisnistrate these groups");
	    iteraList("as admin in", groupsIsAdmin, actions);
	}
	if (numCollaborators > 0) {
	    view.addCategory("as collaborator in", "Collaborate with these groups");
	    iteraList("as collaborator in", groupsIsCollab, actions);
	}

    }

    private void iteraList(final String categoryName, final List groupList, final MemberAction[] actions) {
	final Iterator iter = groupList.iterator();
	while (iter.hasNext()) {
	    final LinkDTO group = (LinkDTO) iter.next();
	    view.addCategoryMember(categoryName, group.getShortName(), group.getLongName(), actions);
	}
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
	return userIsAdmin || userIsCollab;
    }
}
