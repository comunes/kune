package org.ourproject.kune.platf.client.ui.rte.insertimg.ext;

import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;

public class InsertImageExtPresenter implements InsertImageExt {

    private final InsertImageDialog insertImageDialog;

    public InsertImageExtPresenter(InsertImageDialog insertImageDialog) {
        this.insertImageDialog = insertImageDialog;
    }

    public void init(InsertImageExtView view) {
        insertImageDialog.addTab(view);
    }

    public void onInsert() {
        // TODO Auto-generated method stub

    }

    public void onPreview() {
        // TODO Auto-generated method stub

    }
}
