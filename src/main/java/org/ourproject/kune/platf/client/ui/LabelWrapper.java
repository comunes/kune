/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
