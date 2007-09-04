package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.license.LicenseComponent;

class Components {

    private LicenseComponent license;

    public Components(WorkspacePresenter presenter) {
    }

    public LicenseComponent getLicenseComponent() {
	if (license == null)
	    license = WorkspaceFactory.createLicenseComponent();
	return license;
    }

}
