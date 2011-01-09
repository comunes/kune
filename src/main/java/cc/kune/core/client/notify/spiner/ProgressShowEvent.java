package cc.kune.core.client.notify.spiner;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasEventBus;
import com.google.gwt.event.shared.HasHandlers;

public class ProgressShowEvent extends GwtEvent<ProgressShowEvent.ProgressShowHandler> { 

  public interface HasProgressShowHandlers extends HasHandlers {
    HandlerRegistration addProgressShowHandler(ProgressShowHandler handler);
  }

  public interface ProgressShowHandler extends EventHandler {
    public void onProgressShow(ProgressShowEvent event);
  }

  private static final Type<ProgressShowHandler> TYPE = new Type<ProgressShowHandler>();

  public static void fire(HasEventBus source, java.lang.String message) {
    source.fireEvent(new ProgressShowEvent(message));
  }

  public static Type<ProgressShowHandler> getType() {
    return TYPE;
  }

  private final java.lang.String message;

  public ProgressShowEvent(java.lang.String message) {
    this.message = message;
  }

  @Override
  public Type<ProgressShowHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getMessage() {
    return message;
  }

  @Override
  protected void dispatch(ProgressShowHandler handler) {
    handler.onProgressShow(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          ProgressShowEvent o = (ProgressShowEvent) other;
      return true
          && ((o.message == null && this.message == null) || (o.message != null && o.message.equals(this.message)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (message == null ? 1 : message.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ProgressShowEvent["
                 + message
    + "]";
  }

}
