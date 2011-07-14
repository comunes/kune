package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class NewUserRegisteredEvent extends GwtEvent<NewUserRegisteredEvent.NewUserRegisteredHandler> {

  public interface HasNewUserRegisteredHandlers extends HasHandlers {
    HandlerRegistration addNewUserRegisteredHandler(NewUserRegisteredHandler handler);
  }

  public interface NewUserRegisteredHandler extends EventHandler {
    public void onNewUserRegistered(NewUserRegisteredEvent event);
  }

  private static final Type<NewUserRegisteredHandler> TYPE = new Type<NewUserRegisteredHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new NewUserRegisteredEvent());
  }

  public static Type<NewUserRegisteredHandler> getType() {
    return TYPE;
  }

  public NewUserRegisteredEvent() {
  }

  @Override
  protected void dispatch(final NewUserRegisteredHandler handler) {
    handler.onNewUserRegistered(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<NewUserRegisteredHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "NewUserRegisteredEvent[" + "]";
  }
}
