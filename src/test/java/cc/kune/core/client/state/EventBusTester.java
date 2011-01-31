package cc.kune.core.client.state;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;

public class EventBusTester extends SimpleEventBus {
    private final ArrayList<GwtEvent<?>> all;
    private GwtEvent<?> lastEvent;

    public EventBusTester() {
        all = new ArrayList<GwtEvent<?>>();
    }

    @Override
    public void fireEvent(final GwtEvent<?> event) {
        all.add(event);
        this.lastEvent = event;
        super.fireEvent(event);
    }

    public GwtEvent<?> getLastEvent() {
        return lastEvent;
    }

    @SuppressWarnings("unchecked")
    public <T extends GwtEvent<?>> Class<T> getLastEventClass() {
        return (Class<T>) lastEvent.getClass();
    }

    public boolean receivedEventOfClass(final Class<? extends GwtEvent<?>> eventClass) {
        for (final GwtEvent<?> event : all) {
            if (eventClass.equals(event.getClass())) {
                return true;
            }
        }
        return false;
    }
}
