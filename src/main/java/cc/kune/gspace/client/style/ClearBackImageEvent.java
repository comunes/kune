package cc.kune.gspace.client.style;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class ClearBackImageEvent extends GwtEvent<ClearBackImageEvent.ClearBackImageHandler> { 

  public interface HasClearBackImageHandlers extends HasHandlers {
    HandlerRegistration addClearBackImageHandler(ClearBackImageHandler handler);
  }

  public interface ClearBackImageHandler extends EventHandler {
    public void onClearBackImage(ClearBackImageEvent event);
  }

  private static final Type<ClearBackImageHandler> TYPE = new Type<ClearBackImageHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new ClearBackImageEvent());
  }

  public static Type<ClearBackImageHandler> getType() {
    return TYPE;
  }


  public ClearBackImageEvent() {
  }

  @Override
  public Type<ClearBackImageHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ClearBackImageHandler handler) {
    handler.onClearBackImage(this);
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
    return "ClearBackImageEvent["
    + "]";
  }
}
