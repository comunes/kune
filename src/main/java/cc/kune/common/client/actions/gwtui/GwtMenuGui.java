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

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

public class GwtMenuGui extends AbstractGwtMenuGui {

    private Button button;
    private IconLabel iconLabel;
    private boolean notStandAlone;

    @Override
    protected void addStyle(final String style) {
        if (notStandAlone) {
            iconLabel.addStyleName(style);
            layout();
        }
    }

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        // Standalone menus are menus without and associated button in a toolbar
        // (sometimes, a menu showed in a grid, or other special widgets)
        notStandAlone = !((MenuDescriptor) descriptor).isStandalone();
        if (notStandAlone) {
            button = new Button();
            button.setStylePrimaryName("oc-button");
            iconLabel = new IconLabel("");
            button.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(final ClickEvent event) {
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
        } else {
            initWidget(new Label());
        }
        super.create(descriptor);
        configureItemFromProperties();
        return this;
    }

    private void layout() {
        button.setHTML(iconLabel.getElement().getInnerHTML());
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (notStandAlone) {
            button.setVisible(enabled);
        }
    }

    @Override
    public void setIconResource(final ImageResource resource) {
        if (notStandAlone) {
            iconLabel.setIconResource(resource);
            layout();
        }
    }

    @Override
    public void setIconStyle(final String style) {
        if (notStandAlone) {
            iconLabel.setIcon(style);
            layout();
        }
    }

    @Override
    public void setText(final String text) {
        if (notStandAlone) {
            iconLabel.setText(text);
            layout();
        }
    }

    @Override
    public void setToolTipText(final String tooltip) {
        if (notStandAlone) {
            // button.setTooltip(tooltip);
            button.setTitle(tooltip);
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        if (notStandAlone) {
            button.setVisible(visible);
        } else {
            // button.setVisible(visible);
        }
    }

}
