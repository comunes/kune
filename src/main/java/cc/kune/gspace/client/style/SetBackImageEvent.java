package cc.kune.gspace.client.style;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class SetBackImageEvent extends GwtEvent<SetBackImageEvent.SetBackImageHandler> { 

  public interface HasSetBackImageHandlers extends HasHandlers {
    HandlerRegistration addSetBackImageHandler(SetBackImageHandler handler);
  }

  public interface SetBackImageHandler extends EventHandler {
    public void onSetBackImage(SetBackImageEvent event);
  }

  private static final Type<SetBackImageHandler> TYPE = new Type<SetBackImageHandler>();

  public static void fire(HasHandlers source, cc.kune.core.shared.domain.utils.StateToken token) {
    source.fireEvent(new SetBackImageEvent(token));
  }

  public static Type<SetBackImageHandler> getType() {
    return TYPE;
  }

  cc.kune.core.shared.domain.utils.StateToken token;

  public SetBackImageEvent(cc.kune.core.shared.domain.utils.StateToken token) {
    this.token = token;
  }

  protected SetBackImageEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<SetBackImageHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.shared.domain.utils.StateToken getToken() {
    return token;
  }

  @Override
  protected void dispatch(SetBackImageHandler handler) {
    handler.onSetBackImage(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    SetBackImageEvent other = (SetBackImageEvent) obj;
    if (token == null) {
      if (other.token != null)
        return false;
    } else if (!token.equals(other.token))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (token == null ? 1 : token.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "SetBackImageEvent["
                 + token
    + "]";
  }
}
