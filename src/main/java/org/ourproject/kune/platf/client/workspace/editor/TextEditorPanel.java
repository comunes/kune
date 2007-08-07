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

    private final VerticalPanel areaVP;

    private final TextEditorPresenter presenter;

    public TextEditorPanel(final TextEditorPresenter initPresenter) {
        this.presenter = initPresenter;

        gwtRTarea = new RichTextArea();
        textEditorToolbar = new TextEditorToolbar(gwtRTarea, presenter);

        areaVP = new VerticalPanel();
        areaVP.add(textEditorToolbar);
        areaVP.add(gwtRTarea);
        initWidget(areaVP);

        // area.setHeight("20em");
        gwtRTarea.setHeight("100%");
        gwtRTarea.setWidth("100%");
        areaVP.setWidth("100%");
    }

    public void setEnabled(final boolean enabled) {
        if (enabled) {
            DOM.setStyleAttribute(gwtRTarea.getElement(), "backgroundColor", BACKCOLOR_ENABLED);
        } else {
            DOM.setStyleAttribute(gwtRTarea.getElement(), "backgroundColor", BACKCOLOR_DISABLED);
        }
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
