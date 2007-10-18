/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.sitebar.client.msg;

import org.ourproject.kune.sitebar.client.services.Images;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SiteMessagePanel extends HorizontalPanel implements SiteMessageView {
    private static final int TIMEVISIBLE = 5000;

    final Images images = Images.App.getInstance();
    AbstractImagePrototype[] messageIcons = new AbstractImagePrototype[] { images.error(), images.important(),
	    images.emblemImportant(), images.info() };
    String[] messageStyle = new String[] { "error", "veryimp", "imp", "info" };

    HTML message = null;
    Image messageIcon = null;

    private final MessagePresenter presenter;

    private final Timer timer;

    public SiteMessagePanel(final MessagePresenter presenter, final boolean closable) {
	// Initialize
	this.presenter = presenter;
	message = new HTML();
	messageIcon = new Image();
	final Images images = Images.App.getInstance();

	// Layout
	add(messageIcon);
	add(message);

	// Set properties
	setCellVerticalAlignment(messageIcon, VerticalPanel.ALIGN_MIDDLE);

	timer = new Timer() {
	    public void run() {
		presenter.resetMessage();
	    }
	};
	setVisible(false);
	setStyleName("kune-SiteMessagePanel");
	addStyleDependentName("info");
	images.info().applyTo(messageIcon);
	messageIcon.addStyleName("gwt-Image");

	if (closable) {
	    final PushButton closeIcon = new PushButton(images.cross().createImage(), images.crossDark().createImage());
	    add(closeIcon);
	    closeIcon.addClickListener(new ClickListener() {
		public void onClick(final Widget sender) {
		    if (sender == closeIcon) {
			setVisible(false);
			presenter.onClose();
		    }
		}
	    });
	    setCellVerticalAlignment(closeIcon, VerticalPanel.ALIGN_BOTTOM);
	}

    }

    public void setMessage(final String text, final int lastMessageType, final int type) {
	messageIcons[type].applyTo(messageIcon);
	removeStyleDependentName(messageStyle[lastMessageType]);
	addStyleDependentName(messageStyle[type]);
	setMessage(text);
    }

    public void setMessage(final String text) {
	this.message.setHTML(text);
    }

    public void adjustWidth(final int windowWidth) {
	int messageWidth = windowWidth * 60 / 100 - 3;
	this.setWidth("" + messageWidth);
	message.setWidth("" + (messageWidth - 16 - 40));
    }

    public void show() {
	this.setVisible(true);
	timer.schedule(TIMEVISIBLE);
    }

    public void hide() {
	message.setText("");
	this.setVisible(false);
	timer.cancel();
    }
}
