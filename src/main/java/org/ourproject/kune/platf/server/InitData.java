package org.ourproject.kune.platf.server;

import java.util.List;

import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;

public class InitData {
    private List<License> ccLicenses;
    private List<License> notCCLicenses;
    private User currentUser;

    public void setCCLicenses(final List<License> ccLicenses) {
	this.ccLicenses = ccLicenses;
    }

    public List<License> getCCLicenses() {
	return ccLicenses;
    }

    public void setNotCCLicenses(final List<License> notCCLicenses) {
	this.notCCLicenses = notCCLicenses;
    }

    public List<License> getNotCCLicenses() {
	return notCCLicenses;
    }

    public void setCurrentUser(final User currentUser) {
	this.currentUser = currentUser;
    }

    public User getCurrentUser() {
	return currentUser;
    }

}
