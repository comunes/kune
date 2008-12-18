package org.ourproject.kune.workspace.client.licensewizard;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardSndFormView;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener0;

public class LicenseWizardPresenter implements LicenseWizard {

    private LicenseWizardView view;
    private final LicenseWizardFirstFormView firstForm;
    private final LicenseWizardSndFormView sndForm;
    private final Provider<GroupServiceAsync> groupService;

    public LicenseWizardPresenter(LicenseWizardFirstFormView firstForm, LicenseWizardSndFormView sndForm,
            Provider<GroupServiceAsync> groupService) {
        this.firstForm = firstForm;
        this.sndForm = sndForm;
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
    }

    public void onBack() {
        view.clear();
        view.add(firstForm);
        view.setEnabled(false, true, true, true);
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
        view.clear();
        view.add(sndForm);
        view.setEnabled(true, false, true, true);
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

    private void reset() {
        view.clear();
        view.setEnabled(false, false, true, true);
        view.add(firstForm);
        firstForm.reset();
    }
}
