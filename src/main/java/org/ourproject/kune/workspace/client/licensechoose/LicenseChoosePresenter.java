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

package org.ourproject.kune.workspace.client.licensechoose;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public class LicenseChoosePresenter implements LicenseChoose, View {

    private LicenseChooseView view;

    private List<LicenseDTO> licenses;

    private List<LicenseDTO> licensesNonCCList;

    public LicenseChoosePresenter() {
    }

    public void init(final LicenseChooseView view, final List<LicenseDTO> licenses,
            final List<LicenseDTO> licensesNonCCList) {
        this.view = view;
        this.licenses = licenses;
        this.licensesNonCCList = licensesNonCCList;
        this.view.reset();
    }

    public LicenseDTO getLicense() {
        String licenseShortName;

        if (view.isCCselected()) {
            if (view.permitComercial()) {
                licenseShortName = view.isAllowModif() ? "by" : view.isAllowModifShareAlike() ? "by-sa" : "by-nd";
            } else {
                licenseShortName = view.isAllowModif() ? "by-nc" : view.isAllowModifShareAlike() ? "by-nc-sa"
                        : "by-nc-nd";
            }
        } else {
            licenseShortName = (licensesNonCCList.get(view.getSelectedNonCCLicenseIndex())).getShortName();
        }
        return getLicenseFromShortName(licenseShortName);
    }

    private LicenseDTO getLicenseFromShortName(final String shortName) {
        for (int i = 0; i < licenses.size(); i++) {
            LicenseDTO licenseDTO = licenses.get(i);
            if (licenseDTO.getShortName() == shortName) {
                return licenseDTO;
            }
        }
        throw new IndexOutOfBoundsException("License not found");
    }

    public View getView() {
        return view;
    }

    public void onChange() {
        LicenseDTO licenseDTO = getLicense();
        if (licenseDTO.isCopyleft()) {
            view.showIsCopyleft();
        } else {
            view.showIsNotCopyleft();
        }
    }

}
