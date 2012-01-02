package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class GoHomeEvent extends GwtEvent<GoHomeEvent.GoHomeHandler> { 

  public interface HasGoHomeHandlers extends HasHandlers {
    HandlerRegistration addGoHomeHandler(GoHomeHandler handler);
  }

  public interface GoHomeHandler extends EventHandler {
    public void onGoHome(GoHomeEvent event);
  }

  private static final Type<GoHomeHandler> TYPE = new Type<GoHomeHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new GoHomeEvent());
  }

  public static Type<GoHomeHandler> getType() {
    return TYPE;
  }


  public GoHomeEvent() {
  }

  @Override
  public Type<GoHomeHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GoHomeHandler handler) {
    handler.onGoHome(this);
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
    return "GoHomeEvent["
    + "]";
  }
}
