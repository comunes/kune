package cc.kune.common.client.actions.ui.bind;

import com.google.inject.Provider;

public interface GuiProvider {

    <T> GuiBinding get(final Class<T> classType);

    <T, Z> void register(final Class<T> classType, final Provider<Z> provider);

}
