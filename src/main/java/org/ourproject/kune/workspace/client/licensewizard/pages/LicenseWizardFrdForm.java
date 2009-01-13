package org.ourproject.kune.workspace.client.licensewizard.pages;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardView;

import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.core.Template;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;

public class LicenseWizardFrdForm extends DefaultForm implements LicenseWizardFrdFormView {
    public static final String COMMON_LICENSES_ID = "k-lwsf-common";
    public static final String OTHER_LICENSES_ID = "k-lwsf-other";
    public static final String RADIO_FIELD_NAME = "k-lwsf-radio";
    private final ComboBox cb;
    private final Event0 onChange;
    private final Session session;

    public LicenseWizardFrdForm(I18nTranslationService i18n, Session session) {
        this.session = session;
        this.onChange = new Event0("onChange");
        setFrame(true);
        super.setPaddings(10);
        super.setHeight(LicenseWizardView.HEIGHT);

        Label intro = new Label();
        intro.setHtml(i18n.t("Select other kind of licenses:") + DefaultFormUtils.brbr());

        final Store store = new SimpleStore(new String[] { "shortname", "longname", "url" }, getNonCCLicenses());
        store.load();

        final Template template = new Template("<div class=\"x-combo-list-item\">" + "<img src=\"{url}\"> "
                + "{longname}<div class=\"x-clear\"></div></div>");

        super.setHideLabels(true);

        cb = new ComboBox();
        cb.setMinChars(1);
        // cb.setFieldLabel("Other licenses");
        cb.setLabelSeparator("");
        cb.setStore(store);
        cb.setDisplayField("longname");
        cb.setValueField("shortname");
        cb.setMode(ComboBox.LOCAL);
        cb.setTriggerAction(ComboBox.ALL);
        cb.setEmptyText(i18n.t("Select license"));
        cb.setTypeAhead(true);
        cb.setSelectOnFocus(true);
        cb.setEditable(false);
        cb.setWidth(300);
        cb.setResizable(true);
        cb.setTpl(template);
        cb.setTitle("Licenses");
        cb.addListener(new ComboBoxListenerAdapter() {
            @Override
            public void onSelect(ComboBox comboBox, Record record, int index) {
                onChange.fire();
            }
        });
        add(intro);
        add(cb);
    }

    public String getSelectedLicense() {
        return cb.getValueAsString();
    }

    public void onChange(final Listener0 slot) {
        onChange.add(slot);
    }

    @Override
    public void reset() {
        super.reset();
    }

    public void setFlags(boolean isCopyleft, boolean isAppropiateForCulturalWorks, boolean isNonComercial) {
        // TODO Auto-generated method stub
    }

    private String[][] getNonCCLicenses() {
        ArrayList<LicenseDTO> licensesNonCCList = new ArrayList<LicenseDTO>();
        List<LicenseDTO> licenses = session.getLicenses();
        for (LicenseDTO license : licenses) {
            if (!license.isCC()) {
                licensesNonCCList.add(license);
            }
        }
        String[][] licensesArray = new String[licensesNonCCList.size()][3];
        for (int i = 0; i < licensesNonCCList.size(); i++) {
            LicenseDTO license = licensesNonCCList.get(i);
            licensesArray[i][0] = license.getShortName();
            licensesArray[i][1] = license.getLongName();
            licensesArray[i][2] = license.getImageUrl();
        }
        return licensesArray;
    }
}
