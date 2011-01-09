package cc.kune.core.client.state;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasEventBus;
import com.google.gwt.event.shared.HasHandlers;

public class UserSignOutEvent extends GwtEvent<UserSignOutEvent.UserSignOutHandler> { 

  public interface HasUserSignOutHandlers extends HasHandlers {
    HandlerRegistration addUserSignOutHandler(UserSignOutHandler handler);
  }

  public interface UserSignOutHandler extends EventHandler {
    public void onUserSignOut(UserSignOutEvent event);
  }

  private static final Type<UserSignOutHandler> TYPE = new Type<UserSignOutHandler>();

  public static void fire(HasEventBus source) {
    source.fireEvent(new UserSignOutEvent());
  }

  public static Type<UserSignOutHandler> getType() {
    return TYPE;
  }


  public UserSignOutEvent() {
  }

  @Override
  public Type<UserSignOutHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UserSignOutHandler handler) {
    handler.onUserSignOut(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          UserSignOutEvent o = (UserSignOutEvent) other;
      return true
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "UserSignOutEvent["
    + "]";
  }

}
