package org.ourproject.kune.platf.client.ui.rte.edithtml.preview;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialog;

public class EditHtmlPreviewPresenter implements EditHtmlPreview {

    private EditHtmlPreviewView view;
    private final EditHtmlDialog editHtml;

    public EditHtmlPreviewPresenter(EditHtmlDialog editHtml) {
        this.editHtml = editHtml;
    }

    public String getHtml() {
        return editHtml.getHtml();
    }

    public View getView() {
        return view;
    }

    public void init(EditHtmlPreviewView view) {
        this.view = view;
        editHtml.addOptionTab(view);
    }
}
