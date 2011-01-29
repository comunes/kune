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

import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardView;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;

public class LicenseWizardFirstForm extends DefaultForm implements LicenseWizardFirstFormView {
    private static final String POINT = "»&nbsp;";
    public static final String RADIO_ANOTHER_ID = "k-lwff-another";
    public static final String RADIO_COPYLEFT_ID = "k-lwff-copyleft";
    private static final String RADIO_FIELD_NAME = "k-lwff-radio";
    private final Radio anotherLicenseRadio;
    // private final Event0 onCopyLeftLicenseSelected;
    // private final Event0 onAnotherLicenseSelected;
    private final Radio copyleftRadio;

    public LicenseWizardFirstForm(final I18nTranslationService i18n) {
        // this.onCopyLeftLicenseSelected = new
        // Event0("onCopyLeftLicenseSelected");
        // this.onAnotherLicenseSelected = new
        // Event0("onAnotherLicenseSelected");
        super.setFrame(true);
        super.setPaddings(10);
        super.setHeight(LicenseWizardView.HEIGHT);

        final Label intro = new Label();
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
            public void onCheck(final Checkbox field, final boolean checked) {
                if (checked) {
                    // onCopyLeftLicenseSelected.fire();
                }
            }
        });
        anotherLicenseRadio.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(final Checkbox field, final boolean checked) {
                if (checked) {
                    // onAnotherLicenseSelected.fire();
                }
            }
        });

        final FieldSet infoFS = new FieldSet("Info");
        infoFS.setFrame(false);
        infoFS.setIconCls("k-info-icon");
        infoFS.setCollapsible(false);
        infoFS.setAutoHeight(true);

        final Label recommendCopyleft = new Label();
        final Label whyALicense = new Label();
        final Label youCanChangeTheLicenseLater = new Label();
        recommendCopyleft.setHtml(POINT
                + i18n.t("We recommend [%s] licenses, specially for practical works",
                        TextUtils.generateHtmlLink("http://en.wikipedia.org/wiki/Copyleft", i18n.t("copyleft")))
                + DefaultFormUtils.br());
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

    @Override
    public void onAnotherLicenseSelected(final Listener0 slot) {
        // onAnotherLicenseSelected.add(slot);
    }

    @Override
    public void onCopyLeftLicenseSelected(final Listener0 slot) {
        // onCopyLeftLicenseSelected.add(slot);
    }

    @Override
    public void reset() {
        super.reset();
        copyleftRadio.setChecked(true);
    }
}
