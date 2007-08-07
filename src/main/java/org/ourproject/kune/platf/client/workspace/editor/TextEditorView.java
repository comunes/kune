package org.ourproject.kune.platf.client.workspace.editor;

import org.ourproject.kune.platf.client.View;

public interface TextEditorView extends View {
    public void editHTML(boolean edit);
    public String getHTML();
    public String getText();
    public void setEnabled(boolean enabled);
    public void setEnabledCancelButton(boolean enabled);
    public void setEnabledSaveButton(boolean enabled);
    public void setHeight(String height);
    public void setHTML(String html);
    public void setText(String text);
    public void setTextSaveButton(String text);
}