package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.Position;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractGxtMenuGui extends AbstractChildGuiItem implements ParentWidget {

    protected Menu menu;

    public AbstractGxtMenuGui() {
    }

    public AbstractGxtMenuGui(final AbstractGuiActionDescrip descriptor) {
        super(descriptor);
    }

    @Override
    public void add(final UIObject item) {
        menu.add((MenuItem) item);
    }

    public void addSeparator() {
        menu.add(new SeparatorMenuItem());

    }

    @Override
    public void configureItemFromProperties() {
        super.configureItemFromProperties();
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
                    menu.removeAll();
                }
            }
        });
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        super.create(descriptor);
        menu = new Menu();
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
                    menu.hide();
                }
            }
        });
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
                    final String id = (String) descriptor.getValue(MenuDescriptor.MENU_SHOW_NEAR_TO);
                    show(id);
                }
            }
        });
        return this;
    }

    @Override
    public void insert(final int position, final UIObject item) {
        menu.insert((MenuItem) item, position);
    }

    @Override
    public boolean shouldBeAdded() {
        return !descriptor.isChild();
    }

    public void show(final Object relative) {
        if (relative instanceof String) {
            menu.show(RootPanel.get((String) relative));
        } else if (relative instanceof UIObject) {
            menu.show(((UIObject) relative).getElement(), "tl");
        } else if (relative instanceof Position) {
            final Position position = (Position) relative;
            menu.showAt(position.getX(), position.getY());
        }
    }

}