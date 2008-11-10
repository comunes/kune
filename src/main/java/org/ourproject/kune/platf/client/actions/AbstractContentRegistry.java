package org.ourproject.kune.platf.client.actions;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;

public abstract class AbstractContentRegistry {
    private final ArrayList<String> registry;

    public AbstractContentRegistry() {
        registry = new ArrayList<String>();
    }

    public boolean contains(String typeId) {
        Log.debug("Renables: " + registry + " checking: " + typeId);
        return registry.contains(typeId);
    }

    public void register(String... typeIds) {
        for (String typeId : typeIds) {
            registry.add(typeId);
        }
    }

    public void unregister(String... typeIds) {
        for (String typeId : typeIds) {
            registry.remove(typeId);
        }
    }

}
