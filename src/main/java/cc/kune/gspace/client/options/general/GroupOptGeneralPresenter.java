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

// TODO: Auto-generated Javadoc
/**
 * The Class GroupOptGeneralPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupOptGeneralPresenter extends EntityOptGeneralPresenter implements GroupOptGeneral {

  /** The group service. */
  private final Provider<GroupServiceAsync> groupService;

  /** The group view. */
  private final GroupOptGeneralView groupView;

  /**
   * Instantiates a new group opt general presenter.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param entityOptions
   *          the entity options
   * @param groupService
   *          the group service
   * @param view
   *          the view
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralPresenter#applicable
   * ()
   */
  @Override
  protected boolean applicable() {
    return session.isCurrentStateAGroup();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralPresenter#init(cc
   * .kune.gspace.client.options.general.EntityOptGeneralView)
   */
  @Override
  public void init(final EntityOptGeneralView view) {
    super.init(view);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralPresenter#setState()
   */
  @Override
  protected void setState() {
    final GroupDTO group = session.getCurrentState().getGroup();
    if (group.isNotPersonal()) {
      groupView.setShortNameEnabled(!session.getInitData().getSiteShortName().equals(
          group.getShortName()));
      groupView.setShortName(group.getShortName());
      groupView.setLongName(group.getLongName());
      groupView.setGroupType(group.getGroupType());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralPresenter#updateInServer
   * ()
   */
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
