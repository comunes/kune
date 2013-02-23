/**
 * Copyright 2010 Jonas Huckestein, jonas.huckestein@me.com, http://thezukunft.com
 * Copyright 2009 Hilbrand Bouwkamp, hs@bouwkamp.com
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 **/
package com.thezukunft.wave.connectorimpl;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.gadgets.client.GadgetFeature;
import com.thezukunft.wave.connector.ModeChangeEvent;
import com.thezukunft.wave.connector.Participant;
import com.thezukunft.wave.connector.ParticipantUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.Wave;

/**
 * Wave Feature class.
 */
public class WaveFeatureImpl implements GadgetFeature, Wave {

  // singleton
  private static WaveFeatureImpl wave;

  public static EventBus eventBus;

  private WaveFeatureImpl(){
  }

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#getHost()
   */
  public native Participant getHost() /*-{
    return $wnd.wave.getHost();
  }-*/;

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#getMode()
   */
  public native int getMode() /*-{
    return $wnd.wave.getMode();
  }-*/;

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#getParticipantById(java.lang.String)
   */
  public native Participant getParticipantById(String id) /*-{
    return $wnd.wave.getParticipantById(id);
  }-*/;

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#getParticipants()
   */
  public native JsArray<Participant> getParticipants() /*-{
    return $wnd.wave.getParticipants();
  }-*/;

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#getState()
   */
  public native StateImpl getState() /*-{
    return $wnd.wave.getState();
  }-*/;

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#getTime()
   */
  public long getTime() {
    return (long) getTime0();
  }

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#getViewer()
   */
  public native Participant getViewer() /*-{
    return $wnd.wave.getViewer();
  }-*/;

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#isInWaveContainer()
   */
  public native boolean isInWaveContainer() /*-{
    return $wnd.wave && $wnd.wave.isInWaveContainer();
  }-*/;

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#isPlayback()
   */
  public native boolean isPlayback() /*-{
    return $wnd.wave.isPlayback();
  }-*/;

  /* (non-Javadoc)
   * @see com.onetwopoll.wave.connector.Wave#log(java.lang.String)
   */
  public native void log(String log) /*-{
    return $wnd.wave.log(log);
  }-*/;

  /**
   * Sets the mode change callback.
   */
  @SuppressWarnings("unused")
  private native void setModeCallback(String callback,
      JavaScriptObject opt_context) /*-{
    $wnd.wave.setModeCallback(callback, opt_context);
  }-*/;

  /**
   * Sets the participant update callback. If the participant information is
   * already received, the callback is invoked immediately to report the current
   * participant information. Only one callback can be defined. Consecutive
   * calls would remove old callback and set the new one.
   */
  @SuppressWarnings("unused")
  private native void setParticipantCallback(String callback,
      JavaScriptObject opt_context) /*-{
    $wnd.wave.setParticipantCallback(callback, opt_context);
  }-*/;

  /**
   * Sets the gadget state update callback. If the state is already received
   * from the container, the callback is invoked immediately to report the
   * current gadget state. Only one callback can be defined. Consecutive calls
   * would remove the old callback and set the new one.
   */
  @SuppressWarnings("unused")
  private native void setStateCallback(String callback,
      JavaScriptObject opt_context) /*-{
    $wnd.wave.setStateCallback(callback, opt_context);
  }-*/;

  /**
   * Register the mode change method to be called when the mode changes.
   */
  private native void registerModeChangeCallback() /*-{
    $wnd.wave.setModeCallback(@com.thezukunft.wave.connectorimpl.WaveFeatureImpl::modeChangeEvent(I));
  }-*/;

  /**
   * Register the participantUpdated method to be called when the participants
   * are updated.
   */
  private native void registerParticipantUpdateCallback() /*-{
    $wnd.wave.setParticipantCallback(@com.thezukunft.wave.connectorimpl.WaveFeatureImpl::participantUpdateEvent());
  }-*/;

  /**
   * Register the stateUpdated method to be called when the state is updated.
   */
  private native void registerStateUpdateCallback() /*-{
    $wnd.wave.setStateCallback(@com.thezukunft.wave.connectorimpl.WaveFeatureImpl::stateUpdateEvent());
  }-*/;

  /**
   * This method is called from the wave JavaScript library on Mode changes.
   */
  @SuppressWarnings("unused")
  private static void modeChangeEvent(int mode) {

    ModeChangeEvent.fire(eventBus, mode);
  }

  /**
   * This method is called from the wave JavaScript library on Participant
   * changes.
   */
  @SuppressWarnings("unused")
  private static void participantUpdateEvent() {
    ParticipantUpdateEvent.fire(eventBus, wave);
  }

  /**
   * This method is called from the wave JavaScript library on State changes.
   */
  @SuppressWarnings("unused")
  private static void stateUpdateEvent() {
    StateUpdateEvent.fire(eventBus, wave);
  }

  /**
   * Helper method to set the eventBus from externally
   */
  public void setEventBus(EventBus b) {
    if (wave == null) {
      wave = this;
    }

    eventBus = b;

    registerModeChangeCallback();
    registerParticipantUpdateCallback();
    registerStateUpdateCallback();

  }

  private native double getTime0() /*-{
    return $wnd.wave.getTime();
  }-*/;
}
