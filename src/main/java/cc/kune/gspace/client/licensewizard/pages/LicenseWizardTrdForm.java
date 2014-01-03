/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.gspace.client.licensewizard.pages;

import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.DefaultForm;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseWizardTrdForm.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseWizardTrdForm extends DefaultForm implements LicenseWizardTrdFormView {

  /** The Constant RADIO_COMMERCIAL_FIELD_NAME. */
  public static final String RADIO_COMMERCIAL_FIELD_NAME = "k-lwtf-comm-radio";

  /** The Constant RADIO_MODIF_FIELD_NAME. */
  public static final String RADIO_MODIF_FIELD_NAME = "k-lwtf-mod-radio";

  /** The Constant RADIO_NOT_COMM_ID. */
  public static final String RADIO_NOT_COMM_ID = "k-lwtf-not-perm-comm";

  /** The Constant RADIO_NOT_PERMIT_MOD_ID. */
  public static final String RADIO_NOT_PERMIT_MOD_ID = "k-lwtf-not-mod";

  /** The Constant RADIO_PERMIT_COMMERCIAL_ID. */
  public static final String RADIO_PERMIT_COMMERCIAL_ID = "k-lwtf-perm-comm";

  /** The Constant RADIO_PERMIT_MOD_ID. */
  public static final String RADIO_PERMIT_MOD_ID = "k-lwtf-mod-perm";

  /** The Constant RADIO_PERMIT_MOD_SA_ID. */
  public static final String RADIO_PERMIT_MOD_SA_ID = "k-lwtf-mod-perm-sa";

  /** The commertial group. */
  private RadioGroup commertialGroup;

  /** The info. */
  private final LicenseWizardFlags info;

  /** The modifications group. */
  private RadioGroup modificationsGroup;

  /** The not permit comercial license radio. */
  private Radio notPermitComercialLicenseRadio;

  /** The not permit mod radio. */
  private Radio notPermitModRadio;

  /** The on change. */
  private SimpleCallback onChange;

  /** The permit comercial radio. */
  private Radio permitComercialRadio;

  /** The permit mod radio. */
  private Radio permitModRadio;

  /** The permit mod sa radio. */
  private Radio permitModSaRadio;

  /**
   * Instantiates a new license wizard trd form.
   * 
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param commonRes
   *          the common res
   */
  @Inject
  public LicenseWizardTrdForm(final I18nTranslationService i18n, final IconicResources res,
      final CommonResources commonRes) {
    super.setFrame(true);
    super.setPadding(10);
    super.setAutoHeight(true);

    final Label intro = new Label();
    intro.setText(i18n.t("With a Creative Commons license, you keep your copyright but allow people to copy and distribute your work provided they give you credit — and only on the conditions you specify here. What do you want to do?"));
    intro.addStyleName("kune-Margin-10-b");

    final FieldSet commercialfieldSet = new FieldSet();
    commercialfieldSet.setHeadingHtml(i18n.t("Allow any uses of your work, including commercial?"));
    commercialfieldSet.setCollapsible(false);
    commercialfieldSet.setAutoHeight(true);
    final FieldSet modificationsfieldSet = new FieldSet();
    modificationsfieldSet.setHeadingHtml(i18n.t("Allow modifications of your work?"));
    modificationsfieldSet.setCollapsible(false);
    modificationsfieldSet.setAutoHeight(true);

    createRadios(i18n, commercialfieldSet, modificationsfieldSet);
    createRadioListeners();

    info = new LicenseWizardFlags(res, commonRes, i18n);

    add(intro);
    add(commercialfieldSet);
    add(modificationsfieldSet);
    add(info);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return super.getFormPanel();
  }

  /**
   * Creates the radio.
   * 
   * @param radioGroup
   *          the radio group
   * @param radioLabel
   *          the radio label
   * @param radioFieldName
   *          the radio field name
   * @param radioTip
   *          the radio tip
   * @param id
   *          the id
   * @return the radio
   */
  private Radio createRadio(final RadioGroup radioGroup, final String radioLabel,
      final String radioFieldName, final String radioTip, final String id) {
    final Radio radio = new Radio();
    radio.setName(radioFieldName);
    radio.setHideLabel(true);
    radio.setId(id);
    radioGroup.add(radio);

    if (radioTip != null) {
      Tooltip.to(radio, radioTip);
    }
    radio.setBoxLabel(radioLabel);
    return radio;
  }

  /**
   * Creates the radio listeners.
   */
  private void createRadioListeners() {
    final Listener<BaseEvent> listener = new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        onChange.onCallback();
      }
    };
    commertialGroup.addListener(Events.Change, listener);
    modificationsGroup.addListener(Events.Change, listener);
  }

  /**
   * Creates the radios.
   * 
   * @param i18n
   *          the i18n
   * @param commercialfieldSet
   *          the commercialfield set
   * @param modificationsfieldSet
   *          the modificationsfield set
   */
  private void createRadios(final I18nTranslationService i18n, final FieldSet commercialfieldSet,
      final FieldSet modificationsfieldSet) {
    commertialGroup = new RadioGroup();
    modificationsGroup = new RadioGroup();
    commertialGroup.setOrientation(Orientation.VERTICAL);
    modificationsGroup.setOrientation(Orientation.VERTICAL);
    permitComercialRadio = createRadio(
        commertialGroup,
        i18n.t("Yes"),
        RADIO_COMMERCIAL_FIELD_NAME,
        i18n.t("The licensor permits others to copy, distribute, display, and perform the work, including for commercial purposes"),
        RADIO_PERMIT_COMMERCIAL_ID);
    notPermitComercialLicenseRadio = createRadio(
        commertialGroup,
        i18n.t("No"),
        RADIO_COMMERCIAL_FIELD_NAME,
        i18n.t("The licensor permits others to copy, distribute, display, and perform the work for non-commercial purposes only"),
        RADIO_NOT_COMM_ID);
    permitModRadio = createRadio(
        modificationsGroup,
        i18n.t("Yes"),
        RADIO_MODIF_FIELD_NAME,
        i18n.t("The licensor permits others to copy, distribute, display and perform the work, as well as to make derivative works based on it"),
        RADIO_PERMIT_MOD_ID);
    permitModSaRadio = createRadio(
        modificationsGroup,
        i18n.t("Yes, as long as other share alike"),
        RADIO_MODIF_FIELD_NAME,
        i18n.t("The licensor permits others to distribute derivative works only under the same license or one compatible with the one that governs the licensor's work"),
        RADIO_PERMIT_MOD_SA_ID);
    notPermitModRadio = createRadio(
        modificationsGroup,
        i18n.t("No"),
        RADIO_MODIF_FIELD_NAME,
        i18n.t("The licensor permits others to copy, distribute, display and perform only unaltered copies of the work — not derivative works based on it"),
        RADIO_NOT_PERMIT_MOD_ID);
    commertialGroup.add(permitComercialRadio);
    commertialGroup.add(notPermitComercialLicenseRadio);
    modificationsGroup.add(permitModRadio);
    modificationsGroup.add(permitModSaRadio);
    modificationsGroup.add(notPermitModRadio);
    commercialfieldSet.add(commertialGroup);
    modificationsfieldSet.add(modificationsGroup);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdFormView#
   * isAllowComercial()
   */
  @Override
  public boolean isAllowComercial() {
    return permitComercialRadio.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdFormView#isAllowModif
   * ()
   */
  @Override
  public boolean isAllowModif() {
    return permitModRadio.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdFormView#
   * isAllowModifShareAlike()
   */
  @Override
  public boolean isAllowModifShareAlike() {
    return permitModSaRadio.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdFormView#onChange
   * (cc.kune.common.shared.utils.SimpleCallback)
   */
  @Override
  public void onChange(final SimpleCallback onChange) {
    this.onChange = onChange;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.DefaultForm#reset()
   */
  @Override
  public void reset() {
    super.reset();
    permitComercialRadio.setValue(true);
    permitModSaRadio.setValue(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdFormView#setFlags
   * (boolean, boolean, boolean)
   */
  @Override
  public void setFlags(final boolean isCopyleft, final boolean isAppropiateForCulturalWorks,
      final boolean isNonComercial) {
    info.setVisible(isCopyleft, isAppropiateForCulturalWorks, isNonComercial);
  }
}
