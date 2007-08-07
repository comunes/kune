package org.ourproject.kune.sitebar.client.group;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class NewGroupPresenter {

    private final NewGroupListener listener;
    private NewGroupView view;
    private int groupType;

    public NewGroupPresenter(final NewGroupListener listener) {
	this.listener = listener;
	groupType = GroupDTO.TYPE_ORGANIZATION;
    }

    public void init(final NewGroupView view) {
	this.view = view;
	reset();
    }

    private void reset() {
	view.clearData();
    }

    void doCreateNewGroup(final String shortName, final String longName, final String publicDesc) {
	SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
	// TODO
	GroupDTO group = new GroupDTO(shortName, longName, publicDesc, null, groupType);
	siteBarService.createNewGroup(group, new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		// TODO
	    }

	    public void onSuccess(final Object arg0) {
		listener.onNewGroupCreated();
		reset();
	    }
	});
    }

    void doCancel() {
	reset();
	listener.onNewGroupCancel();
    }

    public void selectType(final int type) {
	groupType = type;
    }

}
