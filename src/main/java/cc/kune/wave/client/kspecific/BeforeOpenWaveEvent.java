package cc.kune.wave.client.kspecific;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class BeforeOpenWaveEvent extends GwtEvent<BeforeOpenWaveEvent.BeforeOpenWaveHandler> { 

  public interface HasBeforeOpenWaveHandlers extends HasHandlers {
    HandlerRegistration addBeforeOpenWaveHandler(BeforeOpenWaveHandler handler);
  }

  public interface BeforeOpenWaveHandler extends EventHandler {
    public void onBeforeOpenWave(BeforeOpenWaveEvent event);
  }

  private static final Type<BeforeOpenWaveHandler> TYPE = new Type<BeforeOpenWaveHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new BeforeOpenWaveEvent());
  }

  public static Type<BeforeOpenWaveHandler> getType() {
    return TYPE;
  }


  public BeforeOpenWaveEvent() {
  }

  @Override
  public Type<BeforeOpenWaveHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BeforeOpenWaveHandler handler) {
    handler.onBeforeOpenWave(this);
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
    return "BeforeOpenWaveEvent["
    + "]";
  }
}
