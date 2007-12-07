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

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;

public class Site {
    public static SiteMessage siteUserMessage;

    public static SiteBar sitebar;

    public static final String LOGIN_TOKEN = "login";

    public static final String NEWGROUP_TOKEN = "newgroup";

    public static final String FIXME_TOKEN = "fixme";

    public static void info(final String value) {
        siteUserMessage.setMessage(value, SiteMessage.INFO);
    }

    public static void important(final String value) {
        siteUserMessage.setMessage(value, SiteMessage.IMP);
    }

    public static void veryImportant(final String value) {
        siteUserMessage.setMessage(value, SiteMessage.VERYIMP);
    }

    public static void error(final String value) {
        siteUserMessage.setMessage(value, SiteMessage.ERROR);
    }

    public static void showProgress(final String text) {
        sitebar.showProgress(text);
    }

    public static void hideProgress() {
        sitebar.hideProgress();
    }

    public static void showProgressProcessing() {
        sitebar.showProgress(Kune.I18N.t("Processing"));
    }

    public static void showProgressLoading() {
        sitebar.showProgress(Kune.I18N.t("Loading"));
    }

    public static void doNewGroup(final String returnToken) {
        sitebar.doNewGroup(returnToken);
    }

    public static void doLogin(final String returnToken) {
        sitebar.doLogin(returnToken);
    }

    public static void showAlertMessage(final String message) {
        sitebar.showAlertMessage(message);
    }

}
