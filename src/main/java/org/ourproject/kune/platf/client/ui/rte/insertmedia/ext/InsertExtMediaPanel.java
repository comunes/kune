package org.ourproject.kune.platf.client.ui.rte.insertmedia.ext;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaRegistry;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPanel;

public class InsertExtMediaPanel extends InsertMediaAbstractPanel implements InsertExtMediaView {

    public InsertExtMediaPanel(final InsertExtMediaPresenter presenter, final I18nTranslationService i18n,
            final ExternalMediaRegistry externalMediaRegistry) {
        super(i18n.t("External"), presenter);
        String supportedVideos = externalMediaRegistry.getNames();
        setIntro(i18n.t("Provide a link to the external video (supported videos: [%s])", supportedVideos) + "<br/>");
    }

}
