package org.ourproject.kune.workspace.client.editor.insertlocalimg;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstractPanel;

public class InsertImageLocalPanel extends InsertImageAbstractPanel implements InsertImageLocalView {

    public InsertImageLocalPanel(final InsertImageLocalPresenter presenter, I18nTranslationService i18n) {
        super(i18n.t("Local image"), presenter);
    }

}
