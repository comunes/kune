package org.ourproject.kune.app.client;

import java.util.HashMap;

import org.ourproject.kune.platf.client.Services;

public class PluginManager {

    private final Services services;
    private final HashMap<String, Plugin> plugins;

    public PluginManager(final Services services) {
        this.services = services;
        plugins = new HashMap<String, Plugin>();
    }

    public void install(final Plugin plugin) {
        String name = plugin.getName();
        if (plugins.containsKey(name)) {
            throw new RuntimeException("Plugin with this name already installed");
        }
        plugins.put(name, plugin);
        plugin.init(services);
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
