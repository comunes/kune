package org.ourproject.massmob.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ChangeBackgroundEvent extends GwtEvent<ChangeBackgroundEvent.ChangeBackgroundHandler> {

    public interface ChangeBackgroundHandler extends EventHandler {
        void fire(ChangeBackgroundEvent event);
    }

    public static final GwtEvent.Type<ChangeBackgroundHandler> TYPE = new GwtEvent.Type<ChangeBackgroundHandler>();

    public static Type<ChangeBackgroundHandler> getType() {
        return TYPE;
    }

    private final String bg;

    public ChangeBackgroundEvent(final String bg) {
        this.bg = bg;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ChangeBackgroundHandler> getAssociatedType() {
        return TYPE;
    }

    public String getBg() {
        return bg;
    }

    @Override
    protected void dispatch(final ChangeBackgroundHandler handler) {
        handler.fire(this);
    }

}