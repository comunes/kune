
package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.View;

public interface TextEditor {

    public View getView();

    public View getToolBar();

    public void setContent(String content);

    public String getContent();

    public void onSaved();

    public void onSaveFailed();

    public void reset();

}
