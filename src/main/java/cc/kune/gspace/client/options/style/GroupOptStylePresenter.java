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
package cc.kune.gspace.client.options.style;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.options.GroupOptions;
import cc.kune.gspace.client.style.GSpaceBackgroundManager;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupOptStylePresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupOptStylePresenter extends EntityOptStylePresenter implements GroupOptStyle {

  /**
   * Instantiates a new group opt style presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param entityOptions
   *          the entity options
   * @param groupService
   *          the group service
   * @param backManager
   *          the back manager
   * @param view
   *          the view
   * @param styleSelector
   *          the style selector
   * @param i18n
   *          the i18n
   * @param fileDownUtils
   *          the file down utils
   */
  @Inject
  public GroupOptStylePresenter(final EventBus eventBus, final Session session,
      final StateManager stateManager, final GroupOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final GSpaceBackgroundManager backManager,
      final GroupOptStyleView view, final GSpaceThemeSelectorPresenter styleSelector,
      final I18nTranslationService i18n, final ClientFileDownloadUtils fileDownUtils) {
    super(eventBus, session, stateManager, entityOptions, groupService, backManager, styleSelector,
        i18n, fileDownUtils);
    init(view);
  }

}
