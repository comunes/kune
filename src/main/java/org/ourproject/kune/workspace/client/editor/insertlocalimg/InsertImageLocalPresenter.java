package org.ourproject.kune.workspace.client.editor.insertlocalimg;

import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;

public class InsertImageLocalPresenter implements InsertImageLocal {

    private final InsertImageDialog insertImageDialog;

    public InsertImageLocalPresenter(InsertImageDialog insertImageDialog) {
        this.insertImageDialog = insertImageDialog;
    }

    public void init(InsertImageLocalView view) {
        insertImageDialog.addTab(view);
    }
}
