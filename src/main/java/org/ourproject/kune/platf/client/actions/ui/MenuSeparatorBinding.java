package org.ourproject.kune.platf.client.actions.ui;

public class MenuSeparatorBinding extends GuiBindingAdapter {

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        final AbstractMenuGui menu = ((AbstractMenuGui) descriptor.getParent().getValue(MenuBinding.UI_MENU));
        menu.addSeparator();
        return menu;
    }

    @Override
    public boolean isAttachable() {
        return false;
    }
}
