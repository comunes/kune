package org.ourproject.kune.docs.client.reader;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DocumentReaderPanel extends VerticalPanel implements DocumentReaderView {
    private String title;

    public DocumentReaderPanel() {
        add(new Label("this is the doc panel"));
    }

    public void setContentName(String name) {
        this.title = name;
        clear();
        add(new Label("TÍTULO: " + name));
    }

    public void setContent(String content) {
        clear();
        add(new Label("TÍTULO: " + title));
        add(new Label("CONTENIDO: " + content));
    }

}
