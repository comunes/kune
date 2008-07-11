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
package org.ourproject.kune.workspace.client.sitebar.bar;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LinkDTO;

import com.google.gwt.user.client.ui.Image;

public interface SiteBarView extends View {

    public void hideLoginDialog();

    void centerLoginDialog();

    void centerNewGroupDialog();

    void clearSearchText();

    void clearUserName();

    void hideNewGroupDialog();

    void hideProgress();

    void mask();

    void mask(String message);

    void resetOptionsSubmenu();

    void restoreLoginLink();

    void setContentGotoPublicUrl(String publicUrl);

    void setContentPublic(boolean visible);

    void setDefaultTextSearch();

    void setGroupsIsMember(List<LinkDTO> groupsIsAdmin, List<LinkDTO> groupsIsCollab);

    void setLogo(Image logo);

    void setLogoutLinkVisible(boolean visible);

    void setSearchText(String text);

    void setTextSearchBig();

    void setTextSearchSmall();

    void showAlertMessage(String message);

    void showLoggedUserName(String name, String homePage);

    void showLoginDialog();

    void showNewGroupDialog();

    void showProgress(String text);

    void unMask();

}
