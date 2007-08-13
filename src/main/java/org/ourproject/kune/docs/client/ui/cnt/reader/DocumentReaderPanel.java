package org.ourproject.kune.docs.client.ui.cnt.reader;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DocumentReaderPanel extends VerticalPanel implements DocumentReaderView {
    private Button btnEdit;
    private final HTML content;

    public DocumentReaderPanel() {
	add(createToolBar());
	btnEdit.setVisible(false);
	content = new HTML();
	add(content);
    }

    private Widget createToolBar() {
	FlowPanel panel = new FlowPanel();
	btnEdit = new Button("editar", new ClickListener() {
	    public void onClick(final Widget sender) {
	    }
	});
	panel.add(btnEdit);
	return panel;
    }

    public void setContent(final String text) {
	content.setHTML(text);
    }

    public void setEditEnabled(final boolean isEnabled) {
	btnEdit.setVisible(isEnabled);
    }

    public String getContent() {
	return content.getHTML();
    }

}
