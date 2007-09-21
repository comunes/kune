package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class AddAdminAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
	onAddAdmin(services, (GroupDTO) value);
    }

    private void onAddAdmin(final Services services, final GroupDTO group) {
	Site.showProgressProcessing();
	final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
	server.addAdminMember(services.user, group.getShortName(), services.session.getCurrentState().getGroup()
		.getShortName(), new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
		Site.hideProgress();
	    }

	    public void onSuccess(final Object result) {
		Site.hideProgress();
		// i18n
		Site.info("Member added as admin");
		// TODO: Reload SocialNetwork info only
		services.stateManager.reload();
	    }
	});

    }
}
