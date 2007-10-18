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

package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.tool.ToolTrigger;

import com.google.gwt.user.client.ui.Image;

public interface WorkspaceView extends View {

    void setGroupLogo(String groupName);

    void setGroupLogo(Image image);

    void setContent(View contentView);

    void setContext(View contextView);

    void setTool(String toolName);

    void adjustSize(int windowWidth, int clientHeight);

    void addTab(ToolTrigger trigger);

    void setContentTitle(View view);

    void setContentSubTitle(View view);

    void setBottom(View view);

    void setGroupMembers(View view);

    void setParticipation(View view);

    void setBuddiesPresence(View view);

    void setThemeMenuComponent(View view);

    void setTheme(String theme);

    void setVisible(boolean visible);

}
