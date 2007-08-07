package org.ourproject.kune.platf.client.license;

import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;

public class LicenseChoosePresenter {

    private LicenseChooseView view;

    private LicenseChangeListener listener;

    private List nonCCLicenses;

    public void init(final LicenseChooseView view, final LicenseChangeListener listener, final List nonCCLicenses) {
	this.view = view;
	this.listener = listener;
	this.nonCCLicenses = nonCCLicenses;
	this.view.setDefaults(nonCCLicenses);
    }

    public void onCancel() {
	listener.onCancel();
    }

    public void onSelect() {
	String licenseShortName;

	if (view.isCCselected()) {
	    if (view.permitComercial()) {
		licenseShortName = (view.isAllowModif() ? "by" : (view.isAllowModifShareAlike() ? "by-sa" : "by-nd"));
	    } else {
		licenseShortName = (view.isAllowModif() ? "by-nc" : (view.isAllowModifShareAlike() ? "by-nc-sa"
			: "by-nc-nd"));
	    }
	} else {
	    licenseShortName = ((LicenseDTO) nonCCLicenses.get(view.getSelectedNonCCLicenseIndex())).getShortName();

	}
	listener.onLicenseChange(licenseShortName);
    }

}
