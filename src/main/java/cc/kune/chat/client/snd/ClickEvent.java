package cc.kune.chat.client.snd;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class ClickEvent extends GwtEvent<ClickEvent.ClickHandler> { 

  public interface HasClickHandlers extends HasHandlers {
    HandlerRegistration addClickHandler(ClickHandler handler);
  }

  public interface ClickHandler extends EventHandler {
    public void onClick(ClickEvent event);
  }

  private static final Type<ClickHandler> TYPE = new Type<ClickHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new ClickEvent());
  }

  public static Type<ClickHandler> getType() {
    return TYPE;
  }


  public ClickEvent() {
  }

  @Override
  public Type<ClickHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ClickHandler handler) {
    handler.onClick(this);
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
    return "ClickEvent["
    + "]";
  }
}
