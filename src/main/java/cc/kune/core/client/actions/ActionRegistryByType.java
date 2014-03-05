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
package cc.kune.core.client.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescProviderCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.registry.IdGenerator;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.AccessRights;

import com.google.inject.Provider;

/**
 * A registry of actions by content type (doc, post, etc) and grouped (some
 * actions for some toolbar, etc)
 * 
 */
public class ActionRegistryByType {
  private static final String GENERIC_TYPE_ID = "kgentype";
  private static final String KEY_SEPARATOR = "-";

  private final Map<String, GuiActionDescProviderCollection> actions;

  public ActionRegistryByType() {
    actions = new HashMap<String, GuiActionDescProviderCollection>();
  }

  private void add(final GuiActionDescCollection collection, final GuiActionDescrip descrip,
      final Object targetItem) {
    descrip.setTarget(targetItem);
    collection.add(descrip);
  }

  public void addAction(@Nonnull final String tool, final String actionsGroupId,
      final GuiActionDescrip descrip, final String typeId) {
    addActions(tool, actionsGroupId, new String[] { typeId }, new Provider<GuiActionDescrip>() {
      @Override
      public GuiActionDescrip get() {
        return descrip;
      }
    });
  }

  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final Provider<? extends GuiActionDescrip> action) {
    addActions(tool, actionsGroupId, new String[] { GENERIC_TYPE_ID }, action);
  }

  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, final ContentStatus status,
      @Nonnull final String typeId) {
    addActions(tool, actionsGroupId, new String[] { IdGenerator.generate(typeId, status.toString()) },
        action);
  }

  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, @Nonnull final String typeId) {
    final GuiActionDescProviderCollection actionColl = getActions(tool, actionsGroupId, typeId);
    actionColl.add(action);
    actions.put(genKey(tool, actionsGroupId, typeId), actionColl);
  }

  public void addActions(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final ContentStatus status, @Nonnull final String[] typeIds,
      final @Nonnull Provider<? extends GuiActionDescrip> action) {
    addAction(tool, actionsGroupId, action);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void addActions(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final ContentStatus status, @Nonnull final String[] typeIds,
      final @Nonnull Provider<? extends GuiActionDescrip>... actions) {
    for (final String typeId : typeIds) {
      for (final Provider action : actions) {
        addActions(tool, actionsGroupId,
            new String[] { IdGenerator.generate(typeId, status.toString()) }, action);
      }
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void addActions(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      @Nonnull final String[] typeIds,
      final @Nonnull List<Provider<? extends GuiActionDescrip>> actionList) {
    for (final String typeId : typeIds) {
      for (final Provider action : actionList) {
        final GuiActionDescProviderCollection actionColl = getActions(tool, actionsGroupId, typeId);
        actionColl.add(action);
        actions.put(genKey(tool, actionsGroupId, typeId), actionColl);
      }
    }
  }

  public void addActions(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      @Nonnull final String[] typeIds, final @Nonnull Provider<? extends GuiActionDescrip> action) {
    for (final String typeId : typeIds) {
      addAction(tool, actionsGroupId, action, typeId);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void addActions(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      @Nonnull final String[] typeIds, final @Nonnull Provider<? extends GuiActionDescrip>... actionList) {
    for (final String typeId : typeIds) {
      for (final Provider action : actionList) {
        final GuiActionDescProviderCollection actionColl = getActions(tool, actionsGroupId, typeId);
        actionColl.add(action);
        actions.put(genKey(tool, actionsGroupId, typeId), actionColl);
      }
    }
  }

  private String genKey(final String tool, final String actionsGroupId, final String typeId) {
    return tool + KEY_SEPARATOR + actionsGroupId + KEY_SEPARATOR + typeId;
  }

  private GuiActionDescProviderCollection getActions(final String tool, final String actionsGroupId,
      final String typeId) {
    final String key = genKey(tool, actionsGroupId, typeId);
    GuiActionDescProviderCollection actionColl = actions.get(key);
    if (actionColl == null) {
      actionColl = new GuiActionDescProviderCollection();
      actions.put(key, actionColl);
    }
    return actionColl;
  }

  public GuiActionDescCollection getCurrentActions(final String tool, final Object targetItem,
      final boolean isLogged, final AccessRights rights, @Nullable final String actionsGroup) {
    return getCurrentActions(tool, targetItem, GENERIC_TYPE_ID, isLogged, rights, actionsGroup);
  }

  public GuiActionDescCollection getCurrentActions(@Nonnull final String tool, final Object targetItem,
      final String typeId, final boolean isLogged, final AccessRights rights) {
    return getCurrentActions(tool, targetItem, typeId, isLogged, rights, null);
  }

  public <T> GuiActionDescCollection getCurrentActions(final String tool, final Object targetItem,
      final String typeId, final boolean isLogged, final AccessRights rights,
      @Nullable final String actionsGroupId) {
    final GuiActionDescCollection collection = new GuiActionDescCollection();
    for (final Provider<? extends GuiActionDescrip> descripProv : getActions(tool, actionsGroupId,
        typeId)) {
      final GuiActionDescrip descrip = descripProv.get();
      final AbstractAction action = descrip.getAction();
      if (action instanceof RolAction) {
        // Log.debug("Must add action?: " + action + ", isLogged: " + isLogged +
        // ", r: " + rights);
        final RolAction rolAction = (RolAction) action;
        if (RolActionHelper.mustAdd(rolAction.getRolRequired(), rolAction.getHigherRol(),
            rolAction.isAuthNeed(), isLogged, rights)) {
          add(collection, descrip, targetItem);
        }
      } else {
        add(collection, descrip, targetItem);
      }
    }
    return collection;
  }

  public <T> GuiActionDescCollection getCurrentActions(@Nonnull final String tool,
      final Object targetItem, final String typeId, final ContentStatus status, final boolean isLogged,
      final AccessRights rights, @Nullable final String actionsGroupId) {
    final GuiActionDescCollection collection = new GuiActionDescCollection();
    collection.addAll(getCurrentActions(tool, targetItem, typeId, isLogged, rights, actionsGroupId));
    collection.addAll(getCurrentActions(tool, targetItem,
        IdGenerator.generate(typeId, status.toString()), isLogged, rights, actionsGroupId));
    return collection;
  }

  private boolean mustAdd(final RolAction action, final boolean isLogged, final AccessRights rights) {
    if (action.isAuthNeed()) {
      if (!isLogged) {
        return false;
      }
    }
    switch (action.getRolRequired()) {
    case Administrator:
      return rights.isAdministrable();
    case Editor:
      return rights.isEditable();
    case Viewer:
    default:
      return rights.isVisible();
    }
  }

}
