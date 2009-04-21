package org.ourproject.kune.workspace.client.editor.insertlocalmedia;

import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPresenter;

public class InsertMediaLocalPresenter extends InsertMediaAbstractPresenter implements InsertMediaLocal {

    private final Session session;

    public InsertMediaLocalPresenter(final InsertMediaDialog insertMediaDialog, final Session session) {
        super(insertMediaDialog);
        this.session = session;
    }

    public String getCurrentGroupName() {
        return session.getCurrentGroupShortName();
    }

    public void init(final InsertMediaLocalView view) {
        super.init(view);
    }
}
