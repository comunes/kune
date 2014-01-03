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

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.client.ui.DefaultFormUtils;

import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseWizardSndForm.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseWizardSndForm extends DefaultForm implements LicenseWizardSndFormView {

  /** The Constant COMMON_LICENSES_ID. */
  public static final String COMMON_LICENSES_ID = "k-lwsf-common";

  /** The Constant OTHER_LICENSES_ID. */
  public static final String OTHER_LICENSES_ID = "k-lwsf-other";

  /** The Constant RADIO_FIELD_NAME. */
  public static final String RADIO_FIELD_NAME = "k-lwsf-radio";

  /** The common licenses radio. */
  private final Radio commonLicensesRadio;

  /** The other licenses radio. */
  private final Radio otherLicensesRadio;

  /**
   * Instantiates a new license wizard snd form.
   * 
   * @param i18n
   *          the i18n
   */
  @Inject
  public LicenseWizardSndForm(final I18nTranslationService i18n) {
    setFrame(true);
    super.setPadding(10);
    // super.setHeight(LicenseWizardView.HEIGHT);
    super.setAutoHeight(true);
    final Label intro = new Label();
    intro.setText(i18n.t("Select the license type:"));
    intro.addStyleName("kune-Margin-10-b");

    final FieldSet fieldSet = new FieldSet();
    // fieldSet.setTitle("license type");
    fieldSet.addStyleName("margin-left: 105px");
    fieldSet.setWidth(250);
    commonLicensesRadio = DefaultFormUtils.createRadio(fieldSet,
        i18n.t("Common licenses for cultural works"), RADIO_FIELD_NAME,
        i18n.t("Select a Creative Commons license (recommended for cultural works)"), COMMON_LICENSES_ID);
    otherLicensesRadio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Other kind of licenses"),
        RADIO_FIELD_NAME,
        i18n.t("Use the GNU licenses (recommended for free software works) and other kind of licenses"),
        OTHER_LICENSES_ID);
    add(intro);
    add(commonLicensesRadio);
    add(otherLicensesRadio);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.licensewizard.pages.LicenseWizardSndFormView#
   * isCommonLicensesSelected()
   */
  @Override
  public boolean isCommonLicensesSelected() {
    return commonLicensesRadio.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.DefaultForm#reset()
   */
  @Override
  public void reset() {
    super.reset();
    commonLicensesRadio.setValue(true);
  }
}
