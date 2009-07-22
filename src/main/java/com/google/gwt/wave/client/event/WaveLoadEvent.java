/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.wave.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a selection event.
 * 
 * @param <I>
 *            the type being selected
 */
public class WaveLoadEvent extends GwtEvent<WaveLoadHandler> {

    /**
     * Handler type.
     */
    private static Type<WaveLoadHandler> type;

    /**
     * Fires a selection event on all registered handlers in the handler
     * manager.If no such handlers exist, this method will do nothing.
     * 
     * @param <I>
     *            the selected item type
     * @param source
     *            the source of the handlers
     * @param selectedItem
     *            the selected item
     */
    public static <I> void fire(final HasWaveLoadHandlers source, final String waveId) {
        if (type != null) {
            final WaveLoadEvent event = new WaveLoadEvent(waveId);
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     * 
     * @return returns the handler type
     */
    public static Type<WaveLoadHandler> getType() {
        if (type == null) {
            type = new Type<WaveLoadHandler>();
        }
        return type;
    }

    private final String waveId;

    /**
     * Creates a new selection event.
     * 
     * @param selectedItem
     *            selected item
     */
    public WaveLoadEvent(final String waveId) {
        this.waveId = waveId;
    }

    @Override
    public final Type<WaveLoadHandler> getAssociatedType() {
        return type;
    }

    public String getWaveId() {
        return waveId;
    }

    @Override
    protected void dispatch(final WaveLoadHandler handler) {
        handler.onWaveLoad(this);
    }
}
