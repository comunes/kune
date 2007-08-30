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

package org.ourproject.kune.app.client;

import org.ourproject.kune.chat.client.ChatClientModule;
import org.ourproject.kune.docs.client.DocsClientModule;
import org.ourproject.kune.platf.client.KuneClientModule;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationBuilder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;

public class KuneEntryPoint implements EntryPoint {

    public KuneEntryPoint() {
    }

    public void onModuleLoad() {
	String userHash = obtainUserHash();

	// if (userHash == null) {
	// MockServer.start(MockServer.GWT);
	// }

	if (isNotValid(userHash)) {
	    informUserAndStop();
	}
	KunePlatform platform = new KunePlatform();
	platform.install(new KuneClientModule());
	platform.install(new DocsClientModule());
	platform.install(new ChatClientModule());
	Application app = new ApplicationBuilder(userHash, platform).build();
	app.start();

    }

    private boolean isNotValid(final String userHash) {
	return false;
    }

    private void informUserAndStop() {
	throw new RuntimeException("not logged in!");
    }

    private String obtainUserHash() {
	String cookie = Cookies.getCookie("userHash");
	GWT.log("USER HASH: " + cookie, null);
	return cookie;
    }

}
