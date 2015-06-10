/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

import cc.kune.common.client.actions.BeforeActionCollection;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The Class SitebarSignOutLink.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarSignOutLink extends AbstractSitebarSignOutLink {

  /**
   * The Class BeforeSignOut.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class BeforeSignOut extends BeforeActionCollection {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2326033703822323868L;
  }

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
      final Session session) {
    super(action, eventBus, session);
    setParent(SitebarActions.RIGHT_TOOLBAR);
  }

  @Override
  protected void onUserSignInOrSignOut(final boolean logged) {
    super.onUserSignInOrSignOut(logged);
    final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
        SitebarActions.RIGHT_TOOLBAR);
    separator.setVisible(logged);
  }
}
