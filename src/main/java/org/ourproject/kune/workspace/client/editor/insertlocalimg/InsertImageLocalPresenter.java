package org.ourproject.kune.workspace.client.editor.insertlocalimg;

import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImage;

public class InsertImageLocalPresenter implements InsertImageLocal {

    private final InsertImage insertImageDialog;

    public InsertImageLocalPresenter(InsertImage insertImageDialog) {
        this.insertImageDialog = insertImageDialog;
    }

    public void init(InsertImageLocalView view) {
        insertImageDialog.addOptionTab(view);
    }
}
