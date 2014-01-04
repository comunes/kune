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
import cc.kune.gspace.client.share.items.actions.AdminOfContentShareItemDescriptor;
import cc.kune.gspace.client.share.items.actions.ChangeToEditorForContentsMenuItem;
import cc.kune.gspace.client.share.items.actions.EditorOfContentShareItemDescriptor;
import cc.kune.gspace.client.share.items.actions.EditorOfContentShareItemDescriptor.ChangeToAdminMenuItem;
import cc.kune.gspace.client.share.items.actions.EditorOfContentShareItemDescriptor.ChangeToViewerMenuItem;
import cc.kune.gspace.client.share.items.actions.ViewerOfContentShareItemDescriptor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ContentRolShareItemUi extends AbstractRolShareItemUi {
  @Inject
  public ContentRolShareItemUi(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res,
      final IconicResources icons, final AdminOfContentShareItemDescriptor adminDescr,
      final EditorOfContentShareItemDescriptor editorDescr,
      final ViewerOfContentShareItemDescriptor viewerDescr,
      final ChangeToEditorForContentsMenuItem changeToEditor, final ChangeToAdminMenuItem changeToAdmin,
      final ChangeToViewerMenuItem changeToViewer) {
    super(actionsPanel, downloadUtils, res, icons, adminDescr, editorDescr, viewerDescr, changeToAdmin,
        changeToEditor, changeToViewer);
  }
}
