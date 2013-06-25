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
package cc.kune.tasks.client.actions;

import static cc.kune.tasks.shared.TasksToolConstants.*;
import cc.kune.chat.client.actions.ChatAboutContentBtn;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
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

public class TasksClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_TASK };
  final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
  final String[] containersNoRoot = { TYPE_FOLDER };
  final String[] contents = { TYPE_TASK };
  final String[] noRoot = { TYPE_FOLDER, TYPE_TASK };

  @Inject
  public TasksClientActions(final I18nUITranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<GoParentFolderBtn> folderGoUp, final Provider<NewTaskMenuItem> newTaskItem,
      final Provider<NewTaskIconBtn> newTaskIconBtn, final Provider<NewFolderBtn> newFolderBtn,
      final Provider<OpenFolderMenuItem> openContentMenuItem,
      final Provider<MarkAsDoneTaskMenuItem> marksAsDoneMenuItem,
      final Provider<MarkAsNotDoneTaskMenuItem> marksAsNotDoneMenuItem,
      final Provider<RefreshContentMenuItem> refresh,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<ParticipateInContentBtn> participateBtn, final Provider<TutorialBtn> tutorialBtn,
      final Provider<DelTaskMenuItem> delContentMenuItem,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<AddPublicToContentMenuItem> addPublicMenuItem,
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
      final Provider<ShareSettingsMenuItem> shareSettings,
      final Provider<ShareInTwitterMenuItem> shareInTwitter,
      final Provider<ShareInGPlusMenuItem> shareInGPlus,
      final Provider<ShareInIdenticaMenuItem> shareInIdentica,
      final Provider<ShareInFacebookMenuItem> shareInFacebook) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, optionsMenuContent, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, refresh, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, newTaskIconBtn, all);
    // actionsRegistry.addAction(NAME, ActionGroups.TOOLBAR, taskFolderNewMenu,
    // containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, taskNewMenu, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, newFolderBtn, containers);
    // actionsRegistry.addAction(NAME, ActionGroups.TOOLBAR, newTaskItem,
    // containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.BOTTOMBAR, folderGoUp, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.BOTTOMBAR, folderGoUp, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, tutorialBtn, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareMenuContent, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addAllMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addAdminMembersMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addCollabMembersMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addPublicMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareInTwitter, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareInIdentica, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareInGPlus, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareSettings, contents);
    // actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR,
    // shareInFacebook, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, participateBtn, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, chatAbout, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, copyContent, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, writeToParticipants, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, openContentMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, openContentMenuItem, containersNoRoot);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, moveContentMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, moveContentMenuItem, containersNoRoot);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, marksAsDoneMenuItem,
        ContentStatus.publishedOnline, TYPE_TASK);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, marksAsDoneMenuItem,
        ContentStatus.editingInProgress, TYPE_TASK);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, marksAsNotDoneMenuItem,
        ContentStatus.inTheDustbin, TYPE_TASK);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, delFolderMenuItem, containersNoRoot);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, delContentMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, addAllMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, addAdminMembersMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, addCollabMembersMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, addPublicMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, copyContent, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, writeToParticipants, contents);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.TOPBAR, purgeBtn, contents);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.ITEM_MENU, purgeMenuItem,
        contents);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.TOPBAR, purgeFolderBtn,
        containersNoRoot);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.ITEM_MENU, purgeFolderMenuItem,
        containersNoRoot);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.ITEM_MENU, moveContentMenuItem,
        contents);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.ITEM_MENU, moveContentMenuItem,
        containersNoRoot);
    // Currently new menu in folders has no sense (because we have buttons for
    // the same contents)
    // newMenusRegistry.register(TYPE_FOLDER, taskFolderNewMenu.get());
    // newMenusRegistry.register(TYPE_ROOT, taskFolderNewMenu.get());
    newMenusRegistry.register(TYPE_TASK,
        (MenuDescriptor) taskNewMenu.get().withText(i18n.t("Add Gadget")));
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
