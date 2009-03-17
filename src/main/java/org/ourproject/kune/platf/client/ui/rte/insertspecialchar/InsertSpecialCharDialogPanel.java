package org.ourproject.kune.platf.client.ui.rte.insertspecialchar;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class InsertSpecialCharDialogPanel extends AbstractTabbedDialogPanel implements InsertSpecialCharDialogView {

    private static final String INSERT_SPECIAL_CHAR_DIALOG = "iscdp-dial";
    private static final String INSERT_SPECIAL_CHAR_DIALOG_ERROR_ID = "iscdp-err";
    private final InsertSpecialCharGroup insertSpecialCharGroup;

    public InsertSpecialCharDialogPanel(final InsertSpecialCharDialogPresenter presenter, Images images,
            I18nTranslationService i18n, InsertSpecialCharGroup insertSpecialCharGroup) {
        super(INSERT_SPECIAL_CHAR_DIALOG, i18n.t("Insert Special Character"), 500, HEIGHT + 90, 500, HEIGHT + 90, true,
                images, INSERT_SPECIAL_CHAR_DIALOG_ERROR_ID);
        // super.setIconCls("k-picture-icon");
        this.insertSpecialCharGroup = insertSpecialCharGroup;
        Button close = new Button(i18n.t("Close"));
        close.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                hide();
            }
        });
        addButton(close);
    }

    @Override
    public void createAndShow() {
        insertSpecialCharGroup.createAll();
        super.createAndShow();
    }
}
