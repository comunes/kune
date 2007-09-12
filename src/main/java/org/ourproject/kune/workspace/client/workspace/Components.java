package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.license.LicenseComponent;

class Components {
    private LicenseComponent license;
    private ContentTitleComponent contentTitle;
    private SocialNetworkComponent socialNetwork;
    private BuddiesPresenceComponent buddiesPresence;

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

    public SocialNetworkComponent getSocialNetworkComponent() {
	if (socialNetwork == null) {
	    socialNetwork = WorkspaceFactory.createSocialNetworkComponent();
	}
	return socialNetwork;
    }

    public BuddiesPresenceComponent getBuddiesPresenceComponent() {
	if (buddiesPresence == null) {
	    buddiesPresence = WorkspaceFactory.createBuddiesPresenceComponent();
	}
	return buddiesPresence;
    }

}
