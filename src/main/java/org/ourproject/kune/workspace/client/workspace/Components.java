package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.license.LicenseComponent;

class Components {
    private LicenseComponent license;
    private ContentTitleComponent contentTitle;

    public Components(final WorkspacePresenter presenter) {
    }

    public LicenseComponent getLicenseComponent() {
	if (license == null) {
	    license = WorkspaceFactory.createLicenseComponent();
	}
	return license;
    }

    public ContentTitleComponent getContentTitleComponent() {
	if (contentTitle == null) {
	    contentTitle = WorkspaceFactory.createContentTitleComponent();
	}
	return contentTitle;
    }

}
