package cc.kune.core.client.i18n;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.HasEventBus;

public class I18nReadyEvent extends GwtEvent<I18nReadyEvent.I18nReadyHandler> { 

  public interface HasI18nReadyHandlers extends HasHandlers {
    HandlerRegistration addI18nReadyHandler(I18nReadyHandler handler);
  }

  public interface I18nReadyHandler extends EventHandler {
    public void onI18nReady(I18nReadyEvent event);
  }

  private static final Type<I18nReadyHandler> TYPE = new Type<I18nReadyHandler>();

  public static void fire(HasEventBus source) {
    source.fireEvent(new I18nReadyEvent());
  }

  public static Type<I18nReadyHandler> getType() {
    return TYPE;
  }


  public I18nReadyEvent() {
  }

  @Override
  public Type<I18nReadyHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(I18nReadyHandler handler) {
    handler.onI18nReady(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          I18nReadyEvent o = (I18nReadyEvent) other;
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
    return "I18nReadyEvent["
    + "]";
  }

}
