package cc.kune.core.client.notify.msgs;

import cc.kune.common.client.noti.NotifyLevel;

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

    public static void fire(HasEventBus source, NotifyLevel level, java.lang.String title, java.lang.String message,
            Boolean closeable) {
        source.fireEvent(new UserNotifyEvent(level, title, message, closeable));
    }

    public static void fire(HasEventBus source, NotifyLevel level, java.lang.String title, java.lang.String message) {
        source.fireEvent(new UserNotifyEvent(level, title, message));
    }

    public static void fire(HasEventBus source, NotifyLevel level, java.lang.String message, Boolean closeable) {
        source.fireEvent(new UserNotifyEvent(level, "", message, closeable));
    }

    public static void fire(HasEventBus source, NotifyLevel level, java.lang.String message) {
        source.fireEvent(new UserNotifyEvent(level, "", message));
    }

    public static Type<UserNotifyHandler> getType() {
        return TYPE;
    }

    private final NotifyLevel level;
    private final java.lang.String message;
    private final java.lang.String title;
    private final Boolean closeable;

    public UserNotifyEvent(NotifyLevel level, java.lang.String title, java.lang.String message, Boolean closeable) {
        this.level = level;
        this.title = title;
        this.message = message;
        this.closeable = closeable;
    }

    public UserNotifyEvent(NotifyLevel level, java.lang.String message) {
        this(level, "", message, false);
    }

    public UserNotifyEvent(NotifyLevel level, java.lang.String title, java.lang.String message) {
        this(level, title, message, false);
    }

    public UserNotifyEvent(NotifyLevel level, java.lang.String message, Boolean closeable) {
        this(level, "", message, closeable);
    }

    @Override
    public Type<UserNotifyHandler> getAssociatedType() {
        return TYPE;
    }

    public NotifyLevel getLevel() {
        return level;
    }

    public java.lang.String getMessage() {
        return message;
    }

    public java.lang.String getTitle() {
        return title;
    }

    @Override
    protected void dispatch(UserNotifyHandler handler) {
        handler.onUserNotify(this);
    }

    public Boolean getCloseable() {
        return closeable;
    }

}
