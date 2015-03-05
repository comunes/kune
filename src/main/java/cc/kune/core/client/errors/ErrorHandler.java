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
package cc.kune.core.client.errors;

import cc.kune.common.client.events.EventBusInstance;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.UserNotifyEvent;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.events.GoHomeEvent;
import cc.kune.core.client.events.StackErrorEvent;
import cc.kune.core.client.events.UserMustBeLoggedEvent;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.inject.Singleton;

/**
 * The Class ErrorHandler.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ErrorHandler {
  protected static final String ERROR_ID = "k-general-service-error";

  /**
   * Instantiates a new error handler.
   *
   * @param I18n
   *          the I18n
   * @param EventBusInstance.get()
   *          the event bus
   */
  public ErrorHandler() {
  }

  /**
   * Do session expired.
   */
  public void doSessionExpired() {
    EventBusInstance.get().fireEvent(new SessionExpiredEvent());
  }

  /**
   * Go home.
   */
  private void goHome() {
    GoHomeEvent.fire(EventBusInstance.get());
  }

  /**
   * Log exception.
   *
   * @param caught
   *          the caught
   */
  private void logException(final Throwable caught) {
    logException(caught, false);
  }

  /**
   * Log exception.
   *
   * @param caught
   *          the caught
   * @param showException
   *          the show exception
   */
  private void logException(final Throwable caught, final boolean showException) {
    if (showException) {
      Log.debug("Exception in KuneErrorHandler", caught);
    } else {
      Log.debug("Exception in KuneErrorHandler: " + caught.getMessage());
    }
  }

  /**
   * Process.
   *
   * @param caught
   *          the caught
   */
  public void process(final Throwable caught) {
    EventBusInstance.get().fireEvent(new ProgressHideEvent());
    if (caught instanceof AccessViolationException) {
      logException(caught);
      final String msg = caught.getMessage();
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("You do not have rights to perform that action")
              + (!TextUtils.empty(msg) && msg.length() > 2 ? ". " + I18n.t(msg) : "")));
      goHome();
    } else if (caught instanceof SessionExpiredException) {
      logException(caught);
      doSessionExpired();
    } else if (caught instanceof UserMustBeLoggedException) {
      logException(caught);
      UserMustBeLoggedEvent.fire(EventBusInstance.get());
    } else if (caught instanceof GroupNotFoundException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, I18n.t("Group not found")));
      goHome();
    } else if (caught instanceof IncompatibleRemoteServiceException) {
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("Your browser is outdated with the server software. Please reload this page.")));
    } else if (caught instanceof ContentNotFoundException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, I18n.t("Content not found")));
      goHome();
    } else if (caught instanceof ContentNotPermittedException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("Action not permitted in this location")));
      goHome();
    } else if (caught instanceof ContainerNotPermittedException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("Action not permitted in this location")));
      goHome();
    } else if (caught instanceof NameInUseException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("A content with the same name already exist. Please rename it")));
    } else if (caught instanceof LastAdminInGroupException) {
      logException(caught);
      NotifyUser.showAlertMessage(I18n.t("Warning"),
          I18n.t("Sorry, you are the last admin of this group."
              + " Look for someone to substitute you appropriately as admin before leaving this group."));

    } else if (caught instanceof InvalidSNOperationException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error, I18n.t("Invalid operation")));
    } else if (caught instanceof AlreadyGroupMemberException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("This group is already a group member")));
    } else if (caught instanceof AlreadyUserMemberException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("This user is already a group member")));
    } else if (caught instanceof EmailAddressInUseException) {
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error, I18n.t(
          "This email is already used in [%s]. Please choose another.", I18n.getSiteCommonName())));
    } else if (caught instanceof EmailHashInvalidException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("This confirmation email is invalid")));
    } else if (caught instanceof EmailHashExpiredException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(
          NotifyLevel.error,
          I18n.t("This email verification is expired. In your preferences, resend you the confirmation email")));
    } else if (caught instanceof MoveOnSameContainerException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.info,
          I18n.t("You are trying to move this to the same location")));
    } else if (caught instanceof NameNotPermittedException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.info, I18n.t("This name is not permitted")));
    } else if (caught instanceof ContainerNotEmptyException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error, I18n.t("This is not empty")));
    } else if (caught instanceof CannotDeleteDefaultContentException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.error,
          I18n.t("This is the default content of the group, you cannot delete it. "
              + "Please select other content as default group content before delete this")));
    } else if (caught instanceof UnderDevelopmentException) {
      logException(caught);
      EventBusInstance.get().fireEvent(new UserNotifyEvent(NotifyLevel.info, I18n.t(TextUtils.IN_DEVELOPMENT)));
    } else {
      logException(caught, true);
      final String error = "Other kind of exception received in ErrorHandler (" + caught.getMessage()
          + ")";
      showGeneralError(error);
      StackErrorEvent.fire(EventBusInstance.get(), caught);
    }
  }

  public static void showGeneralError(final String message) {
    NotifyUser.logError(message);
    NotifyUser.showProgress();
    NotifyUser.error(
        I18n.t("We're sorry..."),
        I18n.t("For some reason [%s] is currently experiencing errors. "
            + "Try again refreshing your browser. "
            + "If the problem persist, please provide us feedback with more info "
            + "(see it in topbar menu > Errors info) so we can try to fix it. Thanks",
            I18n.getSiteCommonName()), ERROR_ID, true);
  }
}
