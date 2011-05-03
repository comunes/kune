package cc.kune.core.client.sitebar;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.core.client.notify.msgs.UserNotifyEvent;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.msgs.client.resources.UserMessageImagesUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ErrorsDialog {

    interface Binder extends UiBinder<Widget, ErrorsDialog> {
    }
    private static final Binder BINDER = GWT.create(Binder.class);
    public static final String ERROR_LOGGER_BUTTON_ID = "kune-error-button-diag";
    public static final String ERROR_LOGGER_ID = "kune-error-diag";

    private final BasicTopDialog dialog;
    @UiField
    VerticalPanel panel;

    @Inject
    public ErrorsDialog(final I18nTranslationService i18n, final EventBus eventBus) {
        dialog = new BasicTopDialog.Builder(ERROR_LOGGER_ID, true, true).title(i18n.t("Errors info")).autoscroll(true).firstButtonTitle(
                i18n.t("Ok")).firstButtonId(ERROR_LOGGER_BUTTON_ID).tabIndexStart(1).height("300px").build();
        dialog.getTitleText().setText(i18n.t("Info about errors"));
        final InlineLabel subTitle = new InlineLabel(i18n.t("Please copy/paste this info to report problems"));
        dialog.getInnerPanel().add(subTitle);
        dialog.getInnerPanel().add(BINDER.createAndBindUi(this));
        dialog.getFirstBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                dialog.hide();
            }
        });
        eventBus.addHandler(UserNotifyEvent.getType(), new UserNotifyEvent.UserNotifyHandler() {
            @Override
            public void onUserNotify(final UserNotifyEvent event) {
                final NotifyLevel level = event.getLevel();
                final IconLabel iconMessage = new IconLabel();
                iconMessage.setLeftIconResource(UserMessageImagesUtil.getIcon(level));
                iconMessage.setText(event.getMessage());
                iconMessage.setWordWrap(true);
                panel.insert(iconMessage, 0);
            }
        });
    }

    public void showCentered() {
        dialog.showCentered();
    }

}
