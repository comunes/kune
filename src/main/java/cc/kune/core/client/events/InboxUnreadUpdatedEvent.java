package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class InboxUnreadUpdatedEvent extends GwtEvent<InboxUnreadUpdatedEvent.InboxUnreadUpdatedHandler> {

  public interface HasInboxUnreadUpdatedHandlers extends HasHandlers {
    HandlerRegistration addInboxUnreadUpdatedHandler(InboxUnreadUpdatedHandler handler);
  }

  public interface InboxUnreadUpdatedHandler extends EventHandler {
    public void onInboxUnreadUpdated(InboxUnreadUpdatedEvent event);
  }

  private static final Type<InboxUnreadUpdatedHandler> TYPE = new Type<InboxUnreadUpdatedHandler>();

  public static void fire(final HasHandlers source, final int count, final boolean greater) {
    source.fireEvent(new InboxUnreadUpdatedEvent(count, greater));
  }

  public static Type<InboxUnreadUpdatedHandler> getType() {
    return TYPE;
  }

  int count;
  boolean greater;

  protected InboxUnreadUpdatedEvent() {
    // Possibly for serialization.
  }

  public InboxUnreadUpdatedEvent(final int count, final boolean greater) {
    this.count = count;
    this.greater = greater;
  }

  @Override
  protected void dispatch(final InboxUnreadUpdatedHandler handler) {
    handler.onInboxUnreadUpdated(this);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final InboxUnreadUpdatedEvent other = (InboxUnreadUpdatedEvent) obj;
    if (count != other.count) {
      return false;
    }
    if (greater != other.greater) {
      return false;
    }
    return true;
  }

  @Override
  public Type<InboxUnreadUpdatedHandler> getAssociatedType() {
    return TYPE;
  }

  public int getCount() {
    return count;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Integer(count).hashCode();
    hashCode = (hashCode * 37) + new Boolean(greater).hashCode();
    return hashCode;
  }

  public boolean isGreater() {
    return greater;
  }

  @Override
  public String toString() {
    return "InboxUnreadUpdatedEvent[" + count + "," + greater + "]";
  }
}
