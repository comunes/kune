package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class MyGroupsChangedEvent extends GwtEvent<MyGroupsChangedEvent.MyGroupsChangedHandler> {

  public interface HasMyGroupsChangedHandlers extends HasHandlers {
    HandlerRegistration addMyGroupsChangedHandler(MyGroupsChangedHandler handler);
  }

  public interface MyGroupsChangedHandler extends EventHandler {
    public void onMyGroupsChanged(MyGroupsChangedEvent event);
  }

  private static final Type<MyGroupsChangedHandler> TYPE = new Type<MyGroupsChangedHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new MyGroupsChangedEvent());
  }

  public static Type<MyGroupsChangedHandler> getType() {
    return TYPE;
  }

  public MyGroupsChangedEvent() {
  }

  @Override
  protected void dispatch(final MyGroupsChangedHandler handler) {
    handler.onMyGroupsChanged(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<MyGroupsChangedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "MyGroupsChangedEvent[" + "]";
  }
}
