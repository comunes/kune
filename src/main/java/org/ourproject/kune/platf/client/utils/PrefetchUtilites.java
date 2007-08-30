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

package org.ourproject.kune.platf.client.utils;

import java.util.List;

import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

public class PrefetchUtilites {

    public static void preFetchImpImages() {
	Image.prefetch("images/spin-kune-thund-green.gif");
	Image.prefetch("css/img/button-bg-hard.gif");
	Image.prefetch("css/img/button-bg-soft.gif");

    }

    public static void preFetchLicenses(final Session session) {
	KuneServiceAsync kuneService = KuneService.App.getInstance();
	kuneService.getAllLicenses(new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		// TODO
		Site.error("Error fetching initial data");
	    }

	    public void onSuccess(final Object result) {
		session.setCCLicenses((List) result);
	    }
	});

	kuneService.getNotCCLicenses(new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		// TODO
		Site.error("Error fetching initial data");
	    }

	    public void onSuccess(final Object result) {
		session.setNotCCLicenses((List) result);
	    }
	});

    }
}
