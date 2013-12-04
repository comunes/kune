package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class WaveSessionAvailableEvent extends GwtEvent<WaveSessionAvailableEvent.WaveSessionAvailableHandler> { 

  public interface HasWaveSessionAvailableHandlers extends HasHandlers {
    HandlerRegistration addWaveSessionAvailableHandler(WaveSessionAvailableHandler handler);
  }

  public interface WaveSessionAvailableHandler extends EventHandler {
    public void onWaveSessionAvailable(WaveSessionAvailableEvent event);
  }

  private static final Type<WaveSessionAvailableHandler> TYPE = new Type<WaveSessionAvailableHandler>();

  public static void fire(HasHandlers source, cc.kune.core.shared.dto.UserInfoDTO userInfo) {
    source.fireEvent(new WaveSessionAvailableEvent(userInfo));
  }

  public static Type<WaveSessionAvailableHandler> getType() {
    return TYPE;
  }

  cc.kune.core.shared.dto.UserInfoDTO userInfo;

  public WaveSessionAvailableEvent(cc.kune.core.shared.dto.UserInfoDTO userInfo) {
    this.userInfo = userInfo;
  }

  protected WaveSessionAvailableEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<WaveSessionAvailableHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.shared.dto.UserInfoDTO getUserInfo() {
    return userInfo;
  }

  @Override
  protected void dispatch(WaveSessionAvailableHandler handler) {
    handler.onWaveSessionAvailable(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    WaveSessionAvailableEvent other = (WaveSessionAvailableEvent) obj;
    if (userInfo == null) {
      if (other.userInfo != null)
        return false;
    } else if (!userInfo.equals(other.userInfo))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (userInfo == null ? 1 : userInfo.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "WaveSessionAvailableEvent["
                 + userInfo
    + "]";
  }
}
