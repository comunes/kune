package org.ourproject.kune.workspace.client.editor.insertlocalmedia;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.MediaUtils;

import com.calclab.suco.client.ioc.Provider;

public class InsertMediaLocalPresenter extends InsertMediaAbstractPresenter implements InsertMediaLocal {

    private final Session session;
    private final Provider<MediaUtils> mediaUtils;

    public InsertMediaLocalPresenter(final InsertMediaDialog insertMediaDialog, final Session session,
            final Provider<MediaUtils> mediaUtils) {
        super(insertMediaDialog);
        this.session = session;
        this.mediaUtils = mediaUtils;
    }

    public String getCurrentGroupName() {
        return session.getCurrentGroupShortName();
    }

    public void init(final InsertMediaLocalView view) {
        super.init(view);
    }

    @Override
    protected String updateMediaInfo() {
        // FIXME (avi, mp3, ...)
        return mediaUtils.get().getFlvEmbed(new StateToken(view.getSrc()));
    }
}
