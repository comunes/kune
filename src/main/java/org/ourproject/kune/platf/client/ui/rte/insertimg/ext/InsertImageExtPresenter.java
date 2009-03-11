package org.ourproject.kune.platf.client.ui.rte.insertimg.ext;

import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImage;

public class InsertImageExtPresenter implements InsertImageExt {

    private final InsertImage insertImageDialog;

    public InsertImageExtPresenter(InsertImage insertImageDialog) {
        this.insertImageDialog = insertImageDialog;
    }

    public void init(InsertImageExtView view) {
        insertImageDialog.addOptionTab(view);
    }

    public void onInsert() {
        // TODO Auto-generated method stub

    }

    public void onPreview() {
        // TODO Auto-generated method stub

    }
}
