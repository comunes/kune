package org.ourproject.kune.workspace.client.licensewizard.pages;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardView;

import com.calclab.suco.client.listener.Event0;
import com.calclab.suco.client.listener.Listener0;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;

public class LicenseWizardFirstForm extends DefaultForm implements LicenseWizardFirstFormView {
    private static final String POINT = "»&nbsp;";
    public static final String RADIO_COPYLEFT_ID = "k-lwff-copyleft";
    public static final String RADIO_ANOTHER_ID = "k-lwff-another";
    private static final String RADIO_FIELD_NAME = "k-lwff-radio";
    private final Event0 onCopyLeftLicenseSelected;
    private final Event0 onAnotherLicenseSelected;
    private final Radio copyleftRadio;
    private final Radio anotherLicenseRadio;

    public LicenseWizardFirstForm(I18nTranslationService i18n) {
        this.onCopyLeftLicenseSelected = new Event0("onCopyLeftLicenseSelected");
        this.onAnotherLicenseSelected = new Event0("onAnotherLicenseSelected");
        super.setFrame(true);
        super.setPaddings(10);
        super.setHeight(LicenseWizardView.HEIGHT);

        Label intro = new Label();
        intro.setHtml(i18n.t("Select a license to share your group contents with other people:")
                + DefaultFormUtils.brbr());

        final FieldSet fieldSet = new FieldSet("license recommended");
        fieldSet.setStyle("margin-left: 105px");
        fieldSet.setWidth(250);
        copyleftRadio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Use a copyleft license (recommended)"),
                RADIO_FIELD_NAME, null, RADIO_COPYLEFT_ID);
        anotherLicenseRadio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Use another kind of license (advanced)"),
                RADIO_FIELD_NAME, null, RADIO_ANOTHER_ID);
        copyleftRadio.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(Checkbox field, boolean checked) {
                if (checked) {
                    onCopyLeftLicenseSelected.fire();
                }
            }
        });
        anotherLicenseRadio.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(Checkbox field, boolean checked) {
                if (checked) {
                    onAnotherLicenseSelected.fire();
                }
            }
        });

        FieldSet infoFS = new FieldSet("Info");
        infoFS.setFrame(false);
        infoFS.setIconCls("k-info-icon");
        infoFS.setCollapsible(false);
        infoFS.setAutoHeight(true);

        Label recommendCopyleft = new Label();
        Label whyALicense = new Label();
        Label youCanChangeTheLicenseLater = new Label();
        recommendCopyleft.setHtml(POINT
                + i18n.t("We recommend [%s] licenses, specially for practical works", TextUtils.generateHtmlLink(
                        "http://en.wikipedia.org/wiki/Copyleft", i18n.t("copyleft"))) + DefaultFormUtils.br());
        whyALicense.setHtml(POINT
                + TextUtils.generateHtmlLink("http://mirrors.creativecommons.org/getcreative/",
                        i18n.t("Why do we need a license?")) + DefaultFormUtils.br());
        youCanChangeTheLicenseLater.setHtml(POINT + i18n.t("You can change this license later") + DefaultFormUtils.br());

        infoFS.addStyleName("kune-Margin-20-t");
        add(intro);
        add(copyleftRadio);
        add(anotherLicenseRadio);
        infoFS.add(recommendCopyleft);
        infoFS.add(whyALicense);
        infoFS.add(youCanChangeTheLicenseLater);
        add(infoFS);
    }

    public void onAnotherLicenseSelected(final Listener0 slot) {
        onAnotherLicenseSelected.add(slot);
    }

    public void onCopyLeftLicenseSelected(final Listener0 slot) {
        onCopyLeftLicenseSelected.add(slot);
    }

    @Override
    public void reset() {
        super.reset();
        copyleftRadio.setChecked(true);
    }
}
