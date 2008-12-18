package org.ourproject.kune.workspace.client.licensewizard;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardSndFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardTrdFormView;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener0;

public class LicenseWizardPresenter implements LicenseWizard {

    private LicenseWizardView view;
    private final LicenseWizardFirstFormView firstForm;
    private final LicenseWizardSndFormView sndForm;
    private final Provider<GroupServiceAsync> groupService;
    private final LicenseWizardTrdFormView trdForm;

    public LicenseWizardPresenter(LicenseWizardFirstFormView firstForm, LicenseWizardSndFormView sndForm,
            LicenseWizardTrdFormView trdForm, Provider<GroupServiceAsync> groupService) {
        this.firstForm = firstForm;
        this.sndForm = sndForm;
        this.trdForm = trdForm;
        this.groupService = groupService;
    }

    public View getView() {
        return view;
    }

    public void init(LicenseWizardView view) {
        this.view = view;
        firstForm.onCopyLeftLicenseSelected(new Listener0() {
            public void onEvent() {
                onCopyLeftLicenseSelected();
            }
        });
        firstForm.onAnotherLicenseSelected(new Listener0() {
            public void onEvent() {
                onAnotherLicenseSelecterd();
            }
        });
        trdForm.onChange(new Listener0() {
            public void onEvent() {
                onCreativeCommonsChanged();
            }
        });
        view.add(firstForm);
        view.add(sndForm);
        view.add(trdForm);
    }

    public void onBack() {
        if (view.isCurrentPage(sndForm)) {
            showFst();
        } else if (view.isCurrentPage(trdForm)) {
            showSnd();
        } else {
            Log.error("Programatic error in LicenseWizardPresenter");
        }
    }

    public void onCancel() {
        view.hide();
    }

    public void onChange() {
        // What license?
        // FIXME RPC call()
    }

    public void onClose() {
        view.hide();
    }

    public void onNext() {
        if (view.isCurrentPage(firstForm)) {
            view.clear();
            showSnd();
        } else if (view.isCurrentPage(sndForm)) {
            showTrd();
        } else {
            Log.error("Programatic error in LicenseWizardPresenter");
        }
    }

    public void show() {
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

    private void onCreativeCommonsChanged() {
        boolean isCopyleft = trdForm.isAllowComercial() && trdForm.isAllowModifShareAlike();
        boolean isAppropiateForCulturalWorks = trdForm.isAllowComercial()
                && (trdForm.isAllowModif() || trdForm.isAllowModifShareAlike());
        trdForm.setFlags(isCopyleft, isAppropiateForCulturalWorks, !trdForm.isAllowComercial());
    }

    private void reset() {
        view.clear();
        view.setEnabled(false, false, true, true);
        view.show(firstForm);
        firstForm.reset();
        sndForm.reset();
        trdForm.reset();
    }

    private void showFst() {
        view.clear();
        view.show(firstForm);
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
