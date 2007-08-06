package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.docs.client.DocumentTool;
import org.ourproject.kune.docs.client.editor.DocumentEditor;
import org.ourproject.kune.platf.client.workspace.actions.WorkspaceAction;
import org.ourproject.kune.platf.client.workspace.admin.AjoAdmin;

public class EditAction extends WorkspaceAction {
    public static final String NAME = "doc-edit";

    public void execute(final Object value) {
	DocumentTool docTool = (DocumentTool) app.getTool(DocumentTool.NAME);
	DocumentEditor editor = docTool.getEditor();
	AjoAdmin admin = docTool.getAdmin();
	workspace.setContent(editor);
	workspace.setContext(admin);
    }
}
