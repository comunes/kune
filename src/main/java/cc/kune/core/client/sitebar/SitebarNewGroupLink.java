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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SitebarNewGroupLink.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarNewGroupLink extends ButtonDescriptor {

  /**
   * The Class SitebarNewGroupAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class SitebarNewGroupAction extends AbstractExtendedAction {

    /** The state manager. */
    private final StateManager stateManager;

    /**
     * Instantiates a new sitebar new group action.
     * 
     * @param stateManager
     *          the state manager
     * @param i18n
     *          the i18n
     */
    @Inject
    public SitebarNewGroupAction(final StateManager stateManager, final I18nTranslationService i18n) {
      super();
      this.stateManager = stateManager;
      withText(i18n.t(CoreMessages.NEW_GROUP_TITLE));
      withToolTip(i18n.t("Create a new group for your initiative or organization "
          + "(NGO, collective, academic group...)"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      stateManager.gotoHistoryToken(SiteTokens.NEW_GROUP);
    }
  }

  /** The Constant NEW_GROUP_BTN_ID. */
  public static final String NEW_GROUP_BTN_ID = "k-site-newgroup-btn";

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new sitebar new group link.
   * 
   * @param newGroupAction
   *          the new group action
   * @param sitebarActions
   *          the sitebar actions
   * @param i18n
   *          the i18n
   * @param eventBus
   *          the event bus
   * @param coreResources
   *          the core resources
   * @param session
   *          the session
   */
  @Inject
  public SitebarNewGroupLink(final SitebarNewGroupAction newGroupAction,
      final SitebarActions sitebarActions, final I18nTranslationService i18n, final EventBus eventBus,
      final CoreResources coreResources, final Session session) {
    super(newGroupAction);
    this.session = session;
    withId(NEW_GROUP_BTN_ID).withStyles(ActionStyles.SITEBAR_STYLE_FL);
    withParent(SitebarActions.RIGHT_TOOLBAR);
    final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
        SitebarActions.RIGHT_TOOLBAR);
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        final boolean notLogged = !event.isLogged();
        recalculate(notLogged);
        separator.setVisible(notLogged);
      }
    });

  }

  /**
   * Recalculate.
   * 
   * @param notLogged
   *          the not logged
   */
  public void recalculate(final boolean notLogged) {
    if (notLogged) {
      setVisible(true);
    } else {
      setVisible(!session.userIsJoiningGroups());
    }
  }

}
