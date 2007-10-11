package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.license.LicenseComponent;

class Components {
    private LicenseComponent license;
    private ContentTitleComponent contentTitle;
    private GroupMembersComponent groupMembers;
    private ParticipationComponent participatesInGroups;
    private BuddiesPresenceComponent buddiesPresence;
    private ContentSubTitleComponent contentSubTitle;

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

    public ContentSubTitleComponent getContentSubTitleComponent() {
	if (contentSubTitle == null) {
	    contentSubTitle = WorkspaceFactory.createContentSubTitleComponent();
	}
	return contentSubTitle;
    }

    public GroupMembersComponent getGroupMembersComponent() {
	if (groupMembers == null) {
	    groupMembers = WorkspaceFactory.createGroupMembersComponent();
	}
	return groupMembers;
    }

    public BuddiesPresenceComponent getBuddiesPresenceComponent() {
	if (buddiesPresence == null) {
	    buddiesPresence = WorkspaceFactory.createBuddiesPresenceComponent();
	}
	return buddiesPresence;
    }

    public ParticipationComponent getParticipationComponent() {
	if (participatesInGroups == null) {
	    participatesInGroups = WorkspaceFactory.createParticipationComponent();
	}
	return participatesInGroups;
    }
}
