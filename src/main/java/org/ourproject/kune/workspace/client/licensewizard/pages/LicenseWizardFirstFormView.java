package org.ourproject.kune.workspace.client.licensewizard.pages;

import org.ourproject.kune.platf.client.View;

import com.calclab.suco.client.listener.Listener0;

public interface LicenseWizardFirstFormView extends View {
    void onAnotherLicenseSelected(Listener0 slot);

    void onCopyLeftLicenseSelected(Listener0 slot);

    void reset();
}
