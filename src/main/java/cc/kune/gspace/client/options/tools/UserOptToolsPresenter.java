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
package cc.kune.gspace.client.options.tools;

import java.util.Collection;
import java.util.List;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.gspace.client.options.UserOptions;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserOptToolsPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserOptToolsPresenter extends EntityOptToolsPresenter implements UserOptTools {

  /**
   * Instantiates a new user opt tools presenter.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
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
  public UserOptToolsPresenter(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final UserOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final UserOptToolsView view) {
    super(session, stateManager, i18n, entityOptions, groupService);
    init(view);
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        setState();
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#applicable()
   */
  @Override
  protected boolean applicable() {
    return session.isLogged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#getAllTools()
   */
  @Override
  protected Collection<ToolSimpleDTO> getAllTools() {
    return session.getUserTools();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#getDefContentToken
   * ()
   */
  @Override
  protected StateToken getDefContentToken() {
    final String homePage = session.getCurrentUserInfo().getHomePage();
    return homePage == null ? null : new StateToken(homePage);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#
   * getDefContentTooltip()
   */
  @Override
  protected String getDefContentTooltip() {
    // FIXME
    return i18n.t("You cannot disable this tool because it's where your home page is located. To do that you have to select other content as the default home page but in another tool.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#getEnabledTools
   * ()
   */
  @Override
  protected List<String> getEnabledTools() {
    return session.getCurrentUserInfo().getEnabledTools();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#getOperationToken
   * ()
   */
  @Override
  protected StateToken getOperationToken() {
    return session.getCurrentUser().getStateToken();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#
   * gotoDifLocationIfNecessary(java.lang.String)
   */
  @Override
  protected void gotoDifLocationIfNecessary(final String toolName) {
    if (session.getCurrentStateToken().getGroup().equals(session.getCurrentUserInfo().getShortName())
        && session.getCurrentStateToken().getTool().equals(toolName)) {
      stateManager.gotoStateToken(session.getCurrentState().getGroup().getDefaultContent().getStateToken());
    }
  }
}
