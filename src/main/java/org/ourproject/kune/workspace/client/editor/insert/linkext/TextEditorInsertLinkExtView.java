package org.ourproject.kune.workspace.client.editor.insert.linkext;

import org.ourproject.kune.workspace.client.editor.insert.abstractlink.TextEditorInsertAbstractView;

public interface TextEditorInsertLinkExtView extends TextEditorInsertAbstractView {

    public String getUrl();

    public boolean isValid();

    public void setPreviewUrl(String url);

    public void setUrl(String url);
}
