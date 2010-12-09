package org.ourproject.kune.workspace.client.editor.insertlocalimg;

import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstractPresenter;

import cc.kune.core.client.state.Session;

public class InsertImageLocalPresenter extends InsertImageAbstractPresenter implements InsertImageLocal {

    private final Session session;

    public InsertImageLocalPresenter(final InsertImageDialog insertImageDialog, final Session session) {
        super(insertImageDialog);
        this.session = session;
    }

    public String getCurrentGroupName() {
        return session.getCurrentGroupShortName();
    }

}
