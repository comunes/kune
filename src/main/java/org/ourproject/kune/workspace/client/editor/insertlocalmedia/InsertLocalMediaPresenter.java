package org.ourproject.kune.workspace.client.editor.insertlocalmedia;

import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPresenter;

public class InsertLocalMediaPresenter extends InsertMediaAbstractPresenter implements InsertLocalMedia {

    private InsertLocalMediaView view;

    public InsertLocalMediaPresenter(InsertMediaDialog insertMediaDialog) {
        super(insertMediaDialog);
    }

    public void init(InsertLocalMediaView view) {
        super.init(view);
        this.view = view;
    }
}
