package org.ourproject.massmob.client.actions;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UnconfirmedEvent extends GwtEvent<UnconfirmedEvent.UnconfirmedHandler> {

    public interface UnconfirmedHandler extends EventHandler {

        void fire(UnconfirmedEvent event);
    }

    public static final GwtEvent.Type<UnconfirmedHandler> TYPE = new GwtEvent.Type<UnconfirmedHandler>();

    public static Type<UnconfirmedHandler> getType() {
        return TYPE;
    }

    public UnconfirmedEvent() {
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<UnconfirmedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final UnconfirmedHandler handler) {
        handler.fire(this);
    }

}