package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.SocialNetworkComponent;

public class SocialNetworkPresenter implements SocialNetworkComponent {

    private SocialNetworkView view;

    public void setSocialNetwork(final SocialNetworkDTO socialNetwork) {
	view.setSocialNetwork(socialNetwork);
	final AccessListsDTO accessLists = socialNetwork.getAccessLists();
	view.setAdminsVisible(accessLists.getAdmins().getList().size() > 0);
	view.setCollabVisible(accessLists.getEditors().getList().size() > 0);
	view.setPendingCollaboratorsLabelVisible(socialNetwork.getPendingCollaborators().getList().size() > 0);
    }

    public void init(final SocialNetworkView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void onJoin() {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.REQ_JOIN_GROUP, null, null);
    }

    public void onMore() {
	// TODO Auto-generated method stub

    }

    public void onDelGroup(final GroupDTO group) {
	// TODO:
	// DefaultDispatcher.getInstance().fire(WorkspaceEvents.REQ_UNJOIN_GROUP,
	// null, null);
    }

}
