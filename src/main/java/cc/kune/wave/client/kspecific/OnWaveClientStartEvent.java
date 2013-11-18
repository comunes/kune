package cc.kune.wave.client.kspecific;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class OnWaveClientStartEvent extends GwtEvent<OnWaveClientStartEvent.OnWaveClientStartHandler> { 

  public interface HasOnWaveClientStartHandlers extends HasHandlers {
    HandlerRegistration addOnWaveClientStartHandler(OnWaveClientStartHandler handler);
  }

  public interface OnWaveClientStartHandler extends EventHandler {
    public void onOnWaveClientStart(OnWaveClientStartEvent event);
  }

  private static final Type<OnWaveClientStartHandler> TYPE = new Type<OnWaveClientStartHandler>();

  public static void fire(HasHandlers source, cc.kune.wave.client.kspecific.WaveClientView view) {
    source.fireEvent(new OnWaveClientStartEvent(view));
  }

  public static Type<OnWaveClientStartHandler> getType() {
    return TYPE;
  }

  cc.kune.wave.client.kspecific.WaveClientView view;

  public OnWaveClientStartEvent(cc.kune.wave.client.kspecific.WaveClientView view) {
    this.view = view;
  }

  protected OnWaveClientStartEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<OnWaveClientStartHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.wave.client.kspecific.WaveClientView getView() {
    return view;
  }

  @Override
  protected void dispatch(OnWaveClientStartHandler handler) {
    handler.onOnWaveClientStart(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    OnWaveClientStartEvent other = (OnWaveClientStartEvent) obj;
    if (view == null) {
      if (other.view != null)
        return false;
    } else if (!view.equals(other.view))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (view == null ? 1 : view.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "OnWaveClientStartEvent["
                 + view
    + "]";
  }
}
