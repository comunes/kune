/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share.items;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.share.items.actions.AbstractRoleShareMenuItem;
import cc.kune.gspace.client.share.items.actions.GroupShareItemDescriptor;

import com.google.inject.Singleton;

@Singleton
public class AbstractRolShareItemUi extends AbstractShareItemWithMenuUi {

  private final ShareItemDescriptor adminDescr;
  private final ShareItemDescriptor editorDescr;
  private final ShareItemDescriptor viewerDescr;

  public AbstractRolShareItemUi(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res,
      final IconicResources icons, final ShareItemDescriptor adminDescr,
      final ShareItemDescriptor editorDescr, final ShareItemDescriptor viewerDescr,
      final AbstractRoleShareMenuItem changeToAdmin, final AbstractRoleShareMenuItem changeToEditor,
      final AbstractRoleShareMenuItem changeToViewer) {
    super(actionsPanel, downloadUtils, res);
    this.adminDescr = adminDescr;
    this.editorDescr = editorDescr;
    this.viewerDescr = viewerDescr;
    // When action is performed we replace this UI item with new values
    this.adminDescr.setTarget(this);
    this.editorDescr.setTarget(this);
    this.viewerDescr.setTarget(this);
    changeToAdmin.setNextRoleDescriptor(this.adminDescr);
    changeToEditor.setNextRoleDescriptor(this.editorDescr);
    changeToViewer.setNextRoleDescriptor(this.viewerDescr);
  }

  public AbstractShareItemWithMenuUi with(final AccessRolDTO rol, final GroupDTO group,
      final boolean isMe) {
    ShareItemDescriptor descr;
    switch (rol) {
    case Administrator:
      descr = adminDescr;
      break;
    case Editor:
      descr = editorDescr;
      break;
    case Viewer:
    default:
      descr = viewerDescr;
      break;
    }
    ((GroupShareItemDescriptor) adminDescr).setGroup(group).setIsMe(isMe);
    ((GroupShareItemDescriptor) editorDescr).setGroup(group).setIsMe(isMe);
    ((GroupShareItemDescriptor) viewerDescr).setGroup(group).setIsMe(isMe);
    setValuesViaDescriptor(descr);
    return this;
  }
}
