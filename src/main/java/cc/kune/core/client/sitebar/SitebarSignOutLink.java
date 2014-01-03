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
import cc.kune.common.client.actions.BeforeActionCollection;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.state.Session;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SitebarSignOutLink.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarSignOutLink extends ButtonDescriptor {

  /**
   * The Class BeforeSignOut.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class BeforeSignOut extends BeforeActionCollection {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2326033703822323868L;
  }

  /** The Constant SITE_SIGN_OUT. */
  public static final String SITE_SIGN_OUT = "k-ssolp-lb";

  /**
   * Instantiates a new sitebar sign out link.
   * 
   * @param action
   *          the action
   * @param eventBus
   *          the event bus
   * @param errorHandler
   *          the error handler
   * @param session
   *          the session
   * @param sitebarActions
   *          the sitebar actions
   */
  @Inject
  public SitebarSignOutLink(final AbstractSignOutAction action, final EventBus eventBus,
      final ErrorHandler errorHandler, final Session session) {
    super(action);
    setId(SITE_SIGN_OUT);
    final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
        SitebarActions.RIGHT_TOOLBAR);
    withText(I18n.t("Sign out"));
    setParent(SitebarActions.RIGHT_TOOLBAR);
    setVisible(session.isLogged());
    setStyles(ActionStyles.SITEBAR_STYLE_FL);
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        final boolean logged = event.isLogged();
        SitebarSignOutLink.this.setVisible(logged);
        separator.setVisible(logged);
      }
    });
  }
}
