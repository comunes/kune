package org.ourproject.kune.platf.client.registry;

import java.util.ArrayList;

public abstract class AbstractContentRegistry {
    private final ArrayList<String> registry;

    public AbstractContentRegistry() {
        registry = new ArrayList<String>();
    }

    public boolean contains(String typeId) {
        return registry.contains(typeId);
    }

    public void register(String... typeIds) {
        for (String typeId : typeIds) {
            registry.add(typeId);
        }
    }

    @Override
    public String toString() {
        return "registry: " + registry;
    }

    public void unregister(String... typeIds) {
        for (String typeId : typeIds) {
            registry.remove(typeId);
        }
    }
}
