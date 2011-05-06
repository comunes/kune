package cc.kune.wave.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class WaveClientClearEvent extends GwtEvent<WaveClientClearEvent.WaveClientClearHandler> { 

  public interface HasWaveClientClearHandlers extends HasHandlers {
    HandlerRegistration addWaveClientClearHandler(WaveClientClearHandler handler);
  }

  public interface WaveClientClearHandler extends EventHandler {
    public void onWaveClientClear(WaveClientClearEvent event);
  }

  private static final Type<WaveClientClearHandler> TYPE = new Type<WaveClientClearHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new WaveClientClearEvent());
  }

  public static Type<WaveClientClearHandler> getType() {
    return TYPE;
  }


  public WaveClientClearEvent() {
  }

  @Override
  public Type<WaveClientClearHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(WaveClientClearHandler handler) {
    handler.onWaveClientClear(this);
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
    return "WaveClientClearEvent["
    + "]";
  }
}
