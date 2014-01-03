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
package cc.kune.gspace.client.actions;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.ContentStatus;

import com.google.inject.Provider;

public abstract class AbstractFoldableToolActions {

  protected final ActionRegistryByType actionsRegistry;
  private final String toolName;

  public AbstractFoldableToolActions(final String toolName, final Session session,
      final ActionRegistryByType actionsRegistry) {
    this.toolName = toolName;
    this.actionsRegistry = actionsRegistry;
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        createPostSessionInitActions();
      }
    });
  }

  public void add(@Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, @Nonnull final String typeId) {
    actionsRegistry.addAction(toolName, actionsGroupId, action, typeId);
  }

  public void add(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final ContentStatus status, @Nonnull final String[] typeIds,
      final @Nonnull Provider<? extends GuiActionDescrip> action) {
    actionsRegistry.addActions(tool, actionsGroupId, status, typeIds, action);
  }

  public void add(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final ContentStatus status, @Nonnull final String[] typeIds,
      final @Nonnull Provider<? extends GuiActionDescrip>... actions) {
    actionsRegistry.addActions(tool, actionsGroupId, status, typeIds, actions);
  }

  public void add(@Nonnull final String tool, final String actionsGroupId,
      final GuiActionDescrip descrip, final String typeId) {
    actionsRegistry.addAction(tool, actionsGroupId, descrip, typeId);
  }

  public void add(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final Provider<? extends GuiActionDescrip> action) {
    actionsRegistry.addAction(tool, actionsGroupId, action);
  }

  public void add(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, final ContentStatus status,
      @Nonnull final String typeId) {
    actionsRegistry.addAction(tool, actionsGroupId, action, status, typeId);
  }

  public void add(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, @Nonnull final String typeId) {
    actionsRegistry.addAction(tool, actionsGroupId, action, typeId);
  }

  public void add(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      @Nonnull final String[] typeIds, final @Nonnull Provider<? extends GuiActionDescrip> action) {
    actionsRegistry.addActions(tool, actionsGroupId, typeIds, action);
  }

  public void add(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      @Nonnull final String[] typeIds, final @Nonnull Provider<? extends GuiActionDescrip>... actionList) {
    actionsRegistry.addActions(tool, actionsGroupId, typeIds, actionList);
  }

  public void add(@Nonnull final String actionsGroupId, @Nonnull final String[] typeIds,
      final @Nonnull Provider<? extends GuiActionDescrip>... actionList) {
    actionsRegistry.addActions(toolName, actionsGroupId, typeIds, actionList);
  }

  protected abstract void createPostSessionInitActions();

}
