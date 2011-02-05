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
package org.ourproject.kune.workspace.client.themes;

import org.cobogw.gwt.user.client.CSS;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;

import cc.kune.core.shared.domain.utils.StateToken;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class WsBackManagerImpl implements WsBackManager {

    private static final StateToken NO_TOKEN = new StateToken("none.none.0.0");
    private final FileDownloadUtils downloadUtils;
    private StateToken lastToken;

    // private final Event0 onBackClear;
    // private final Event<StateToken> onSetBackImage;

    public WsBackManagerImpl(final FileDownloadUtils downloadUtils) {
        this.downloadUtils = downloadUtils;
        lastToken = NO_TOKEN;
        // this.onBackClear = new Event0("onBackClear");
        // this.onSetBackImage = new Event<StateToken>("onSetBackImage");
    }

    @Override
    public void addBackClear(final Listener0 listener) {
        // onBackClear.add(listener);
    }

    @Override
    public void addSetBackImage(final Listener<StateToken> listener) {
        // onSetBackImage.add(listener);
    }

    @Override
    public void clearBackImage() {
        // onBackClear.fire();
        if (!lastToken.equals(NO_TOKEN)) {
            DOM.setStyleAttribute(RootPanel.getBodyElement(), CSS.A.BACKGROUND, "transparent");
            lastToken = NO_TOKEN;
        }
    }

    @Override
    public void setBackImage(final StateToken token) {
        // onSetBackImage.fire(token);
        if (!token.equals(lastToken)) {
            final String bodyProp = "#FFFFFF url('" + downloadUtils.getImageUrl(token) + "') fixed no-repeat top left";
            DOM.setStyleAttribute(RootPanel.getBodyElement(), CSS.A.BACKGROUND, bodyProp);
            lastToken = token;
        }
    }
}
