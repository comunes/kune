package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.gwtui.GwtIconLabelGui;
import cc.kune.common.client.actions.gwtui.GwtToolbarSeparatorGui;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GxtGuiProvider {

    @Inject
    public GxtGuiProvider(final GuiProvider guiProvider, final Provider<GxtSubMenuGui> gxtSubMenuGui,
            final Provider<GxtMenuGui> gxtMenuGui, final Provider<GxtMenuItemGui> gxtMenuItemGui,
            final Provider<GxtMenuSeparatorGui> gxtMenuSeparatorGui, final Provider<GxtPushButtonGui> gxtPushButtonGui,
            final Provider<GxtButtonGui> gxtButtonGui, final Provider<GwtIconLabelGui> gwtIconLabelGui,
            final Provider<GxtToolbarGui> gxtToolbarGui, final Provider<GwtToolbarSeparatorGui> gxtToolbarSeparatorGui) {

        guiProvider.register(SubMenuDescriptor.class, gxtSubMenuGui);
        guiProvider.register(MenuDescriptor.class, gxtMenuGui);
        guiProvider.register(MenuRadioItemDescriptor.class, gxtMenuItemGui);
        guiProvider.register(MenuCheckItemDescriptor.class, gxtMenuItemGui);
        guiProvider.register(MenuItemDescriptor.class, gxtMenuItemGui);
        guiProvider.register(MenuSeparatorDescriptor.class, gxtMenuSeparatorGui);
        guiProvider.register(PushButtonDescriptor.class, gxtPushButtonGui);
        guiProvider.register(ButtonDescriptor.class, gxtButtonGui);
        guiProvider.register(IconLabelDescriptor.class, gwtIconLabelGui);
        guiProvider.register(ToolbarDescriptor.class, gxtToolbarGui);
        guiProvider.register(ToolbarSeparatorDescriptor.class, gxtToolbarSeparatorGui);

    }
}
