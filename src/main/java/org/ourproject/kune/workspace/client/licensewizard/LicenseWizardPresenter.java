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

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFrdFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardSndFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardTrdFormView;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.LicenseDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class LicenseWizardPresenter implements LicenseWizard {

    private LicenseWizardView view;
    private final LicenseWizardFirstFormView fstForm;
    private final LicenseWizardSndFormView sndForm;
    private final LicenseWizardTrdFormView trdForm;
    private final LicenseWizardFrdFormView frdForm;
    private final Session session;
    private Listener<LicenseDTO> selectLicenseListener;
    private final String ccVers;

    public LicenseWizardPresenter(LicenseWizardFirstFormView firstForm, LicenseWizardSndFormView sndForm,
            LicenseWizardTrdFormView trdForm, LicenseWizardFrdFormView frdForm, Session session) {
        this.fstForm = firstForm;
        this.sndForm = sndForm;
        this.trdForm = trdForm;
        this.frdForm = frdForm;
        this.session = session;
        ccVers = "-" + session.getCurrentCCversion();
    }

    public View getView() {
        return view;
    }

    public void init(final LicenseWizardView view) {
        this.view = view;
        fstForm.onCopyLeftLicenseSelected(new Listener0() {
            public void onEvent() {
                onCopyLeftLicenseSelected();
            }
        });
        fstForm.onAnotherLicenseSelected(new Listener0() {
            public void onEvent() {
                onAnotherLicenseSelecterd();
            }
        });
        trdForm.onChange(new Listener0() {
            public void onEvent() {
                onCreativeCommonsChanged();
            }
        });
        frdForm.onChange(new Listener0() {
            public void onEvent() {
                if (frdForm.getSelectedLicense().length() > 0) {
                    view.setEnabled(true, false, true, true);
                }
            }
        });
        view.add(fstForm);
        view.add(sndForm);
        view.add(trdForm);
        view.add(frdForm);
    }

    public void onBack() {
        if (view.isCurrentPage(sndForm)) {
            showFst();
        } else if (in(trdForm)) {
            showSnd();
        } else if (in(frdForm)) {
            showSnd();
        } else {
            Log.error("Programatic error in LicenseWizardPresenter");
        }
    }

    public void onCancel() {
        view.hide();
    }

    public void onChange() {
        String licenseShortName;
        if (in(fstForm)) {
            licenseShortName = "by-sa" + ccVers;
        } else if (in(trdForm)) {
            if (trdForm.isAllowComercial()) {
                licenseShortName = trdForm.isAllowModif() ? "by" + ccVers : trdForm.isAllowModifShareAlike() ? "by-sa"
                        + ccVers : "by-nd" + ccVers;
            } else {
                licenseShortName = trdForm.isAllowModif() ? "by-nc" + ccVers
                        : trdForm.isAllowModifShareAlike() ? "by-nc-sa" + ccVers : "by-nc-nd" + ccVers;
            }
        } else if (in(sndForm)) {
            licenseShortName = "by-sa" + ccVers;
            NotifyUser.error("Programatic error in LicenseWizardPresenter");
        } else {
            licenseShortName = frdForm.getSelectedLicense();
        }
        view.hide();
        selectLicenseListener.onEvent(getLicenseFromShortName(licenseShortName));
    }

    public void onClose() {
        view.hide();
    }

    public void onNext() {
        if (in(fstForm)) {
            view.clear();
            showSnd();
        } else if (in(sndForm)) {
            if (sndForm.isCommonLicensesSelected()) {
                showTrd();
            } else {
                showFrd();
            }
        } else {
            Log.error("Programatic error in LicenseWizardPresenter");
        }
    }

    public void start(Listener<LicenseDTO> selectLicenseListener) {
        this.selectLicenseListener = selectLicenseListener;
        reset();
        view.show();
        view.center();
    }

    void onAnotherLicenseSelecterd() {
        view.setEnabled(false, true, true, false);
    }

    void onCopyLeftLicenseSelected() {
        view.setEnabled(false, false, true, true);
    }

    private LicenseDTO getLicenseFromShortName(final String shortName) {
        List<LicenseDTO> licenses = session.getLicenses();
        for (int i = 0; i < licenses.size(); i++) {
            final LicenseDTO licenseDTO = licenses.get(i);
            if (licenseDTO.getShortName().equals(shortName)) {
                return licenseDTO;
            }
        }
        Log.error("Internal error: License not found");
        throw new IndexOutOfBoundsException("License not found");
    }

    private boolean in(View page) {
        return view.isCurrentPage(page);
    }

    private void onCreativeCommonsChanged() {
        boolean isCopyleft = trdForm.isAllowComercial() && trdForm.isAllowModifShareAlike();
        boolean isAppropiateForCulturalWorks = trdForm.isAllowComercial()
                && (trdForm.isAllowModif() || trdForm.isAllowModifShareAlike());
        trdForm.setFlags(isCopyleft, isAppropiateForCulturalWorks, !trdForm.isAllowComercial());
    }

    private void reset() {
        view.clear();
        view.setEnabled(false, false, true, true);
        view.show(fstForm);
        fstForm.reset();
        sndForm.reset();
        trdForm.reset();
        frdForm.reset();
    }

    private void showFrd() {
        view.show(frdForm);
        view.setEnabled(true, false, true, false);
    }

    private void showFst() {
        view.clear();
        view.show(fstForm);
        view.setEnabled(false, true, true, true);
    }

    private void showSnd() {
        view.show(sndForm);
        view.setEnabled(true, true, true, false);
    }

    private void showTrd() {
        view.show(trdForm);
        view.setEnabled(true, false, true, true);
    }
}
