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

import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;

public class Site {
    public static SiteMessage siteUserMessage;

    public static SiteBar sitebar;

    public static void info(final String value) {
	siteUserMessage.setValue(value, SiteMessage.INFO);
    }

    public static void important(final String value) {
	siteUserMessage.setValue(value, SiteMessage.IMP);
    }

    public static void veryImportant(final String value) {
	siteUserMessage.setValue(value, SiteMessage.VERYIMP);
    }

    public static void error(final String value) {
	siteUserMessage.setValue(value, SiteMessage.ERROR);
    }

    public static void showProgress(final String text) {
	sitebar.showProgress(text);
    }

    public static void hideProgress() {
	sitebar.hideProgress();
    }

    public static void showProgressProcessing() {
	// i18n
	sitebar.showProgress("Processing");
    }

    public static void showProgressLoading() {
	// i18n
	sitebar.showProgress("Loading");
    }

}
