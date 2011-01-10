package cc.kune.core.client.notify.spiner;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.HasEventBus;

public class ProgressHideEvent extends GwtEvent<ProgressHideEvent.ProgressHideHandler> { 

  public interface HasProgressHideHandlers extends HasHandlers {
    HandlerRegistration addProgressHideHandler(ProgressHideHandler handler);
  }

  public interface ProgressHideHandler extends EventHandler {
    public void onProgressHide(ProgressHideEvent event);
  }

  private static final Type<ProgressHideHandler> TYPE = new Type<ProgressHideHandler>();

  public static void fire(HasEventBus source) {
    source.fireEvent(new ProgressHideEvent());
  }

  public static Type<ProgressHideHandler> getType() {
    return TYPE;
  }


  public ProgressHideEvent() {
  }

  @Override
  public Type<ProgressHideHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ProgressHideHandler handler) {
    handler.onProgressHide(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          ProgressHideEvent o = (ProgressHideEvent) other;
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
    return "ProgressHideEvent["
    + "]";
  }

}
