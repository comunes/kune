package cc.kune.core.client.sitebar;

import cc.kune.core.client.ui.dialogs.BasicTopDialog;
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

    public static final String SITE_OPTIONS_MENU = "kune-sop-om";
    private final BasicTopDialog dialog;
    @UiField
    FlowPanel flow;
    @UiField
    Frame frame;

    @Inject
    public AboutKuneDialog(final I18nTranslationService i18n) {
        dialog = new BasicTopDialog(ABOUT_KUNE_ID, i18n.t("About Kune"), true, true, false, 400, 400, "", i18n.t("Ok"),
                ABOUT_KUNE_BTN_ID, 1);
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
