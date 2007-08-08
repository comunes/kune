package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.View;

public interface TextEditor {
    public View getView();

    public void setContent(String content);

    public String getContent();
}
