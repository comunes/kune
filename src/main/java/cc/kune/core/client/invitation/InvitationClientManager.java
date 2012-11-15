/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.core.client.invitation;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.errors.IncorrectHashException;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.InvitationServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.dto.InvitationDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class InvitationClientManager {
  private final I18nUITranslationService i18n;
  private final Provider<InvitationServiceAsync> invitationService;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public InvitationClientManager(final Provider<InvitationServiceAsync> invitationService,
      final Session session, final I18nUITranslationService i18n, final StateManager stateManager) {
    this.invitationService = invitationService;
    this.session = session;
    this.i18n = i18n;
    this.stateManager = stateManager;
  }

  public void process(final String hash) {
    invitationService.get().getInvitation(hash, new AsyncCallbackSimple<InvitationDTO>() {
      @Override
      public void onFailure(final Throwable caught) {
        if (caught instanceof IncorrectHashException) {
          NotifyUser.error(I18n.t("Sorry, this is a wrong or expired invitation"), true);
          stateManager.gotoHomeSpace();
        } else {
          super.onFailure(caught);
        }
      }

      @Override
      public void onSuccess(final InvitationDTO invitation) {
        final String invitationToToken = invitation.getInvitedToToken();
        final String name = invitation.getName();
        final String description = invitation.getDescription();
        final String whoInvites = invitation.getFromUser().getName();
        final InvitationType type = invitation.getType();

        if (session.isNotLogged()) {
          BasicTopDialog dialog;
          AbstractInvitationConfirmDialog.Builder builder;
          switch (type) {
          case TO_SITE:
            stateManager.gotoHomeSpace();
            builder = new SiteInvitationConfirmDialog.Builder(i18n, whoInvites);
            break;
          case TO_GROUP:
            stateManager.gotoHistoryToken(invitationToToken);
            builder = new GroupInvitationConfirmDialog.Builder(i18n, whoInvites, name, description);
            break;
          case TO_LISTS:
            stateManager.gotoHistoryToken(invitationToToken);
            builder = new ListInvitationConfirmDialog.Builder(i18n, whoInvites, name, description);
            break;
          default:
            throw new RuntimeException("Unexpected type");
          }
          dialog = builder.build();
          dialog.showCentered();
        } else {
          // Logged! TODO
          switch (type) {
          case TO_SITE:
            // Goto buddie!?
            stateManager.gotoHomeSpace();
            break;
          case TO_GROUP:
            stateManager.gotoHistoryToken(invitationToToken);
            break;
          case TO_LISTS:
            stateManager.gotoHistoryToken(invitationToToken);
            break;
          default:
            throw new RuntimeException("Unexpected type");
          }
        }
      }

    });
  }
}
