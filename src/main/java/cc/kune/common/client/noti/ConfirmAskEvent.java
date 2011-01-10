package cc.kune.common.client.noti;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class ConfirmAskEvent extends GwtEvent<ConfirmAskEvent.ConfirmAskHandler> { 

  public interface HasConfirmAskHandlers extends HasHandlers {
    HandlerRegistration addConfirmAskHandler(ConfirmAskHandler handler);
  }

  public interface ConfirmAskHandler extends EventHandler {
    public void onConfirmAsk(ConfirmAskEvent event);
  }

  private static final Type<ConfirmAskHandler> TYPE = new Type<ConfirmAskHandler>();

  public static void fire(HasHandlers source, java.lang.String title, java.lang.String message, java.lang.String acceptBtnMsg, java.lang.String cancelBtnMsg, java.lang.String acceptBtnTooltip, java.lang.String cancelBtnTooltip, cc.kune.common.client.noti.SimpleCallback onAccept, cc.kune.common.client.noti.SimpleCallback onCancel) {
    source.fireEvent(new ConfirmAskEvent(title, message, acceptBtnMsg, cancelBtnMsg, acceptBtnTooltip, cancelBtnTooltip, onAccept, onCancel));
  }

  public static void fire(HasHandlers source, java.lang.String message) {
    source.fireEvent(new ConfirmAskEvent(message));
  }

  public static Type<ConfirmAskHandler> getType() {
    return TYPE;
  }

  java.lang.String title;
  java.lang.String message;
  java.lang.String acceptBtnMsg;
  java.lang.String cancelBtnMsg;
  java.lang.String acceptBtnTooltip;
  java.lang.String cancelBtnTooltip;
  cc.kune.common.client.noti.SimpleCallback onAccept;
  cc.kune.common.client.noti.SimpleCallback onCancel;

  public ConfirmAskEvent(java.lang.String title, java.lang.String message, java.lang.String acceptBtnMsg, java.lang.String cancelBtnMsg, java.lang.String acceptBtnTooltip, java.lang.String cancelBtnTooltip, cc.kune.common.client.noti.SimpleCallback onAccept, cc.kune.common.client.noti.SimpleCallback onCancel) {
    this.title = title;
    this.message = message;
    this.acceptBtnMsg = acceptBtnMsg;
    this.cancelBtnMsg = cancelBtnMsg;
    this.acceptBtnTooltip = acceptBtnTooltip;
    this.cancelBtnTooltip = cancelBtnTooltip;
    this.onAccept = onAccept;
    this.onCancel = onCancel;
  }

  public ConfirmAskEvent(java.lang.String message) {
    this.message = message;
  }

  protected ConfirmAskEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<ConfirmAskHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getTitle() {
    return title;
  }

  public java.lang.String getMessage() {
    return message;
  }

  public java.lang.String getAcceptBtnMsg() {
    return acceptBtnMsg;
  }

  public java.lang.String getCancelBtnMsg() {
    return cancelBtnMsg;
  }

  public java.lang.String getAcceptBtnTooltip() {
    return acceptBtnTooltip;
  }

  public java.lang.String getCancelBtnTooltip() {
    return cancelBtnTooltip;
  }

  public cc.kune.common.client.noti.SimpleCallback getOnAccept() {
    return onAccept;
  }

  public cc.kune.common.client.noti.SimpleCallback getOnCancel() {
    return onCancel;
  }

  @Override
  protected void dispatch(ConfirmAskHandler handler) {
    handler.onConfirmAsk(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ConfirmAskEvent other = (ConfirmAskEvent) obj;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (message == null) {
      if (other.message != null)
        return false;
    } else if (!message.equals(other.message))
      return false;
    if (acceptBtnMsg == null) {
      if (other.acceptBtnMsg != null)
        return false;
    } else if (!acceptBtnMsg.equals(other.acceptBtnMsg))
      return false;
    if (cancelBtnMsg == null) {
      if (other.cancelBtnMsg != null)
        return false;
    } else if (!cancelBtnMsg.equals(other.cancelBtnMsg))
      return false;
    if (acceptBtnTooltip == null) {
      if (other.acceptBtnTooltip != null)
        return false;
    } else if (!acceptBtnTooltip.equals(other.acceptBtnTooltip))
      return false;
    if (cancelBtnTooltip == null) {
      if (other.cancelBtnTooltip != null)
        return false;
    } else if (!cancelBtnTooltip.equals(other.cancelBtnTooltip))
      return false;
    if (onAccept == null) {
      if (other.onAccept != null)
        return false;
    } else if (!onAccept.equals(other.onAccept))
      return false;
    if (onCancel == null) {
      if (other.onCancel != null)
        return false;
    } else if (!onCancel.equals(other.onCancel))
      return false;
    return true;
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
    hashCode = (hashCode * 37) + (onAccept == null ? 1 : onAccept.hashCode());
    hashCode = (hashCode * 37) + (onCancel == null ? 1 : onCancel.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ConfirmAskEvent["
                 + title
                 + ","
                 + message
                 + ","
                 + acceptBtnMsg
                 + ","
                 + cancelBtnMsg
                 + ","
                 + acceptBtnTooltip
                 + ","
                 + cancelBtnTooltip
                 + ","
                 + onAccept
                 + ","
                 + onCancel
    + "]";
  }
}
