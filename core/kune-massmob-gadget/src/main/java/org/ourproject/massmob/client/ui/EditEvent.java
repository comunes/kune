package org.ourproject.massmob.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class EditEvent extends GwtEvent<EditEvent.EditHandler> {

    public interface EditHandler extends EventHandler {

        void fire(EditEvent event);
    }

    public static final GwtEvent.Type<EditHandler> TYPE = new GwtEvent.Type<EditHandler>();

    public static Type<EditHandler> getType() {
        return TYPE;
    }

    private final String text;

    public EditEvent(final String text) {
        this.text = text;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<EditHandler> getAssociatedType() {
        return TYPE;
    }

    public String getText() {
        return text;
    }

    @Override
    protected void dispatch(final EditHandler handler) {
        handler.fire(this);
    }
}