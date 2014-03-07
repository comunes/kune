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
package cc.kune.tasks.client.actions;

import static cc.kune.gspace.client.actions.ActionGroups.*;
import static cc.kune.tasks.shared.TasksToolConstants.*;
import cc.kune.chat.client.actions.ChatAboutContentBtn;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.ContentStatus;
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
import cc.kune.gspace.client.actions.share.ShareDialogMenuItem;
import cc.kune.gspace.client.actions.share.ShareInHelper;
import cc.kune.gspace.client.actions.share.ShareMenu;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TasksClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_TASK };
  final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
  final String[] containersNoRoot = { TYPE_FOLDER };
  final String[] contents = { TYPE_TASK };
  final String[] noRoot = { TYPE_FOLDER, TYPE_TASK };

  @SuppressWarnings("unchecked")
  @Inject
  public TasksClientActions(final Session session, final ActionRegistryByType registry,
      final CoreResources res, final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<NewTaskMenuItem> newTaskItem, final Provider<NewTaskIconBtn> newTaskIconBtn,
      final Provider<NewFolderBtn> newFolderBtn, final Provider<OpenFolderMenuItem> openContentMenuItem,
      final Provider<MarkAsDoneTaskMenuItem> marksAsDoneMenuItem,
      final Provider<MarkAsNotDoneTaskMenuItem> marksAsNotDoneMenuItem,
      final Provider<RefreshContentMenuItem> refresh,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ShareMenu> shareMenuContent,
      final Provider<ParticipateInContentBtn> participateBtn, final Provider<TutorialBtn> tutorialBtn,
      final Provider<DelTaskMenuItem> delContentMenuItem,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<PurgeContentMenuItem> purgeMenuItem, final Provider<PurgeContentBtn> purgeBtn,
      final Provider<PurgeContainerMenuItem> purgeFolderMenuItem,
      final Provider<PurgeContainerBtn> purgeFolderBtn, final Provider<ChatAboutContentBtn> chatAbout,
      final Provider<CopyContentMenuItem> copyContent,
      final Provider<WriteToParticipantsMenuItem> writeToParticipants,
      final TasksFolderNewMenu taskFolderNewMenu, final TasksNewMenu taskNewMenu,
      final NewMenusForTypeIdsRegistry newMenusRegistry,
      final Provider<DelFolderMenuItem> delFolderMenuItem,
      final Provider<MoveContentMenuItem> moveContentMenuItem,
      final Provider<SetAsHomePageMenuItem> setAsHomePage,
      final Provider<ShareDialogMenuItem> shareSettings, final ShareInHelper shareIHelper) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, all, optionsMenuContent);
    add(TOPBAR, all, refresh);
    add(TOPBAR, all, newTaskIconBtn);
    // add(NAME, TOOLBAR, taskFolderNewMenu,
    // containers);
    add(TOPBAR, contents, taskNewMenu);
    add(TOPBAR, containers, newFolderBtn);
    // add(NAME, TOOLBAR, newTaskItem,
    // containers);
    add(BOTTOMBAR, contents, folderGoUp);
    add(BOTTOMBAR, containers, folderGoUp);
    add(TOPBAR, all, tutorialBtn);
    add(TOPBAR, all, shareMenuContent);
    add(TOPBAR, contents, addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem);
    add(TOPBAR, contents, shareIHelper.getShareInWaves());
    add(TOPBAR, all, shareIHelper.getShareInAll());
    add(TOPBAR, contents, shareSettings, participateBtn, chatAbout, copyContent, writeToParticipants);
    add(ITEM_MENU, contents, openContentMenuItem, moveContentMenuItem);
    add(ITEM_MENU, containersNoRoot, openContentMenuItem, moveContentMenuItem);
    add(TOOL_NAME, ITEM_MENU, marksAsDoneMenuItem, ContentStatus.publishedOnline, TYPE_TASK);
    add(TOOL_NAME, ITEM_MENU, marksAsDoneMenuItem, ContentStatus.editingInProgress, TYPE_TASK);
    add(TOOL_NAME, ITEM_MENU, marksAsNotDoneMenuItem, ContentStatus.inTheDustbin, TYPE_TASK);
    add(ITEM_MENU, containersNoRoot, delFolderMenuItem);
    add(ITEM_MENU, contents, delContentMenuItem, addAllMenuItem, addAdminMembersMenuItem,
        addCollabMembersMenuItem, copyContent, writeToParticipants);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, contents, purgeBtn);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, containersNoRoot, purgeFolderBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, purgeMenuItem, moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, purgeFolderMenuItem,
        moveContentMenuItem);
    // Currently new menu in folders has no sense (because we have buttons for
    // the same contents)
    // newMenusRegistry.register(TYPE_FOLDER, taskFolderNewMenu.get());
    // newMenusRegistry.register(TYPE_ROOT, taskFolderNewMenu.get());
    newMenusRegistry.register(TYPE_TASK,
        (MenuDescriptor) taskNewMenu.get().withText(I18n.t("Add Gadget")));
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
