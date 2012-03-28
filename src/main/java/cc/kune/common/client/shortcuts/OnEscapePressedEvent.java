package cc.kune.common.client.shortcuts;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class OnEscapePressedEvent extends GwtEvent<OnEscapePressedEvent.OnEscapePressedHandler> {

  public interface HasOnEscapePressedHandlers extends HasHandlers {
    HandlerRegistration addOnEscapePressedHandler(OnEscapePressedHandler handler);
  }

  public interface OnEscapePressedHandler extends EventHandler {
    public void onOnEscapePressed(OnEscapePressedEvent event);
  }

  private static final Type<OnEscapePressedHandler> TYPE = new Type<OnEscapePressedHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new OnEscapePressedEvent());
  }

  public static Type<OnEscapePressedHandler> getType() {
    return TYPE;
  }

  public OnEscapePressedEvent() {
  }

  @Override
  protected void dispatch(final OnEscapePressedHandler handler) {
    handler.onOnEscapePressed(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<OnEscapePressedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "OnEscapePressedEvent[" + "]";
  }
}
