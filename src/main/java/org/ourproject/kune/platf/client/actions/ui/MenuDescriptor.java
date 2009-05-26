package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;

import com.google.gwt.libideas.resources.client.ImageResource;

public class MenuDescriptor extends AbstractUIActionDescriptor {

    private transient AbstractMenu menu;

    public MenuDescriptor(final AbstractAction action) {
        this(NO_PARENT, action);
    }

    public MenuDescriptor(final AbstractUIActionDescriptor parent, final AbstractAction action) {
        super(action);
        setParent(parent);
    }

    public MenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public AbstractMenu getMenu() {
        createIfNeeded();
        return menu;
    }

    @Override
    public View getView() {
        createIfNeeded();
        return view;
    }

    private void createIfNeeded() {
        if (view == NO_VIEW) {
            if (isChild()) {
                final DefaultSubMenu submenu = new DefaultSubMenu(this);
                final AbstractMenu parentMenu = ((MenuDescriptor) parent).getMenu();
                final int position = getPosition();
                if (position == NO_POSITION) {
                    parentMenu.add(submenu.getMenuItem());
                } else {
                    parentMenu.insert(position, submenu.getMenuItem());
                }
                view = menu = submenu;
            } else {
                // Is main parent menu
                final DefaultMenu newMenu = new DefaultMenu(this);
                menu = newMenu;
                view = newMenu.getButton();
            }
        }
    }
}
