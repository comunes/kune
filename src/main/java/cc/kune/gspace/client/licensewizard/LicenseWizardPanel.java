/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.gspace.client.licensewizard;

import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.client.ui.dialogs.wizard.WizardListener;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.dialogs.WizardFormDialog;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class LicenseWizardPanel extends WizardFormDialog implements LicenseWizardView {

  public static final String BACK_BTN_ID = "k-lwp-back-btn";
  public static final String CANCEL_BTN_ID = "k-lwp-cancel-btn";
  public static final String CLOSE_BTN_ID = "k-lwp-close-btn";
  public static final String FINISH_BTN_ID = "k-lwp-finish-btn";
  public static final String LIC_WIZ_DIALOG = "k-lwp-diag";
  public static final String NEXT_BTN_ID = "k-lwp-next-btn";

  @Inject
  public LicenseWizardPanel(final MaskWidget maskWidget, final I18nTranslationService i18n,
      final IconicResources res) {
    super(LIC_WIZ_DIALOG, i18n.t("License wizard"), true, false, WIDTH, null, BACK_BTN_ID, NEXT_BTN_ID,
        FINISH_BTN_ID, CANCEL_BTN_ID, CLOSE_BTN_ID, i18n, maskWidget);
    super.setFinishText(i18n.t("Select"));
    super.setIcon(res.copyleftGrey());
  }

  @Override
  public void addToSlot(final Object slot, final Widget content) {
  }

  @Override
  public Widget asWidget() {
    return null;
  }

  @Override
  public void removeFromSlot(final Object slot, final Widget content) {
  }

  @Override
  public void setInSlot(final Object slot, final Widget content) {
  }

  public void setWizardListener(final WizardListener listener) {
    super.setListener(listener);
  }
}
