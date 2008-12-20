package org.ourproject.kune.workspace.client.licensewizard;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFrdFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardSndFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardTrdFormView;
import org.ourproject.kune.workspace.client.site.Site;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;

public class LicenseWizardPresenter implements LicenseWizard {

    private LicenseWizardView view;
    private final LicenseWizardFirstFormView fstForm;
    private final LicenseWizardSndFormView sndForm;
    private final LicenseWizardTrdFormView trdForm;
    private final LicenseWizardFrdFormView frdForm;
    private final Session session;
    private Listener<LicenseDTO> selectLicenseListener;

    public LicenseWizardPresenter(LicenseWizardFirstFormView firstForm, LicenseWizardSndFormView sndForm,
            LicenseWizardTrdFormView trdForm, LicenseWizardFrdFormView frdForm, Session session) {
        this.fstForm = firstForm;
        this.sndForm = sndForm;
        this.trdForm = trdForm;
        this.frdForm = frdForm;
        this.session = session;
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
            licenseShortName = "by-sa";
        } else if (in(trdForm)) {
            if (trdForm.isAllowComercial()) {
                licenseShortName = trdForm.isAllowModif() ? "by" : trdForm.isAllowModifShareAlike() ? "by-sa" : "by-nd";
            } else {
                licenseShortName = trdForm.isAllowModif() ? "by-nc" : trdForm.isAllowModifShareAlike() ? "by-nc-sa"
                        : "by-nc-nd";
            }
        } else if (in(sndForm)) {
            licenseShortName = "by-sa";
            Site.error("Programatic error in LicenseWizardPresenter");
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
