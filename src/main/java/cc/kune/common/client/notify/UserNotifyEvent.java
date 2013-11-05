/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class UserNotifyEvent extends GwtEvent<UserNotifyEvent.UserNotifyHandler> {

  public interface HasUserNotifyHandlers extends HasHandlers {
    HandlerRegistration addUserNotifyHandler(UserNotifyHandler handler);
  }

  public interface UserNotifyCloser {
    public void close();
  }

  public interface UserNotifyHandler extends EventHandler {
    public void onUserNotify(UserNotifyEvent event);
  }

  private static final Type<UserNotifyHandler> TYPE = new Type<UserNotifyHandler>();

  public static void fire(final HasHandlers source, final NotifyLevel level,
      final java.lang.String message) {
    source.fireEvent(new UserNotifyEvent(level, "", message));
  }

  public static void fire(final HasHandlers source, final NotifyLevel level,
      final java.lang.String message, final Boolean closeable) {
    source.fireEvent(new UserNotifyEvent(level, "", message, closeable));
  }

  public static void fire(final HasHandlers source, final NotifyLevel level,
      final java.lang.String title, final java.lang.String message) {
    source.fireEvent(new UserNotifyEvent(level, title, message));
  }

  public static UserNotifyEvent fire(final HasHandlers source, final NotifyLevel level,
      final java.lang.String title, final java.lang.String message, final Boolean closeable) {
    final UserNotifyEvent event = new UserNotifyEvent(level, title, message, closeable);
    source.fireEvent(event);
    return event;
  }

  public static Type<UserNotifyHandler> getType() {
    return TYPE;
  }

  private ClickHandler clickHandler;
  private final Boolean closeable;
  private UserNotifyCloser closer;
  private java.lang.String id;
  private final NotifyLevel level;
  private final java.lang.String message;
  private final java.lang.String title;

  public UserNotifyEvent(final NotifyLevel level, final java.lang.String message) {
    this(level, "", message, false);
  }

  public UserNotifyEvent(final NotifyLevel level, final java.lang.String message, final Boolean closeable) {
    this(level, "", message, closeable);
  }

  public UserNotifyEvent(final NotifyLevel level, final java.lang.String title,
      final java.lang.String message) {
    this(level, title, message, false);
  }

  public UserNotifyEvent(final NotifyLevel level, final java.lang.String title,
      final java.lang.String message, final Boolean closeable) {
    this.level = level;
    this.title = title;
    this.message = message;
    this.closeable = closeable;
  }

  public UserNotifyEvent(final String message) {
    this(NotifyLevel.info, message);
  }

  @Override
  protected void dispatch(final UserNotifyHandler handler) {
    handler.onUserNotify(this);
  }

  @Override
  public Type<UserNotifyHandler> getAssociatedType() {
    return TYPE;
  }

  public ClickHandler getClickHandler() {
    return clickHandler;
  }

  public Boolean getCloseable() {
    return closeable;
  }

  public UserNotifyCloser getCloser() {
    return closer;
  }

  public java.lang.String getId() {
    return id;
  }

  public NotifyLevel getLevel() {
    return level;
  }

  public java.lang.String getMessage() {
    return message;
  }

  public java.lang.String getTitle() {
    return title;
  }

  public void setClickHandler(final ClickHandler clickHandler) {
    this.clickHandler = clickHandler;
  }

  public void setCloser(final UserNotifyCloser closer) {
    this.closer = closer;
  }

  public void setId(final String id) {
    this.id = id;
  }

}
