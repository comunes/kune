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

package org.ourproject.kune.sitebar.client.bar;

import java.util.List;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Image;

public interface SiteBarView extends View {

    void showLoggedUserName(String name, String homePage);

    void clearUserName();

    void setLogo(Image logo);

    void showProgress(String text);

    void hideProgress();

    void clearSearchText();

    void setSearchText(String text);

    void showLoginDialog();

    void setLogoutLinkVisible(boolean visible);

    public void hideLoginDialog();

    void restoreLoginLink();

    void showNewGroupDialog();

    void hideNewGroupDialog();

    void setDefaultTextSearch();

    void setGroupsIsMember(List groupsIsAdmin, List groupsIsEditor);

    void resetOptionsSubmenu();

}
