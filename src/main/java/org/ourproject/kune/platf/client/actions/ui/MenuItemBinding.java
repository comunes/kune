package org.ourproject.kune.platf.client.actions.ui;

import com.gwtext.client.widgets.menu.Item;

public class MenuItemBinding extends GuiBindingAdapter {

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        final MenuItemGui item = new MenuItemGui((MenuItemDescriptor) descriptor);
        final int position = descriptor.getPosition();
        final Item menuItem = (Item) item.getWidget();
        final AbstractMenuGui menu = ((AbstractMenuGui) descriptor.getParent().getValue(MenuBinding.UI_MENU));
        if (position == AbstractGuiActionDescrip.NO_POSITION) {
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
