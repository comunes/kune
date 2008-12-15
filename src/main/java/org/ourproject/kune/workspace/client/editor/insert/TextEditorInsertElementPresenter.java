package org.ourproject.kune.workspace.client.editor.insert;

import org.ourproject.kune.workspace.client.options.AbstractOptionsPresenter;

import com.calclab.suco.client.listener.Event2;
import com.calclab.suco.client.listener.Listener2;

public class TextEditorInsertElementPresenter extends AbstractOptionsPresenter implements TextEditorInsertElement {

    private final Event2<String, String> onInsert;

    public TextEditorInsertElementPresenter() {
        this.onInsert = new Event2<String, String>("onInsert");
    }

    public void fireOnInsert(String name, String link) {
        onInsert.fire(name, link);
        super.hide();
    }

    public void init(TextEditorInsertElementView view) {
        super.init(view);
    }

    public void onInsert(final Listener2<String, String> slot) {
        onInsert.add(slot);
    }
}