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
package cc.kune.common.client.notify;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import cc.kune.common.client.log.Log;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.state.SessionInstance;
import de.codeset.gwt.notification.api.client.Notification;
import de.codeset.gwt.notification.api.client.Notification.NotificationOptions;
import de.codeset.gwt.notification.api.client.Notification.NotificationPermissionCallback;
import de.codeset.gwt.notification.api.client.NotificationPermission;
import de.codeset.gwt.notification.api.client.eventing.click.NotificationClickEvent;
import de.codeset.gwt.notification.api.client.eventing.click.NotificationClickHandler;

/**
 * The Class UserNotifierHtml5.
 *
 */
@Singleton
public class UserNotifierHtml5 {

  /**
   * Instantiates a new user desktop notifier.
   *
   * https://developer.mozilla.org/en-US/docs/Web/API/notification
   * https://www.w3.org/TR/notifications/
   *
   * @param eventBus
   *          the event bus
   */
  @Inject
  public UserNotifierHtml5(final EventBus eventBus) {
    Log.info("Notifications supported: " + Notification.isSupported());
    if (Notification.isSupported()) {
      if (Notification.getPermission().equals(NotificationPermission.DEFAULT)) {
        Log.info("Notification permission: " + Notification.getPermission());
        Notification.requestPermission(new NotificationPermissionCallback() {
          @Override
          public void call(NotificationPermission permission) {
            Log.info("Notification permission: " + permission);
          }
        });
      }
      eventBus.addHandler(UserNotifyEvent.getType(), new UserNotifyEvent.UserNotifyHandler() {
        @Override
        public void onUserNotify(final UserNotifyEvent event) {
          if (Notification.isSupported()
              && Notification.getPermission().equals(NotificationPermission.GRANTED)) {
            // event.getCloseable();
            String icon = "";
            String image = "";
            String id = event.getId();
            final NotifyLevel level = event.getLevel();
            String siteUrl = SessionInstance.get().getSiteUrl();
            switch (level) {
            case error:
            case veryImportant:
            case important:
            case success:
            case info:
              icon = siteUrl + "/images/touch/icon-72x72.png";
              // image =
              // UserMessageImagesUtil.getIcon(event.getLevel()).getSafeUri().asString();
              break;
            case avatar:
              icon = siteUrl + event.getLevel().getUrl();
              break;
            case log:
              // Do nothing with this level
              return;
            default:
              break;
            }

            NotificationOptions options = NotificationOptions.create();
            options.dir(I18n.isRTL() ? "rtl" : "ltr");
            options.body(event.getMessage());
            options.lang(SessionInstance.get().getCurrentLanguage().getCode());
            Boolean closeable = event.getCloseable();
            // very intrusive ?
            options.requireInteraction(closeable);
            if (TextUtils.notEmpty(id)) {
              options.tag(id);
            }
            if (TextUtils.notEmpty(icon)) {
              options.icon(icon);
            }
            if (TextUtils.notEmpty(image)) {
              options.image(image);
            }
            ClickHandler clickHandler = event.getClickHandler();
            final Notification notification = Notification.createIfSupported(event.getTitle(), options);
            if (clickHandler != null) {
              notification.addClickHandler(new NotificationClickHandler() {
                @Override
                public void onClick(NotificationClickEvent event) {
                  clickHandler.onClick(null);
                  event.getNotification().close();
                }
              });
            }
            if (!closeable) {
              new Timer() {
                @Override
                public void run() {
                  notification.close();
                }
              }.schedule(5000);
            }
          }
        }
      });
    }
  }
}
