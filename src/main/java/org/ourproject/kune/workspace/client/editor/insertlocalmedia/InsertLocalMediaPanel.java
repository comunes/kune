package org.ourproject.kune.workspace.client.editor.insertlocalmedia;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPanel;

public class InsertLocalMediaPanel extends InsertMediaAbstractPanel implements InsertLocalMediaView {

    public InsertLocalMediaPanel(final InsertLocalMediaPresenter presenter, final I18nTranslationService i18n) {
        super(i18n.t("Local"), presenter);
        setIntro(TextUtils.IN_DEVELOPMENT + "<br/><br/>");
    }

}
