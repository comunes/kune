/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.workspace.client.licensechoose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.state.Session;

import com.allen_sauer.gwt.log.client.Log;

public class LicenseChoosePresenter implements LicenseChoose, View {

    private LicenseChooseView view;

    private final List<LicenseDTO> licenses;

    private final List<LicenseDTO> licensesNonCCList;

    public LicenseChoosePresenter(final Session session) {
        licensesNonCCList = new ArrayList<LicenseDTO>();
        licenses = session.getLicenses();
        for (final Iterator<LicenseDTO> iterator = licenses.iterator(); iterator.hasNext();) {
            final LicenseDTO license = iterator.next();
            if (!license.isCC()) {
                licensesNonCCList.add(license);
            }
        }
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
            licenseShortName = licensesNonCCList.get(view.getSelectedNonCCLicenseIndex()).getShortName();
        }
        return getLicenseFromShortName(licenseShortName);
    }

    public List<LicenseDTO> getNonCCLicenses() {
        return licensesNonCCList;
    }

    public View getView() {
        return view;
    }

    public void init(final LicenseChooseView view) {
        this.view = view;
        this.view.reset();
    }

    public void onChange() {
        final LicenseDTO licenseDTO = getLicense();
        if (licenseDTO.isCopyleft()) {
            view.showIsCopyleft();
        } else {
            view.showIsNotCopyleft();
        }
    }

    private LicenseDTO getLicenseFromShortName(final String shortName) {
        for (int i = 0; i < licenses.size(); i++) {
            final LicenseDTO licenseDTO = licenses.get(i);
            if (licenseDTO.getShortName().equals(shortName)) {
                return licenseDTO;
            }
        }
        Log.error("Internal error: License not found");
        throw new IndexOutOfBoundsException("License not found");
    }

}
