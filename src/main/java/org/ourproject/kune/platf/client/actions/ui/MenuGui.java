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

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;

import com.google.gwt.user.client.ui.UIObject;
import com.gwtext.client.widgets.ToolbarButton;

public class MenuGui extends AbstractMenuGui {

    private final boolean notStandAlone;
    private ToolbarButton button = null;

    public MenuGui(final MenuDescriptor descriptor) {
        super(descriptor);
        // Standalone menus are menus without and associated button in a toolbar
        // (sometimes, a menu showed in a grid, or other special widgets)
        notStandAlone = !descriptor.isStandalone();
        if (notStandAlone) {
            button = new ToolbarButton();
            button.setMenu(menu);
            final String id = descriptor.getId();
            if (id != null) {
                button.setId(id);
            }
            initWidget(button);
        } else {
            initWidget(menu);
        }
        configureItemFromProperties();
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
                    menu.hide(true);
                }
            }
        });
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
                    menu.show(UIObject.DEBUG_ID_PREFIX + (String) descriptor.getValue(MenuDescriptor.MENU_SHOW_ID));
                }
            }
        });
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (notStandAlone) {
            if (enabled) {
                button.enable();
            } else {
                button.disable();
            }
        }
    }

    @Override
    public void setIconStyle(final String style) {
        if (notStandAlone) {
            button.setIconCls(style);
        }
    }

    @Override
    public void setIconUrl(final String imageUrl) {
        if (notStandAlone) {
            button.setIcon(imageUrl);
            if (descriptor.getValue(Action.NAME) != null) {
                button.setCls("x-btn-text-icon");
            }
        }
    }

    @Override
    public void setText(final String text) {
        if (notStandAlone) {
            button.setText(text);
        }
    }

    @Override
    public void setToolTipText(final String tooltip) {
        if (notStandAlone) {
            button.setTooltip(tooltip);
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        if (notStandAlone) {
            button.setVisible(visible);
        } else {
            menu.setVisible(visible);
        }
    }
}
