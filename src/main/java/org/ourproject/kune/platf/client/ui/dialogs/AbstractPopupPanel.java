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
package org.ourproject.kune.platf.client.ui.dialogs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractPopupPanel {

    private PopupPanel popupPalette;
    protected Widget widget;

    private final boolean autoHide;
    private final boolean modal;

    public AbstractPopupPanel() {
        this(true, true);
    }

    public AbstractPopupPanel(final boolean autohide) {
        this(autohide, false);
    }

    public AbstractPopupPanel(final boolean autohide, final boolean modal) {
        this.autoHide = autohide;
        this.modal = modal;
    }

    public void addStyleName(final String style) {
        assert popupPalette != null;
        popupPalette.addStyleName(style);
    }

    public void hide() {
        if (popupPalette != null) {
            popupPalette.hide();
        }
    }

    public boolean isVisible() {
        if (popupPalette != null && popupPalette.isVisible()) {
            return true;
        }
        return false;
    }

    public void show(final int left, final int top) {
        popupPalette = new PopupPanel(autoHide, modal);
        popupPalette.addStyleName("k-def-popup");
        if (widget == null) {
            createWidget();
        }
        popupPalette.setWidget(widget);
        popupPalette.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            public void setPosition(final int offsetWidth, final int offsetHeight) {
                final int maxLeft = Window.getClientWidth() - offsetWidth;
                final int maxTop = Window.getClientHeight() - offsetHeight;
                popupPalette.setPopupPosition(left < maxLeft ? left : maxLeft, top < maxTop ? top : maxTop);
            }
        });
    }

    protected abstract void createWidget();

}
