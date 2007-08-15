package org.ourproject.kune.docs.client.ui.forms.newdoc;

import org.ourproject.kune.platf.client.View;

public class NewDocumentFormPresenter implements NewDocumentForm {
    private final NewDocumentFormView view;

    public NewDocumentFormPresenter(final NewDocumentFormView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public String getName() {
	return view.getName();
    }

    public void setCommandLabels(final String acceptLabel, final String cancelLabel) {
	view.setCommandLabels(acceptLabel, cancelLabel);
    }

}
