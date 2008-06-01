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

import java.util.HashMap;

import org.ourproject.kune.platf.client.app.ApplicationBuilder;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.ui.Location;
import org.ourproject.kune.platf.client.ui.WindowUtils;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
        Location loc = WindowUtils.getLocation();
        String locale = loc.getParameter("locale");
        final I18nServiceAsync server = I18nService.App.getInstance();
        server.getInitialLanguage(locale, new AsyncCallback<I18nLanguageDTO>() {
            public void onFailure(final Throwable caught) {
                Log.debug("Workspace adaptation to your language failed");
            }

            public void onSuccess(final I18nLanguageDTO initialLang) {
                server.getLexicon(initialLang.getCode(), new AsyncCallback<HashMap<String, String>>() {
                    public void onFailure(final Throwable caught) {
                        Log.debug("Workspace adaptation to your language failed");
                    }

                    public void onSuccess(final HashMap<String, String> lexicon) {
                        new ApplicationBuilder().build(initialLang, lexicon);
                    }
                });
            }
        });
    }
}
