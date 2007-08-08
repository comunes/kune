package org.ourproject.kune.platf.client.license;

import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;

public class LicenseChoosePresenter {

    private LicenseChooseView view;

    private LicenseChangeListener listener;

    private List nonCCLicenses;

    private List allLicenses;

    public void init(final LicenseChooseView view, final List allLicenses, final List nonCCLicenses,
	    final LicenseChangeListener listener) {
	this.view = view;
	this.listener = listener;
	this.allLicenses = allLicenses;
	this.nonCCLicenses = nonCCLicenses;
	this.view.reset();
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
	listener.onLicenseChange(getLicenseFromShortName(licenseShortName));
    }

    private LicenseDTO getLicenseFromShortName(final String shortName) {
	for (int i = 0; i < allLicenses.size(); i++) {
	    LicenseDTO licenseDTO = ((LicenseDTO) allLicenses.get(i));
	    if (licenseDTO.getShortName() == shortName) {
		return licenseDTO;
	    }
	}
	return null;
    }

    public void onCCselected() {
	view.showCCoptions();
    }

    public void onNotCCselected() {
	view.showNotCCoptiones();
    }

}
