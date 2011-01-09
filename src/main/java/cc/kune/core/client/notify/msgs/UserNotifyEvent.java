package cc.kune.core.client.notify.msgs;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.HasEventBus;

public class UserNotifyEvent extends GwtEvent<UserNotifyEvent.UserNotifyHandler> {

    public interface HasUserNotifyHandlers extends HasHandlers {
        HandlerRegistration addUserNotifyHandler(UserNotifyHandler handler);
    }

    public interface UserNotifyHandler extends EventHandler {
        public void onUserNotify(UserNotifyEvent event);
    }

    private static final Type<UserNotifyHandler> TYPE = new Type<UserNotifyHandler>();

    public static void fire(HasEventBus source, org.ourproject.common.client.notify.NotifyLevel level,
            java.lang.String message, Boolean closeable) {
        source.fireEvent(new UserNotifyEvent(level, message, closeable));
    }

    public static void fire(HasEventBus source, org.ourproject.common.client.notify.NotifyLevel level,
            java.lang.String message) {
        source.fireEvent(new UserNotifyEvent(level, message));
    }

    public static Type<UserNotifyHandler> getType() {
        return TYPE;
    }

    private final org.ourproject.common.client.notify.NotifyLevel level;
    private final java.lang.String message;
    private final Boolean closeable;

    public UserNotifyEvent(org.ourproject.common.client.notify.NotifyLevel level, java.lang.String message,
            Boolean closeable) {
        this.level = level;
        this.message = message;
        this.closeable = closeable;
    }

    public UserNotifyEvent(org.ourproject.common.client.notify.NotifyLevel level, java.lang.String message) {
        this(level, message, false);
    }

    @Override
    public Type<UserNotifyHandler> getAssociatedType() {
        return TYPE;
    }

    public org.ourproject.common.client.notify.NotifyLevel getLevel() {
        return level;
    }

    public java.lang.String getMessage() {
        return message;
    }

    @Override
    protected void dispatch(UserNotifyHandler handler) {
        handler.onUserNotify(this);
    }

    public Boolean getCloseable() {
        return closeable;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (closeable == null ? 0 : closeable.hashCode());
        result = prime * result + (level == null ? 0 : level.hashCode());
        result = prime * result + (message == null ? 0 : message.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserNotifyEvent other = (UserNotifyEvent) obj;
        if (closeable == null) {
            if (other.closeable != null) {
                return false;
            }
        } else if (!closeable.equals(other.closeable)) {
            return false;
        }
        if (level != other.level) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserNotifyEvent [level=" + level + ", message=" + message + ", closeable=" + closeable + "]";
    }

}
