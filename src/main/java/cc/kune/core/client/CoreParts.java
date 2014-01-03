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
package cc.kune.core.client;

import cc.kune.common.client.shortcuts.GlobalShortcuts;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.auth.AnonUsersManager;
import cc.kune.core.client.auth.EmailNotVerifiedReminder;
import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.groups.newgroup.NewGroup;
import cc.kune.core.client.init.WebSocketChecker;
import cc.kune.core.client.invitation.InvitationClientManager;
import cc.kune.core.client.invitation.SiteInvitationBtn;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.sitebar.AboutKuneDialog;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sitebar.auth.AskForPasswordResetPanel;
import cc.kune.core.client.sitebar.auth.PasswordResetPanel;
import cc.kune.core.client.sitebar.auth.VerifyEmailClientManager;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.sn.GroupSNPresenter;
import cc.kune.core.client.sn.UserSNPresenter;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.core.client.state.HistoryTokenAuthNotNeededCallback;
import cc.kune.core.client.state.HistoryTokenMustBeAuthCallback;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.LinkInterceptor;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.sub.SubtitlesManager;
import cc.kune.gspace.client.options.GroupOptions;
import cc.kune.gspace.client.options.UserOptions;
import cc.kune.gspace.client.viewers.TutorialViewer;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class CoreParts.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CoreParts {

  /**
   * Instantiates a new core parts.
   * 
   * @param session
   *          the session
   * @param groupMembersPresenter
   *          the group members presenter
   * @param buddiesAndParticipationPresenter
   *          the buddies and participation presenter
   * @param groupMembersConfActions
   *          the group members conf actions
   * @param userSNConfActions
   *          the user sn conf actions
   * @param anonUsers
   *          the anon users
   * @param userOptions
   *          the user options
   * @param spaceSelector
   *          the space selector
   * @param tokenListener
   *          the token listener
   * @param signIn
   *          the sign in
   * @param register
   *          the register
   * @param aboutKuneDialog
   *          the about kune dialog
   * @param newGroup
   *          the new group
   * @param subProvider
   *          the sub provider
   * @param eventBus
   *          the event bus
   * @param verifyManager
   *          the verify manager
   * @param userOptionsDialog
   *          the user options dialog
   * @param groupOptionsDialog
   *          the group options dialog
   * @param passReset
   *          the pass reset
   * @param askForPass
   *          the ask for pass
   * @param shortcuts
   *          the shortcuts
   * @param i18n
   *          the i18n
   * @param tutorialViewer
   *          the tutorial viewer
   * @param websocketChecker
   *          the websocket checker
   * @param emailNotVerifiedReminder
   *          the email not verified reminder
   * @param siteInvitation
   *          the site invitation
   * @param invitationManager
   *          the invitation manager
   */
  @Inject
  public CoreParts(final Session session, final Provider<GroupSNPresenter> groupMembersPresenter,
      final Provider<UserSNPresenter> buddiesAndParticipationPresenter,
      final Provider<GroupSNConfActions> groupMembersConfActions,
      final Provider<UserSNConfActions> userSNConfActions, final Provider<AnonUsersManager> anonUsers,
      final Provider<SiteUserOptionsPresenter> userOptions,
      final Provider<SpaceSelectorPresenter> spaceSelector, final SiteTokenListeners tokenListener,
      final Provider<SignIn> signIn, final Provider<Register> register,
      final Provider<AboutKuneDialog> aboutKuneDialog, final Provider<NewGroup> newGroup,
      final Provider<SubtitlesManager> subProvider, final EventBus eventBus,
      final Provider<VerifyEmailClientManager> verifyManager,
      final Provider<UserOptions> userOptionsDialog, final Provider<GroupOptions> groupOptionsDialog,
      final Provider<PasswordResetPanel> passReset, final Provider<AskForPasswordResetPanel> askForPass,
      final GlobalShortcuts shortcuts, final I18nTranslationService i18n,
      final Provider<TutorialViewer> tutorialViewer, final Provider<WebSocketChecker> websocketChecker,
      final Provider<EmailNotVerifiedReminder> emailNotVerifiedReminder,
      final Provider<SiteInvitationBtn> siteInvitation, final InvitationClientManager invitationManager,
      final Provider<LinkInterceptor> linkInterceptor, final HistoryWrapper history) {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        groupMembersConfActions.get();
        userSNConfActions.get();
        groupMembersPresenter.get();
        buddiesAndParticipationPresenter.get();
        userOptions.get();
        anonUsers.get();
        spaceSelector.get();
        tutorialViewer.get();
        emailNotVerifiedReminder.get();
        websocketChecker.get();
        siteInvitation.get();
        linkInterceptor.get();
      }
    });
    tokenListener.put(SiteTokens.SIGN_IN, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        Scheduler.get().scheduleFinally(new ScheduledCommand() {
          @Override
          public void execute() {
            if (session.isLogged()) {
              // We are logged, then redirect:
              if (token != null && token.matches(TextUtils.URL_REGEXP)) {
                // Redirect to other website
                KuneWindowUtils.open(token);
              } else {
                history.newItem(token, true);
              }
            } else {
              signIn.get().showSignInDialog(token);
            }
          }
        });
      }
    });
    tokenListener.put(SiteTokens.ABOUT_KUNE, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        // FIXME, something to come back
        aboutKuneDialog.get().showCentered();
      }
    });
    tokenListener.put(SiteTokens.REGISTER, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        register.get().doRegister(token);
      }
    });
    tokenListener.put(SiteTokens.TUTORIAL, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        // Do nothing (move SMD part here, but depends of getContent callback)
      }
    });
    tokenListener.put(SiteTokens.NEW_GROUP,
        new HistoryTokenMustBeAuthCallback(i18n.t(CoreMessages.REGISTER_TO_CREATE_A_GROUP)) {
          @Override
          public void onHistoryToken(final String token) {
            Scheduler.get().scheduleFinally(new ScheduledCommand() {
              @Override
              public void execute() {
                newGroup.get().doNewGroup();
              }
            });
          }
        });
    tokenListener.put(SiteTokens.SUBTITLES, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        subProvider.get().show(token);
      }
    });
    tokenListener.put(SiteTokens.HOME, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        SpaceSelectEvent.fire(eventBus, Space.homeSpace);
      }
    });
    tokenListener.put(SiteTokens.WAVE_INBOX,
        new HistoryTokenMustBeAuthCallback(i18n.t(CoreMessages.SIGN_IN_TO_ACCESS_INBOX)) {
          @Override
          public void onHistoryToken(final String token) {
            SpaceSelectEvent.fire(eventBus, Space.userSpace);
          }
        });
    tokenListener.put(SiteTokens.PREFS, new HistoryTokenMustBeAuthCallback("") {
      @Override
      public void onHistoryToken(final String token) {
        Scheduler.get().scheduleFinally(new ScheduledCommand() {
          @Override
          public void execute() {
            SpaceSelectEvent.fire(eventBus, Space.groupSpace);
            userOptionsDialog.get().show();
          }
        });
      }
    });
    tokenListener.put(SiteTokens.GROUP_PREFS, new HistoryTokenMustBeAuthCallback("") {
      @Override
      public void onHistoryToken(final String token) {
        Scheduler.get().scheduleFinally(new ScheduledCommand() {
          @Override
          public void execute() {
            groupOptionsDialog.get().show(token);
          }
        });
      }
    });
    tokenListener.put(SiteTokens.RESET_PASSWD, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        Scheduler.get().scheduleFinally(new ScheduledCommand() {
          @Override
          public void execute() {
            if (!session.isLogged()) {
              askForPass.get().show();
            }
          }
        });
      }
    });
    tokenListener.put(SiteTokens.ASK_RESET_PASSWD, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        Scheduler.get().scheduleFinally(new ScheduledCommand() {
          @Override
          public void execute() {
            if (!session.isLogged()) {
              askForPass.get().show();
            }
          }
        });
      }
    });
    tokenListener.put(SiteTokens.RESET_PASSWD, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        if (!session.isLogged()) {
          passReset.get().setPasswordHash(token);
          passReset.get().show();
        }
      }
    });
    tokenListener.put(SiteTokens.INVITATION, new HistoryTokenAuthNotNeededCallback() {
      @Override
      public void onHistoryToken(final String token) {
        invitationManager.process(token);
      }
    });
    verifyManager.get();
  }
}
