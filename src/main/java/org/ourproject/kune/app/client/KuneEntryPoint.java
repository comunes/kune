/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import org.ourproject.kune.platf.client.services.Loader;
import org.ourproject.kune.platf.client.services.PlatformModule;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class KuneEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        Log.setUncaughtExceptionHandler();

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                onModuleLoadCont();
            }
        });
    }

    public void onModuleLoadCont() {
        // At the moment, in runtime:
        Log.setCurrentLogLevel(Log.LOG_LEVEL_DEBUG);
        // final WsArmorResources resources = WsArmorResources.INSTANCE;
        // resources.style().ensureInjected();
        //
        // final Body body = new Body();
        // RootLayoutPanel.get().add(body);
        // Loader.install(new RegistryModule(), new DocumentClientModule(), new
        // BlogClientModule(),
        // new WikiClientModule(), new GalleryClientModule(), new
        // EmiteUIModule(), new ChatClientModule(),
        // new WorkspaceModule(), new PlatformModule());

        Loader.install(new PlatformModule());

        // We install our HelloWorldModule
        // Loader.install(new HelloWorldModule());
    }
}
