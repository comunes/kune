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
package cc.kune.gspace.client.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.viewers.NoHomePageViewer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentViewerSelector.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ContentViewerSelector {

  /** The current view. */
  private ContentViewer currentView;

  /** The def views register. */
  private final HashMap<String, ContentViewer> defViewsRegister;

  /** The no home. */
  private final Provider<NoHomePageViewer> noHome;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /** The views register. */
  private final HashMap<String, List<ContentViewer>> viewsRegister;

  /**
   * Instantiates a new content viewer selector.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param noHome
   *          the no home
   */
  @Inject
  public ContentViewerSelector(final StateManager stateManager, final Session session,
      final Provider<NoHomePageViewer> noHome) {
    this.stateManager = stateManager;
    this.session = session;
    this.noHome = noHome;
    viewsRegister = new HashMap<String, List<ContentViewer>>();
    defViewsRegister = new HashMap<String, ContentViewer>();
  }

  /**
   * Detach current.
   */
  private void detachCurrent() {
    if (currentView != null) {
      currentView.detach();
    }
  }

  /**
   * Inits the.
   */
  public void init() {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        stateManager.onStateChanged(true, new StateChangedHandler() {
          @Override
          public void onStateChanged(final StateChangedEvent event) {
            final StateAbstractDTO state = event.getState();
            if (state instanceof StateContentDTO || state instanceof StateContainerDTO) {
              setContent((HasContent) state);
            } else {
              setContent(noHome.get(), null);
            }
          }
        });
      }
    });
  }

  /**
   * Register.
   * 
   * @param view
   *          the view
   * @param isDefault
   *          the is default
   * @param typeIds
   *          the type ids
   */
  public void register(@Nonnull final ContentViewer view, final boolean isDefault,
      @Nonnull final String... typeIds) {
    for (final String typeId : typeIds) {
      List<ContentViewer> list = viewsRegister.get(typeId);
      if (list == null) {
        list = new ArrayList<ContentViewer>();
      }
      if (!list.contains(view)) {
        list.add(view);
      }
      if (isDefault) {
        defViewsRegister.put(typeId, view);
      }
    }
  }

  /**
   * Register.
   * 
   * @param typeId
   *          the type id
   * @param view
   *          the view
   */
  public void register(final String typeId, final ContentViewer view) {
    Log.info("Registered " + typeId + " with class " + ContentViewer.class);
    register(view, false, typeId);
  }

  /**
   * Sets the content.
   * 
   * @param view
   *          the view
   * @param state
   *          the state
   */
  private void setContent(final ContentViewer view, final HasContent state) {
    detachCurrent();
    view.setContent(state);
    view.attach();
    currentView = view;
  }

  /**
   * Sets the content.
   * 
   * @param state
   *          the new content
   */
  public void setContent(@Nonnull final HasContent state) {
    final String typeId = state.getTypeId();
    assert typeId != null;
    final ContentViewer defView = defViewsRegister.get(typeId);
    if (defView == null) {
      final List<ContentViewer> viewsList = viewsRegister.get(typeId);
      if (viewsList != null && !viewsList.isEmpty()) {
        setContent(viewsList.get(0), state);
      } else {
        NotifyUser.error("Unsupported typeId: " + typeId);
      }
    } else {
      setContent(defView, state);
    }
  }
}
