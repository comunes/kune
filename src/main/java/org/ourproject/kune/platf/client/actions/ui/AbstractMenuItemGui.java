/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public abstract class AbstractMenuItemGui extends AbstractGuiItem {

    private Item item;

    public AbstractMenuItemGui(final MenuItemDescriptor descriptor) {
        super(descriptor);
        if (descriptor instanceof MenuRadioItemDescriptor) {
            final CheckItem checkItem = createCheckItem(descriptor);
            checkItem.setGroup(((MenuRadioItemDescriptor) descriptor).getGroup());
            confCheckListener(descriptor, checkItem);
            item = checkItem;
        } else if (descriptor instanceof MenuCheckItemDescriptor) {
            final CheckItem checkItem = createCheckItem(descriptor);
            confCheckListener(descriptor, checkItem);
            item = checkItem;
        } else {
            item = new Item();
        }
        final String id = descriptor.getId();
        if (id != null) {
            item.setId(id);
        }
        // initWidget(item);
        final BaseItemListenerAdapter clickListener = new BaseItemListenerAdapter() {
            @Override
            public void onClick(final BaseItem item, final EventObject event) {
                final AbstractAction action = getAction();
                if (action != null) {
                    action.actionPerformed(new ActionEvent(item, event.getBrowserEvent()));
                }
            }
        };
        item.addListener(clickListener);
        configureItemFromProperties();
    }

    @Override
    public void setIconUrl(final String imageUrl) {
        item.setIcon(imageUrl);
    }

    @Override
    public void setVisible(final boolean visible) {
        item.setVisible(visible);
    }

    @Override
    protected Widget getWidget() {
        return item;
    }

    @Override
    protected void setEnabled(final boolean enabled) {
        if (enabled) {
            item.enable();
        } else {
            item.disable();
        }
    }

    @Override
    protected void setIconStyle(final String style) {
        item.setIconCls(style);
    }

    @Override
    protected void setText(final String text) {
        if (text != null) {
            final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
            if (key == null) {
                item.setText(text);
            } else {
                item.setText(text + createShortCut(key, "k-mshortcut-hidden") + createShortCut(key, "k-mshortcut"));
            }
        }
    }

    @Override
    protected void setToolTipText(final String text) {
        if (text != null) {
            item.setTitle(text);
        }
    }

    private void confCheckListener(final MenuItemDescriptor descriptor, final CheckItem checkItem) {
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuCheckItemDescriptor.CHECKED)) {
                    checkItem.setChecked((Boolean) event.getNewValue());
                }
            }
        });
    }

    private CheckItem createCheckItem(final MenuItemDescriptor descriptor) {
        final CheckItem checkItem = new CheckItem();
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
}
