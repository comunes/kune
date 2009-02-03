package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;

public interface RTEditorView extends View {

    void addComment(String userName);

    boolean canBeExtended();

    void insertHR();

    void redo();

    void selectAll();

    void undo();
}
