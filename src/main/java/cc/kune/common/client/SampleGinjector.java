package cc.kune.common.client;

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.gxtui.GxtGuiProvider;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(SampleGinModule.class)
public interface SampleGinjector extends Ginjector {
    GlobalShortcutRegister getGlobalShortcutRegister();

    GuiProvider getGuiProvider();

    GwtGuiProvider getGwtGuiProvider();

    GxtGuiProvider getGxtGuiProvider();
}