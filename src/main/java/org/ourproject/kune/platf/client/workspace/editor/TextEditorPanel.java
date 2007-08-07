package org.ourproject.kune.platf.client.workspace.editor;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TextEditorPanel extends Composite implements TextEditorView {
    private static final String BACKCOLOR_ENABLED = "#FFF";
    private static final String BACKCOLOR_DISABLED = "#CCC";
    private final RichTextArea gwtRTarea;
    private final TextEditorToolbar textEditorToolbar;

    public TextEditorPanel(final TextEditorPresenter panelListener) {

	gwtRTarea = new RichTextArea();
	textEditorToolbar = new TextEditorToolbar(gwtRTarea, panelListener);
	VerticalPanel areaVP = new VerticalPanel();
	areaVP.add(textEditorToolbar);
	areaVP.add(gwtRTarea);
	initWidget(areaVP);

	gwtRTarea.setWidth("100%");
	areaVP.setWidth("100%");
    }

    public void setEnabled(final boolean enabled) {
	String bgColor = enabled ? BACKCOLOR_ENABLED : BACKCOLOR_DISABLED;
	DOM.setStyleAttribute(gwtRTarea.getElement(), "backgroundColor", bgColor);
	gwtRTarea.setEnabled(enabled);
    }

    public String getHTML() {
	return gwtRTarea.getHTML();
    }

    public String getText() {
	return gwtRTarea.getText();
    }

    public void setHTML(final String html) {
	gwtRTarea.setHTML(html);
    }

    public void setText(final String text) {
	gwtRTarea.setText(text);
    }

    public void setHeight(final String height) {
	gwtRTarea.setHeight(height);
    }

    public void setEnabledSaveButton(final boolean enabled) {
	textEditorToolbar.setEnabledSaveButton(enabled);
    }

    public void setEnabledCancelButton(final boolean enabled) {
	textEditorToolbar.setEnabledCancelButton(enabled);
    }

    public void setTextSaveButton(final String text) {
	textEditorToolbar.setTextSaveButton(text);
    }

    public void editHTML(final boolean edit) {
	textEditorToolbar.editHTML(edit);
    }

}
