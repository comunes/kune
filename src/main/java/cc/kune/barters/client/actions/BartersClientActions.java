/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
import cc.kune.gspace.client.actions.share.AddPublicToContentMenuItem;
import cc.kune.gspace.client.actions.share.ContentViewerShareMenu;
import cc.kune.gspace.client.actions.share.ShareInFacebookMenuItem;
import cc.kune.gspace.client.actions.share.ShareInGPlusMenuItem;
import cc.kune.gspace.client.actions.share.ShareInIdenticaMenuItem;
import cc.kune.gspace.client.actions.share.ShareInTwitterMenuItem;
import cc.kune.gspace.client.actions.share.ShareSettingsMenuItem;
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
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<AddPublicToContentMenuItem> addPublicMenuItem,
      final Provider<TutorialBtn> tutorialBtn, final Provider<ParticipateInContentBtn> participateBtn,
      final Provider<DelFolderMenuItem> delFolderMenuItem,
      final Provider<PurgeContentMenuItem> purgeMenuItem, final Provider<PurgeContentBtn> purgeBtn,
      final Provider<PurgeContainerMenuItem> purgeFolderMenuItem,
      final Provider<PurgeContainerBtn> purgeFolderBtn, final Provider<RefreshContentMenuItem> refresh,
      final Provider<CopyContentMenuItem> copyContent,
      final Provider<MoveContentMenuItem> moveContentMenuItem,
      final Provider<WriteToParticipantsMenuItem> writeToParticipants,
      final Provider<ShareSettingsMenuItem> shareSettings,
      final Provider<ShareInTwitterMenuItem> shareInTwitter,
      final Provider<ShareInGPlusMenuItem> shareInGPlus,
      final Provider<ShareInIdenticaMenuItem> shareInIdentica,
      final Provider<ShareInFacebookMenuItem> shareInFacebook) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, all, optionsMenuContent, refresh);
    add(BOTTOMBAR, contents, folderGoUp);
    add(BOTTOMBAR, containers, folderGoUp);
    add(TOPBAR, all, shareMenuContent);
    add(TOPBAR, contents, addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem,
        addPublicMenuItem);
    add(TOPBAR, all, tutorialBtn);
    add(TOPBAR, containers, newBartersBtn, newFolderBtn);
    add(TOPBAR, contents, participateBtn, copyContent, writeToParticipants);
    add(ITEM_MENU, containersNoRoot, openContentMenuItem, moveContentMenuItem, delFolderMenuItem);
    add(ITEM_MENU, contents, openContentMenuItem, moveContentMenuItem, delContentMenuItem,
        addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem, addPublicMenuItem);
    add(TOPBAR, containers, shareInTwitter, shareInIdentica, shareInGPlus);
    add(TOPBAR, contents, shareSettings);
    // actionsRegistry.addAction(TOPBAR,
    // shareInFacebook, all);
    add(ITEM_MENU, contents, copyContent);
    add(ITEM_MENU, contents, writeToParticipants);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, contents, purgeBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, purgeMenuItem);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, containersNoRoot, purgeFolderBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, purgeFolderMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, moveContentMenuItem);
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
