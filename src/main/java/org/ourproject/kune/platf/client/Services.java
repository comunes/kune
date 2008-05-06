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
package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetsManager;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

public class Services {
    public final Application app;
    public final StateManager stateManager;
    public final Dispatcher dispatcher;
    public final Session session;
    public final ExtensibleWidgetsManager extensionPointManager;
    public final I18nTranslationService i18n;

    public Services(final Application application, final StateManager stateManager, final Dispatcher dispatcher,
            final Session session, final ExtensibleWidgetsManager extensionPointManager,
            final I18nTranslationService i18n) {
        this.app = application;
        this.stateManager = stateManager;
        this.dispatcher = dispatcher;
        this.session = session;
        this.extensionPointManager = extensionPointManager;
        this.i18n = i18n;
    }

}
