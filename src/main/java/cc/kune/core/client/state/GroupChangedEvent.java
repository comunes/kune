package cc.kune.core.client.state;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class GroupChangedEvent extends GwtEvent<GroupChangedEvent.GroupChangedHandler> { 

  public interface HasGroupChangedHandlers extends HasHandlers {
    HandlerRegistration addGroupChangedHandler(GroupChangedHandler handler);
  }

  public interface GroupChangedHandler extends EventHandler {
    public void onGroupChanged(GroupChangedEvent event);
  }

  private static final Type<GroupChangedHandler> TYPE = new Type<GroupChangedHandler>();

  public static void fire(HasHandlers source, java.lang.String previousGroup, java.lang.String newGroup) {
    source.fireEvent(new GroupChangedEvent(previousGroup, newGroup));
  }

  public static Type<GroupChangedHandler> getType() {
    return TYPE;
  }

  java.lang.String previousGroup;
  java.lang.String newGroup;

  public GroupChangedEvent(java.lang.String previousGroup, java.lang.String newGroup) {
    this.previousGroup = previousGroup;
    this.newGroup = newGroup;
  }

  protected GroupChangedEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<GroupChangedHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getPreviousGroup() {
    return previousGroup;
  }

  public java.lang.String getNewGroup() {
    return newGroup;
  }

  @Override
  protected void dispatch(GroupChangedHandler handler) {
    handler.onGroupChanged(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GroupChangedEvent other = (GroupChangedEvent) obj;
    if (previousGroup == null) {
      if (other.previousGroup != null)
        return false;
    } else if (!previousGroup.equals(other.previousGroup))
      return false;
    if (newGroup == null) {
      if (other.newGroup != null)
        return false;
    } else if (!newGroup.equals(other.newGroup))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (previousGroup == null ? 1 : previousGroup.hashCode());
    hashCode = (hashCode * 37) + (newGroup == null ? 1 : newGroup.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GroupChangedEvent["
                 + previousGroup
                 + ","
                 + newGroup
    + "]";
  }
}
