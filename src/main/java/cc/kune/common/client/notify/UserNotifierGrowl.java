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

import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Styles;
import org.gwtbootstrap3.extras.growl.client.ui.Growl;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlHelper;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlOptions;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlPosition;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlTemplate;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import cc.kune.common.shared.utils.TextUtils;
import de.codeset.gwt.notification.api.client.Notification;
import de.codeset.gwt.notification.api.client.NotificationPermission;

/**
 * The Class UserNotifierGrowl.
 *
 * http://bootstrap-growl.remabledesigns.com/
 */
@Singleton
public class UserNotifierGrowl {
  /** The Constant AVATAR_SIZE. */
  private static final String AVATAR_SIZE = "40px";
  private static final String SEPARATOR = " ";

  /**
   * Instantiates a new user notifier growl.
   *
   * @param eventBus
   *          the event bus
   */
  @Inject
  public UserNotifierGrowl(final EventBus eventBus, UserNotifierHtml5 htmlNotif) {

    eventBus.addHandler(UserNotifyEvent.getType(), new UserNotifyEvent.UserNotifyHandler() {

      @Override
      public void onUserNotify(final UserNotifyEvent event) {
        if (!Notification.isSupported() || !Notification.getPermission().equals(NotificationPermission.GRANTED)) {
          final GrowlOptions options = GrowlHelper.getNewOptions();

          final GrowlPosition position = GrowlHelper.getNewPosition();
          position.setCenter();
          position.setTop(false);
          options.setGrowlPosition(position);

          String id = event.getId();
          boolean hasId = TextUtils.notEmpty(id);

          if (hasId && DOM.getElementById(id) != null) {
            // this notification is already present so, don't do nothing
            return;
          }

          final GrowlTemplate gt = GrowlHelper.getNewTemplate();

          // As a workaround, we set the id in the <br>

          gt.setTitleDivider(hasId ? "<br id=\"" + id + "\">" : "<br>");
          options.setTemplateObject(gt);

          final Boolean closeable = event.getCloseable();
          if (closeable) {
            options.setDelay(0);
          }
          options.setAllowDismiss(closeable);
          options.setPauseOnMouseOver(true);

          final String message = event.getMessage();
          String icon = "";
          final String iconStyleBase = Styles.FONT_AWESOME_BASE + SEPARATOR
              + IconSize.TIMES2.getCssName() + " growl-icon-margin ";

          final NotifyLevel level = event.getLevel();
          switch (level) {
          case error:
            options.setDangerType();
            icon = iconStyleBase + IconType.EXCLAMATION_CIRCLE.getCssName();
            break;
          case avatar:
            final ClickHandler clickHandler = event.getClickHandler();
            final Container container = new Container();
            container.setFluid(true);
            final Image avatar = new Image(event.getLevel().getUrl());
            avatar.setSize(AVATAR_SIZE, AVATAR_SIZE);
            avatar.addStyleName("k-fl");
            avatar.addStyleName("growl-icon-margin");
            container.add(avatar);
            container.add(new HTML(message));
            container.addDomHandler(clickHandler, ClickEvent.getType());
            Growl.growl(container.getElement().getInnerHTML(), options);
            return;
          case veryImportant:
          case important:
            options.setWarningType();
            icon = iconStyleBase + IconType.WARNING.getCssName();
            break;
          case success:
            options.setSuccessType();
            icon = iconStyleBase + IconType.CHECK_CIRCLE.getCssName();
            break;
          case info:
            options.setInfoType();
            icon = iconStyleBase + IconType.INFO_CIRCLE.getCssName();
            break;
          case log:
            // Do nothing with this level
            return;
          default:
            break;
          }

          Growl.growl(event.getTitle(), message, icon, options);
        }
      }
    });
  }
}
