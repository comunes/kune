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
package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.services.I18nTranslationService;

public abstract class Plugin {
    private String name;
    private final boolean started;
    private ExtensibleWidgetsManager extensionPointManager;
    private I18nTranslationService i18n;
    private Dispatcher dispatcher;

    public Plugin(final String name) {
        this.name = name;
        this.started = false;
        // InitDataDTO...
    }

    protected void init(final Dispatcher dispatcher, final ExtensibleWidgetsManager extensionPointManager,
            final I18nTranslationService i18n) {
        this.dispatcher = dispatcher;
        this.extensionPointManager = extensionPointManager;
        this.i18n = i18n;
    }

    public final boolean isActive() {
        return started;
    }

    public final Dispatcher getDispatcher() {
        return dispatcher;
    }

    public final I18nTranslationService getI18n() {
        return i18n;
    }

    public ExtensibleWidgetsManager getExtensionPointManager() {
        return extensionPointManager;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    protected abstract void start();

    protected abstract void stop();

}
