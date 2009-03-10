package org.ourproject.kune.platf.client.ui.rte.edithtml.preview;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtml;

public class EditHtmlPreviewPresenter implements EditHtmlPreview {

    private EditHtmlPreviewView view;
    private final EditHtml editHtml;

    public EditHtmlPreviewPresenter(EditHtml editHtml) {
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
