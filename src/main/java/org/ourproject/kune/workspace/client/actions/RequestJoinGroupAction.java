package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class RequestJoinGroupAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
	onRequestJoinGroup(services);
    }

    private void onRequestJoinGroup(final Services services) {
	Site.showProgressProcessing();
	final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
	server.requestJoinGroup(services.user, services.session.getCurrentState().getGroup().getShortName(),
		new AsyncCallback() {
		    public void onFailure(final Throwable caught) {
			Site.hideProgress();
		    }

		    public void onSuccess(final Object result) {
			Site.hideProgress();
			final String resultType = (String) result;
			// i18n
			if (resultType == SocialNetworkDTO.REQ_JOIN_ACEPTED) {
			    Site.info("This is a open group, you are now member of this group");
			}
			if (resultType == SocialNetworkDTO.REQ_JOIN_DENIED) {
			    Site.important("Sorry this is a closed group");
			}
			if (resultType == SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION) {
			    Site.info("Requested. Waiting for admins");
			}
		    }
		});

    }
}
