package org.ourproject.kune.workspace.client.licensefoot;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;

public class LicensePresenter implements LicenseComponent {

    private LicenseView view;
    private LicenseDTO license;

    public LicensePresenter() {
    }

    public void init(final LicenseView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setLicense(final StateDTO state) {
        this.license = state.getLicense();
        view.showLicense(state.getGroup().getLongName(), license);
    }

    public void onLicenseClick() {
        view.openWindow(license.getUrl());
    }
}
