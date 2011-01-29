package cc.kune.core.client.state;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class StateChangedEvent extends GwtEvent<StateChangedEvent.StateChangedHandler> { 

  public interface HasStateChangedHandlers extends HasHandlers {
    HandlerRegistration addStateChangedHandler(StateChangedHandler handler);
  }

  public interface StateChangedHandler extends EventHandler {
    public void onStateChanged(StateChangedEvent event);
  }

  private static final Type<StateChangedHandler> TYPE = new Type<StateChangedHandler>();

  public static void fire(HasHandlers source, cc.kune.core.shared.dto.StateAbstractDTO state) {
    source.fireEvent(new StateChangedEvent(state));
  }

  public static Type<StateChangedHandler> getType() {
    return TYPE;
  }

  cc.kune.core.shared.dto.StateAbstractDTO state;

  public StateChangedEvent(cc.kune.core.shared.dto.StateAbstractDTO state) {
    this.state = state;
  }

  protected StateChangedEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<StateChangedHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.shared.dto.StateAbstractDTO getState() {
    return state;
  }

  @Override
  protected void dispatch(StateChangedHandler handler) {
    handler.onStateChanged(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    StateChangedEvent other = (StateChangedEvent) obj;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (state == null ? 1 : state.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "StateChangedEvent["
                 + state
    + "]";
  }
}
