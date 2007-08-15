package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.forms.create.NewDocumentForm;
import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.ui.form.FormListener;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractAddAction extends WorkspaceAction {
    private DialogBox dialog;
    protected FolderDTO folderDTO;
    protected NewDocumentForm form;

    protected void showNewDocDialog(final FolderDTO folderDTO, final String title) {
	this.folderDTO = folderDTO;
	if (dialog == null) {
	    createDialog();
	}
	dialog.setTitle(title);
	dialog.show();
	dialog.center();
    }

    protected void createDialog() {
	dialog = new DialogBox();
	form = DocumentFactory.createNewDocumentForm(new FormListener() {
	    public void onAccept() {
		add();
		dialog.hide();
	    }

	    public void onCancel() {
		dialog.hide();
	    }
	});
	form.setCommandLabels("aceptar", "cancelar");
	dialog.setWidget((Widget) form.getView());
    }

    protected abstract void add();
}
