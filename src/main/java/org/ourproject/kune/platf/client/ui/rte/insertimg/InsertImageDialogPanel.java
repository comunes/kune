package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.options.AbstractOptionsPanel;

public class InsertImageDialogPanel extends AbstractOptionsPanel implements InsertImageDialogView {

    private static final String INSERT_IMG_DIALOG = "iip-dial";
    private static final String INSERT_IMG_DIALOG_ERROR_ID = "iip-err";
    private final InsertImageGroup insertImageGroup;

    public InsertImageDialogPanel(final InsertImageDialogPresenter presenter, I18nTranslationService i18n, Images images,
            InsertImageGroup insertImageGroup) {
        super(INSERT_IMG_DIALOG, i18n.t("Insert an image"), 380, HEIGHT + 90, 380, HEIGHT + 90, true, images,
                INSERT_IMG_DIALOG_ERROR_ID);
        super.setIconCls("k-picture-icon");
        this.insertImageGroup = insertImageGroup;
    }

    @Override
    public void createAndShow() {
        insertImageGroup.createAll();
        super.createAndShow();
    }
}
