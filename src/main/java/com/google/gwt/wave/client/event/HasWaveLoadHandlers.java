package com.google.gwt.wave.client.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasWaveLoadHandlers extends HasHandlers {
  HandlerRegistration addWaveLoadHandler(WaveLoadHandler handler);
}
