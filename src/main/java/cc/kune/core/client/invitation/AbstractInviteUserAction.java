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

package cc.kune.core.client.invitation;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.actions.RolActionAutoUpdated;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.InvitationServiceAsync;
import cc.kune.core.client.sitebar.search.EntitySearchPanel;
import cc.kune.core.client.sitebar.search.OnEntitySelectedInSearch;
import cc.kune.core.client.state.*;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Provider;

/**
 * The class AbstractInviteUserAction. Abstracts the invitation of an existing
 * kune users to groups and lists
 *
 * @author Antonio Tenorio Fornés <antoniotenorio@ucm.es>
 *
 */
public abstract class AbstractInviteUserAction extends RolActionAutoUpdated {

  /** The Constant INVITE_USER_TEXTBOX. */
  public static final String INVITE_USER_SEARCH_PANEL = "kune-invite-user-search-panel";

  /** the invitation service */
  private final Provider<InvitationServiceAsync> invitationService;

  /** The search panel */
  private final EntitySearchPanel searchPanel;

  /** The state token */
  private StateToken token;

  /** the invitation type */
  private final InvitationType type;

  /**
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param rightsManager
   *          the rights manager
   * @param rolRequired
   *          the rol required
   * @param authNeed
   *          the auth need
   * @param visibleForNonMemb
   *          the visible for non memb
   * @param visibleForMembers
   *          the visible for members
   * @param searchPanel
   *          the search panel
   * @param i18n
   */
  public AbstractInviteUserAction(final StateManager stateManager, final Session session,
      final AccessRightsClientManager rightsManager, final AccessRolDTO rolRequired,
      final boolean authNeed, final boolean visibleForNonMemb, final boolean visibleForMembers,
      final EntitySearchPanel searchPanel, final IconicResources res, final InvitationType type,
      final Provider<InvitationServiceAsync> invitationService) {
    super(stateManager, session, rightsManager, rolRequired, authNeed, visibleForNonMemb,
        visibleForMembers);
    this.searchPanel = searchPanel;
    this.type = type;
    this.invitationService = invitationService;
    searchPanel.init(true, INVITE_USER_SEARCH_PANEL, new OnEntitySelectedInSearch() {
      @Override
      public void onSeleted(final String shortName) {
        confirmSelectedItem(shortName);
      }
    });

  };

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    final StateToken token;
    if (event.getTarget() instanceof AbstractContentSimpleDTO) {
      token = ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
    } else {
      token = session.getCurrentStateToken();
    }
    this.token = token;
    searchPanel.show();
    searchPanel.focus();
  }

  private void confirmSelectedItem(final String shortName) {
    String confirmText = I18n.t("Do you want to invite '[%s]'?", shortName);
    switch (type) {
    case TO_GROUP:
      confirmText = I18n.t("Do you want to invite '[%s]' as a member of '[%s]'?", shortName,
          token.getGroup());
      break;
    case TO_LISTS:
      // TODO: Add list name to confirmText
      confirmText = I18n.t("Do you want to invite '[%s]' to the list?", shortName);
      break;
    case TO_SITE:
      // It does not make sense to invite an already existing user to the
      // site
      break;
    default:
      break;

    }
    NotifyUser.askConfirmation(I18n.t("Are you sure?"), confirmText, new SimpleResponseCallback() {
      @Override
      public void onCancel() {
      }

      @Override
      public void onSuccess() {
        final AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {

          @Override
          public void onFailure(final Throwable caught) {

          }

          @Override
          public void onSuccess(final Void result) {
            NotifyUser.info(I18n.t("Invitation sent"));
          }
        };
        NotifyUser.showProgress();
        switch (type) {
        case TO_GROUP:
          invitationService.get().inviteUserToGroup(session.getUserHash(), token, shortName,
              asyncCallback);
          break;
        case TO_LISTS:
          invitationService.get().inviteUserToList(session.getUserHash(), token, shortName,
              asyncCallback);
          break;
        case TO_SITE:
          // It does not make sense to invite an already existing user to
          // the site
          break;
        default:
          break;
        }
        NotifyUser.hideProgress();

      }
    });
  }
}