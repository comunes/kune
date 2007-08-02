package org.ourproject.kune.sitebar.client.group;

import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class NewGroupPresenter {

    private NewGroupListener listener;
    private NewGroupView view;

    public NewGroupPresenter(NewGroupListener listener) {
	this.listener = listener;
    }

    public void init(NewGroupView view) {
	this.view = view;
	reset();
    }

    private void reset() {
	view.clearData();
    }

    void doCreateNewGroup(String shortName, String longName, String publicDesc, int type) {
	SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
	siteBarService.createNewGroup(shortName, longName, publicDesc, type, new AsyncCallback() {
	    public void onFailure(Throwable arg0) {
		// TODO
	    }

	    public void onSuccess(Object arg0) {
		listener.onNewGroupCreated();
		reset();
	    }
	});
    }

    void doCancel() {
	reset();
	listener.onNewGroupCancel();
    }
}
