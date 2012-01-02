package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class UserMustBeLoggedEvent extends GwtEvent<UserMustBeLoggedEvent.UserMustBeLoggedHandler> {

  public interface HasUserMustBeLoggedHandlers extends HasHandlers {
    HandlerRegistration addUserMustBeLoggedHandler(UserMustBeLoggedHandler handler);
  }

  public interface UserMustBeLoggedHandler extends EventHandler {
    public void onUserMustBeLogged(UserMustBeLoggedEvent event);
  }

  private static final Type<UserMustBeLoggedHandler> TYPE = new Type<UserMustBeLoggedHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new UserMustBeLoggedEvent());
  }

  public static Type<UserMustBeLoggedHandler> getType() {
    return TYPE;
  }

  public UserMustBeLoggedEvent() {
  }

  @Override
  protected void dispatch(final UserMustBeLoggedHandler handler) {
    handler.onUserMustBeLogged(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<UserMustBeLoggedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "UserMustBeLoggedEvent[" + "]";
  }
}
