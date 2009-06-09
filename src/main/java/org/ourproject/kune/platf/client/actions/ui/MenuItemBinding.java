package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.errors.UIException;

import com.gwtext.client.widgets.menu.Item;

public class MenuItemBinding extends GuiBindingAdapter {

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        final MenuItemGui item = new MenuItemGui((MenuItemDescriptor) descriptor);
        final int position = descriptor.getPosition();
        final Item menuItem = (Item) item.getWidget();
        final AbstractMenuGui menu = ((AbstractMenuGui) descriptor.getParent().getValue(MenuBinding.UI_MENU));
        if (menu == null) {
            throw new UIException("To add a menu item you need to add the menu before. Item: " + descriptor);
        }
        if (position == GuiActionDescrip.NO_POSITION) {
            menu.add(menuItem);
        } else {
            menu.insert(position, menuItem);
        }
        return item;
    }

    @Override
    public boolean isAttachable() {
        return false;
    }
}
