package org.ourproject.kune.workspace.client.editor.insert;

import org.ourproject.kune.workspace.client.options.AbstractOptions;

import com.calclab.suco.client.events.Listener2;

public interface TextEditorInsertElement extends AbstractOptions {

    void addOnCreateLink(Listener2<String, String> slot);

    void fireOnCreateLink(String name, String link);

}
