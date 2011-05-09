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
package cc.kune.gspace.client.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.viewers.NoHomePageViewer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentViewerSelector {

  private ContentViewer currentView;
  private final HashMap<String, ContentViewer> defViewsRegister;
  private final Provider<NoHomePageViewer> noHome;
  private final Session session;
  private final StateManager stateManager;
  private final HashMap<String, List<ContentViewer>> viewsRegister;

  @Inject
  public ContentViewerSelector(final StateManager stateManager, final Session session,
      final Provider<NoHomePageViewer> noHome) {
    this.stateManager = stateManager;
    this.session = session;
    this.noHome = noHome;
    viewsRegister = new HashMap<String, List<ContentViewer>>();
    defViewsRegister = new HashMap<String, ContentViewer>();
  }

  private void detachCurrent() {
    if (currentView != null) {
      currentView.detach();
    }
  }

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

  public void register(final String typeId, final ContentViewer view) {
    Log.info("Registered " + typeId + " with class " + ContentViewer.class);
    register(view, false, typeId);
  }

  private void setContent(final ContentViewer view, final HasContent state) {
    detachCurrent();
    view.setContent(state);
    view.attach();
    currentView = view;
  }

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
