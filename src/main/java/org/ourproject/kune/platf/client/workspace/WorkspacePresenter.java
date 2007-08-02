package org.ourproject.kune.platf.client.workspace;

import java.util.Iterator;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.user.client.ui.Widget;

public class WorkspacePresenter implements Workspace {
    private final WorkspaceView view;
    private WorkspaceComponent context;
    private WorkspaceComponent content;

    public WorkspacePresenter(WorkspaceView view) {
	this.view = view;
    }

    public void showError(Throwable caught) {

    }

    public void setGroup(GroupDTO group) {
	view.setLogo("group name here");
    }

    public void attachTools(Iterator iterator) {
	Tool tool;
	while (iterator.hasNext()) {
	    tool = ((Tool) iterator.next());
	    view.addTab(tool.getName(), tool.getCaption());
	}
    }

    public void setTool(String toolName) {
	view.setTool(toolName);
    }


    public void setContext(WorkspaceComponent contextComponent) {
	if (context != null) {
	    context.detach();
	}
	context = contextComponent;
	context.attach();
	view.setContextMenu((Widget) context.getView());
    }

    public void setContent(WorkspaceComponent contentComponent) {
	if (content != null) {
	    content.detach();
	}
	content = contentComponent;
	context.attach();
	view.setContent((Widget) content.getView());
    }

}
