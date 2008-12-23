package org.ourproject.kune.workspace.client.editor.insert.abstractlink;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;

public class TextEditorInsertAbstractPresenter {

    private TextEditorInsertAbstractView view;
    private final TextEditorInsertElement editorInsertElement;

    public TextEditorInsertAbstractPresenter(TextEditorInsertElement editorInsertElement) {
        this.editorInsertElement = editorInsertElement;
    }

    public View getView() {
        return view;
    }

    public void init(TextEditorInsertAbstractView view) {
        this.view = view;
        editorInsertElement.addOptionTab(view);
    }

    public void onInsert(String name, String link) {
        editorInsertElement.fireOnInsert(name, link);
        view.reset();
    }
}
