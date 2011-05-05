package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class AvatarChangedEvent extends GwtEvent<AvatarChangedEvent.AvatarChangedHandler> {

  public interface AvatarChangedHandler extends EventHandler {
    public void onAvatarChanged(AvatarChangedEvent event);
  }

  public interface HasAvatarChangedHandlers extends HasHandlers {
    HandlerRegistration addAvatarChangedHandler(AvatarChangedHandler handler);
  }

  private static final Type<AvatarChangedHandler> TYPE = new Type<AvatarChangedHandler>();

  public static void fire(final HasHandlers source, final java.lang.String photoBinary) {
    source.fireEvent(new AvatarChangedEvent(photoBinary));
  }

  public static Type<AvatarChangedHandler> getType() {
    return TYPE;
  }

  java.lang.String photoBinary;

  protected AvatarChangedEvent() {
    // Possibly for serialization.
  }

  public AvatarChangedEvent(final java.lang.String photoBinary) {
    this.photoBinary = photoBinary;
  }

  @Override
  protected void dispatch(final AvatarChangedHandler handler) {
    handler.onAvatarChanged(this);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final AvatarChangedEvent other = (AvatarChangedEvent) obj;
    if (photoBinary == null) {
      if (other.photoBinary != null) {
        return false;
      }
    } else if (!photoBinary.equals(other.photoBinary)) {
      return false;
    }
    return true;
  }

  @Override
  public Type<AvatarChangedHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getPhotoBinary() {
    return photoBinary;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (photoBinary == null ? 1 : photoBinary.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "AvatarChangedEvent[" + photoBinary + "]";
  }
}
