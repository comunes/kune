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

package org.ourproject.kune.sitebar.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.license.LicenseChoose;
import org.ourproject.kune.platf.client.license.LicenseChoosePanel;
import org.ourproject.kune.platf.client.license.LicenseChoosePresenter;
import org.ourproject.kune.platf.client.newgroup.NewGroup;
import org.ourproject.kune.platf.client.newgroup.NewGroupListener;
import org.ourproject.kune.platf.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.platf.client.newgroup.ui.NewGroupPanel;
import org.ourproject.kune.platf.client.search.SearchSite;
import org.ourproject.kune.platf.client.search.SearchSitePresenter;
import org.ourproject.kune.platf.client.search.SearchSiteView;
import org.ourproject.kune.platf.client.search.ui.SearchSitePanel;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.sitebar.client.bar.SiteBarPanel;
import org.ourproject.kune.sitebar.client.bar.SiteBarPresenter;
import org.ourproject.kune.sitebar.client.login.Login;
import org.ourproject.kune.sitebar.client.login.LoginListener;
import org.ourproject.kune.sitebar.client.login.LoginPanel;
import org.ourproject.kune.sitebar.client.login.LoginPresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePanel;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessageView;

public class SiteBarFactory {
    private static SiteMessage siteMessage;
    private static Login login;
    private static NewGroup newGroup;
    private static SearchSite search;
    private static Session session;

    public static SiteBar createSiteBar(final SiteBarListener listener, final Session session) {
        SiteBarFactory.session = session;
        SiteBarPresenter siteBarPresenter = new SiteBarPresenter(listener, session);
        SiteBarPanel siteBarView = new SiteBarPanel(siteBarPresenter);
        siteBarPresenter.init(siteBarView);
        Site.sitebar = siteBarPresenter;
        return siteBarPresenter;
    }

    public static SiteMessage getSiteMessage() {
        if (siteMessage == null) {
            SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
            SiteMessageView siteMessageView = new SiteMessagePanel(siteMessagePresenter, true);
            siteMessagePresenter.init(siteMessageView);
            siteMessage = siteMessagePresenter;
            Site.siteUserMessage = siteMessagePresenter;
        }
        return siteMessage;
    }

    public static Login getLoginForm(final LoginListener listener) {
        if (login == null) {
            LoginPresenter presenter = new LoginPresenter(session, listener);
            LoginPanel view = new LoginPanel(presenter);
            presenter.init(view);
            login = presenter;
        }
        return login;
    }

    public static NewGroup getNewGroupForm(final NewGroupListener listener) {
        if (newGroup == null) {
            NewGroupPresenter presenter = new NewGroupPresenter(listener);
            NewGroupPanel view = new NewGroupPanel(presenter);
            presenter.init(view);
            newGroup = presenter;
        }
        return newGroup;
    }

    public static SearchSite getSearch() {
        if (search == null) {
            SearchSitePresenter presenter = new SearchSitePresenter();
            SearchSiteView view = new SearchSitePanel(presenter);
            presenter.init(view);
            search = presenter;
        }
        return search;
    }

    public static LicenseChoose createLicenseChoose() {
        List<LicenseDTO> licensesList = session.getLicenses();
        List<LicenseDTO> licensesNonCCList = new ArrayList<LicenseDTO>();

        for (Iterator<LicenseDTO> iterator = licensesList.iterator(); iterator.hasNext();) {
            LicenseDTO license = iterator.next();
            if (!license.isCC()) {
                licensesNonCCList.add(license);
            }
        }
        LicenseChoosePresenter presenter = new LicenseChoosePresenter();
        LicenseChoosePanel view = new LicenseChoosePanel(licensesNonCCList, presenter);
        presenter.init(view, licensesList, licensesNonCCList);
        return presenter;
    }
}
