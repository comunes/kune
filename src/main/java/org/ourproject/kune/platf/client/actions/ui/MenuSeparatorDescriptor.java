package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;

public class MenuSeparatorDescriptor extends AbstractSeparatorDescriptor {

    public MenuSeparatorDescriptor(final MenuDescriptor parent) {
        super();
        setParent(parent);
    }

    @Override
    public View getView() {
        if (view == NO_VIEW) {
            final AbstractMenu menu = ((MenuDescriptor) getParent()).getMenu();
            menu.addSeparator();
            view = menu;
        }
        return view;
    }
}