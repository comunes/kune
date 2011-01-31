package cc.kune.core.client.logs;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;

public class EventBusWithLogging extends SimpleEventBus {
    @Override
    public void fireEvent(final GwtEvent<?> event) {
        Log.debug("Kune " + event.toDebugString());
        super.fireEvent(event);
    }
}
