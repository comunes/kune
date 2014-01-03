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
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupOptToolsPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupOptToolsPresenter extends EntityOptToolsPresenter implements GroupOptTools {

  /**
   * Instantiates a new group opt tools presenter.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
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
  public GroupOptToolsPresenter(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final GroupOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final GroupOptToolsView view) {
    super(session, stateManager, i18n, entityOptions, groupService);
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
   * cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#applicable()
   */
  @Override
  protected boolean applicable() {
    return session.isCurrentStateAGroup();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#getAllTools()
   */
  @Override
  protected Collection<ToolSimpleDTO> getAllTools() {
    return session.getGroupTools();
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
    final ContentSimpleDTO defaultContent = session.getCurrentState().getGroup().getDefaultContent();
    return defaultContent == null ? null : defaultContent.getStateToken();
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
    return i18n.t("This tool cannot be disabled as long as it’s where the group’s home page is located. Change the default home page to another tool then try again.");
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
    return session.getCurrentState().getEnabledTools();
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
    return session.getCurrentStateToken();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.tools.EntityOptToolsPresenter#
   * gotoDifLocationIfNecessary(java.lang.String)
   */
  @Override
  protected void gotoDifLocationIfNecessary(final String toolName) {
    if (session.getCurrentStateToken().getTool().equals(toolName)) {
      final ContentSimpleDTO defaultContent = session.getCurrentState().getGroup().getDefaultContent();
      if (defaultContent != null) {
        stateManager.gotoStateToken(defaultContent.getStateToken());
      }
    }
  }

}
