package org.ourproject.kune.workspace.client.license;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public class LicensePresenter implements LicenseComponent {

    private LicenseView view;

    public LicensePresenter() {
    }

    public void init(final LicenseView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void setLicense(final String groupName, final LicenseDTO licenseDTO) {
	view.showName(groupName, licenseDTO.getLongName());
	view.showImage(licenseDTO.getImageUrl());
    }

    public void onLicenseClick() {
	// TODO:
    }
}
