package org.ourproject.kune.platf.client.ui.rte.insertimg.ext;

import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstractPresenter;

public class InsertImageExtPresenter extends InsertImageAbstractPresenter implements InsertImageExt {

    private InsertImageExtView view;

    public InsertImageExtPresenter(InsertImageDialog insertImageDialog) {
        super(insertImageDialog);
    }

    public void init(InsertImageExtView view) {
        super.init(view);
        this.view = view;
    }

    public void onPreview() {
        if (isValid()) {
            view.setPreviewUrl(view.getSrc());
        }
    }

}
