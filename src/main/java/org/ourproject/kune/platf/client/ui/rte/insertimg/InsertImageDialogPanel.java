package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;

import cc.kune.common.client.noti.NotifyLevelImages;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class InsertImageDialogPanel extends AbstractTabbedDialogPanel implements InsertImageDialogView {

    private static final String INSERT_IMG_DIALOG = "k-iip-dial";
    private static final String INSERT_IMG_DIALOG_ERROR_ID = "k-iip-err";
    private final InsertImageGroup insertImageGroup;

    public InsertImageDialogPanel(final InsertImageDialogPresenter presenter, final I18nTranslationService i18n,
            final NotifyLevelImages images, final InsertImageGroup insertImageGroup) {
        super(INSERT_IMG_DIALOG, i18n.t("Insert Image"), 390, HEIGHT + 100, 390, HEIGHT + 100, true, images,
                INSERT_IMG_DIALOG_ERROR_ID);
        super.setIconCls("k-picture-icon");
        this.insertImageGroup = insertImageGroup;

        super.addHideListener(new Listener0() {
            @Override
            public void onEvent() {
                insertImageGroup.resetAll();
            }
        });

        final Button insert = new Button(i18n.t("Insert"));
        insert.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                presenter.onInsert();
            }
        });

        final Button cancel = new Button(i18n.t("Cancel"));
        cancel.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                presenter.onCancel();
            }
        });
        addButton(insert);
        addButton(cancel);
    }

    @Override
    public void createAndShow() {
        insertImageGroup.createAll();
        super.createAndShow();
    }
}
