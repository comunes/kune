/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.barters.client.actions;

import static cc.kune.barters.shared.BartersToolConstants.*;
import static cc.kune.gspace.client.actions.ActionGroups.*;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.CopyContentMenuItem;
import cc.kune.gspace.client.actions.MoveContentMenuItem;
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.PurgeContainerBtn;
import cc.kune.gspace.client.actions.PurgeContainerMenuItem;
import cc.kune.gspace.client.actions.PurgeContentBtn;
import cc.kune.gspace.client.actions.PurgeContentMenuItem;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.TutorialBtn;
import cc.kune.gspace.client.actions.WriteToParticipantsMenuItem;
import cc.kune.gspace.client.actions.share.AddAdminMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.AddAllMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.AddCollabMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.ShareDialogMenuItem;
import cc.kune.gspace.client.actions.share.ShareInHelper;
import cc.kune.gspace.client.actions.share.ShareMenu;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BartersClientActions extends AbstractFoldableToolActions {
  final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_BARTER };
  final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
  final String[] containersNoRoot = { TYPE_FOLDER };
  final String[] contents = { TYPE_BARTER };

  @SuppressWarnings("unchecked")
  @Inject
  public BartersClientActions(final Session session, final ActionRegistryByType registry,
      final CoreResources res, final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<NewBartersBtn> newBartersBtn, final Provider<NewFolderBtn> newFolderBtn,
      final Provider<OpenBartersMenuItem> openContentMenuItem,
      final Provider<DelBartersMenuItem> delContentMenuItem,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ShareMenu> shareMenuContent,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<TutorialBtn> tutorialBtn, final Provider<ParticipateInContentBtn> participateBtn,
      final Provider<DelFolderMenuItem> delFolderMenuItem,
      final Provider<PurgeContentMenuItem> purgeMenuItem, final Provider<PurgeContentBtn> purgeBtn,
      final Provider<PurgeContainerMenuItem> purgeFolderMenuItem,
      final Provider<PurgeContainerBtn> purgeFolderBtn, final Provider<RefreshContentMenuItem> refresh,
      final Provider<CopyContentMenuItem> copyContent,
      final Provider<MoveContentMenuItem> moveContentMenuItem,
      final Provider<WriteToParticipantsMenuItem> writeToParticipants,
      final Provider<ShareDialogMenuItem> shareSettings, final ShareInHelper shareIHelper) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, all, optionsMenuContent, refresh);
    add(BOTTOMBAR, contents, folderGoUp);
    add(BOTTOMBAR, containers, folderGoUp);
    add(TOPBAR, all, shareMenuContent);
    add(TOPBAR, contents, addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem);
    add(TOPBAR, all, tutorialBtn);
    add(TOPBAR, containers, newBartersBtn, newFolderBtn);
    add(ITEM_MENU, containersNoRoot, openContentMenuItem, moveContentMenuItem, delFolderMenuItem);
    add(TOPBAR, contents, participateBtn, copyContent, writeToParticipants);
    add(ITEM_MENU, contents, openContentMenuItem, moveContentMenuItem, delContentMenuItem,
        addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem);
    add(TOPBAR, contents, shareIHelper.getShareInWaves());
    add(TOPBAR, all, shareIHelper.getShareInAll());
    add(TOPBAR, contents, shareSettings);
    add(ITEM_MENU, contents, copyContent);
    add(ITEM_MENU, contents, writeToParticipants);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, contents, purgeBtn);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, containersNoRoot, purgeFolderBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, purgeMenuItem, moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, purgeFolderMenuItem,
        moveContentMenuItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.actions.AbstractFoldableToolActions#
   * createPostSessionInitActions()
   */
  @Override
  protected void createPostSessionInitActions() {
  }
}
