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

import org.ourproject.kune.platf.client.View;

public class SiteMessagePresenter implements SiteMessage {
    private SiteMessageView view;
    private boolean isVisible;
    private String message;
    private int lastMessageType;

    public SiteMessagePresenter() {
    }

    public void init(final SiteMessageView siteMessageView) {
	this.view = siteMessageView;
	this.lastMessageType = SiteMessage.INFO;
	reset();
    }

    public void reset() {
	this.message = "";
	this.isVisible = false;
	view.hide();
    }

    public void setValue(final String message, final int type) {
	if (lastMessageType != type) {
	    if (isVisible) {
		if (type < lastMessageType) {
		    // more severe message
		    view.setMessage(this.message, lastMessageType, type);
		    lastMessageType = type;
		} else {
		    view.setMessage(this.message);
		}
	    } else {
		// Was closed, and different message level
		view.setMessage(this.message, lastMessageType, type);
	    }
	} else {
	    view.setMessage(this.message);
	}
	if (isVisible) {
	    this.message = this.message + "<br>" + message;
	} else {
	    this.message = message;
	    isVisible = true;
	}
	view.show();

    }

    public void onClose() {
	reset();
    }

    public void adjustWidth(final int windowWidth) {
	view.adjustWidth(windowWidth);
    }

    public View getView() {
	return view;
    }
}
