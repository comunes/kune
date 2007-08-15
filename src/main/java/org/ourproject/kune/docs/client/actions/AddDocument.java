package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.forms.newdoc.NewDocumentForm;
import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.ui.form.FormListener;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class AddDocument extends WorkspaceAction {
    public static final String KEY = "docs.addDocument";
    private NewDocumentForm form;
    private DialogBox dialog;
    private FolderDTO folderDTO;

    public void execute(final Object value) {
	showNewDocDialog((FolderDTO) value);
    }

    protected void addDocument() {
	ContentServiceAsync server = ContentService.App.getInstance();
	String name = form.getName();
	server.addContent(user, folderDTO.getId(), name, new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
	    }

	    public void onSuccess(final Object result) {
	    }
	});
    }

    private void showNewDocDialog(final FolderDTO folderDTO) {
	this.folderDTO = folderDTO;
	if (dialog == null) {
	    createDialog();
	}
	dialog.show();
	dialog.center();
    }

    private void createDialog() {
	dialog = new DialogBox();
	form = DocumentFactory.createNewDocumentForm(new FormListener() {
	    public void onAccept() {
		addDocument();
		dialog.hide();
	    }

	    public void onCancel() {
		dialog.hide();
	    }
	});
	form.setCommandLabels("aceptar", "cancelar");
	dialog.setWidget((Widget) form.getView());
    }

}
