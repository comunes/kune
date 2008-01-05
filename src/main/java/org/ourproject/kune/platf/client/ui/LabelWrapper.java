package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.Label;

public class LabelWrapper extends Label implements AbstractLabel {
    private ClickListenerCollection doubleClickListeners;

    public LabelWrapper() {
        super();
    }

    public LabelWrapper(final String text) {
        super(text);
        sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS | Event.ONMOUSEWHEEL | Event.ONDBLCLICK);
    }

    public LabelWrapper(final String text, final boolean wordWrap) {
        super(text, wordWrap);
    }

    public void setColor(final String color) {
        DOM.setStyleAttribute(super.getElement(), "color", color);
    }

    public void addDoubleClickListener(final ClickListener listener) {
        if (doubleClickListeners == null) {
            doubleClickListeners = new ClickListenerCollection();
        }
        doubleClickListeners.add(listener);
    }

    public void removeDoubleClickListener(final ClickListener listener) {
        if (doubleClickListeners != null) {
            doubleClickListeners.remove(listener);
        }
    }

    public void onBrowserEvent(final Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONDBLCLICK:

            if (doubleClickListeners != null) {
                doubleClickListeners.fireClick(this);
            }
            break;
        }
        super.onBrowserEvent(event);
    }
}
