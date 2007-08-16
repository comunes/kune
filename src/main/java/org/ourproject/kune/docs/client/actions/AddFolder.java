package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class AddFolder extends WorkspaceAction {
    public static final String EVENT = "docs.AddFolder";
    public static final String KEY = "docs.AddFolder";

    public void execute(final Object value, final Object extra) {
	String name = (String) value;
	GroupDTO group = getState().getGroup();
	ContainerDTO container = getState().getFolder();
	addDocument(name, group, container);
    }

    private void addDocument(final String name, final GroupDTO group, final ContainerDTO container) {
	Site.showProgress("adding document");
	ContentServiceAsync server = ContentService.App.getInstance();
	server.addFolder(user, group.getShortName(), container.getId(), name, new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
	    }

	    public void onSuccess(final Object result) {
		StateDTO content = (StateDTO) result;
		stateManager.setState(content);
	    }
	});
    }

    public String getActionName() {
	return KEY;
    }

    public String getEventName() {
	return EVENT;
    }
}
