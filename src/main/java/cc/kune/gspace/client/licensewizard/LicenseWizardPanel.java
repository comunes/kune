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
package cc.kune.gspace.client.licensewizard;

import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.client.ui.dialogs.wizard.WizardListener;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.dialogs.WizardFormDialog;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseWizardPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseWizardPanel extends WizardFormDialog implements LicenseWizardView {

  /** The Constant BACK_BTN_ID. */
  public static final String BACK_BTN_ID = "k-lwp-back-btn";

  /** The Constant CANCEL_BTN_ID. */
  public static final String CANCEL_BTN_ID = "k-lwp-cancel-btn";

  /** The Constant CLOSE_BTN_ID. */
  public static final String CLOSE_BTN_ID = "k-lwp-close-btn";

  /** The Constant FINISH_BTN_ID. */
  public static final String FINISH_BTN_ID = "k-lwp-finish-btn";

  /** The Constant LIC_WIZ_DIALOG. */
  public static final String LIC_WIZ_DIALOG = "k-lwp-diag";

  /** The Constant NEXT_BTN_ID. */
  public static final String NEXT_BTN_ID = "k-lwp-next-btn";

  /**
   * Instantiates a new license wizard panel.
   * 
   * @param maskWidget
   *          the mask widget
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   */
  @Inject
  public LicenseWizardPanel(final MaskWidget maskWidget, final I18nTranslationService i18n,
      final IconicResources res) {
    super(LIC_WIZ_DIALOG, i18n.t("License wizard"), true, false, WIDTH, null, BACK_BTN_ID, NEXT_BTN_ID,
        FINISH_BTN_ID, CANCEL_BTN_ID, CLOSE_BTN_ID, i18n, maskWidget);
    super.setFinishText(i18n.t("Select"));
    super.setIcon(res.copyleftGrey());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#addToSlot(java.lang.Object,
   * com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public void addToSlot(final Object slot, final Widget content) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#removeFromSlot(java.lang.Object,
   * com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public void removeFromSlot(final Object slot, final Widget content) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#setInSlot(java.lang.Object,
   * com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public void setInSlot(final Object slot, final Widget content) {
  }

  /**
   * Sets the wizard listener.
   * 
   * @param listener
   *          the new wizard listener
   */
  public void setWizardListener(final WizardListener listener) {
    super.setListener(listener);
  }
}
