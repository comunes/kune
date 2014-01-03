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
 */
package cc.kune.trash.client.actions;

import static cc.kune.gspace.client.actions.ActionGroups.TOPBAR;
import static cc.kune.trash.shared.TrashToolConstants.TOOL_NAME;
import static cc.kune.trash.shared.TrashToolConstants.TYPE_ROOT;
import cc.kune.chat.client.actions.GoParentChatBtn;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TrashClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT };
  final String[] containers = { TYPE_ROOT };
  final String[] containersNoRoot = {};

  @SuppressWarnings("unchecked")
  @Inject
  public TrashClientActions(final Session session, final ActionRegistryByType registry,
      final CoreResources res, final Provider<RefreshContentMenuItem> refresh,
      final Provider<GoParentChatBtn> folderGoUp, final Provider<EmptyTrashBinBtn> emptyTrashBin,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, all, optionsMenuContent);
    add(TOPBAR, all, refresh);
    add(TOOL_NAME, TOPBAR, emptyTrashBin, TYPE_ROOT);
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
