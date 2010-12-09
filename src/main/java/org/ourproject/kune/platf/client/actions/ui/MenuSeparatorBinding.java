package org.ourproject.kune.platf.client.actions.ui;

import cc.kune.core.client.errors.UIException;

public class MenuSeparatorBinding extends GuiBindingAdapter {

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        final AbstractMenuGui menu = ((AbstractMenuGui) descriptor.getParent().getValue(MenuBinding.UI_MENU));
        if (menu == null) {
            throw new UIException("To add a menu separator you need to add the menu before. Item: " + descriptor);
        }
        menu.addSeparator();
        return menu;
    }

    @Override
    public boolean isAttachable() {
        return false;
    }
}
