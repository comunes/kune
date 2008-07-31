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
package org.ourproject.kune.workspace.client.workspace;

import java.util.Iterator;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetChild;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.licensefoot.LicenseComponent;

public interface Workspace extends Component {

    public static final int MIN_WIDTH = 800;

    public static final int MIN_HEIGHT = 480;

    public void adjustSize(int windowWidth, int clientHeight);

    public void attachToExtensibleWidget(ExtensibleWidgetChild ext);

    public void attachTools(Iterator<ClientTool> iterator);

    public int calculateHeight(int clientHeight);

    public int calculateWidth(int clientWidth);

    public void clearExtensibleWidget(String extId);

    public void detachFromExtensibleWidget(ExtensibleWidgetChild ext);

    public ContentSubTitleComponent getContentSubTitleComponent();

    public ContentTitleComponent getContentTitleComponent();

    public LicenseComponent getLicenseComponent();

    public void setContent(WorkspaceComponent contentComponent);

    public void setContext(WorkspaceComponent contextComponent);

    public void setTheme(String theme);

    public void setTool(String toolName);

    public void setVisible(boolean visible);

    public void showGroup(GroupDTO group, boolean isAdmin);

}