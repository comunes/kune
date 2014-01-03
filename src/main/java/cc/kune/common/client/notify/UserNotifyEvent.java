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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class UserNotifyEvent.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserNotifyEvent extends GwtEvent<UserNotifyEvent.UserNotifyHandler> {

  /**
   * The Interface HasUserNotifyHandlers.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasUserNotifyHandlers extends HasHandlers {
    
    /**
     * Adds the user notify handler.
     *
     * @param handler the handler
     * @return the handler registration
     */
    HandlerRegistration addUserNotifyHandler(UserNotifyHandler handler);
  }

  /**
   * The Interface UserNotifyCloser.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface UserNotifyCloser {
    
    /**
     * Close.
     */
    public void close();
  }

  /**
   * The Interface UserNotifyHandler.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface UserNotifyHandler extends EventHandler {
    
    /**
     * On user notify.
     *
     * @param event the event
     */
    public void onUserNotify(UserNotifyEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<UserNotifyHandler> TYPE = new Type<UserNotifyHandler>();

  /**
   * Fire.
   *
   * @param source the source
   * @param level the level
   * @param message the message
   */
  public static void fire(final HasHandlers source, final NotifyLevel level,
      final java.lang.String message) {
    source.fireEvent(new UserNotifyEvent(level, "", message));
  }

  /**
   * Fire.
   *
   * @param source the source
   * @param level the level
   * @param message the message
   * @param closeable the closeable
   */
  public static void fire(final HasHandlers source, final NotifyLevel level,
      final java.lang.String message, final Boolean closeable) {
    source.fireEvent(new UserNotifyEvent(level, "", message, closeable));
  }

  /**
   * Fire.
   *
   * @param source the source
   * @param level the level
   * @param title the title
   * @param message the message
   */
  public static void fire(final HasHandlers source, final NotifyLevel level,
      final java.lang.String title, final java.lang.String message) {
    source.fireEvent(new UserNotifyEvent(level, title, message));
  }

  /**
   * Fire.
   *
   * @param source the source
   * @param level the level
   * @param title the title
   * @param message the message
   * @param closeable the closeable
   * @return the user notify event
   */
  public static UserNotifyEvent fire(final HasHandlers source, final NotifyLevel level,
      final java.lang.String title, final java.lang.String message, final Boolean closeable) {
    final UserNotifyEvent event = new UserNotifyEvent(level, title, message, closeable);
    source.fireEvent(event);
    return event;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public static Type<UserNotifyHandler> getType() {
    return TYPE;
  }

  /** The click handler. */
  private ClickHandler clickHandler;
  
  /** The closeable. */
  private final Boolean closeable;
  
  /** The closer. */
  private UserNotifyCloser closer;
  
  /** The id. */
  private java.lang.String id;
  
  /** The level. */
  private final NotifyLevel level;
  
  /** The message. */
  private final java.lang.String message;
  
  /** The title. */
  private final java.lang.String title;

  /**
   * Instantiates a new user notify event.
   *
   * @param level the level
   * @param message the message
   */
  public UserNotifyEvent(final NotifyLevel level, final java.lang.String message) {
    this(level, "", message, false);
  }

  /**
   * Instantiates a new user notify event.
   *
   * @param level the level
   * @param message the message
   * @param closeable the closeable
   */
  public UserNotifyEvent(final NotifyLevel level, final java.lang.String message, final Boolean closeable) {
    this(level, "", message, closeable);
  }

  /**
   * Instantiates a new user notify event.
   *
   * @param level the level
   * @param title the title
   * @param message the message
   */
  public UserNotifyEvent(final NotifyLevel level, final java.lang.String title,
      final java.lang.String message) {
    this(level, title, message, false);
  }

  /**
   * Instantiates a new user notify event.
   *
   * @param level the level
   * @param title the title
   * @param message the message
   * @param closeable the closeable
   */
  public UserNotifyEvent(final NotifyLevel level, final java.lang.String title,
      final java.lang.String message, final Boolean closeable) {
    this.level = level;
    this.title = title;
    this.message = message;
    this.closeable = closeable;
  }

  /**
   * Instantiates a new user notify event.
   *
   * @param message the message
   */
  public UserNotifyEvent(final String message) {
    this(NotifyLevel.info, message);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(final UserNotifyHandler handler) {
    handler.onUserNotify(this);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<UserNotifyHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the click handler.
   *
   * @return the click handler
   */
  public ClickHandler getClickHandler() {
    return clickHandler;
  }

  /**
   * Gets the closeable.
   *
   * @return the closeable
   */
  public Boolean getCloseable() {
    return closeable;
  }

  /**
   * Gets the closer.
   *
   * @return the closer
   */
  public UserNotifyCloser getCloser() {
    return closer;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * Gets the level.
   *
   * @return the level
   */
  public NotifyLevel getLevel() {
    return level;
  }

  /**
   * Gets the message.
   *
   * @return the message
   */
  public java.lang.String getMessage() {
    return message;
  }

  /**
   * Gets the title.
   *
   * @return the title
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * Sets the click handler.
   *
   * @param clickHandler the new click handler
   */
  public void setClickHandler(final ClickHandler clickHandler) {
    this.clickHandler = clickHandler;
  }

  /**
   * Sets the closer.
   *
   * @param closer the new closer
   */
  public void setCloser(final UserNotifyCloser closer) {
    this.closer = closer;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final String id) {
    this.id = id;
  }

}
