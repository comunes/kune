/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
