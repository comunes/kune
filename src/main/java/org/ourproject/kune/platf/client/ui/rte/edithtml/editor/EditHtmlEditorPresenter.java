package org.ourproject.kune.platf.client.ui.rte.edithtml.editor;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlAgent;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialog;

public class EditHtmlEditorPresenter implements EditHtmlEditor, EditHtmlAgent {

    private EditHtmlEditorView view;
    private final EditHtmlDialog editHtml;

    public EditHtmlEditorPresenter(EditHtmlDialog editHtml) {
        this.editHtml = editHtml;
        editHtml.setAgent(this);
    }

    public String getHtml() {
        return view.getHtml();
    }

    public View getView() {
        return view;
    }

    public void init(EditHtmlEditorView view) {
        this.view = view;
        editHtml.addTab(view);
    }

    public void setHtml(String html) {
        view.setHtml(html);
    }
}
