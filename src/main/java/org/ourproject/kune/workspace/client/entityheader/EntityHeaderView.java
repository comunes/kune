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
package org.ourproject.kune.workspace.client.entityheader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.themes.WsTheme;

public interface EntityHeaderView {

    int LOGO_ICON_DEFAULT_HEIGHT = 60;
    int LOGO_ICON_DEFAULT_WIDTH = 468;

    int LOGO_ICON_MIN_HEIGHT = 28;
    int LOGO_ICON_MIN_WIDTH = 468;

    String LOGO_FORM_FIELD = "k-elogov-ff";

    void addWidget(View view);

    void reloadImage(GroupDTO group);

    void setFullLogo(StateToken stateToken, boolean clipped);

    void setLargeFont();

    void setLogoImage(StateToken stateToken);

    void setLogoImageVisible(boolean visible);

    void setLogoText(final String groupName);

    void setMediumFont();

    void setSmallFont();

    void setTheme(final WsTheme oldTheme, WsTheme newTheme);

    void showDefUserLogo();
}
