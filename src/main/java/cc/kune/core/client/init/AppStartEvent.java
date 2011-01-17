package cc.kune.core.client.init;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.HasEventBus;

public class AppStartEvent extends GwtEvent<AppStartEvent.AppStartHandler> { 

  public interface HasAppStartHandlers extends HasHandlers {
    HandlerRegistration addAppStartHandler(AppStartHandler handler);
  }

  public interface AppStartHandler extends EventHandler {
    public void onAppStart(AppStartEvent event);
  }

  private static final Type<AppStartHandler> TYPE = new Type<AppStartHandler>();

  public static void fire(HasEventBus source, cc.kune.core.shared.dto.InitDataDTO initData) {
    source.fireEvent(new AppStartEvent(initData));
  }

  public static Type<AppStartHandler> getType() {
    return TYPE;
  }

  private final cc.kune.core.shared.dto.InitDataDTO initData;

  public AppStartEvent(cc.kune.core.shared.dto.InitDataDTO initData) {
    this.initData = initData;
  }

  @Override
  public Type<AppStartHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.shared.dto.InitDataDTO getInitData() {
    return initData;
  }

  @Override
  protected void dispatch(AppStartHandler handler) {
    handler.onAppStart(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          AppStartEvent o = (AppStartEvent) other;
      return true
          && ((o.initData == null && this.initData == null) || (o.initData != null && o.initData.equals(this.initData)))
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (initData == null ? 1 : initData.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "AppStartEvent["
                 + initData
    + "]";
  }

}
