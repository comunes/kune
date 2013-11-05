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
package cc.kune.core.client.actions;

import java.util.HashMap;
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

// TODO: Auto-generated Javadoc
/**
 * A registry of actions by content type (doc, post, etc) and grouped (some
 * actions for some toolbar, etc).
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ActionRegistryByType {
  
  /** The Constant GENERIC_TYPE_ID. */
  private static final String GENERIC_TYPE_ID = "kgentype";
  
  /** The Constant KEY_SEPARATOR. */
  private static final String KEY_SEPARATOR = "-";

  /** The actions. */
  private final Map<String, GuiActionDescProviderCollection> actions;

  /**
   * Instantiates a new action registry by type.
   */
  public ActionRegistryByType() {
    actions = new HashMap<String, GuiActionDescProviderCollection>();
  }

  /**
   * Adds the.
   *
   * @param collection the collection
   * @param descrip the descrip
   * @param targetItem the target item
   */
  private void add(final GuiActionDescCollection collection, final GuiActionDescrip descrip,
      final Object targetItem) {
    descrip.setTarget(targetItem);
    collection.add(descrip);
  }

  /**
   * Adds the action.
   *
   * @param tool the tool
   * @param actionsGroupId the actions group id
   * @param descrip the descrip
   * @param typeId the type id
   */
  public void addAction(@Nonnull final String tool, final String actionsGroupId,
      final GuiActionDescrip descrip, final String typeId) {
    addAction(tool, actionsGroupId, new Provider<GuiActionDescrip>() {
      @Override
      public GuiActionDescrip get() {
        return descrip;
      }
    }, typeId);
  }

  /**
   * Adds the action.
   *
   * @param tool the tool
   * @param actionsGroupId the actions group id
   * @param action the action
   */
  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final Provider<? extends GuiActionDescrip> action) {
    addAction(tool, actionsGroupId, action, GENERIC_TYPE_ID);
  }

  /**
   * Adds the action.
   *
   * @param tool the tool
   * @param actionsGroupId the actions group id
   * @param action the action
   * @param status the status
   * @param typeIds the type ids
   */
  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, final ContentStatus status,
      @Nonnull final String... typeIds) {
    for (final String typeId : typeIds) {
      addAction(tool, actionsGroupId, action, IdGenerator.generate(typeId, status.toString()));
    }
  }

  /**
   * Adds the action.
   *
   * @param tool the tool
   * @param actionsGroupId the actions group id
   * @param action the action
   * @param typeIds the type ids
   */
  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, @Nonnull final String... typeIds) {
    for (final String typeId : typeIds) {
      final GuiActionDescProviderCollection actionColl = getActions(tool, actionsGroupId, typeId);
      actionColl.add(action);
      actions.put(genKey(tool, actionsGroupId, typeId), actionColl);
    }
  }

  /**
   * Gen key.
   *
   * @param tool the tool
   * @param actionsGroupId the actions group id
   * @param typeId the type id
   * @return the string
   */
  private String genKey(final String tool, final String actionsGroupId, final String typeId) {
    return tool + KEY_SEPARATOR + actionsGroupId + KEY_SEPARATOR + typeId;
  }

  /**
   * Gets the actions.
   *
   * @param tool the tool
   * @param actionsGroupId the actions group id
   * @param typeId the type id
   * @return the actions
   */
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

  /**
   * Gets the current actions.
   *
   * @param tool the tool
   * @param targetItem the target item
   * @param isLogged the is logged
   * @param rights the rights
   * @param actionsGroup the actions group
   * @return the current actions
   */
  public GuiActionDescCollection getCurrentActions(final String tool, final Object targetItem,
      final boolean isLogged, final AccessRights rights, @Nullable final String actionsGroup) {
    return getCurrentActions(tool, targetItem, GENERIC_TYPE_ID, isLogged, rights, actionsGroup);
  }

  /**
   * Gets the current actions.
   *
   * @param tool the tool
   * @param targetItem the target item
   * @param typeId the type id
   * @param isLogged the is logged
   * @param rights the rights
   * @return the current actions
   */
  public GuiActionDescCollection getCurrentActions(@Nonnull final String tool, final Object targetItem,
      final String typeId, final boolean isLogged, final AccessRights rights) {
    return getCurrentActions(tool, targetItem, typeId, isLogged, rights, null);
  }

  /**
   * Gets the current actions.
   *
   * @param <T> the generic type
   * @param tool the tool
   * @param targetItem the target item
   * @param typeId the type id
   * @param isLogged the is logged
   * @param rights the rights
   * @param actionsGroupId the actions group id
   * @return the current actions
   */
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
        if (mustAdd((RolAction) action, isLogged, rights)) {
          add(collection, descrip, targetItem);
        }
      } else {
        add(collection, descrip, targetItem);
      }
    }
    return collection;
  }

  /**
   * Gets the current actions.
   *
   * @param <T> the generic type
   * @param tool the tool
   * @param targetItem the target item
   * @param typeId the type id
   * @param status the status
   * @param isLogged the is logged
   * @param rights the rights
   * @param actionsGroupId the actions group id
   * @return the current actions
   */
  public <T> GuiActionDescCollection getCurrentActions(@Nonnull final String tool,
      final Object targetItem, final String typeId, final ContentStatus status, final boolean isLogged,
      final AccessRights rights, @Nullable final String actionsGroupId) {
    final GuiActionDescCollection collection = new GuiActionDescCollection();
    collection.addAll(getCurrentActions(tool, targetItem, typeId, isLogged, rights, actionsGroupId));
    collection.addAll(getCurrentActions(tool, targetItem,
        IdGenerator.generate(typeId, status.toString()), isLogged, rights, actionsGroupId));
    return collection;
  }

  /**
   * Must add.
   *
   * @param action the action
   * @param isLogged the is logged
   * @param rights the rights
   * @return true, if successful
   */
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
