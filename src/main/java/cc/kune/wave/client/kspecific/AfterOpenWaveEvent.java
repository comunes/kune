package cc.kune.wave.client.kspecific;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class AfterOpenWaveEvent extends GwtEvent<AfterOpenWaveEvent.AfterOpenWaveHandler> { 

  public interface HasAfterOpenWaveHandlers extends HasHandlers {
    HandlerRegistration addAfterOpenWaveHandler(AfterOpenWaveHandler handler);
  }

  public interface AfterOpenWaveHandler extends EventHandler {
    public void onAfterOpenWave(AfterOpenWaveEvent event);
  }

  private static final Type<AfterOpenWaveHandler> TYPE = new Type<AfterOpenWaveHandler>();

  public static void fire(HasHandlers source, java.lang.String waveId) {
    source.fireEvent(new AfterOpenWaveEvent(waveId));
  }

  public static Type<AfterOpenWaveHandler> getType() {
    return TYPE;
  }

  java.lang.String waveId;

  public AfterOpenWaveEvent(java.lang.String waveId) {
    this.waveId = waveId;
  }

  protected AfterOpenWaveEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<AfterOpenWaveHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getWaveId() {
    return waveId;
  }

  @Override
  protected void dispatch(AfterOpenWaveHandler handler) {
    handler.onAfterOpenWave(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    AfterOpenWaveEvent other = (AfterOpenWaveEvent) obj;
    if (waveId == null) {
      if (other.waveId != null)
        return false;
    } else if (!waveId.equals(other.waveId))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (waveId == null ? 1 : waveId.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "AfterOpenWaveEvent["
                 + waveId
    + "]";
  }
}
