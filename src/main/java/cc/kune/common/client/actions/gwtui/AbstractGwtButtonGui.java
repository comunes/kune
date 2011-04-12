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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.ToggleButton;

public abstract class AbstractGwtButtonGui extends AbstractChildGuiItem {

    private ButtonBase button;
    protected boolean enableTongle;
    private IconLabel iconLabel;
    private boolean isChild;

    public AbstractGwtButtonGui() {
        this(null, false);
    }

    public AbstractGwtButtonGui(final ButtonDescriptor buttonDescriptor) {
        this(buttonDescriptor, false);
    }

    public AbstractGwtButtonGui(final ButtonDescriptor buttonDescriptor, final boolean enableTongle) {
        super(buttonDescriptor);
        this.enableTongle = enableTongle;
    }

    @Override
    protected void addStyle(final String style) {
        iconLabel.addStyleName(style);
        button.addStyleName(style);
        layout();
    }

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        iconLabel = new IconLabel("");
        if (enableTongle) {
            button = new ToggleButton();
        } else {
            button = new Button();
        }
        button.addStyleName("oc-button");
        button.addStyleName("k-btn");
        button.addStyleName("k-5corners");
        layout();
        // button.setEnableToggle(enableTongle);
        final String id = descriptor.getId();
        if (id != null) {
            button.ensureDebugId(id);
        }
        isChild = descriptor.isChild();
        if (isChild) {
            // If button is inside a toolbar don't init...
            if (descriptor.isChild()) {
                child = button;
            }
        } else {
            initWidget(button);
        }
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                descriptor.fire(new ActionEvent(button, getTargetObjectOfAction(descriptor),
                        Event.as(event.getNativeEvent())));
            }
        });
        super.create(descriptor);
        configureItemFromProperties();
        return this;
    }

    private void layout() {
        button.setHTML(iconLabel.getElement().getInnerHTML());
    }

    @Override
    public void setEnabled(final boolean enabled) {
        button.setEnabled(enabled);
    }

    @Override
    public void setIconResource(final ImageResource icon) {
        iconLabel.setLeftIconResource(icon);
        layout();
    }

    @Override
    protected void setIconStyle(final String style) {
        iconLabel.setRightIcon(style);
        layout();
    }

    public void setPressed(final boolean pressed) {
        final ToggleButton toggleButton = (ToggleButton) button;

        if (toggleButton.isDown() != pressed) {
            toggleButton.setDown(pressed);
        }
    }

    @Override
    public void setText(final String text) {
        iconLabel.setText(text);
        layout();
    }

    @Override
    public void setToolTipText(final String tooltip) {
        if (tooltip != null && !tooltip.isEmpty()) {
            final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
            Tooltip.to(button, key == null ? tooltip : tooltip + key.toString());
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        button.setVisible(visible);
    }

    @Override
    public boolean shouldBeAdded() {
        return !descriptor.isChild();
    }

}
