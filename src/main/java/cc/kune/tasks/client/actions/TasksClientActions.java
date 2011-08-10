/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import static cc.kune.tasks.shared.TasksConstants.TYPE_FOLDER;
import static cc.kune.tasks.shared.TasksConstants.TYPE_ROOT;
import static cc.kune.tasks.shared.TasksConstants.TYPE_TASK;
import cc.kune.chat.client.actions.ChatAboutContentBtn;
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
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.SetAsHomePageMenuItem;

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
      final Provider<NewTaskIconBtn> newTaskIconBtn,
      final Provider<NewFolderMenuItem> newFolderMenuItem,
      final Provider<OpenFolderMenuItem> openContentMenuItem,
      final Provider<MarkAsDoneTaskMenuItem> marksAsDoneMenuItem,
      final Provider<MarkAsNotDoneTaskMenuItem> marksAsNotDoneMenuItem,
      final Provider<RefreshContentMenuItem> refresh,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ParticipateInContentBtn> participateBtn, final TasksNewMenu taskNewMenu,
      final NewMenusForTypeIdsRegistry newMenusRegistry, final Provider<ChatAboutContentBtn> chatAbout,
      final Provider<DelFolderMenuItem> delFolderMenuItem,
      final Provider<SetAsHomePageMenuItem> setAsHomePage) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, optionsMenuContent, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, taskNewMenu, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, refresh, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, newTaskItem, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, newTaskIconBtn, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, newFolderMenuItem, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, folderGoUp, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, folderGoUp, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, participateBtn, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, chatAbout, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, openContentMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, openContentMenuItem, containersNoRoot);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, marksAsDoneMenuItem,
        ContentStatus.publishedOnline, TYPE_TASK);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, marksAsDoneMenuItem,
        ContentStatus.editingInProgress, TYPE_TASK);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, marksAsNotDoneMenuItem,
        ContentStatus.inTheDustbin, TYPE_TASK);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, delFolderMenuItem, containersNoRoot);
    newMenusRegistry.register(TYPE_FOLDER, taskNewMenu.get());
    newMenusRegistry.register(TYPE_ROOT, taskNewMenu.get());
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
