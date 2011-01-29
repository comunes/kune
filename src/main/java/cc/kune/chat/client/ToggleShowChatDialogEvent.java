package cc.kune.chat.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ToggleShowChatDialogEvent extends GwtEvent<ToggleShowChatDialogEvent.ToggleShowChatDialogHandler> {

    public interface HasToggleShowChatDialogHandlers extends HasHandlers {
        HandlerRegistration addToggleShowChatDialogHandler(ToggleShowChatDialogHandler handler);
    }

    public interface ToggleShowChatDialogHandler extends EventHandler {
        public void onToggleShowChatDialog(ToggleShowChatDialogEvent event);
    }

    private static final Type<ToggleShowChatDialogHandler> TYPE = new Type<ToggleShowChatDialogHandler>();

    public static void fire(final HasHandlers source, final boolean show) {
        source.fireEvent(new ToggleShowChatDialogEvent());
    }

    public static Type<ToggleShowChatDialogHandler> getType() {
        return TYPE;
    }

    protected ToggleShowChatDialogEvent() {
        // Possibly for serialization.
    }

    @Override
    protected void dispatch(final ToggleShowChatDialogHandler handler) {
        handler.onToggleShowChatDialog(this);
    }

    @Override
    public Type<ToggleShowChatDialogHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return "ToggleShowChatDialogEvent";
    }
}
