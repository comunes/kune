package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;

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

    @Override
    public void setAction(final AbstractAction newaction) {
        super.setAction(newaction);
        action.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
                    menu.removeAll();
                }
            }
        });
    }

    public void show(final int x, final int y) {
        menu.showAt(x, y);
    }

    public void show(final String id) {
        menu.show(id);
    }

}