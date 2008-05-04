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

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.newgroup.ui.SiteErrorType;
import org.ourproject.kune.workspace.client.sitebar.bar.SiteBar;
import org.ourproject.kune.workspace.client.sitebar.msg.SiteMessage;

public class Site {

    public static SiteMessage siteUserMessage;

    public static SiteBar sitebar;

    public static final String LOGIN_TOKEN = "login";

    public static final String NEWGROUP_TOKEN = "newgroup";

    public static final String FIXME_TOKEN = "fixme";

    public static final String TRANSLATE_TOKEN = "translate";

    public static final String IN_DEVELOPMENT = " (in development)";

    public static void doLogin(final String returnToken) {
	sitebar.doLogin(returnToken);
    }

    public static void doLogout() {
	sitebar.doLogout();
    }

    public static void doNewGroup(final String returnToken) {
	sitebar.doNewGroup(returnToken);
    }

    public static void error(final String value) {
	siteUserMessage.setMessage(value, SiteErrorType.error);
    }

    public static void hideProgress() {
	sitebar.hideProgress();
    }

    public static void important(final String value) {
	siteUserMessage.setMessage(value, SiteErrorType.imp);
    }

    public static void info(final String value) {
	siteUserMessage.setMessage(value, SiteErrorType.info);
    }

    public static void mask() {
	sitebar.mask();
    }

    public static void mask(final String message) {
	sitebar.mask(message);
    }

    public static void showAlertMessage(final String message) {
	sitebar.showAlertMessage(message);
    }

    public static void showProgress(final String text) {
	sitebar.showProgress(text);
    }

    public static void showProgressLoading() {
	sitebar.showProgress(Kune.I18N.t("Loading"));
    }

    public static void showProgressProcessing() {
	sitebar.showProgress(Kune.I18N.t("Processing"));
    }

    public static void showProgressSaving() {
	sitebar.showProgress(Kune.I18N.t("Saving"));
    }

    public static void unMask() {
	sitebar.unMask();
    }

    public static void veryImportant(final String value) {
	siteUserMessage.setMessage(value, SiteErrorType.veryimp);
    }

}
