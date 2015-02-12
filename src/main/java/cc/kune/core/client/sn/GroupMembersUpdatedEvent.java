package cc.kune.core.client.sn;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class GroupMembersUpdatedEvent extends GwtEvent<GroupMembersUpdatedEvent.GroupMembersUpdatedHandler> { 

  int members;

  protected GroupMembersUpdatedEvent() {
    // Possibly for serialization.
  }

  public GroupMembersUpdatedEvent(int members) {
    this.members = members;
  }

  public static void fire(HasHandlers source, int members) {
    GroupMembersUpdatedEvent eventInstance = new GroupMembersUpdatedEvent(members);
    source.fireEvent(eventInstance);
  }

  public static void fire(HasHandlers source, GroupMembersUpdatedEvent eventInstance) {
    source.fireEvent(eventInstance);
  }

  public interface HasGroupMembersUpdatedHandlers extends HasHandlers {
    HandlerRegistration addGroupMembersUpdatedHandler(GroupMembersUpdatedHandler handler);
  }

  public interface GroupMembersUpdatedHandler extends EventHandler {
    public void onGroupMembersUpdated(GroupMembersUpdatedEvent event);
  }

  private static final Type<GroupMembersUpdatedHandler> TYPE = new Type<GroupMembersUpdatedHandler>();

  public static Type<GroupMembersUpdatedHandler> getType() {
    return TYPE;
  }

  @Override
  public Type<GroupMembersUpdatedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GroupMembersUpdatedHandler handler) {
    handler.onGroupMembersUpdated(this);
  }

  public int getMembers(){
    return members;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GroupMembersUpdatedEvent other = (GroupMembersUpdatedEvent) obj;
    if (members != other.members)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Integer(members).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "GroupMembersUpdatedEvent["
                 + members
    + "]";
  }
}
