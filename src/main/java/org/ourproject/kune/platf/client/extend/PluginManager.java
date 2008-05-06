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

import java.util.HashMap;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.services.I18nTranslationService;

public class PluginManager {

    private final HashMap<String, Plugin> plugins;
    private final Dispatcher dispacher;
    private final ExtensibleWidgetsManager extensionPointManager;
    private final I18nTranslationService i18n;

    public PluginManager(final Dispatcher dispacher, final ExtensibleWidgetsManager extensionPointManager,
            final I18nTranslationService i18n) {
        plugins = new HashMap<String, Plugin>();
        this.dispacher = dispacher;
        this.extensionPointManager = extensionPointManager;
        this.i18n = i18n;
    }

    public void install(final Plugin plugin) {
        String name = plugin.getName();
        if (plugins.containsKey(name)) {
            throw new RuntimeException("Plugin with this name already installed");
        }
        plugins.put(name, plugin);
        plugin.init(dispacher, extensionPointManager, i18n);
        plugin.start();
    }

    public void uninstall(final String name) {
        if (!plugins.containsKey(name)) {
            throw new RuntimeException("Plugin with this name not installed");
        }
        Plugin plugin = plugins.get(name);
        plugin.stop();
        plugins.remove(name);
    }

}
