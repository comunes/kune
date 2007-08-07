package org.ourproject.kune.docs.client.reader;

import org.ourproject.kune.platf.client.workspace.editor.TextEditorPanel;
import org.ourproject.kune.platf.client.workspace.editor.TextEditorPresenter;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DocumentReaderPanel extends VerticalPanel implements DocumentReaderView {
    private String title;

    public DocumentReaderPanel() {
        add(new Label("this is the doc panel"));
    }

    public void setContentName(final String name) {
        this.title = name;
        clear();
        add(new Label("TÍTULO: " + name));
    }

    public void setContent(final String content) {
        clear();
        add(new Label("TÍTULO: " + title));
        add(new Label("CONTENIDO: " + content));

        // TODO DELETE THIS
        TextEditorPresenter textEditorPresenter = new TextEditorPresenter();
        TextEditorPanel textEditorPanel = new TextEditorPanel(textEditorPresenter);
        textEditorPresenter.init("Prueba de edición desde DocumentReaderPanel", textEditorPanel, true);
        add(textEditorPanel);
    }

}
