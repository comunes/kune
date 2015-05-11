/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.LicenseDTO;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.watopi.chosen.client.ChosenOptions;
import com.watopi.chosen.client.event.ChosenChangeEvent;
import com.watopi.chosen.client.event.ChosenChangeEvent.ChosenChangeHandler;
import com.watopi.chosen.client.gwt.ChosenListBox;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseWizardFrdForm.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseWizardFrdForm extends FlowPanel implements LicenseWizardFrdFormView {

  /** The Constant COMMON_LICENSES_ID. */
  public static final String COMMON_LICENSES_ID = "k-lwsf-common";

  /** The Constant OTHER_LICENSES_ID. */
  public static final String OTHER_LICENSES_ID = "k-lwsf-other";

  /** The Constant RADIO_FIELD_NAME. */
  public static final String RADIO_FIELD_NAME = "k-lwsf-radio";

  private final ChosenListBox licenseChoose;

  /** The on change. */
  private SimpleCallback onChange;

  /**
   * Instantiates a new license wizard frd form.
   *
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   */
  @Inject
  public LicenseWizardFrdForm(final I18nTranslationService i18n, final Session session) {

    // setFrame(true);
    // super.setPadding(10);

    final Label intro = new Label();
    intro.setText(i18n.t("Select other kind of licenses:"));
    intro.addStyleName("kune-Margin-10-b");

    // super.setHideLabels(true);
    final ChosenOptions options = new ChosenOptions();
    options.setNoResultsText(i18n.t("License not found"));
    options.setPlaceholderText(i18n.t("Select license"));
    options.setSearchContains(true);
    licenseChoose = new ChosenListBox(false, options);
    // First empty
    licenseChoose.addItem("", "");
    for (final LicenseDTO license : session.getLicenses()) {
      if (!license.isCC()) {
        licenseChoose.addItem(license.getLongName(), license.getShortName());
      }
    }
    licenseChoose.addChosenChangeHandler(new ChosenChangeHandler() {
      @Override
      public void onChange(final ChosenChangeEvent event) {
        onChange.onCallback();
      }
    });
    add(licenseChoose);

    // super.setHeight(200);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView#
   * getSelectedLicense()
   */
  @Override
  public String getSelectedLicense() {
    return licenseChoose.getSelectedValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView#onChange
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
    // super.reset();
    licenseChoose.setSelectedIndex(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView#setFlags
   * (boolean, boolean, boolean)
   */
  @Override
  public void setFlags(final boolean isCopyleft, final boolean isAppropiateForCulturalWorks,
      final boolean isNonComercial) {

  }
}
