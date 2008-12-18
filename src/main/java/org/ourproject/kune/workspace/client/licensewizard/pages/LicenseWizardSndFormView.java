package org.ourproject.kune.workspace.client.licensewizard.pages;

import org.ourproject.kune.platf.client.View;

import com.calclab.suco.client.listener.Listener0;

public interface LicenseWizardSndFormView extends View {
    void onCommonLicensesSelected(Listener0 slot);

    void onOtherLicensesSelected(Listener0 slot);

    void reset();
}
