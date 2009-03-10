package org.ourproject.kune.platf.client.ui.rte.edithtml;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.workspace.client.options.AbstractOptionsPanel;

import com.google.gwt.user.client.Window;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class EditHtmlPanel extends AbstractOptionsPanel implements EditHtmlView {

    public static final String EDIT_HTML_DIALOG = "ehtml-dialog";
    public static final String EDIG_HTML_DIALOG_ERROR_ID = "ehtml-dialgo-error";
    private final EditHtmlGroup editHtmlGroup;
    private final I18nTranslationService i18n;
    private Button cancel;
    private final EditHtmlPresenter presenter;

    public EditHtmlPanel(final EditHtmlPresenter presenter, I18nTranslationService i18n, RTEImgResources resources,
            Images images, EditHtmlGroup editHtmlGroup) {
        super(EDIT_HTML_DIALOG, i18n.tWithNT("Edit the HTML",
                "Option in a content editor to edit the html internal code (for advance users)"),
                Window.getClientWidth() - 100, HEIGHT + 80, Window.getClientWidth() - 100, HEIGHT + 80, true, images,
                EDIG_HTML_DIALOG_ERROR_ID);
        this.presenter = presenter;
        this.i18n = i18n;
        this.editHtmlGroup = editHtmlGroup;
        // super.setIconCls(RTEImgResources.SUFFIX +
        // resources.edithtml().getName());
    }

    @Override
    public void createAndShow() {
        if (cancel == null) {
            cancel = new Button(i18n.t("Cancel"));
            Button update = new Button(i18n.t("Update"));
            cancel.addListener(new ButtonListenerAdapter() {
                @Override
                public void onClick(Button button, EventObject e) {
                    presenter.onCancel();
                }
            });
            update.addListener(new ButtonListenerAdapter() {
                @Override
                public void onClick(Button button, EventObject e) {
                    presenter.onUpdate();
                }
            });
            super.addButton(update);
            super.addButton(cancel);
        }
        editHtmlGroup.createAll();
        super.createAndShow();
        super.setErrorMessage(i18n.t("This option is only for advanced users"), Level.info);
    }
}
