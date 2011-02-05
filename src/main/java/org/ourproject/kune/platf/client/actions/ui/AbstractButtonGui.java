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
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.KeyStroke;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public abstract class AbstractButtonGui extends AbstractGuiItem {

    private final Button button;

    public AbstractButtonGui(final ButtonDescriptor buttonDescriptor) {
        this(buttonDescriptor, false);
    }

    public AbstractButtonGui(final ButtonDescriptor buttonDescriptor, final boolean enableTongle) {
        super(buttonDescriptor);
        button = new Button();
        button.setEnableToggle(enableTongle);
        final String id = buttonDescriptor.getId();
        if (id != null) {
            button.setId(id);
        }
        initWidget(button);
        button.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject event) {
                if (getAction() != null) {
                    getAction().actionPerformed(new ActionEvent(button, event.getBrowserEvent()));
                }
            }
        });
        configureItemFromProperties();
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            button.enable();
        } else {
            button.disable();
        }
    }

    public void setPressed(final boolean pressed) {
        if (button.isPressed() != pressed) {
            button.toggle();
        }
    }

    @Override
    public void setText(final String text) {
        button.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
        if (key == null) {
            button.setTooltip(tooltip);
        } else {
            button.setTooltip(tooltip + key.toString());
        }
    }

    @Override
    protected void setIconStyle(final String style) {
        button.setIconCls(style);
    }

    @Override
    protected void setIconUrl(final String imageUrl) {
        button.setIcon(imageUrl);
    }

}
