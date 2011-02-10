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

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;

public class GwtIconLabelGui extends AbstractGuiItem {
    private IconLabel iconLabel;

    public GwtIconLabelGui() {
    }

    public GwtIconLabelGui(final IconLabelDescriptor iconLabelDescriptor) {
        super(iconLabelDescriptor);
    }

    @Override
    protected void addStyle(final String style) {
        iconLabel.addStyleName(style);
    }

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        iconLabel = new IconLabel("");
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        final String id = descriptor.getId();
        if (id != null) {
            iconLabel.ensureDebugId(id);
        }
        initWidget(iconLabel);
        iconLabel.getFocus().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                final AbstractAction action = descriptor.getAction();
                if (action != null) {
                    action.actionPerformed(new ActionEvent(iconLabel, Event.as(event.getNativeEvent())));
                }
            }
        });
        configureItemFromProperties();
        return this;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        // Not implemented
    }

    @Override
    protected void setIconStyle(final String style) {
        iconLabel.setIcon(style);
    }

    @Override
    public void setText(final String text) {
        iconLabel.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
        if (key == null) {
            iconLabel.setTitle(tooltip);
        } else {
            iconLabel.setTitle(tooltip + key.toString());
        }
    }

    @Override
    public boolean shouldBeAdded() {
        return true;
    }

}
