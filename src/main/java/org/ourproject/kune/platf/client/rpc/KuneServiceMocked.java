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

package org.ourproject.kune.platf.client.rpc;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class KuneServiceMocked extends MockedService implements KuneServiceAsync {
    public void getDefaultGroup(final String userHash, final AsyncCallback callback) {
	final GroupDTO group = new GroupDTO("Kune dev", "kune", "This is the default group", null,
		GroupDTO.TYPE_ORGANIZATION);
	answer(group, callback);
    }

    public void createNewGroup(final GroupDTO group, final AsyncCallback callback) {
	answer(null, callback);
    }

    public void getNotCCLicenses(final AsyncCallback callback) {
	List licenseList = new ArrayList();
	licenseList.add(new LicenseDTO("gfdl", "GNU Free Documentation License", "",
		"http://www.gnu.org/copyleft/fdl.html", false, true, false, "", ""));
	answer(licenseList, callback);
    }

    public void getAllLicenses(final AsyncCallback callback) {
	List licenseList = new ArrayList();
	licenseList.add(new LicenseDTO("by", "Creative Commons Attribution", "",
		"http://creativecommons.org/licenses/by/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("by-sa", "Creative Commons Attribution-ShareAlike", "",
		"http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", ""));
	licenseList.add(new LicenseDTO("by-nd", "Creative Commons Attribution-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("by-nc", "Creative Commons Attribution-NonCommercial", "",
		"http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("by-nc-sa", "Creative Commons Attribution-NonCommercial-ShareAlike", "",
		"http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("gfdl", "GNU Free Documentation License", "",
		"http://www.gnu.org/copyleft/fdl.html", false, true, false, "", ""));
	answer(licenseList, callback);
    }

    public void getGroup(final String userHash, final String shortName, final AsyncCallback asyncCallback) {

    }
}
