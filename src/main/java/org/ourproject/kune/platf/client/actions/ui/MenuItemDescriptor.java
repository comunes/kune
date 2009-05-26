package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;

import com.gwtext.client.widgets.menu.Item;

public class MenuItemDescriptor extends AbstractUIActionDescriptor {

    public MenuItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
        super(action);
        setParent(parent);
    }

    @Override
    public View getView() {
        if (view == NO_VIEW) {
            final DefaultMenuItem item = new DefaultMenuItem(this);
            final int position = getPosition();
            final Item menuItem = (Item) item.getWidget();
            final AbstractMenu menu = ((MenuDescriptor) parent).getMenu();
            if (position == NO_POSITION) {
                menu.add(menuItem);
            } else {
                menu.insert(position, menuItem);
            }
            view = item;
        }
        return view;
    }

}
