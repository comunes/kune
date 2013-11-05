/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.wave.client.kspecific;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class WaveUnsaveNotificator extends Composite {
    private final Label msg;
    private PopupPanel popupPalette;

    public WaveUnsaveNotificator() {
        msg = new Label();
        msg.addStyleName("k-unsave-popup");
        msg.setHeight("14px");
    }

    public void hide() {
        if (popupPalette != null) {
            popupPalette.hide();
        }
    }

    public void show(final String message) {
        msg.setText(message);
        popupPalette = new PopupPanel(true, false);
        popupPalette.setWidget(msg);
        popupPalette.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            public void setPosition(final int offsetWidth, final int offsetHeight) {
                popupPalette.setPopupPosition(Window.getClientWidth() - msg.getOffsetWidth() +1,
                        Window.getClientHeight() - msg.getOffsetHeight() + 1);
            }
        });
        popupPalette.setStyleName("oc-user-msg-popup");
        popupPalette.setAnimationEnabled(false);
    }
}
