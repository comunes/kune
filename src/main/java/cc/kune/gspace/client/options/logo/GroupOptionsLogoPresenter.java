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
package cc.kune.gspace.client.options.logo;

import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.GroupChangedEvent;
import cc.kune.core.client.state.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupOptionsLogoPresenter extends EntityOptionsLogoPresenter {

  @Inject
  public GroupOptionsLogoPresenter(final EventBus eventBus, final Session session,
      final GroupOptions entityOptions, final StateManager stateManager,
      final Provider<UserServiceAsync> userService, final GroupOptionsLogoView view,
      final I18nTranslationService i18n) {
    super(eventBus, session, entityOptions, userService, i18n);
    init(view);
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        setState();
      }
    });
  }

  private void init(final GroupOptionsLogoView view) {
    super.init(view);
    view.setNormalGroupsLabels();
  }

  @Override
  protected void setState() {
    view.setUploadParams(session.getUserHash(), session.getCurrentStateToken().toString());
  }
}
