package cc.kune.core.client.sn;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class UserFollowersUpdatedEvent extends GwtEvent<UserFollowersUpdatedEvent.UserFollowersUpdatedHandler> { 

  int followers;

  protected UserFollowersUpdatedEvent() {
    // Possibly for serialization.
  }

  public UserFollowersUpdatedEvent(int followers) {
    this.followers = followers;
  }

  public static void fire(HasHandlers source, int followers) {
    UserFollowersUpdatedEvent eventInstance = new UserFollowersUpdatedEvent(followers);
    source.fireEvent(eventInstance);
  }

  public static void fire(HasHandlers source, UserFollowersUpdatedEvent eventInstance) {
    source.fireEvent(eventInstance);
  }

  public interface HasUserFollowersUpdatedHandlers extends HasHandlers {
    HandlerRegistration addUserFollowersUpdatedHandler(UserFollowersUpdatedHandler handler);
  }

  public interface UserFollowersUpdatedHandler extends EventHandler {
    public void onUserFollowersUpdated(UserFollowersUpdatedEvent event);
  }

  private static final Type<UserFollowersUpdatedHandler> TYPE = new Type<UserFollowersUpdatedHandler>();

  public static Type<UserFollowersUpdatedHandler> getType() {
    return TYPE;
  }

  @Override
  public Type<UserFollowersUpdatedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UserFollowersUpdatedHandler handler) {
    handler.onUserFollowersUpdated(this);
  }

  public int getFollowers(){
    return followers;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    UserFollowersUpdatedEvent other = (UserFollowersUpdatedEvent) obj;
    if (followers != other.followers)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Integer(followers).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "UserFollowersUpdatedEvent["
                 + followers
    + "]";
  }
}
