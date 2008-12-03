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
package org.ourproject.kune.app.client;

import org.ourproject.kune.blogs.client.BlogClientModule;
import org.ourproject.kune.chat.client.ChatClientModule;
import org.ourproject.kune.docs.client.DocumentClientModule;
import org.ourproject.kune.gallery.client.GalleryClientModule;
import org.ourproject.kune.platf.client.services.KuneCoreModule;
import org.ourproject.kune.platf.client.services.KuneModule;
import org.ourproject.kune.platf.client.services.KunePlatformModule;
import org.ourproject.kune.platf.client.services.KuneRegistryModule;
import org.ourproject.kune.platf.client.services.KuneWorkspaceModule;
import org.ourproject.kune.wiki.client.WikiClientModule;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emiteuimodule.client.EmiteUIModule;
import com.calclab.suco.client.Suco;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class KuneEntryPoint implements EntryPoint {

    public KuneEntryPoint() {
    }

    public void onModuleLoad() {
        Log.setUncaughtExceptionHandler();

        // At the moment, in runtime:
        Log.setCurrentLogLevel(Log.LOG_LEVEL_DEBUG);

        DeferredCommand.addCommand(new Command() {
            public void execute() {
                onModuleLoadCont();
            }
        });
    }

    public void onModuleLoadCont() {
        Suco.install(new KuneCoreModule(), new KuneRegistryModule(), new KunePlatformModule(),
                new KuneWorkspaceModule(), new EmiteUIModule(), new DocumentClientModule(), new BlogClientModule(),
                new WikiClientModule(), new GalleryClientModule(), new ChatClientModule(), new KuneModule());
    }
}
