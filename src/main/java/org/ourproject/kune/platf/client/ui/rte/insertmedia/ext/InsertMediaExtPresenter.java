package org.ourproject.kune.platf.client.ui.rte.insertmedia.ext;

import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaDescriptor;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaRegistry;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPresenter;

public class InsertMediaExtPresenter extends InsertMediaAbstractPresenter implements InsertMediaExt {

    private InsertMediaExtView view;
    private final ExternalMediaRegistry externalMediaRegistry;
    private ExternalMediaDescriptor mediaDescriptor;

    public InsertMediaExtPresenter(final InsertMediaDialog insertMediaDialog,
            final ExternalMediaRegistry externalMediaRegistry) {
        super(insertMediaDialog);
        this.externalMediaRegistry = externalMediaRegistry;

        externalMediaRegistry.add(new ExternalMediaDescriptor("youtube", "http://youtube.com",
                "http://[a-z]*.youtube.com/watch?v=.*", "FIXME", "<embed [%d]>"));
    }

    public void init(final InsertMediaExtView view) {
        super.init(view);
        this.view = view;
    }

    @Override
    public boolean isValid() {
        String url = view.getSrc();
        mediaDescriptor = externalMediaRegistry.get(url);
        if (mediaDescriptor.equals(ExternalMediaRegistry.NO_MEDIA)) {
            insertMediaDialog.setErrorMessage("We cannot process this video link", Level.error);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected String updateMediaInfo() {
        return mediaDescriptor.getEmbed(view.getSrc());
    }
}
