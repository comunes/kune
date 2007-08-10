package org.ourproject.kune.workspace.client;

import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;
import org.ourproject.kune.workspace.client.editor.TextEditorPanel;
import org.ourproject.kune.workspace.client.editor.TextEditorPresenter;
import org.ourproject.kune.workspace.client.workspace.WorkspacePanel;
import org.ourproject.kune.workspace.client.workspace.WorkspacePresenter;

public class WorkspaceFactory {
    private static Workspace workspace;

    public static Workspace getWorkspace() {
	if (workspace == null) {
	    WorkspaceView view = new WorkspacePanel();
	    workspace = new WorkspacePresenter(view);
	}
	return workspace;
    }

    public static TextEditor createDocumentEditor(final TextEditorListener listener) {
        TextEditorPresenter presenter = new TextEditorPresenter(listener, true);
        TextEditorPanel panel = new TextEditorPanel(presenter);
        presenter.init(panel);
        return presenter;
    }
}
