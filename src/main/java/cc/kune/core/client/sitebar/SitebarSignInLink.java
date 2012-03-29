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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.SessionConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class SitebarSignInLink extends ButtonDescriptor {

  public static class SitebarSignInAction extends AbstractExtendedAction {

    private final StateManager stateManager;

    @Inject
    public SitebarSignInAction(final StateManager stateManager, final I18nUITranslationService i18n,
        final SessionConstants session) {
      super();
      this.stateManager = stateManager;
      putValue(Action.NAME, i18n.t("Sign in to collaborate"));
      // putValue(
      // Action.TOOLTIP,
      // i18n.t("Please sign in or register to get full access to [%s] tools and contents",
      // i18n.getSiteCommonName()));
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      stateManager.gotoHistoryTokenButRedirectToCurrent(SiteTokens.SIGN_IN);
    }

  }

  public static final String SITE_SIGN_IN = "kune-ssilp-hy";

  @Inject
  public SitebarSignInLink(final SitebarSignInAction action, final EventBus eventBus,
      final Session session) {
    super(action);
    // setParent(SitebarActionsPresenter.RIGHT_TOOLBAR);
    setId(SITE_SIGN_IN);
    setVisible(!session.isLogged());
    setStyles("k-no-backimage, k-btn-sitebar, k-fl, k-noborder, k-nobackcolor");
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
