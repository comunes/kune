package org.ourproject.kune.workspace.client.licensewizard.pages;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;

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
    private final Event0 onCommonLicensesSelected;
    private final Event0 onOtherLicensesSelected;
    private final Radio commonLicensesRadio;
    private final Radio otherLicensesRadio;

    public LicenseWizardSndForm(I18nTranslationService i18n) {
        this.onCommonLicensesSelected = new Event0("onCommonLicensesSelected");
        this.onOtherLicensesSelected = new Event0("onOtherLicensesSelected");

        Label intro = new Label();
        intro.setHtml(i18n.t("Select the type of license:") + DefaultFormUtils.brbr());

        final FieldSet fieldSet = new FieldSet(i18n.t("Group type"));
        fieldSet.setStyle("margin-left: 105px");
        fieldSet.setWidth(250);
        commonLicensesRadio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Common licenses"), null,
                COMMON_LICENSES_ID);
        otherLicensesRadio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Other kind of licenses"), null,
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
