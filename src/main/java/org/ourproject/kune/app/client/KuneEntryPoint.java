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
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationBuilder;
import org.ourproject.kune.workspace.client.WorkspaceClientModule;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;

public class KuneEntryPoint implements EntryPoint {

    public KuneEntryPoint() {
    }

    public void onModuleLoad() {
        String userHash = obtainUserHash();
        KunePlatform platform = new KunePlatform();
        platform.install(new WorkspaceClientModule());
        platform.install(new DocsClientModule());
        platform.install(new ChatClientModule());
        final Application app = new ApplicationBuilder(platform).build(userHash);
        Window.addWindowCloseListener(new WindowCloseListener() {
            public void onWindowClosed() {
                app.stop();
            }

            public String onWindowClosing() {
                return null;
            }
        });
        app.start();

    }

    private String obtainUserHash() {
        String userHash = Cookies.getCookie("userHash");
        GWT.log("UserHash: " + userHash, null);
        return userHash;
    }

}
