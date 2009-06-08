package org.ourproject.kune.platf.client.actions.ui;

import java.util.HashMap;
import java.util.Map;

public class GuiBindingsRegister {

    private final Map<Class<?>, GuiBinding> map;

    public GuiBindingsRegister() {
        map = new HashMap<Class<?>, GuiBinding>();
    }

    public <T> GuiBinding get(final Class<T> classType) {
        return map.get(classType);
    }

    public <T> void register(final Class<T> classType, final GuiBinding binding) {
        map.put(classType, binding);
    }
}
