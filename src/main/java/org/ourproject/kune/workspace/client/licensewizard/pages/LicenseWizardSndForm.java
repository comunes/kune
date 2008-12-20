package org.ourproject.kune.workspace.client.licensewizard.pages;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardView;

import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.Radio;

public class LicenseWizardSndForm extends DefaultForm implements LicenseWizardSndFormView {
    public static final String COMMON_LICENSES_ID = "k-lwsf-common";
    public static final String OTHER_LICENSES_ID = "k-lwsf-other";
    public static final String RADIO_FIELD_NAME = "k-lwsf-radio";
    private final Radio commonLicensesRadio;
    private final Radio otherLicensesRadio;

    public LicenseWizardSndForm(I18nTranslationService i18n) {
        setFrame(true);
        super.setPaddings(10);
        super.setHeight(LicenseWizardView.HEIGHT);

        Label intro = new Label();
        intro.setHtml(i18n.t("Select the license type:") + DefaultFormUtils.brbr());

        final FieldSet fieldSet = new FieldSet("license type");
        fieldSet.setStyle("margin-left: 105px");
        fieldSet.setWidth(250);
        commonLicensesRadio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Common licenses for cultural works"),
                RADIO_FIELD_NAME, i18n.t("Select a Creative Commons license (recommended for cultural works)"),
                COMMON_LICENSES_ID);
        otherLicensesRadio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Other kind of licenses"), RADIO_FIELD_NAME,
                i18n.t("Use the GNU licenses (recommended for free software works) and other kind of licenses"),
                OTHER_LICENSES_ID);
        add(intro);
        add(commonLicensesRadio);
        add(otherLicensesRadio);
    }

    public boolean isCommonLicensesSelected() {
        return commonLicensesRadio.getValue();
    }

    @Override
    public void reset() {
        super.reset();
        commonLicensesRadio.setChecked(true);
    }
}
