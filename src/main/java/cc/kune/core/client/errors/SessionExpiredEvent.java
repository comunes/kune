package cc.kune.core.client.errors;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class SessionExpiredEvent extends GwtEvent<SessionExpiredEvent.SessionExpiredHandler> { 

  public interface HasSessionExpiredHandlers extends HasHandlers {
    HandlerRegistration addSessionExpiredHandler(SessionExpiredHandler handler);
  }

  public interface SessionExpiredHandler extends EventHandler {
    public void onSessionExpired(SessionExpiredEvent event);
  }

  private static final Type<SessionExpiredHandler> TYPE = new Type<SessionExpiredHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new SessionExpiredEvent());
  }

  public static Type<SessionExpiredHandler> getType() {
    return TYPE;
  }


  public SessionExpiredEvent() {
  }

  @Override
  public Type<SessionExpiredHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SessionExpiredHandler handler) {
    handler.onSessionExpired(this);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "SessionExpiredEvent["
    + "]";
  }
}
