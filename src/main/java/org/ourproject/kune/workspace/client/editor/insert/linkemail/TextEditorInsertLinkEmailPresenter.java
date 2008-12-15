package org.ourproject.kune.workspace.client.editor.insert.linkemail;

import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;
import org.ourproject.kune.workspace.client.editor.insert.abstractlink.TextEditorInsertAbstractPresenter;

public class TextEditorInsertLinkEmailPresenter extends TextEditorInsertAbstractPresenter implements
        TextEditorInsertLinkEmail {

    public TextEditorInsertLinkEmailPresenter(TextEditorInsertElement editorInsertElement) {
        super(editorInsertElement);
    }

    public void init(TextEditorInsertLinkEmailView view) {
        super.init(view);
    }
}
