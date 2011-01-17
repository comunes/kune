package cc.kune.core.client.init;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.HasEventBus;

public class AppStopEvent extends GwtEvent<AppStopEvent.AppStopHandler> { 

  public interface HasAppStopHandlers extends HasHandlers {
    HandlerRegistration addAppStopHandler(AppStopHandler handler);
  }

  public interface AppStopHandler extends EventHandler {
    public void onAppStop(AppStopEvent event);
  }

  private static final Type<AppStopHandler> TYPE = new Type<AppStopHandler>();

  public static void fire(HasEventBus source) {
    source.fireEvent(new AppStopEvent());
  }

  public static Type<AppStopHandler> getType() {
    return TYPE;
  }


  public AppStopEvent() {
  }

  @Override
  public Type<AppStopHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AppStopHandler handler) {
    handler.onAppStop(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          AppStopEvent o = (AppStopEvent) other;
      return true
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "AppStopEvent["
    + "]";
  }

}
