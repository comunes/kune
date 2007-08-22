/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.license;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public class LicenseChooseFormPresenter implements LicenseChooseForm {

    private LicenseChooseFormView view;

    private final LicenseChangeListener listener;

    private List allLicenses;

    private List nonCCLicenses;

    private List CClicenses;

    public LicenseChooseFormPresenter(final LicenseChangeListener listener) {
	this.listener = listener;
    }

    public void init(final LicenseChooseFormView view, final List allLicenses) {
	this.view = view;
	this.allLicenses = allLicenses;

	CClicenses = new ArrayList();
	nonCCLicenses = new ArrayList();
	for (int i = 0; i < allLicenses.size(); i++) {
	    LicenseDTO licenseDTO = ((LicenseDTO) allLicenses.get(i));
	    if (licenseDTO.isCC()) {
		CClicenses.add(licenseDTO);
	    } else {
		nonCCLicenses.add(licenseDTO);
	    }
	}

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

    public View getView() {
	return view;
    }

}
