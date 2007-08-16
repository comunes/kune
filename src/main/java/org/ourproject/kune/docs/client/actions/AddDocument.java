package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class AddDocument extends AbstractAddAction {
    public static final String KEY = "docs.addDocument";

    public void execute(final Object value, Object extra) {
	showNewDocDialog((ContainerDTO) value, "create new document");
    }

    protected void add() {
	// i18n
	Site.showProgress("adding document");
	ContentServiceAsync server = ContentService.App.getInstance();
	String name = form.getName();
	server.addContent(user, containerDTO.getId(), name, new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
	    }

	    public void onSuccess(final Object result) {
		StateDTO content = (StateDTO) result;
		stateManager.setState(content);
	    }
	});
    }

}
