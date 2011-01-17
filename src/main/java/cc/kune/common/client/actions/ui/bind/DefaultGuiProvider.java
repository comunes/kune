package cc.kune.common.client.actions.ui.bind;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Provider;

public class DefaultGuiProvider implements GuiProvider {

    private final Map<Class<?>, Provider<?>> map;

    public DefaultGuiProvider() {
        map = new HashMap<Class<?>, Provider<?>>();
    }

    @Override
    public <T> GuiBinding get(final Class<T> classType) {
        return (GuiBinding) map.get(classType).get();
    }

    @Override
    public <T, Z> void register(final Class<T> classType, final Provider<Z> binding) {
        map.put(classType, binding);
    }
}
