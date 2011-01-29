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

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardView;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;

public class LicenseWizardTrdForm extends DefaultForm implements LicenseWizardTrdFormView {
    public static final String RADIO_COMMERCIAL_FIELD_NAME = "k-lwtf-comm-radio";
    public static final String RADIO_MODIF_FIELD_NAME = "k-lwtf-mod-radio";
    public static final String RADIO_NOT_COMM_ID = "k-lwtf-not-perm-comm";
    public static final String RADIO_NOT_PERMIT_MOD_ID = "k-lwtf-not-mod";
    public static final String RADIO_PERMIT_COMMERCIAL_ID = "k-lwtf-perm-comm";
    public static final String RADIO_PERMIT_MOD_ID = "k-lwtf-mod-perm";
    public static final String RADIO_PERMIT_MOD_SA_ID = "k-lwtf-mod-perm-sa";
    private final LicenseWizardFlags info;
    private Radio notPermitComercialLicenseRadio;
    // private final Event0 onChange;
    private Radio notPermitModRadio;
    private Radio permitComercialRadio;
    private Radio permitModRadio;
    private Radio permitModSaRadio;

    public LicenseWizardTrdForm(final Images images, final I18nTranslationService i18n) {
        super.setFrame(true);
        super.setHeight(LicenseWizardView.HEIGHT);
        super.setPaddings(10);

        // this.onChange = new Event0("onChange");

        final Label intro = new Label();
        intro.setHtml(i18n.t("With a Creative Commons license, you keep your copyright but allow people to copy and distribute your work provided they give you credit — and only on the conditions you specify here. What do you want to do?")
                + DefaultFormUtils.brbr());

        final Label commercialQuestion = new Label();
        commercialQuestion.setHtml(i18n.t("Allow any uses of your work, including commercial?") + DefaultFormUtils.br());
        final Label modificationsQuestion = new Label();
        modificationsQuestion.setHtml(i18n.t("Allow modifications of your work?") + DefaultFormUtils.br());

        final FieldSet commercialfieldSet = new FieldSet(i18n.t("Allow any uses of your work, including commercial?"));
        commercialfieldSet.setFrame(false);
        commercialfieldSet.setCollapsible(false);
        commercialfieldSet.setAutoHeight(true);
        final FieldSet modificationsfieldSet = new FieldSet(i18n.t("Allow modifications of your work?"));
        modificationsfieldSet.setFrame(false);
        modificationsfieldSet.setCollapsible(false);
        modificationsfieldSet.setAutoHeight(true);

        createRadios(i18n, commercialfieldSet, modificationsfieldSet);
        createRadioListeners();

        info = new LicenseWizardFlags(images, i18n);

        add(intro);
        add(commercialfieldSet);
        add(modificationsfieldSet);
        add(new PaddedPanel(info, 0, 0, 0, 0));
    }

    private void createRadioListeners() {
        final Radio[] radios = { permitModRadio, permitModSaRadio, notPermitModRadio, permitComercialRadio,
                notPermitComercialLicenseRadio };
        for (final Radio radio : radios) {
            radio.addListener(new CheckboxListenerAdapter() {
                @Override
                public void onChange(final Field field, final Object newVal, final Object oldVal) {
                    // onChange.fire();
                }

                @Override
                public void onCheck(final Checkbox field, final boolean checked) {
                    // onChange.fire();
                }
            });
        }
    }

    private void createRadios(final I18nTranslationService i18n, final FieldSet commercialfieldSet,
            final FieldSet modificationsfieldSet) {
        permitComercialRadio = DefaultFormUtils.createRadio(
                commercialfieldSet,
                i18n.t("Yes"),
                RADIO_COMMERCIAL_FIELD_NAME,
                i18n.t("The licensor permits others to copy, distribute, display, and perform the work, including for commercial purposes"),
                RADIO_PERMIT_COMMERCIAL_ID);
        notPermitComercialLicenseRadio = DefaultFormUtils.createRadio(
                commercialfieldSet,
                i18n.t("No"),
                RADIO_COMMERCIAL_FIELD_NAME,
                i18n.t("The licensor permits others to copy, distribute, display, and perform the work for non-commercial purposes only"),
                RADIO_NOT_COMM_ID);
        permitModRadio = DefaultFormUtils.createRadio(
                modificationsfieldSet,
                i18n.t("Yes"),
                RADIO_MODIF_FIELD_NAME,
                i18n.t("The licensor permits others to copy, distribute, display and perform the work, as well as make derivative works based on it"),
                RADIO_PERMIT_MOD_ID);
        permitModSaRadio = DefaultFormUtils.createRadio(
                modificationsfieldSet,
                i18n.t("Yes, as long as other share alike"),
                RADIO_MODIF_FIELD_NAME,
                i18n.t("The licensor permits others to distribute derivative works only under the same license or one compatible with the one that governs the licensor's work"),
                RADIO_PERMIT_MOD_SA_ID);
        notPermitModRadio = DefaultFormUtils.createRadio(
                modificationsfieldSet,
                i18n.t("No"),
                RADIO_MODIF_FIELD_NAME,
                i18n.t("The licensor permits others to copy, distribute, display and perform only unaltered copies of the work — not derivative works based on it"),
                RADIO_NOT_PERMIT_MOD_ID);
    }

    @Override
    public boolean isAllowComercial() {
        return permitComercialRadio.getValue();
    }

    @Override
    public boolean isAllowModif() {
        return permitModRadio.getValue();
    }

    @Override
    public boolean isAllowModifShareAlike() {
        return permitModSaRadio.getValue();
    }

    @Override
    public void onChange(final Listener0 slot) {
        // onChange.add(slot);
    }

    @Override
    public void reset() {
        super.reset();
        permitComercialRadio.setChecked(true);
        permitModSaRadio.setChecked(true);
    }

    @Override
    public void setFlags(final boolean isCopyleft, final boolean isAppropiateForCulturalWorks,
            final boolean isNonComercial) {
        info.setVisible(isCopyleft, isAppropiateForCulturalWorks, isNonComercial);
    }
}
