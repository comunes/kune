package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.errors.UIException;

public class MenuItemDescriptor extends AbstractGuiActionDescrip {

    public MenuItemDescriptor(final AbstractAction action) {
        super(action);
        throw new UIException("You must define a menu item with its parent menu");
    }

    public MenuItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
        super(action);
        setParent(parent);
    }

    @Override
    public Class<?> getType() {
        return MenuItemDescriptor.class;
    }
}
