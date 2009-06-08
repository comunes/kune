package org.ourproject.kune.platf.client.actions.ui;

import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Menu;

public abstract class AbstractMenuGui extends AbstractGuiItem {

    protected final Menu menu;

    public AbstractMenuGui() {
        super();
        menu = new Menu();
        menu.setShadow(true);
    }

    public void add(final BaseItem item) {
        menu.addItem(item);
    }

    public void addSeparator() {
        menu.addSeparator();
    }

    public void insert(final int position, final BaseItem item) {
        menu.insert(position, item);
    }

}