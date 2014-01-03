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
package cc.kune.chat.client.actions;

import static cc.kune.chat.shared.ChatToolConstants.*;
import static cc.kune.gspace.client.actions.ActionGroups.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.TutorialBtn;
import cc.kune.gspace.client.actions.share.ContentViewerShareMenu;
import cc.kune.gspace.client.actions.share.ShareInFacebookMenuItem;
import cc.kune.gspace.client.actions.share.ShareInGPlusMenuItem;
import cc.kune.gspace.client.actions.share.ShareInIdenticaMenuItem;
import cc.kune.gspace.client.actions.share.ShareInTwitterMenuItem;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChatClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT, TYPE_ROOM };
  final String[] containers = { TYPE_ROOT, TYPE_ROOM };
  final String[] containersNoRoot = { TYPE_ROOM };
  final String[] contents = {};

  @SuppressWarnings("unchecked")
  @Inject
  public ChatClientActions(final I18nTranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<RefreshContentMenuItem> refresh, final Provider<NewRoomBtn> newRoomBtn,
      final Provider<OpenRoomMenuItem> openRoomMenuItem, final Provider<GoParentChatBtn> folderGoUp,
      final Provider<OpenRoomArchiveMenuItem> openRoomArchiveMenuItem,
      final Provider<OpenRoomBtn> openRoomBtn, final Provider<TutorialBtn> tutorialBtn,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<ShareInTwitterMenuItem> shareInTwitter,
      final Provider<ShareInGPlusMenuItem> shareInGPlus,
      final Provider<ShareInIdenticaMenuItem> shareInIdentica,
      final Provider<ShareInFacebookMenuItem> shareInFacebook) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, all, optionsMenuContent, refresh);
    add(TOOL_NAME, TOPBAR, newRoomBtn, TYPE_ROOT);
    add(BOTTOMBAR, containers, folderGoUp);
    add(TOPBAR, all, tutorialBtn);
    add(TOPBAR, containersNoRoot, openRoomBtn);
    add(ITEM_MENU, containersNoRoot, openRoomMenuItem, openRoomArchiveMenuItem);
    add(TOPBAR, all, shareMenuContent);
    add(TOPBAR, all, shareInTwitter, shareInGPlus);
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
