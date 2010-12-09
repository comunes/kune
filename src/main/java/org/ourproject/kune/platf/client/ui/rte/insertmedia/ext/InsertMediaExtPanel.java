package org.ourproject.kune.platf.client.ui.rte.insertmedia.ext;

import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaRegistry;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPanel;

import cc.kune.core.shared.i18n.I18nTranslationService;

public class InsertMediaExtPanel extends InsertMediaAbstractPanel implements InsertMediaExtView {

    public InsertMediaExtPanel(final InsertMediaExtPresenter presenter, final I18nTranslationService i18n,
            final ExternalMediaRegistry externalMediaRegistry) {
        super(i18n.t("External"), presenter);
        String supportedVideos = externalMediaRegistry.getNames();
        setIntro(i18n.t("Provide a link to an external video (supported videos: [%s])", supportedVideos) + "<br/>");
        hrefField.setTitle(i18n.t("Write something like: http://www.youtube.com/watch?v=PxsGyljd6B0"));
    }

}
