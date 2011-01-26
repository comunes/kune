package cc.kune.common.client;

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.gxtui.GxtGuiProvider;
import cc.kune.common.client.actions.ui.bind.DefaultGuiProvider;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.shortcuts.DefaultGlobalShortcutRegister;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class SampleGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);
        bind(GxtGuiProvider.class).in(Singleton.class);
        bind(GwtGuiProvider.class).in(Singleton.class);
        bind(GlobalShortcutRegister.class).to(DefaultGlobalShortcutRegister.class).in(Singleton.class);
    }
}
