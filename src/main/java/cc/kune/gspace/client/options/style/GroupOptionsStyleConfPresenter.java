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
package cc.kune.gspace.client.options.style;

import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.options.GroupOptions;
import cc.kune.gspace.client.style.GSpaceBackManager;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupOptionsStyleConfPresenter extends EntityOptionsStyleConfPresenter implements
    GroupOptionsStyleConf {

  @Inject
  public GroupOptionsStyleConfPresenter(final EventBus eventBus, final Session session,
      final StateManager stateManager, final GroupOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final GSpaceBackManager backManager,
      final GroupOptionsStyleConfView view, final GSpaceThemeSelectorPresenter styleSelector) {
    super(eventBus, session, stateManager, entityOptions, groupService, backManager, styleSelector);
    init(view);
    stateManager.onStateChanged(true, new StateChangedHandler() {

      @Override
      public void onStateChanged(final StateChangedEvent event) {
        // final String theme = state.getGroup().getWorkspaceTheme();
        // themeSelector.select(theme);
      }
    });
  }

}