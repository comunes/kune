package cc.kune.core.client.sitebar;

import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AboutKuneDialog {

    interface Binder extends UiBinder<Widget, AboutKuneDialog> {
    }
    public static final String ABOUT_KUNE_BTN_ID = "kune-about-button-diag";
    public static final String ABOUT_KUNE_ID = "kune-about-diag";
    private static final Binder BINDER = GWT.create(Binder.class);
    private final BasicTopDialog dialog;
    @UiField
    FlowPanel flow;
    @UiField
    Frame frame;

    @Inject
    public AboutKuneDialog(final I18nTranslationService i18n) {
        dialog = new BasicTopDialog.Builder(ABOUT_KUNE_ID, true, true).title(i18n.t("About Kune")).autoscroll(false).firstButtonTitle(
                i18n.t("Ok")).firstButtonId(ABOUT_KUNE_BTN_ID).tabIndexStart(1).height("70%").build();
        dialog.getInnerPanel().add(BINDER.createAndBindUi(this));
        dialog.getFirstBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                dialog.hide();
            }
        });
    }

    public void showCentered() {
        dialog.showCentered();
    }

}
