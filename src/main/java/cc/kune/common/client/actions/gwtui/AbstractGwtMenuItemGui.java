package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

public abstract class AbstractGwtMenuItemGui extends AbstractGuiItem {

    private IconLabel iconLabel;
    private GwtBaseMenuItem item;

    public AbstractGwtMenuItemGui() {
    }

    public AbstractGwtMenuItemGui(final MenuItemDescriptor descriptor) {
        super(descriptor);

    }

    private void confCheckListener(final MenuItemDescriptor descriptor, final GwtCheckItem checkItem) {
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
        iconLabel = new IconLabel("");
        iconLabel.addTextStyleName("oc-ico-pad");
        if (descriptor instanceof MenuRadioItemDescriptor) {
            final GwtCheckItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
            checkItem.setGroup(((MenuRadioItemDescriptor) descriptor).getGroup());
            confCheckListener((MenuItemDescriptor) descriptor, checkItem);
            item = checkItem;
        } else if (descriptor instanceof MenuCheckItemDescriptor) {
            final GwtCheckItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
            confCheckListener((MenuItemDescriptor) descriptor, checkItem);
            item = checkItem;
        } else {
            item = new GwtBaseMenuItem("", true);
        }

        final String id = descriptor.getId();
        if (id != null) {
            item.ensureDebugId(id);
        }
        // initWidget(item);
        item.setCommand(new Command() {
            @Override
            public void execute() {
                final AbstractAction action = descriptor.getAction();
                if (action != null) {
                    descriptor.getAction().actionPerformed(new ActionEvent(item, Event.getCurrentEvent()));
                }
            }
        });
        configureItemFromProperties();

        final int position = descriptor.getPosition();
        final AbstractGwtMenuGui menu = ((AbstractGwtMenuGui) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
        if (menu == null) {
            throw new UIException("To add a menu item you need to add the menu before. Item: " + descriptor);
        }
        if (position == AbstractGuiActionDescrip.NO_POSITION) {
            menu.add(item);
        } else {
            menu.insert(position, item);
        }
        return this;
    }

    private GwtCheckItem createCheckItem(final MenuItemDescriptor descriptor) {
        final GwtCheckItem checkItem = new GwtCheckItem();
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

    public GwtBaseMenuItem getItem() {
        return item;
    }

    private void layout() {
        item.setHTML(iconLabel.toString());
    }

    @Override
    protected void setEnabled(final boolean enabled) {
        item.setVisible(enabled);
    }

    @Override
    protected void setIconStyle(final String style) {
        iconLabel.setIcon(style);
        layout();
    }

    @Override
    protected void setText(final String text) {
        if (text != null) {
            final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
            if (key == null) {
                iconLabel.setText(text);
            } else {
                iconLabel.setLabelHtml(text + createShortCut(key, "oc-mshortcut-hidden")
                        + createShortCut(key, "oc-mshortcut"));
            }
        }
        layout();
    }

    @Override
    protected void setToolTipText(final String text) {
        if (text != null) {
            item.setTitle(text);
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
