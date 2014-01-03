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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmAskEvent.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ConfirmAskEvent extends GwtEvent<ConfirmAskEvent.ConfirmAskHandler> {

  /**
   * The Interface ConfirmAskHandler.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ConfirmAskHandler extends EventHandler {
    
    /**
     * On confirm ask.
     *
     * @param event the event
     */
    public void onConfirmAsk(ConfirmAskEvent event);
  }

  /**
   * The Interface HasConfirmAskHandlers.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasConfirmAskHandlers extends HasHandlers {
    
    /**
     * Adds the confirm ask handler.
     *
     * @param handler the handler
     * @return the handler registration
     */
    HandlerRegistration addConfirmAskHandler(ConfirmAskHandler handler);
  }

  /** The Constant TYPE. */
  private static final Type<ConfirmAskHandler> TYPE = new Type<ConfirmAskHandler>();

  /**
   * Fire.
   *
   * @param source the source
   * @param message the message
   */
  public static void fire(final HasHandlers source, final java.lang.String message) {
    source.fireEvent(new ConfirmAskEvent(message));
  }

  /**
   * Fire.
   *
   * @param source the source
   * @param title the title
   * @param message the message
   * @param acceptBtnMsg the accept btn msg
   * @param cancelBtnMsg the cancel btn msg
   * @param acceptBtnTooltip the accept btn tooltip
   * @param cancelBtnTooltip the cancel btn tooltip
   * @param callback the callback
   */
  public static void fire(final HasHandlers source, final java.lang.String title,
      final java.lang.String message, final java.lang.String acceptBtnMsg,
      final java.lang.String cancelBtnMsg, final java.lang.String acceptBtnTooltip,
      final java.lang.String cancelBtnTooltip,
      final cc.kune.common.shared.utils.SimpleResponseCallback callback) {
    source.fireEvent(new ConfirmAskEvent(title, message, acceptBtnMsg, cancelBtnMsg, acceptBtnTooltip,
        cancelBtnTooltip, callback));
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public static Type<ConfirmAskHandler> getType() {
    return TYPE;
  }

  /** The accept btn msg. */
  java.lang.String acceptBtnMsg;
  
  /** The accept btn tooltip. */
  java.lang.String acceptBtnTooltip;
  
  /** The callback. */
  cc.kune.common.shared.utils.SimpleResponseCallback callback;
  
  /** The cancel btn msg. */
  java.lang.String cancelBtnMsg;
  
  /** The cancel btn tooltip. */
  java.lang.String cancelBtnTooltip;
  
  /** The icon. */
  ImageResource icon;
  
  /** The message. */
  java.lang.String message;
  
  /** The title. */
  java.lang.String title;

  /**
   * Instantiates a new confirm ask event.
   */
  protected ConfirmAskEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new confirm ask event.
   *
   * @param icon the icon
   * @param title the title
   * @param message the message
   * @param acceptBtnMsg the accept btn msg
   * @param cancelBtnMsg the cancel btn msg
   * @param callback the callback
   */
  public ConfirmAskEvent(final ImageResource icon, final java.lang.String title,
      final java.lang.String message, final java.lang.String acceptBtnMsg,
      final java.lang.String cancelBtnMsg,
      final cc.kune.common.shared.utils.SimpleResponseCallback callback) {
    this(title, message, acceptBtnMsg, cancelBtnMsg, acceptBtnMsg, cancelBtnMsg, callback);
    this.icon = icon;
  }

  /**
   * Instantiates a new confirm ask event.
   *
   * @param icon the icon
   * @param title the title
   * @param message the message
   * @param acceptBtnMsg the accept btn msg
   * @param cancelBtnMsg the cancel btn msg
   * @param acceptBtnTooltip the accept btn tooltip
   * @param cancelBtnTooltip the cancel btn tooltip
   * @param callback the callback
   */
  public ConfirmAskEvent(final ImageResource icon, final java.lang.String title,
      final java.lang.String message, final java.lang.String acceptBtnMsg,
      final java.lang.String cancelBtnMsg, final java.lang.String acceptBtnTooltip,
      final java.lang.String cancelBtnTooltip,
      final cc.kune.common.shared.utils.SimpleResponseCallback callback) {
    this(title, message, acceptBtnMsg, cancelBtnMsg, acceptBtnTooltip, cancelBtnTooltip, callback);
    this.icon = icon;
  }

  /**
   * Instantiates a new confirm ask event.
   *
   * @param message the message
   */
  public ConfirmAskEvent(final java.lang.String message) {
    this.message = message;
  }

  /**
   * Instantiates a new confirm ask event.
   *
   * @param title the title
   * @param message the message
   * @param acceptBtnMsg the accept btn msg
   * @param cancelBtnMsg the cancel btn msg
   * @param callback the callback
   */
  public ConfirmAskEvent(final java.lang.String title, final java.lang.String message,
      final java.lang.String acceptBtnMsg, final java.lang.String cancelBtnMsg,
      final cc.kune.common.shared.utils.SimpleResponseCallback callback) {
    this.title = title;
    this.message = message;
    this.acceptBtnMsg = acceptBtnMsg;
    this.cancelBtnMsg = cancelBtnMsg;
    this.callback = callback;
  }

  /**
   * Instantiates a new confirm ask event.
   *
   * @param title the title
   * @param message the message
   * @param acceptBtnMsg the accept btn msg
   * @param cancelBtnMsg the cancel btn msg
   * @param acceptBtnTooltip the accept btn tooltip
   * @param cancelBtnTooltip the cancel btn tooltip
   * @param callback the callback
   */
  public ConfirmAskEvent(final java.lang.String title, final java.lang.String message,
      final java.lang.String acceptBtnMsg, final java.lang.String cancelBtnMsg,
      final java.lang.String acceptBtnTooltip, final java.lang.String cancelBtnTooltip,
      final cc.kune.common.shared.utils.SimpleResponseCallback callback) {
    this(title, message, acceptBtnMsg, cancelBtnMsg, callback);
    this.acceptBtnTooltip = acceptBtnTooltip;
    this.cancelBtnTooltip = cancelBtnTooltip;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(final ConfirmAskHandler handler) {
    handler.onConfirmAsk(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ConfirmAskEvent other = (ConfirmAskEvent) obj;
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }
    if (message == null) {
      if (other.message != null) {
        return false;
      }
    } else if (!message.equals(other.message)) {
      return false;
    }
    if (acceptBtnMsg == null) {
      if (other.acceptBtnMsg != null) {
        return false;
      }
    } else if (!acceptBtnMsg.equals(other.acceptBtnMsg)) {
      return false;
    }
    if (cancelBtnMsg == null) {
      if (other.cancelBtnMsg != null) {
        return false;
      }
    } else if (!cancelBtnMsg.equals(other.cancelBtnMsg)) {
      return false;
    }
    if (acceptBtnTooltip == null) {
      if (other.acceptBtnTooltip != null) {
        return false;
      }
    } else if (!acceptBtnTooltip.equals(other.acceptBtnTooltip)) {
      return false;
    }
    if (cancelBtnTooltip == null) {
      if (other.cancelBtnTooltip != null) {
        return false;
      }
    } else if (!cancelBtnTooltip.equals(other.cancelBtnTooltip)) {
      return false;
    }
    if (callback == null) {
      if (other.callback != null) {
        return false;
      }
    } else if (!callback.equals(other.callback)) {
      return false;
    }
    return true;
  }

  /**
   * Gets the accept btn msg.
   *
   * @return the accept btn msg
   */
  public java.lang.String getAcceptBtnMsg() {
    return acceptBtnMsg;
  }

  /**
   * Gets the accept btn tooltip.
   *
   * @return the accept btn tooltip
   */
  public java.lang.String getAcceptBtnTooltip() {
    return acceptBtnTooltip;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<ConfirmAskHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the callback.
   *
   * @return the callback
   */
  public cc.kune.common.shared.utils.SimpleResponseCallback getCallback() {
    return callback;
  }

  /**
   * Gets the cancel btn msg.
   *
   * @return the cancel btn msg
   */
  public java.lang.String getCancelBtnMsg() {
    return cancelBtnMsg;
  }

  /**
   * Gets the cancel btn tooltip.
   *
   * @return the cancel btn tooltip
   */
  public java.lang.String getCancelBtnTooltip() {
    return cancelBtnTooltip;
  }

  /**
   * Gets the icon.
   *
   * @return the icon
   */
  public ImageResource getIcon() {
    return icon;
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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (title == null ? 1 : title.hashCode());
    hashCode = (hashCode * 37) + (message == null ? 1 : message.hashCode());
    hashCode = (hashCode * 37) + (acceptBtnMsg == null ? 1 : acceptBtnMsg.hashCode());
    hashCode = (hashCode * 37) + (cancelBtnMsg == null ? 1 : cancelBtnMsg.hashCode());
    hashCode = (hashCode * 37) + (acceptBtnTooltip == null ? 1 : acceptBtnTooltip.hashCode());
    hashCode = (hashCode * 37) + (cancelBtnTooltip == null ? 1 : cancelBtnTooltip.hashCode());
    hashCode = (hashCode * 37) + (callback == null ? 1 : callback.hashCode());
    return hashCode;
  }

  /* (non-Javadoc)
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "ConfirmAskEvent[" + title + "," + message + "," + acceptBtnMsg + "," + cancelBtnMsg + ","
        + acceptBtnTooltip + "," + cancelBtnTooltip + "," + callback + "]";
  }
}
