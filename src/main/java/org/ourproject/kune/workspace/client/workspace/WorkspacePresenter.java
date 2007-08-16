package org.ourproject.kune.workspace.client.workspace;

import java.util.Iterator;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.WorkspaceView;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public class WorkspacePresenter implements Workspace {
    private final WorkspaceView view;
    private WorkspaceComponent context;
    private WorkspaceComponent content;

    public WorkspacePresenter(final WorkspaceView view) {
	this.view = view;
    }

    public void showError(final Throwable caught) {

    }

    public void showGroup(final GroupDTO group) {
	view.setLogo("group name here");
    }

    public void attachTools(final Iterator iterator) {
	ClientTool clientTool;
	while (iterator.hasNext()) {
	    clientTool = ((ClientTool) iterator.next());
	    view.addTab(clientTool.getTrigger());
	}
    }

    public void setTool(final String toolName) {
	view.setTool(toolName);
    }

    public void setContext(final WorkspaceComponent contextComponent) {
	if (context != null) {
	    context.detach();
	}
	context = contextComponent;
	context.attach();
	view.setContext(context.getView());
    }

    public void setContent(final WorkspaceComponent contentComponent) {
	if (content != null) {
	    content.detach();
	}
	content = contentComponent;
	content.attach();
	view.setContent(content.getView());
    }

    public void adjustSize(final int windowWidth, final int clientHeight) {
	view.adjustSize(windowWidth, clientHeight);
    }

    public View getView() {
	return view;
    }

}
