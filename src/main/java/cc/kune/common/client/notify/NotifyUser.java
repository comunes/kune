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
package cc.kune.common.client.notify;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;

public class NotifyUser {
  private static EventBus eventBus;
  private static I18nTranslationService i18n;
  private static SimpleResponseCallback onOk;

  public static void askConfirmation(final ImageResource icon, final String title, final String message,
      final SimpleResponseCallback callback) {
    eventBus.fireEvent(new ConfirmAskEvent(icon, title, message, i18n.t("Yes"), i18n.t("No"), callback));
  }

  public static void askConfirmation(final String title, final String message,
      final SimpleResponseCallback callback) {
    askConfirmation(null, title, message, callback);
  }

  public static void avatar(final String url, final String message, final ClickHandler clickHandler) {
    final UserNotifyEvent event = new UserNotifyEvent(NotifyLevel.avatar.url(url), message);
    event.setClickHandler(clickHandler);
    eventBus.fireEvent(event);
  }

  public static void error(final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message));
  }

  public static void error(final String message, final boolean closeable) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message, closeable));
  }

  public static void error(final String message, final String additionalMessage) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message
        + (TextUtils.empty(additionalMessage) ? "" : ": " + additionalMessage)));
  }

  public static void error(final String message, final String title, final boolean closeable) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message, title, closeable));
  }

  public static void error(final String title, final String message, final String id,
      final boolean closeable) {
    sendEventImpl(title, message, id, closeable, NotifyLevel.error);
  }

  public static void hideProgress() {
    eventBus.fireEvent(new ProgressHideEvent());
  }

  public static void important(final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.important, message));
  }

  public static void info(final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, message));
  }

  public static void info(final String message, final boolean closeable) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, message, closeable));
  }

  public static void info(final String title, final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, title, message));
  }

  public static void info(final String title, final String message, final String id,
      final boolean closeable) {
    sendEventImpl(title, message, id, closeable, NotifyLevel.info);
  }

  public static void init(final EventBus eventBus, final I18nTranslationService i18n) {
    NotifyUser.eventBus = eventBus;
    NotifyUser.i18n = i18n;
    onOk = new SimpleResponseCallback() {

      @Override
      public void onCancel() {
        // Do nothing
      }

      @Override
      public void onSuccess() {
        // Do nothing
      }
    };

  }

  public static void logError(final String message) {
    if (eventBus != null) {
      eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.log, message));
    }
  }

  private static void sendEventImpl(final String title, final String message, final String id,
      final boolean closeable, final NotifyLevel level) {
    final UserNotifyEvent event = new UserNotifyEvent(level, title, message, closeable);
    event.setId(id);
    eventBus.fireEvent(event);
  }

  public static void showAlertMessage(final String title, final String message) {
    showAlertMessage(title, message, onOk);
  }

  public static void showAlertMessage(final String title, final String message,
      final SimpleResponseCallback callback) {
    eventBus.fireEvent(new ConfirmAskEvent(title, message, i18n.t("Ok"), "", callback));
  }

  public static void showProgress() {
    eventBus.fireEvent(new ProgressShowEvent(""));
  }

  public static void showProgress(final String text) {
    eventBus.fireEvent(new ProgressShowEvent(text));
  }

  public static void showProgressLoading() {
    // Better empty message
    eventBus.fireEvent(new ProgressShowEvent());
  }

  public static void showProgressSearching() {
    eventBus.fireEvent(new ProgressShowEvent(i18n.t("Searching")));
  }

  public static void veryImportant(final String message) {
    eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, message));
  }

}
