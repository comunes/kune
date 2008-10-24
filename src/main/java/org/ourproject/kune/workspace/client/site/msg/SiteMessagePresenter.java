/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public class SiteMessagePresenter implements SiteMessage, MessagePresenter {
    private SiteMessageView view;
    private boolean isVisible;
    private String message;
    private SiteErrorType lastMessageType;

    public SiteMessagePresenter() {
    }

    @Deprecated
    public void adjustWidth(final int windowWidth) {
        view.adjustWidth(windowWidth);
    }

    public View getView() {
        return view;
    }

    public void init(final SiteMessageView siteMessageView) {
        this.view = siteMessageView;
        this.lastMessageType = SiteErrorType.info;
        resetMessage();
    }

    public void resetMessage() {
        this.message = "";
        this.isVisible = false;
        view.hide();
    }

    public void setMessage(final String message, final SiteErrorType type) {
        if (isVisible) {
            if (!this.message.equals(message)) {
                // Concatenate message (if not is the same)
                this.message = this.message + "<br>" + message;
            }
        } else {
            // New message
            this.message = message;
        }
        if (lastMessageType != type) {
            if (isVisible) {
                if (type.compareTo(lastMessageType) < 0) {
                    // more severe message
                    view.setMessage(this.message, lastMessageType, type);
                    lastMessageType = type;
                } else {
                    view.setMessage(this.message);
                }
            } else {
                // Was closed, and different message level
                view.setMessage(this.message, lastMessageType, type);
                lastMessageType = type;
            }
        } else {
            view.setMessage(this.message);
        }
        isVisible = true;
        view.show();
    }
}
