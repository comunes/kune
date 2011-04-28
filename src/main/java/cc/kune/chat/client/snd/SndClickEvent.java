package cc.kune.chat.client.snd;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class SndClickEvent extends GwtEvent<SndClickEvent.SndClickHandler> {

    public interface HasSndClickHandlers extends HasHandlers {
        HandlerRegistration addSndClickHandler(SndClickHandler handler);
    }

    public interface SndClickHandler extends EventHandler {
        public void onClick(SndClickEvent event);
    }

    private static final Type<SndClickHandler> TYPE = new Type<SndClickHandler>();

    public static void fire(final HasHandlers source) {
        source.fireEvent(new SndClickEvent());
    }

    public static Type<SndClickHandler> getType() {
        return TYPE;
    }

    public SndClickEvent() {
    }

    @Override
    protected void dispatch(final SndClickHandler handler) {
        handler.onClick(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    public Type<SndClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "SndClickEvent[" + "]";
    }
}
