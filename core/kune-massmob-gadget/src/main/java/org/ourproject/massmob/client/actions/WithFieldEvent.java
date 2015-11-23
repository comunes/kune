package org.ourproject.massmob.client.actions;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class WithFieldEvent extends GwtEvent<WithFieldEvent.WithFieldHandler> {

    public interface WithFieldHandler extends EventHandler {

        void fire(WithFieldEvent event);
    }

    public static final GwtEvent.Type<WithFieldHandler> TYPE = new GwtEvent.Type<WithFieldHandler>();

    public static Type<WithFieldHandler> getType() {
        return TYPE;
    }

    private final boolean with;
    private final String field;

    public WithFieldEvent(final boolean with, final String field) {
        this.with = with;
        this.field = field;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<WithFieldHandler> getAssociatedType() {
        return TYPE;
    }

    public String getField() {
        return field;
    }

    public boolean getWith() {
        return with;
    }

    @Override
    protected void dispatch(final WithFieldHandler handler) {
        handler.fire(this);
    }

}