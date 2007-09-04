package org.ourproject.kune.workspace.client.license;

import org.ourproject.kune.platf.client.View;

public class LicensePresenter implements LicenseComponent {

    private final LicenseView view;

    public LicensePresenter(LicenseView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

}
