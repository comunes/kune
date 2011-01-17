package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

public abstract class AbstractGxtMenuItemGui extends AbstractChildGuiItem {

    private MenuItem item;

    public AbstractGxtMenuItemGui() {
        super();
    }

    public AbstractGxtMenuItemGui(final MenuItemDescriptor descriptor) {
        super(descriptor);

    }

    private void confCheckListener(final MenuItemDescriptor descriptor, final CheckMenuItem checkItem) {
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuCheckItemDescriptor.CHECKED)) {
                    checkItem.setChecked((Boolean) event.getNewValue());
                }
            }
        });
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        if (descriptor instanceof MenuRadioItemDescriptor) {
            final CheckMenuItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
            checkItem.setGroup(((MenuRadioItemDescriptor) descriptor).getGroup());
            confCheckListener((MenuItemDescriptor) descriptor, checkItem);
            item = checkItem;
        } else if (descriptor instanceof MenuCheckItemDescriptor) {
            final CheckMenuItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
            confCheckListener((MenuItemDescriptor) descriptor, checkItem);
            item = checkItem;
        } else {
            item = new MenuItem("");
        }

        final String id = descriptor.getId();
        if (id != null) {
            item.ensureDebugId(id);
        }
        item.addSelectionListener(new SelectionListener<MenuEvent>() {
            @Override
            public void componentSelected(final MenuEvent ce) {
                final AbstractAction action = descriptor.getAction();
                if (action != null) {
                    action.actionPerformed(new ActionEvent(item, Event.getCurrentEvent()));
                }
            }
        });
        child = item;
        super.create(descriptor);
        configureItemFromProperties();
        return this;
    }

    private CheckMenuItem createCheckItem(final MenuItemDescriptor descriptor) {
        final CheckMenuItem checkItem = new CheckMenuItem();
        checkItem.setChecked(((MenuCheckItemDescriptor) descriptor).isChecked());
        return checkItem;
    }

    private String createShortCut(final KeyStroke key, final String style) {
        // See: https://yui-ext.com/forum/showthread.php?t=5762
        final Element keyLabel = DOM.createSpan();
        keyLabel.setId(style);
        keyLabel.setInnerText(key.toString());
        return keyLabel.getString();
    }

    public MenuItem getItem() {
        return item;
    }

    @Override
    protected void setEnabled(final boolean enabled) {
        item.setVisible(enabled);
    }

    @Override
    protected void setIconStyle(final String style) {
        item.setIconStyle(style);
    }

    @Override
    protected void setText(final String text) {
        if (text != null) {
            final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
            if (key == null) {
                item.setText(text);
            } else {
                item.setText(text + createShortCut(key, "oc-mshortcut-hidden") + createShortCut(key, "oc-mshortcut"));
            }
        }
    }

    @Override
    protected void setToolTipText(final String tooltip) {
        if (tooltip != null) {
            item.setToolTip(new GxtDefTooltip(tooltip));
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        item.setVisible(visible);
    }

    @Override
    public boolean shouldBeAdded() {
        return false;
    }
}
