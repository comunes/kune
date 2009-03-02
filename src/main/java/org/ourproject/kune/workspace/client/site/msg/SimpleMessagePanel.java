/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleMessagePanel extends HorizontalPanel {
    HTML message = null;
    Image messageIcon = null;
    private final Images images;

    public SimpleMessagePanel(Images images) {
        this.images = images;
        message = new HTML();
        messageIcon = new Image();
        add(messageIcon);
        add(message);
        setCellVerticalAlignment(messageIcon, VerticalPanel.ALIGN_MIDDLE);
        setVisible(false);
        setStyleName("kune-SiteMessagePanel");
        addStyleDependentName("info");
        images.info().applyTo(messageIcon);
        messageIcon.addStyleName("gwt-Image");
        message.setWidth("100%");
        this.setCellWidth(message, "100%");
    }

    public void adjustWidth(final int windowWidth) {
        final int messageWidth = windowWidth * 60 / 100 - 3;
        this.setWidth("" + messageWidth);
    }

    public void hide() {
        message.setText("");
        this.setVisible(false);
    }

    public void reset() {
        message.setText("");
    }

    public void setMessage(final String text) {
        this.message.setHTML(text);
    }

    public void setMessage(final String text, final Level lastMessageType, final Level type) {
        AbstractImagePrototype imagePrototype = null;
        switch (type) {
        case error:
            imagePrototype = images.error();
            break;
        case veryImportant:
            imagePrototype = images.important();
            break;
        case important:
            imagePrototype = images.emblemImportant();
            break;
        case info:
            imagePrototype = images.info();
            break;
        }
        imagePrototype.applyTo(messageIcon);
        removeStyleDependentName(lastMessageType.toString());
        addStyleDependentName(type.toString());
        setMessage(text);
    }

    public void show() {
        this.setVisible(true);
    }
}
