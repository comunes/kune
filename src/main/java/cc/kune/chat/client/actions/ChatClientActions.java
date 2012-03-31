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
package cc.kune.chat.client.actions;

import static cc.kune.chat.shared.ChatToolConstants.TOOL_NAME;
import static cc.kune.chat.shared.ChatToolConstants.TYPE_ROOM;
import static cc.kune.chat.shared.ChatToolConstants.TYPE_ROOT;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.TutorialContainerBtn;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChatClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT, TYPE_ROOM };
  final String[] containers = { TYPE_ROOT, TYPE_ROOM };
  final String[] containersNoRoot = { TYPE_ROOM };
  final String[] contents = {};

  @Inject
  public ChatClientActions(final I18nUITranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<RefreshContentMenuItem> refresh, final Provider<NewRoomBtn> newRoomBtn,
      final Provider<OpenRoomMenuItem> openRoomMenuItem, final Provider<GoParentChatBtn> folderGoUp,
      final Provider<OpenRoomArchiveMenuItem> openRoomArchiveMenuItem,
      final Provider<OpenRoomBtn> openRoomBtn, final Provider<TutorialContainerBtn> tutorialBtn,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, optionsMenuContent, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, refresh, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, newRoomBtn, TYPE_ROOT);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.BOTTOMBAR, folderGoUp, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, tutorialBtn, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, openRoomBtn, containersNoRoot);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, openRoomMenuItem, containersNoRoot);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.ITEM_MENU, openRoomArchiveMenuItem, containersNoRoot);
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
