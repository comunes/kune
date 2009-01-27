package org.ourproject.kune.workspace.client.editor.insert;

import org.ourproject.kune.workspace.client.options.AbstractOptionsPresenter;

import com.calclab.suco.client.events.Event2;
import com.calclab.suco.client.events.Listener2;

public class TextEditorInsertElementPresenter extends AbstractOptionsPresenter implements TextEditorInsertElement {

    private final Event2<String, String> onCreateLink;

    public TextEditorInsertElementPresenter() {
        this.onCreateLink = new Event2<String, String>("onCreateLink");
    }

    public void addOnCreateLink(final Listener2<String, String> slot) {
        onCreateLink.add(slot);
    }

    public void fireOnCreateLink(String name, String link) {
        onCreateLink.fire(name, link);
        super.hide();
    }

    public void fireOnInsertHtml(String name, String link) {
        onCreateLink.fire(name, link);
        super.hide();
    }

    public void init(TextEditorInsertElementView view) {
        super.init(view);
    }
}