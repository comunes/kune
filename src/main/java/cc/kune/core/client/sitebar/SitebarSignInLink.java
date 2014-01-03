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

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.state.Session;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * The Class SitebarSignInLink.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarSignInLink extends ButtonDescriptor {

  /** The Constant SITE_SIGN_IN. */
  public static final String SITE_SIGN_IN = "kune-ssilp-hy";

  /**
   * Instantiates a new sitebar sign in link.
   * 
   * @param action
   *          the action
   * @param eventBus
   *          the event bus
   * @param session
   *          the session
   */
  @Inject
  public SitebarSignInLink(final AbstractSignInAction action, final EventBus eventBus,
      final Session session) {
    super(action);
    // setParent(SitebarActionsPresenter.RIGHT_TOOLBAR);
    setId(SITE_SIGN_IN);
    setVisible(!session.isLogged());
    setStyles(ActionStyles.SITEBAR_STYLE_FL);
    setParent(SitebarActions.RIGHT_TOOLBAR);
    final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
        SitebarActions.RIGHT_TOOLBAR);
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        final boolean logged = event.isLogged();
        SitebarSignInLink.this.setVisible(!logged);
        separator.setVisible(!logged);
      }
    });
  }
}
