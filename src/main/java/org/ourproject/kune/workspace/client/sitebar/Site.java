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

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.newgroup.ui.SiteErrorType;
import org.ourproject.kune.workspace.client.sitebar.msg.SiteMessage;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteprogress.SiteProgress;

import com.calclab.suco.client.container.Provider;

public class Site {
    public static final String USERHASH = "userHash";
    public static final String IN_DEVELOPMENT = " (in development)";
    private static I18nTranslationService i18n;
    private static SiteProgress progress;
    private static Provider<SiteMessage> siteMessageProvider;

    @Deprecated
    public static void doNewGroup(final String returnToken) {
	// sitebar.doNewGroup(returnToken);
    }

    public static void error(final String value) {
	getSiteMessage().setMessage(value, SiteErrorType.error);
    }

    public static void hideProgress() {
	progress.hideProgress();
    }

    public static void important(final String value) {
	getSiteMessage().setMessage(value, SiteErrorType.imp);
    }

    public static void info(final String value) {
	getSiteMessage().setMessage(value, SiteErrorType.info);
    }

    public static void showProgress(final String text) {
	progress.showProgress(text);
    }

    public static void showProgressLoading() {
	progress.showProgress(i18n.t("Loading"));
    }

    public static void showProgressProcessing() {
	progress.showProgress(i18n.t("Processing"));
    }

    public static void showProgressSaving() {
	progress.showProgress(i18n.t("Saving"));
    }

    public static void showProgressStarting() {
	progress.showProgress(i18n.t("Starting"));
    }

    public static void veryImportant(final String value) {
	getSiteMessage().setMessage(value, SiteErrorType.veryimp);
    }

    private static SiteMessage getSiteMessage() {
	return siteMessageProvider.get();
    }

    public Site(final I18nUITranslationService i18n, final SiteProgress progress,
	    final Provider<SiteMessage> siteMessageProvider) {
	Site.i18n = i18n;
	Site.progress = progress;
	Site.siteMessageProvider = siteMessageProvider;
    }

}
