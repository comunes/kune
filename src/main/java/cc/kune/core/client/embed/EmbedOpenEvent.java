package cc.kune.core.client.embed;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class EmbedOpenEvent extends GwtEvent<EmbedOpenEvent.EmbedOpenHandler> { 

  public interface HasEmbedOpenHandlers extends HasHandlers {
    HandlerRegistration addEmbedOpenHandler(EmbedOpenHandler handler);
  }

  public interface EmbedOpenHandler extends EventHandler {
    public void onEmbedOpen(EmbedOpenEvent event);
  }

  private static final Type<EmbedOpenHandler> TYPE = new Type<EmbedOpenHandler>();

  public static void fire(HasHandlers source, java.lang.String stateToken) {
    source.fireEvent(new EmbedOpenEvent(stateToken));
  }

  public static Type<EmbedOpenHandler> getType() {
    return TYPE;
  }

  java.lang.String stateToken;

  public EmbedOpenEvent(java.lang.String stateToken) {
    this.stateToken = stateToken;
  }

  protected EmbedOpenEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<EmbedOpenHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getStateToken() {
    return stateToken;
  }

  @Override
  protected void dispatch(EmbedOpenHandler handler) {
    handler.onEmbedOpen(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    EmbedOpenEvent other = (EmbedOpenEvent) obj;
    if (stateToken == null) {
      if (other.stateToken != null)
        return false;
    } else if (!stateToken.equals(other.stateToken))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (stateToken == null ? 1 : stateToken.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "EmbedOpenEvent["
                 + stateToken
    + "]";
  }
}
