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

import java.util.Iterator;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.extend.UIExtensionElement;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorComponent;
import org.ourproject.kune.workspace.client.licensefoot.LicenseComponent;

public interface Workspace extends Component {

    public static final int MIN_WIDTH = 800;

    public static final int MIN_HEIGHT = 480;

    public void showError(Throwable caught);

    public void showGroup(GroupDTO group, boolean isAdmin);

    public void setTool(String toolName);

    public void setContext(WorkspaceComponent contextComponent);

    public void setContent(WorkspaceComponent contentComponent);

    public void attachTools(Iterator<ClientTool> iterator);

    public void adjustSize(int windowWidth, int clientHeight);

    public LicenseComponent getLicenseComponent();

    public ContentTitleComponent getContentTitleComponent();

    public ContentSubTitleComponent getContentSubTitleComponent();

    public GroupMembersComponent getGroupMembersComponent();

    public ParticipationComponent getParticipationComponent();

    public GroupSummaryComponent getGroupSummaryComponent();

    public GroupLiveSearchComponent getGroupLiveSearchComponent();

    public UserLiveSearchComponent getUserLiveSearchComponent();

    public TagsComponent getTagsComponent();

    public void setTheme(String theme);

    public ThemeMenuComponent getThemeMenuComponent();

    public void setVisible(boolean visible);

    public ContentBottomToolBarComponent getContentBottomToolBarComponent();

    public void attachToExtensionPoint(UIExtensionElement ext);

    public void detachFromExtensionPoint(UIExtensionElement ext);

    public void clearExtensionPoint(String id);

    public I18nTranslatorComponent getI18nTranslatorComponent();

    public int calculateWidth(int clientWidth);

    public int calculateHeight(int clientHeight);

}