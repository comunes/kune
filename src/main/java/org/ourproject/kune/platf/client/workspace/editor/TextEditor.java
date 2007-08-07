package org.ourproject.kune.platf.client.workspace.editor;

import org.ourproject.kune.platf.client.View;

public interface TextEditor {
    public View getView();

    public void setContent(String content);
}
