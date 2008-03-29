package org.ourproject.kune.platf.client.extend;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.services.I18nTranslationService;

public class PluginManager {

    private final HashMap<String, Plugin> plugins;
    private final Dispatcher dispacher;
    private final UIExtensionPointManager extensionPointManager;
    private final I18nTranslationService i18n;

    public PluginManager(final Dispatcher dispacher, final UIExtensionPointManager extensionPointManager,
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
