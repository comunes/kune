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

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.errors.IncorrectHashException;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.InvitationServiceAsync;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.dto.InvitationDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class InvitationClientManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class InvitationClientManager {

  /** The chat engine. */
  private final ChatClient chatEngine;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The invitation service. */
  private final Provider<InvitationServiceAsync> invitationService;

  /** The session. */
  private final Session session;

  /** The sn service. */
  private final Provider<SocialNetServiceAsync> snService;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new invitation client manager.
   * 
   * @param invitationService
   *          the invitation service
   * @param chatEngine
   *          the chat engine
   * @param snService
   *          the sn service
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param stateManager
   *          the state manager
   */
  @Inject
  public InvitationClientManager(final Provider<InvitationServiceAsync> invitationService,
      final ChatClient chatEngine, final Provider<SocialNetServiceAsync> snService,
      final Session session, final I18nTranslationService i18n, final StateManager stateManager) {
    this.invitationService = invitationService;
    this.chatEngine = chatEngine;
    this.snService = snService;
    this.session = session;
    this.i18n = i18n;
    this.stateManager = stateManager;
  }

  /**
   * Process.
   * 
   * @param hash
   *          the hash
   */
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
        final String invitationHash = invitation.getHash();
        final String invitationToToken = invitation.getInvitedToToken();
        final String name = invitation.getName();
        final String description = invitation.getDescription();
        final UserSimpleDTO from = invitation.getFromUser();
        final String whoInvitesName = from.getName();
        final String whoInvitesShortName = from.getShortName();
        final InvitationType type = invitation.getType();

        if (session.isNotLogged()) {
          final String redirect = TokenUtils.addRedirect(SiteTokens.INVITATION, invitationHash);
          BasicTopDialog dialog;
          AbstractInvitationConfirmDialog.Builder builder;
          switch (type) {
          case TO_SITE:
            stateManager.gotoHomeSpace();
            builder = new SiteInvitationConfirmDialog.Builder(redirect, i18n, whoInvitesName);
            break;
          case TO_GROUP:
            stateManager.gotoHistoryToken(invitationToToken);
            builder = new GroupInvitationConfirmDialog.Builder(redirect, i18n, whoInvitesName, name,
                description);
            break;
          case TO_LISTS:
            stateManager.gotoHistoryToken(invitationToToken);
            builder = new ListInvitationConfirmDialog.Builder(redirect, i18n, whoInvitesName, name,
                description);
            break;
          default:
            throw new RuntimeException("Unexpected type");
          }
          dialog = builder.build();
          dialog.showCentered();
        } else {
          switch (type) {
          case TO_SITE:
            stateManager.gotoHomeSpace();
            invitationService.get().confirmationInvitationToSite(session.getUserHash(), invitationHash,
                new AsyncCallbackSimple<Void>() {
                  @Override
                  public void onSuccess(final Void result) {
                  }
                });
            if (!chatEngine.isBuddy(whoInvitesShortName)) {
              NotifyUser.askConfirmation(I18n.t("Do you want to add '[%s]' as a buddie?",
                  whoInvitesShortName), I18n.t(
                  "'[%s]' invited you to this site. Do you want to add him/her as a buddie?",
                  whoInvitesName), new SimpleResponseCallback() {
                @Override
                public void onCancel() {
                }

                @Override
                public void onSuccess() {
                  chatEngine.addNewBuddy(whoInvitesShortName);
                  snService.get().addAsBuddie(session.getUserHash(), whoInvitesShortName,
                      new AsyncCallbackSimple<Void>() {
                        @Override
                        public void onSuccess(final Void result) {
                        }
                      });
                }
              });
            }
            break;
          case TO_GROUP:
            NotifyUser.askConfirmation(I18n.t("Invitation to join the group '[%s]'", name), I18n.t(
                "[%s] has invited you to join the group '[%s]'. Do you want to accept the invitation?",
                whoInvitesName, description), new SimpleResponseCallback() {
              @Override
              public void onCancel() {
              }

              @Override
              public void onSuccess() {
                invitationService.get().confirmationInvitationToGroup(session.getUserHash(),
                    invitationHash, new AsyncCallbackSimple<Void>() {
                      @Override
                      public void onSuccess(final Void result) {
                        // TODO
                        stateManager.gotoHistoryToken(invitationToToken);
                        stateManager.refreshCurrentStateWithoutCache();
                      }
                    });
              }
            });

            break;
          case TO_LISTS:
            NotifyUser.askConfirmation(
                I18n.t("Invitation to join the list '[%s]'", name),
                I18n.t(
                    "[%s] has invited you to join the list '[%s]' of group '[%s]'.  Do you want to accept the invitation?",
                    whoInvitesName, name, description), new SimpleResponseCallback() {
                  @Override
                  public void onCancel() {
                  }

                  @Override
                  public void onSuccess() {
                    invitationService.get().confirmInvitationToList(session.getUserHash(),
                        invitationHash, new AsyncCallbackSimple<StateContainerDTO>() {
                          @Override
                          public void onSuccess(final StateContainerDTO result) {
                            stateManager.gotoHistoryToken(invitationToToken);
                            NotifyUser.info(i18n.t("Subscribed"));
                            stateManager.setRetrievedState(result);
                            stateManager.refreshCurrentState();
                            NotifyUser.hideProgress();
                          }
                        });
                  }
                });
            break;
          default:
            throw new RuntimeException("Unexpected type");
          }
        }
      }

    });
  }
}
