package org.ourproject.kune.docs.client.ui.forms.newdoc;

import org.ourproject.kune.workspace.client.ui.form.AbstractFormPanel;
import org.ourproject.kune.workspace.client.ui.form.FormListener;

import com.google.gwt.user.client.ui.TextBox;

public class NewDocumentFormPanel extends AbstractFormPanel implements NewDocumentFormView {
    private final TextBox fieldName;

    public NewDocumentFormPanel(final FormListener listener) {
	super(listener);
	fieldName = new TextBox();
	addRow("nombre:", fieldName);
    }

    public String getName() {
	return fieldName.getText();
    }
}
