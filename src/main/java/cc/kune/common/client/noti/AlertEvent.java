package cc.kune.common.client.noti;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class AlertEvent extends GwtEvent<AlertEvent.AlertHandler> { 

  public interface HasAlertHandlers extends HasHandlers {
    HandlerRegistration addAlertHandler(AlertHandler handler);
  }

  public interface AlertHandler extends EventHandler {
    public void onAlert(AlertEvent event);
  }

  private static final Type<AlertHandler> TYPE = new Type<AlertHandler>();

  public static void fire(HasHandlers source, java.lang.String title, java.lang.String message, cc.kune.common.client.utils.SimpleCallback onOk) {
    source.fireEvent(new AlertEvent(title, message, onOk));
  }

  public static void fire(HasHandlers source, java.lang.String message) {
    source.fireEvent(new AlertEvent(message));
  }

  public static Type<AlertHandler> getType() {
    return TYPE;
  }

  java.lang.String title;
  java.lang.String message;
  cc.kune.common.client.utils.SimpleCallback onOk;

  public AlertEvent(java.lang.String title, java.lang.String message, cc.kune.common.client.utils.SimpleCallback onOk) {
    this.title = title;
    this.message = message;
    this.onOk = onOk;
  }

  public AlertEvent(java.lang.String message) {
    this.message = message;
  }

  protected AlertEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<AlertHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getTitle() {
    return title;
  }

  public java.lang.String getMessage() {
    return message;
  }

  public cc.kune.common.client.utils.SimpleCallback getOnOk() {
    return onOk;
  }

  @Override
  protected void dispatch(AlertHandler handler) {
    handler.onAlert(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    AlertEvent other = (AlertEvent) obj;
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
    if (onOk == null) {
      if (other.onOk != null)
        return false;
    } else if (!onOk.equals(other.onOk))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (title == null ? 1 : title.hashCode());
    hashCode = (hashCode * 37) + (message == null ? 1 : message.hashCode());
    hashCode = (hashCode * 37) + (onOk == null ? 1 : onOk.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "AlertEvent["
                 + title
                 + ","
                 + message
                 + ","
                 + onOk
    + "]";
  }
}
