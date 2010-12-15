package cc.kune.core.client.i18n;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class I18nReadyEvent extends GwtEvent<I18nReadyEvent.I18nReadyHandler> {

    public interface I18nReadyHandler extends EventHandler {

        void fire(I18nReadyEvent event);
    }

    public static final GwtEvent.Type<I18nReadyHandler> TYPE = new GwtEvent.Type<I18nReadyHandler>();

    public static Type<I18nReadyHandler> getType() {
        return TYPE;
    }

    public I18nReadyEvent() {
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<I18nReadyHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final I18nReadyHandler handler) {
        handler.fire(this);
    }

}