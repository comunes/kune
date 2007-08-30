package org.ourproject.kune.platf.client.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InitDataDTO implements IsSerializable {
    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    ArrayList ccLicenses;
    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    ArrayList notCCLicenses;

    UserDTO currentUser;

    public ArrayList getCCLicenses() {
	return ccLicenses;
    }

    public void setCCLicenses(final ArrayList ccLicenses) {
	this.ccLicenses = ccLicenses;
    }

    public ArrayList getNotCCLicenses() {
	return notCCLicenses;
    }

    public void setNotCCLicenses(final ArrayList notLicenses) {
	this.notCCLicenses = notLicenses;
    }

    public UserDTO getCurrentUser() {
	return currentUser;
    }

    public void setCurrentUser(final UserDTO currentUser) {
	this.currentUser = currentUser;
    }

}
