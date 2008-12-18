package org.ourproject.kune.workspace.client.licensewizard.pages;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
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

public class LicenseWizardSndForm extends DefaultForm implements LicenseWizardSndFormView {
    public static final String COMMON_LICENSES_ID = "k-lwsf-common";
    public static final String OTHER_LICENSES_ID = "k-lwsf-other";
    public static final String RADIO_FIELD_NAME = "k-lwsf-radio";
    private final Event0 onCommonLicensesSelected;
    private final Event0 onOtherLicensesSelected;
    private final Radio commonLicensesRadio;
    private final Radio otherLicensesRadio;

    public LicenseWizardSndForm(I18nTranslationService i18n) {
        this.onCommonLicensesSelected = new Event0("onCommonLicensesSelected");
        this.onOtherLicensesSelected = new Event0("onOtherLicensesSelected");
        setFrame(true);
        super.setPaddings(10);
        super.setHeight(LicenseWizardView.HEIGHT);

        Label intro = new Label();
        intro.setHtml(i18n.t("Select the type of license:") + DefaultFormUtils.brbr());

        final FieldSet fieldSet = new FieldSet("license type");
        fieldSet.setStyle("margin-left: 105px");
        fieldSet.setWidth(250);
        commonLicensesRadio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Common licenses"), RADIO_FIELD_NAME,
                i18n.t("Select a Creative Commons license (recommended for cultural works)"), COMMON_LICENSES_ID);
        otherLicensesRadio = DefaultFormUtils.createRadio(
                fieldSet,
                i18n.t("Other kind of licenses"),
                RADIO_FIELD_NAME,
                i18n.t("Use other kind of licenses like the FSF licenses (recommended for free software works) and other kind of licenses"),
                OTHER_LICENSES_ID);
        commonLicensesRadio.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(Checkbox field, boolean checked) {
                onCommonLicensesSelected.fire();
            }
        });
        otherLicensesRadio.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(Checkbox field, boolean checked) {
                onOtherLicensesSelected.fire();
            }
        });
        add(intro);
        add(commonLicensesRadio);
        add(otherLicensesRadio);
    }

    public void onCommonLicensesSelected(final Listener0 slot) {
        onCommonLicensesSelected.add(slot);
    }

    public void onOtherLicensesSelected(final Listener0 slot) {
        onOtherLicensesSelected.add(slot);
    }

    @Override
    public void reset() {
        super.reset();
        commonLicensesRadio.setChecked(true);
    }
}
