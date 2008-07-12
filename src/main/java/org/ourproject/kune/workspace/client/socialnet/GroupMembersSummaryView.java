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
package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

public interface GroupMembersSummaryView extends View {

    public static final String ICON_ALERT = "alert";

    void addAddMemberLink();

    void addCategory(String name, String title);

    void addCategory(String name, String title, String iconType);

    void addCategoryMember(String categoryName, String name, String title, MemberAction[] memberActions);

    void addComment(String comment);

    void addJoinLink();

    void addUnjoinLink();

    void clear();

    void confirmAddCollab(String groupShortName, String groupLongName);

    void hide();

    void setDropDownContentVisible(boolean visible);

    void setTheme(WsTheme oldTheme, WsTheme newTheme);

    void show();

    void showCategory(String name);

}
