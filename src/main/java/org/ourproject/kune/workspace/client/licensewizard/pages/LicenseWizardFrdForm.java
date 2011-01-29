/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.workspace.client.licensewizard.pages;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardView;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

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
    // private final Event0 onChange;
    private final Session session;

    public LicenseWizardFrdForm(final I18nTranslationService i18n, final Session session) {
        this.session = session;
        // this.onChange = new Event0("onChange");
        setFrame(true);
        super.setPaddings(10);
        super.setHeight(LicenseWizardView.HEIGHT);

        final Label intro = new Label();
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
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                // onChange.fire();
            }
        });
        add(intro);
        add(cb);
    }

    private String[][] getNonCCLicenses() {
        final ArrayList<LicenseDTO> licensesNonCCList = new ArrayList<LicenseDTO>();
        final List<LicenseDTO> licenses = session.getLicenses();
        for (final LicenseDTO license : licenses) {
            if (!license.isCC()) {
                licensesNonCCList.add(license);
            }
        }
        final String[][] licensesArray = new String[licensesNonCCList.size()][3];
        for (int i = 0; i < licensesNonCCList.size(); i++) {
            final LicenseDTO license = licensesNonCCList.get(i);
            licensesArray[i][0] = license.getShortName();
            licensesArray[i][1] = license.getLongName();
            licensesArray[i][2] = license.getImageUrl();
        }
        return licensesArray;
    }

    @Override
    public String getSelectedLicense() {
        return cb.getValueAsString();
    }

    @Override
    public void onChange(final Listener0 slot) {
        // onChange.add(slot);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setFlags(final boolean isCopyleft, final boolean isAppropiateForCulturalWorks,
            final boolean isNonComercial) {
        // TODO Auto-generated method stub
    }
}
