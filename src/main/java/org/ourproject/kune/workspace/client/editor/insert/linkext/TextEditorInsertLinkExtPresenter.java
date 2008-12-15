package org.ourproject.kune.workspace.client.editor.insert.linkext;

import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;
import org.ourproject.kune.workspace.client.editor.insert.abstractlink.TextEditorInsertAbstractPresenter;

public class TextEditorInsertLinkExtPresenter extends TextEditorInsertAbstractPresenter implements
        TextEditorInsertLinkExt {

    public TextEditorInsertLinkExtPresenter(TextEditorInsertElement editorInsertElement) {
        super(editorInsertElement);
    }

    public void init(TextEditorInsertLinkExtView view) {
        super.init(view);
    }
}
