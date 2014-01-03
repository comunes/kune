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

package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.SessionConstants;

import com.google.inject.Inject;

/**
 * The Class SitebarSignInAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarSignInAction extends AbstractSignInAction {

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new sitebar sign in action.
   * 
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   */
  @Inject
  public SitebarSignInAction(final StateManager stateManager, final I18nTranslationService i18n,
      final SessionConstants session) {
    super();
    this.stateManager = stateManager;
    putValue(Action.NAME, i18n.t("Sign in to collaborate"));
    // putValue(
    // Action.TOOLTIP,
    // i18n.t("Please sign in or register to get full access to [%s] tools and contents",
    // i18n.getSiteCommonName()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    stateManager.gotoHistoryTokenButRedirectToCurrent(SiteTokens.SIGN_IN);
  }

}