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
package cc.kune.docs.client.actions;

import static cc.kune.docs.shared.DocsToolConstants.*;
import static cc.kune.gspace.client.actions.ActionGroups.*;
import cc.kune.chat.client.actions.ChatAboutContentBtn;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
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
import cc.kune.gspace.client.actions.SetAsHomePageMenuItem;
import cc.kune.gspace.client.actions.TutorialBtn;
import cc.kune.gspace.client.actions.WriteToParticipantsMenuItem;
import cc.kune.gspace.client.actions.share.AddAdminMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.AddAllMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.AddCollabMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.ContentViewerShareMenu;
import cc.kune.gspace.client.actions.share.ShareDialogMenuItem;
import cc.kune.gspace.client.actions.share.ShareInFacebookMenuItem;
import cc.kune.gspace.client.actions.share.ShareInGPlusMenuItem;
import cc.kune.gspace.client.actions.share.ShareInIdenticaMenuItem;
import cc.kune.gspace.client.actions.share.ShareInTwitterMenuItem;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DocsClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_DOCUMENT, TYPE_UPLOADEDFILE };
  final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
  final String[] containersNoRoot = { TYPE_FOLDER };
  final String[] contents = { TYPE_DOCUMENT, TYPE_UPLOADEDFILE };
  final String[] contentsModerated = { TYPE_DOCUMENT, TYPE_UPLOADEDFILE };
  final String[] root = { TYPE_ROOT };

  @SuppressWarnings("unchecked")
  @Inject
  public DocsClientActions(final Session session, final ActionRegistryByType registry,
      final CoreResources res, final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<NewDocMenuItem> newDocMenuItem, final Provider<NewDocIconBtn> newDocIconBtn,
      final Provider<NewFolderMenuItem> newFolderMenuItem,
      final Provider<OpenDocMenuItem> openContentMenuItem, final Provider<NewFolderBtn> newFolderBtn,
      final Provider<DelDocMenuItem> delContentMenuItem,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<ParticipateInContentBtn> participateBtn,
      final Provider<DelFolderMenuItem> delFolderMenuItem, final Provider<TutorialBtn> tutorialBtn,
      final Provider<ChatAboutContentBtn> chatAbout, final Provider<RefreshContentMenuItem> refresh,
      final Provider<SetAsHomePageMenuItem> setAsHomePage,
      final Provider<CopyContentMenuItem> copyContent,
      final Provider<WriteToParticipantsMenuItem> writeToParticipants,
      final Provider<PurgeContentMenuItem> purgeMenuItem, final Provider<PurgeContentBtn> purgeBtn,
      final Provider<PurgeContainerMenuItem> purgeFolderMenuItem,
      final Provider<PurgeContainerBtn> purgeFolderBtn,
      final Provider<MoveContentMenuItem> moveContentMenuItem,
      final NewMenusForTypeIdsRegistry newMenusRegistry, final DocsFolderNewMenu foldersNewMenu,
      final Provider<ShareDialogMenuItem> shareSettings, final DocsNewMenu docsNewMenu,
      final Provider<ShareInTwitterMenuItem> shareInTwitter,
      final Provider<ShareInGPlusMenuItem> shareInGPlus,
      final Provider<ShareInIdenticaMenuItem> shareInIdentica,
      final Provider<ShareInFacebookMenuItem> shareInFacaddAllMenuItemebook) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, all, optionsMenuContent, refresh, newDocIconBtn);
    add(TOPBAR, containers, newFolderBtn, foldersNewMenu, newDocMenuItem);
    add(TOPBAR, contents, docsNewMenu);
    add(TOPBAR, all, tutorialBtn);
    add(TOPBAR, containers, newFolderMenuItem);
    newMenusRegistry.register(TYPE_DOCUMENT,
        (MenuDescriptor) docsNewMenu.get().withText(I18n.t("Add Gadget")));
    newMenusRegistry.register(TYPE_UPLOADEDFILE, docsNewMenu.get());
    add(TOPBAR, all, shareMenuContent);
    add(TOPBAR, contents, addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem,
        shareSettings);
    add(TOPBAR, all, shareInTwitter, shareInGPlus);
    add(TOPBAR, contents, participateBtn, chatAbout, copyContent, writeToParticipants);
    add(BOTTOMBAR, contents, folderGoUp);
    add(BOTTOMBAR, containers, folderGoUp);
    add(ITEM_MENU, containersNoRoot, openContentMenuItem, moveContentMenuItem, delFolderMenuItem);
    add(ITEM_MENU, contents, openContentMenuItem, moveContentMenuItem, delContentMenuItem,
        setAsHomePage, addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem, copyContent,
        writeToParticipants);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, contents, purgeBtn);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, containersNoRoot, purgeFolderBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, purgeFolderMenuItem,
        moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, purgeMenuItem, moveContentMenuItem);
    newMenusRegistry.register(TYPE_FOLDER, foldersNewMenu.get());
    newMenusRegistry.register(TYPE_ROOT, foldersNewMenu.get());
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
