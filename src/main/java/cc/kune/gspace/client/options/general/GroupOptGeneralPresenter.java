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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupOptGeneralPresenter extends EntityOptGeneralPresenter implements GroupOptGeneral {

  private final Provider<GroupServiceAsync> groupService;
  private final GroupOptGeneralView groupView;

  @Inject
  public GroupOptGeneralPresenter(final StateManager stateManager, final Session session,
      final EventBus eventBus, final I18nUITranslationService i18n, final GroupOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final GroupOptGeneralView view) {
    super(session, stateManager, eventBus, i18n, entityOptions);
    this.groupService = groupService;
    groupView = view;
    init(view);
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        setState();
      }
    });
  }

  @Override
  protected boolean applicable() {
    return session.isCurrentStateAGroup();
  }

  @Override
  public void init(final EntityOptGeneralView view) {
    super.init(view);
  }

  @Override
  protected void setState() {
    final GroupDTO group = session.getCurrentState().getGroup();
    groupView.setShortNameEnabled(!session.getInitData().getSiteShortName().equals(group.getShortName()));
    groupView.setShortName(group.getShortName());
    groupView.setLongName(group.getLongName());
  }

  @Override
  protected void updateInServer() {
    if (view.isValid()) {
      NotifyUser.showProgress(i18n.t("Saving"));
      final GroupDTO group = session.getCurrentState().getGroup();
      final String previousGroupName = group.getShortName();
      final StateToken token = group.getStateToken().copy();
      final String newShortName = groupView.getShortName().toLowerCase();
      final String newLongName = groupView.getLongName();
      group.setShortName(newShortName);
      group.setLongName(newLongName);
      group.setGroupType(groupView.getGroupType());
      groupService.get().updateGroup(session.getUserHash(), token, group,
          new AsyncCallbackSimple<StateAbstractDTO>() {
            @Override
            public void onSuccess(final StateAbstractDTO result) {
              NotifyUser.hideProgress();
              stateManager.removeCacheOfGroup(previousGroupName);
              stateManager.setRetrievedStateAndGo(result);
              setState();
              sendChangeEntityEvent(newShortName, newLongName);
            }
          });
    }
  }

}
