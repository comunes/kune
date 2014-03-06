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
import cc.kune.gspace.client.share.items.actions.AdminOfListShareItemDescriptor;
import cc.kune.gspace.client.share.items.actions.ChangeToEditorForListsMenuItem;
import cc.kune.gspace.client.share.items.actions.EditorOfListShareItemDescriptor;
import cc.kune.gspace.client.share.items.actions.EditorOfListShareItemDescriptor.ChangeToAdminMenuItem;
import cc.kune.gspace.client.share.items.actions.EditorOfListShareItemDescriptor.ChangeToViewerMenuItem;
import cc.kune.gspace.client.share.items.actions.ViewerOfListShareItemDescriptor;

import com.google.inject.Inject;

public class ListRolShareItemUi extends AbstractRolShareItemUi {
  @Inject
  public ListRolShareItemUi(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res,
      final IconicResources icons, final AdminOfListShareItemDescriptor adminDescr,
      final EditorOfListShareItemDescriptor editorDescr,
      final ViewerOfListShareItemDescriptor viewerDescr,
      final ChangeToEditorForListsMenuItem changeToEditor, final ChangeToAdminMenuItem changeToAdmin,
      final ChangeToViewerMenuItem changeToViewer) {
    super(actionsPanel, downloadUtils, res, icons, adminDescr, editorDescr, viewerDescr, changeToAdmin,
        changeToEditor, changeToViewer);
  }
}
