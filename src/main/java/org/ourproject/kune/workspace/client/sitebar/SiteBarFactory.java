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
package org.ourproject.kune.workspace.client.sitebar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoose;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoosePanel;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoosePresenter;
import org.ourproject.kune.workspace.client.newgroup.NewGroup;
import org.ourproject.kune.workspace.client.newgroup.NewGroupListener;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.workspace.client.newgroup.ui.NewGroupPanel;
import org.ourproject.kune.workspace.client.sitebar.login.Login;
import org.ourproject.kune.workspace.client.sitebar.login.LoginListener;
import org.ourproject.kune.workspace.client.sitebar.login.LoginPanel;
import org.ourproject.kune.workspace.client.sitebar.login.LoginPresenter;

public class SiteBarFactory {
    private static Login login;
    private static NewGroup newGroup;
    private static Session session;
    private static I18nUITranslationService i18n;

    public static LicenseChoose createLicenseChoose() {
	final List<LicenseDTO> licensesList = session.getLicenses();
	final List<LicenseDTO> licensesNonCCList = new ArrayList<LicenseDTO>();

	for (final Iterator<LicenseDTO> iterator = licensesList.iterator(); iterator.hasNext();) {
	    final LicenseDTO license = iterator.next();
	    if (!license.isCC()) {
		licensesNonCCList.add(license);
	    }
	}
	final LicenseChoosePresenter presenter = new LicenseChoosePresenter();
	final LicenseChoosePanel view = new LicenseChoosePanel(licensesNonCCList, presenter, i18n);
	presenter.init(view, licensesList, licensesNonCCList);
	return presenter;
    }

    public static Login getLoginForm(final LoginListener listener) {
	if (login == null) {
	    final LoginPresenter presenter = new LoginPresenter(session, listener, i18n);
	    final LoginPanel view = new LoginPanel(presenter, i18n);
	    presenter.init(view);
	    login = presenter;
	}
	return login;
    }

    public static NewGroup getNewGroupForm(final NewGroupListener listener) {
	if (newGroup == null) {
	    final NewGroupPresenter presenter = new NewGroupPresenter(listener, i18n);
	    final NewGroupPanel view = new NewGroupPanel(presenter, i18n);
	    presenter.init(view);
	    newGroup = presenter;
	}
	return newGroup;
    }

}
