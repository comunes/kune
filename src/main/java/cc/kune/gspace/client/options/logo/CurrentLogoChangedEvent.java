package cc.kune.gspace.client.options.logo;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class CurrentLogoChangedEvent extends GwtEvent<CurrentLogoChangedEvent.CurrentLogoChangedHandler> {

    public interface CurrentLogoChangedHandler extends EventHandler {
        public void onCurrentLogoChanged(CurrentLogoChangedEvent event);
    }

    public interface HasCurrentLogoChangedHandlers extends HasHandlers {
        HandlerRegistration addCurrentLogoChangedHandler(CurrentLogoChangedHandler handler);
    }

    private static final Type<CurrentLogoChangedHandler> TYPE = new Type<CurrentLogoChangedHandler>();

    public static void fire(final HasHandlers source) {
        source.fireEvent(new CurrentLogoChangedEvent());
    }

    public static Type<CurrentLogoChangedHandler> getType() {
        return TYPE;
    }

    public CurrentLogoChangedEvent() {
    }

    @Override
    protected void dispatch(final CurrentLogoChangedHandler handler) {
        handler.onCurrentLogoChanged(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    public Type<CurrentLogoChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "CurrentLogoChangedEvent[" + "]";
    }
}
