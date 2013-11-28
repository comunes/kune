/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import static cc.kune.trash.shared.TrashToolConstants.*;
import cc.kune.chat.client.actions.GoParentChatBtn;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class TrashClientActions.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TrashClientActions extends AbstractFoldableToolActions {

  /** The all. */
  final String[] all = { TYPE_ROOT };
  
  /** The containers. */
  final String[] containers = { TYPE_ROOT };
  
  /** The containers no root. */
  final String[] containersNoRoot = {};

  /**
   * Instantiates a new trash client actions.
   *
   * @param i18n the i18n
   * @param session the session
   * @param stateManager the state manager
   * @param registry the registry
   * @param res the res
   * @param refresh the refresh
   * @param folderGoUp the folder go up
   * @param emptyTrashBin the empty trash bin
   * @param optionsMenuContent the options menu content
   */
  @Inject
  public TrashClientActions(final I18nTranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<RefreshContentMenuItem> refresh, final Provider<GoParentChatBtn> folderGoUp,
      final Provider<EmptyTrashBinBtn> emptyTrashBin,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, optionsMenuContent, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, refresh, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, emptyTrashBin, TYPE_ROOT);
  }

  /* (non-Javadoc)
   * @see cc.kune.gspace.client.actions.AbstractFoldableToolActions#createPostSessionInitActions()
   */
  @Override
  protected void createPostSessionInitActions() {
  }
}
