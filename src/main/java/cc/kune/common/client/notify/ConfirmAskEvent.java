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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.resources.client.ImageResource;

public class ConfirmAskEvent extends GwtEvent<ConfirmAskEvent.ConfirmAskHandler> {

  public interface ConfirmAskHandler extends EventHandler {
    public void onConfirmAsk(ConfirmAskEvent event);
  }

  public interface HasConfirmAskHandlers extends HasHandlers {
    HandlerRegistration addConfirmAskHandler(ConfirmAskHandler handler);
  }

  private static final Type<ConfirmAskHandler> TYPE = new Type<ConfirmAskHandler>();

  public static void fire(final HasHandlers source, final java.lang.String message) {
    source.fireEvent(new ConfirmAskEvent(message));
  }

  public static void fire(final HasHandlers source, final java.lang.String title,
      final java.lang.String message, final java.lang.String acceptBtnMsg,
      final java.lang.String cancelBtnMsg, final java.lang.String acceptBtnTooltip,
      final java.lang.String cancelBtnTooltip,
      final cc.kune.common.client.utils.SimpleResponseCallback callback) {
    source.fireEvent(new ConfirmAskEvent(title, message, acceptBtnMsg, cancelBtnMsg, acceptBtnTooltip,
        cancelBtnTooltip, callback));
  }

  public static Type<ConfirmAskHandler> getType() {
    return TYPE;
  }

  java.lang.String acceptBtnMsg;
  java.lang.String acceptBtnTooltip;
  cc.kune.common.client.utils.SimpleResponseCallback callback;
  java.lang.String cancelBtnMsg;
  java.lang.String cancelBtnTooltip;
  ImageResource icon;
  java.lang.String message;
  java.lang.String title;

  protected ConfirmAskEvent() {
    // Possibly for serialization.
  }

  public ConfirmAskEvent(final ImageResource icon, final java.lang.String title,
      final java.lang.String message, final java.lang.String acceptBtnMsg,
      final java.lang.String cancelBtnMsg,
      final cc.kune.common.client.utils.SimpleResponseCallback callback) {
    this(title, message, acceptBtnMsg, cancelBtnMsg, acceptBtnMsg, cancelBtnMsg, callback);
    this.icon = icon;
  }

  public ConfirmAskEvent(final ImageResource icon, final java.lang.String title,
      final java.lang.String message, final java.lang.String acceptBtnMsg,
      final java.lang.String cancelBtnMsg, final java.lang.String acceptBtnTooltip,
      final java.lang.String cancelBtnTooltip,
      final cc.kune.common.client.utils.SimpleResponseCallback callback) {
    this(title, message, acceptBtnMsg, cancelBtnMsg, acceptBtnTooltip, cancelBtnTooltip, callback);
    this.icon = icon;
  }

  public ConfirmAskEvent(final java.lang.String message) {
    this.message = message;
  }

  public ConfirmAskEvent(final java.lang.String title, final java.lang.String message,
      final java.lang.String acceptBtnMsg, final java.lang.String cancelBtnMsg,
      final cc.kune.common.client.utils.SimpleResponseCallback callback) {
    this.title = title;
    this.message = message;
    this.acceptBtnMsg = acceptBtnMsg;
    this.cancelBtnMsg = cancelBtnMsg;
    this.callback = callback;
  }

  public ConfirmAskEvent(final java.lang.String title, final java.lang.String message,
      final java.lang.String acceptBtnMsg, final java.lang.String cancelBtnMsg,
      final java.lang.String acceptBtnTooltip, final java.lang.String cancelBtnTooltip,
      final cc.kune.common.client.utils.SimpleResponseCallback callback) {
    this(title, message, acceptBtnMsg, cancelBtnMsg, callback);
    this.acceptBtnTooltip = acceptBtnTooltip;
    this.cancelBtnTooltip = cancelBtnTooltip;
  }

  @Override
  protected void dispatch(final ConfirmAskHandler handler) {
    handler.onConfirmAsk(this);
  }

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

  public java.lang.String getAcceptBtnMsg() {
    return acceptBtnMsg;
  }

  public java.lang.String getAcceptBtnTooltip() {
    return acceptBtnTooltip;
  }

  @Override
  public Type<ConfirmAskHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.common.client.utils.SimpleResponseCallback getCallback() {
    return callback;
  }

  public java.lang.String getCancelBtnMsg() {
    return cancelBtnMsg;
  }

  public java.lang.String getCancelBtnTooltip() {
    return cancelBtnTooltip;
  }

  public ImageResource getIcon() {
    return icon;
  }

  public java.lang.String getMessage() {
    return message;
  }

  public java.lang.String getTitle() {
    return title;
  }

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

  @Override
  public String toString() {
    return "ConfirmAskEvent[" + title + "," + message + "," + acceptBtnMsg + "," + cancelBtnMsg + ","
        + acceptBtnTooltip + "," + cancelBtnTooltip + "," + callback + "]";
  }
}
