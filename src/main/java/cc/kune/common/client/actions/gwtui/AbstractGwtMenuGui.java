package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.Position;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractGwtMenuGui extends AbstractChildGuiItem implements ParentWidget {

    protected MenuBar menu;
    private PopupPanel popup;

    public AbstractGwtMenuGui() {
    }

    public AbstractGwtMenuGui(final AbstractGuiActionDescrip descriptor) {
        super(descriptor);
    }

    @Override
    public void add(final UIObject item) {
        menu.addItem((MenuItem) item);
    }

    public void addSeparator() {
        menu.addSeparator();
    }

    @Override
    public void configureItemFromProperties() {
        super.configureItemFromProperties();
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
                    menu.clearItems();
                }
            }
        });
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.create(descriptor);
        menu = new MenuBar(true);
        menu.setAnimationEnabled(true);
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
                    if (popup != null && popup.isShowing()) {
                        popup.hide();
                    }
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

    private PopupPanel createPopup() {
        popup = new PopupPanel(true);
        popup.addStyleName("oc-menu");
        popup.add(menu);
        popup.addCloseHandler(new CloseHandler<PopupPanel>() {
            @Override
            public void onClose(final CloseEvent<PopupPanel> event) {
                descriptor.putValue(MenuDescriptor.MENU_ONHIDE, popup);
            }
        });
        return popup;
    }

    @Override
    public void insert(final int position, final UIObject item) {
        menu.insertItem((MenuItem) item, position);
    }

    @Override
    public boolean shouldBeAdded() {
        return !descriptor.isChild();
    }

    public void show(final Object relative) {
        createPopup();
        if (relative instanceof String) {
            popup.showRelativeTo(RootPanel.get((String) relative));
        } else if (relative instanceof UIObject) {
            popup.showRelativeTo((UIObject) relative);
        } else if (relative instanceof Position) {
            popup.setPopupPositionAndShow(new PositionCallback() {
                @Override
                public void setPosition(final int offsetWidth, final int offsetHeight) {
                    final Position position = (Position) relative;
                    popup.setPopupPosition(position.getX(), position.getY());
                }
            });
        }
        descriptor.putValue(MenuDescriptor.MENU_ONSHOW, popup);
    }

}