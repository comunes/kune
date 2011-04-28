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
package cc.kune.gspace.client.style;

import org.cobogw.gwt.user.client.CSS;

import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

public class GSpaceBackManagerImpl implements GSpaceBackManager {

    private static final StateToken NO_TOKEN = new StateToken("none.none.0.0");
    private final FileDownloadUtils downloadUtils;
    private final EventBus eventBus;
    private StateToken lastToken;

    @Inject
    public GSpaceBackManagerImpl(final EventBus eventBus, final FileDownloadUtils downloadUtils) {
        this.eventBus = eventBus;
        this.downloadUtils = downloadUtils;
        lastToken = NO_TOKEN;
    }

    @Override
    public void clearBackImage() {
        if (!lastToken.equals(NO_TOKEN)) {
            DOM.setStyleAttribute(RootPanel.getBodyElement(), CSS.A.BACKGROUND, "transparent");
            lastToken = NO_TOKEN;
        }
        ClearBackImageEvent.fire(eventBus);
    }

    @Override
    public void setBackImage(final StateToken token) {
        if (!token.equals(lastToken)) {
            final String bodyProp = "#FFFFFF url('" + downloadUtils.getImageUrl(token) + "') fixed no-repeat top left";
            DOM.setStyleAttribute(RootPanel.getBodyElement(), CSS.A.BACKGROUND, bodyProp);
            lastToken = token;
        }
        SetBackImageEvent.fire(eventBus, token);
    }
}
