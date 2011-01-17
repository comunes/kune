package cc.kune.common.client.actions.gwtui;

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

public class GwtGuiProvider {

    @Inject
    public GwtGuiProvider(final GuiProvider guiProvider, final Provider<GwtSubMenuGui> gwtSubMenuGui,
            final Provider<GwtMenuGui> gwtMenuGui, final Provider<GwtMenuItemGui> gwtMenuItemGui,
            final Provider<GwtMenuSeparatorGui> gwtMenuSeparatorGui, final Provider<GwtPushButtonGui> gwtPushButtonGui,
            final Provider<GwtButtonGui> gwtButtonGui, final Provider<GwtIconLabelGui> gwtIconLabelGui,
            final Provider<GwtToolbarGui> gwtToolbarGui, final Provider<GwtToolbarSeparatorGui> gwtToolbarSeparatorGui) {

        guiProvider.register(SubMenuDescriptor.class, gwtSubMenuGui);
        guiProvider.register(MenuDescriptor.class, gwtMenuGui);
        guiProvider.register(MenuRadioItemDescriptor.class, gwtMenuItemGui);
        guiProvider.register(MenuCheckItemDescriptor.class, gwtMenuItemGui);
        guiProvider.register(MenuItemDescriptor.class, gwtMenuItemGui);
        guiProvider.register(MenuSeparatorDescriptor.class, gwtMenuSeparatorGui);
        guiProvider.register(PushButtonDescriptor.class, gwtPushButtonGui);
        guiProvider.register(ButtonDescriptor.class, gwtButtonGui);
        guiProvider.register(IconLabelDescriptor.class, gwtIconLabelGui);
        guiProvider.register(ToolbarDescriptor.class, gwtToolbarGui);
        guiProvider.register(ToolbarSeparatorDescriptor.class, gwtToolbarSeparatorGui);
    }

}
