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
package cc.kune.common.client.notify;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class NotifyUser.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class NotifyUser {
  
  /** The event bus. */
  @Inject
  private static EventBus eventBus;

  /** The on ok. */
  private static SimpleResponseCallback onOk = new SimpleResponseCallback() {

    @Override
    public void onCancel() {
      // Do nothing
    }

    @Override
    public void onSuccess() {
      // Do nothing
    }
  };

  /**
   * Ask confirmation.
   *
   * @param icon the icon
   * @param title the title
   * @param message the message
   * @param callback the callback
   */
  public static void askConfirmation(final ImageResource icon, final String title, final String message,
      final SimpleResponseCallback callback) {
    eventBus.fireEvent(new ConfirmAskEvent(icon, title, message, I18n.t("Yes"), I18n.t("No"), callback));
  }

  /**
   * Ask confirmation.
   *
   * @param title the title
   * @param message the message
   * @param callback the callback
   */
  public static void askConfirmation(final String title, final String message,
      final SimpleResponseCallback callback) {
    askConfirmation(null, title, message, callback);
  }

  /**
   * Avatar.
   *
   * @param url the url
   * @param message the message
   * @param clickHandler the click handler
   */
  public static void avatar(final String url, final String message, final ClickHandler clickHandler) {
    final UserNotifyEvent event = new UserNotifyEvent(NotifyLevel.avatar.url(url), message);
    event.setClickHandler(clickHandler);
    eventBus.fireEvent(event);
  }

  /**
   * Error.
   *
   * @param message the message
   */
  public static void error(final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message));
  }

  /**
   * Error.
   *
   * @param message the message
   * @param closeable the closeable
   */
  public static void error(final String message, final boolean closeable) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message, closeable));
  }

  /**
   * Error.
   *
   * @param message the message
   * @param additionalMessage the additional message
   */
  public static void error(final String message, final String additionalMessage) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message
        + (TextUtils.empty(additionalMessage) ? "" : ": " + additionalMessage)));
  }

  /**
   * Error.
   *
   * @param title the title
   * @param message the message
   * @param closeable the closeable
   */
  public static void error(final String title, final String message, final boolean closeable) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, title, message, closeable));
  }

  /**
   * Error.
   *
   * @param title the title
   * @param message the message
   * @param id the id
   * @param closeable the closeable
   */
  public static void error(final String title, final String message, final String id,
      final boolean closeable) {
    sendEventImpl(title, message, id, closeable, NotifyLevel.error);
  }

  /**
   * Hide progress.
   */
  public static void hideProgress() {
    eventBus.fireEvent(new ProgressHideEvent());
  }

  /**
   * Important.
   *
   * @param message the message
   */
  public static void important(final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.important, message));
  }

  /**
   * Info.
   *
   * @param message the message
   */
  public static void info(final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, message));
  }

  /**
   * Info.
   *
   * @param message the message
   * @param closeable the closeable
   */
  public static void info(final String message, final boolean closeable) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, message, closeable));
  }

  /**
   * Info.
   *
   * @param title the title
   * @param message the message
   */
  public static void info(final String title, final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, title, message));
  }

  /**
   * Info.
   *
   * @param title the title
   * @param message the message
   * @param id the id
   * @param closeable the closeable
   */
  public static void info(final String title, final String message, final String id,
      final boolean closeable) {
    sendEventImpl(title, message, id, closeable, NotifyLevel.info);
  }

  /**
   * Log error.
   *
   * @param message the message
   */
  public static void logError(final String message) {
    if (eventBus != null) {
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.log, message));
    }
  }

  /**
   * Send event impl.
   *
   * @param title the title
   * @param message the message
   * @param id the id
   * @param closeable the closeable
   * @param level the level
   */
  private static void sendEventImpl(final String title, final String message, final String id,
      final boolean closeable, final NotifyLevel level) {
    final UserNotifyEvent event = new UserNotifyEvent(level, title, message, closeable);
    event.setId(id);
    eventBus.fireEvent(event);
  }

  /**
   * Show alert message.
   *
   * @param title the title
   * @param message the message
   */
  public static void showAlertMessage(final String title, final String message) {
    showAlertMessage(title, message, onOk);
  }

  /**
   * Show alert message.
   *
   * @param title the title
   * @param message the message
   * @param callback the callback
   */
  public static void showAlertMessage(final String title, final String message,
      final SimpleResponseCallback callback) {
    eventBus.fireEvent(new ConfirmAskEvent(title, message, I18n.t("Ok"), "", callback));
  }

  /**
   * Show progress.
   */
  public static void showProgress() {
    eventBus.fireEvent(new ProgressShowEvent(""));
  }

  /**
   * Show progress.
   *
   * @param text the text
   */
  public static void showProgress(final String text) {
    eventBus.fireEvent(new ProgressShowEvent(text));
  }

  /**
   * Show progress loading.
   */
  public static void showProgressLoading() {
    // Better empty message
    eventBus.fireEvent(new ProgressShowEvent());
  }

  /**
   * Show progress searching.
   */
  public static void showProgressSearching() {
    eventBus.fireEvent(new ProgressShowEvent(I18n.t("Searching")));
  }

  /**
   * Very important.
   *
   * @param message the message
   */
  public static void veryImportant(final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, message));
  }

}
