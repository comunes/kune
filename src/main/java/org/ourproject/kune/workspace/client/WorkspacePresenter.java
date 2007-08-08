package org.ourproject.kune.workspace.client;

import java.util.Iterator;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dto.GroupDTO;

public class WorkspacePresenter implements Workspace {
    private final WorkspaceView view;
    private WorkspaceComponent context;
    private WorkspaceComponent content;

    public WorkspacePresenter(final WorkspaceView view) {
	this.view = view;
    }

    public void showError(final Throwable caught) {

    }

    public void setGroup(final GroupDTO group) {
	view.setLogo("group name here");
    }

    public void attachTools(final Iterator iterator) {
	Tool tool;
	while (iterator.hasNext()) {
	    tool = ((Tool) iterator.next());
	    view.addTab(tool.getName(), tool.getCaption());
	}
    }

    public void setTool(final String toolName) {
	view.setTool(toolName);
    }

    public void setContext(final WorkspaceComponent contextComponent) {
	if (context != null) {
	    context.detach();
	} else if (context != contextComponent) {
	    context = contextComponent;
	    context.attach();
	    view.setContext(context.getView());
	}
    }

    public void setContent(final WorkspaceComponent contentComponent) {
	if (content != null) {
	    content.detach();
	}
	content = contentComponent;
	content.attach();
	view.setContent(content.getView());
    }

}
