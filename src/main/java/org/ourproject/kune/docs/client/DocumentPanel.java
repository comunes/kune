package org.ourproject.kune.docs.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DocumentPanel extends VerticalPanel implements DocumentView {
    private String title;

    public DocumentPanel() {
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

    public void setWaiting() {
        add(new Label("conectando con el servidor..."));
    }

}
