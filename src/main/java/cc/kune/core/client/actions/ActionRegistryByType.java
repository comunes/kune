/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
import cc.kune.common.client.log.Log;
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
    addAction(tool, actionsGroupId, new Provider<GuiActionDescrip>() {
      @Override
      public GuiActionDescrip get() {
        return descrip;
      }
    }, typeId);
  }

  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final Provider<? extends GuiActionDescrip> action) {
    addAction(tool, actionsGroupId, action, GENERIC_TYPE_ID);
  }

  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, final ContentStatus status,
      @Nonnull final String... typeIds) {
    for (final String typeId : typeIds) {
      addAction(tool, actionsGroupId, action, IdGenerator.generate(typeId, status.toString()));
    }
  }

  public void addAction(@Nonnull final String tool, @Nonnull final String actionsGroupId,
      final @Nonnull Provider<? extends GuiActionDescrip> action, @Nonnull final String... typeIds) {
    for (final String typeId : typeIds) {
      final GuiActionDescProviderCollection actionColl = getActions(tool, actionsGroupId, typeId);
      actionColl.add(action);
      actions.put(genKey(tool, actionsGroupId, typeId), actionColl);
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
        Log.debug("Must add action?: " + action + ", isLogged: " + isLogged + ", r: " + rights);
        if (mustAdd((RolAction) action, isLogged, rights)) {
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
