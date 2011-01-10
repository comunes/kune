package cc.kune.core.client.notify.alerts;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

@Deprecated
public class AlertEvent extends GwtEvent<AlertEvent.AlertHandler> {

    public interface HasAlertHandlers extends HasHandlers {
        HandlerRegistration addAlertHandler(AlertHandler handler);
    }

    public interface AlertHandler extends EventHandler {
        public void onAlert(AlertEvent event);
    }

    private static final Type<AlertHandler> TYPE = new Type<AlertHandler>();

    public static void fire(HasHandlers source, java.lang.String title, java.lang.String message) {
        source.fireEvent(new AlertEvent(title, message));
    }

    public static Type<AlertHandler> getType() {
        return TYPE;
    }

    private final java.lang.String title;
    private final java.lang.String message;

    public AlertEvent(java.lang.String title, java.lang.String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public Type<AlertHandler> getAssociatedType() {
        return TYPE;
    }

    public java.lang.String getTitle() {
        return title;
    }

    public java.lang.String getMessage() {
        return message;
    }

    @Override
    protected void dispatch(AlertHandler handler) {
        handler.onAlert(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass().equals(this.getClass())) {
            AlertEvent o = (AlertEvent) other;
            return true
                    && (o.title == null && this.title == null || o.title != null && o.title.equals(this.title))
                    && (o.message == null && this.message == null || o.message != null
                            && o.message.equals(this.message));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = hashCode * 37 + getClass().hashCode();
        hashCode = hashCode * 37 + (title == null ? 1 : title.hashCode());
        hashCode = hashCode * 37 + (message == null ? 1 : message.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "AlertEvent[" + title + "," + message + "]";
    }

}
