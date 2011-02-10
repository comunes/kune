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
package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.SplitButton;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.google.gwt.user.client.ui.UIObject;

public class GxtMenuGui extends AbstractGxtMenuGui implements ParentWidget {

    private SplitButton button;
    private boolean notStandAlone;

    public GxtMenuGui() {
        super();
    }

    @Override
    public void add(final UIObject item) {
        menu.add((MenuItem) item);
    }

    @Override
    public void addSeparator() {
        menu.add(new SeparatorMenuItem());
    }

    @Override
    protected void addStyle(final String style) {
        button.addStyleName(style);
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
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        // Standalone menus are menus without and associated button in a
        // toolbar (sometimes, a menu showed in a grid, or other special
        // widgets)
        notStandAlone = !((MenuDescriptor) descriptor).isStandalone();
        if (notStandAlone) {
            button = new SplitButton("");
            button.setStylePrimaryName("oc-button");
            button.addSelectionListener(new SelectionListener<ButtonEvent>() {
                @Override
                public void componentSelected(final ButtonEvent ce) {
                    show(button);
                }
            });
            final String id = descriptor.getId();
            if (id != null) {
                button.ensureDebugId(id);
            }
            if (!descriptor.isChild()) {
                initWidget(button);
            } else {
                child = button;
            }
        }
        super.create(descriptor);
        configureItemFromProperties();
        return this;
    }

    @Override
    public void insert(final int position, final UIObject item) {
        menu.insert((MenuItem) item, position);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (button != null) {
            button.setEnabled(enabled);
        }
    }

    @Override
    public void setIconStyle(final String style) {
        if (button != null) {
            button.setIconStyle(style);
        }
    }

    @Override
    public void setText(final String text) {
        if (button != null) {
            button.setText(text);
        }
    }

    @Override
    public void setToolTipText(final String tooltip) {
        if (button != null) {
            if (tooltip != null && tooltip.length() > 0) {
                button.setToolTip(new GxtDefTooltip(tooltip));
            }
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        if (button != null) {
            button.setVisible(visible);
        }
    }

    @Override
    public boolean shouldBeAdded() {
        return notStandAlone;
    }
}
