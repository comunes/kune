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
package cc.kune.core.client.errors;

import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.notify.msgs.UserNotifyEvent;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.wave.client.WebClient;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.inject.Inject;

public class ErrorHandler {

  private final EventBus eventBus;
  private final I18nTranslationService i18n;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public ErrorHandler(final Session session, final I18nTranslationService i18n,
      final StateManager stateManager, final EventBus eventBus) {
    this.session = session;
    this.i18n = i18n;
    this.stateManager = stateManager;
    this.eventBus = eventBus;
  }

  public void doSessionExpired() {
    eventBus.fireEvent(new SessionExpiredEvent());
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info,
        "Your session has expired. Please log in again."));
  }

  private void goHome() {
    stateManager.gotoHistoryToken(SiteTokens.GROUP_HOME);
  }

  private void logException(final Throwable caught) {
    logException(caught, false);
  }

  private void logException(final Throwable caught, final boolean showException) {
    if (showException) {
      Log.debug("Exception in KuneErrorHandler", caught);
    } else {
      Log.debug("Exception in KuneErrorHandler: " + caught.getMessage());
    }
  }

  public void process(final Throwable caught) {
    eventBus.fireEvent(new ProgressHideEvent());
    if (caught instanceof AccessViolationException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("You do not have rights to perform that action")));
    } else if (caught instanceof SessionExpiredException) {
      logException(caught);
      doSessionExpired();
    } else if (caught instanceof UserMustBeLoggedException) {
      logException(caught);
      if (session.isLogged()) {
        doSessionExpired();
      } else {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.important,
            i18n.t("Please sign in or register to collaborate")));
      }
    } else if (caught instanceof GroupNotFoundException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, i18n.t("Group not found")));
      goHome();
    } else if (caught instanceof IncompatibleRemoteServiceException) {
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("Your browser is outdated with the server software. Please reload this page.")));
    } else if (caught instanceof ContentNotFoundException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, i18n.t("Content not found")));
      goHome();
    } else if (caught instanceof ContentNotPermittedException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("Action not permitted in this location")));
      goHome();
    } else if (caught instanceof ContainerNotPermittedException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("Action not permitted in this location")));
      goHome();
    } else if (caught instanceof LastAdminInGroupException) {
      logException(caught);
      NotifyUser.showAlertMessage(i18n.t("Warning"),
          i18n.t("Sorry, you are the last admin of this group."
              + " Look for someone to substitute you appropriately as admin before leaving this group."));
    } else if (caught instanceof AlreadyGroupMemberException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("This group is already a group member")));
    } else if (caught instanceof AlreadyUserMemberException) {
      logException(caught);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("This user is already a group member")));
    } else {
      logException(caught, true);
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
          i18n.t("Oops! Something has gone wrong with our servers. Retry later, please.")));
      final String error = "Other kind of exception in StateManagerDefault/processErrorException";
      Log.error(error, caught);
      WebClient.ErrorHandler.getStackTraceAsync(caught, new Accessor<SafeHtml>() {
        @Override
        public void use(final SafeHtml stack) {
          NotifyUser.logError(stack.asString().replace("<br>", "\n"));
        }
      });
    }
  }

}
