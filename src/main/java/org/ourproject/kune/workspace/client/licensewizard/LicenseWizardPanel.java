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
package org.ourproject.kune.workspace.client.licensewizard;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.WizardDialog;
import org.ourproject.kune.platf.client.ui.dialogs.WizardListener;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

public class LicenseWizardPanel extends WizardDialog implements LicenseWizardView {

    public static final String LIC_WIZ_DIALOG = "k-lwp-diag";
    public static final String BACK_BTN_ID = "k-lwp-back-btn";
    public static final String NEXT_BTN_ID = "k-lwp-next-btn";
    public static final String FINISH_BTN_ID = "k-lwp-finish-btn";
    public static final String CLOSE_BTN_ID = "k-lwp-close-btn";
    public static final String CANCEL_BTN_ID = "k-lwp-cancel-btn";

    public LicenseWizardPanel(final LicenseWizardPresenter presenter, final WorkspaceSkeleton ws,
            I18nTranslationService i18n) {
        super(LIC_WIZ_DIALOG, i18n.t("License wizard"), true, false, WIDTH, HEIGHT, WIDTH, HEIGHT, BACK_BTN_ID,
                NEXT_BTN_ID, FINISH_BTN_ID, CANCEL_BTN_ID, CLOSE_BTN_ID, i18n, new WizardListener() {
                    public void onBack() {
                        presenter.onBack();
                    }

                    public void onCancel() {
                        presenter.onCancel();
                    }

                    public void onClose() {
                        presenter.onClose();
                    }

                    public void onFinish() {
                        presenter.onChange();
                    }

                    public void onNext() {
                        presenter.onNext();
                    }
                });
        super.setFinishText(i18n.t("Select"));
        super.setIconCls("k-copyleft-icon");
    }
}
