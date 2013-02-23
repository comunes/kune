/**
 * Copyright 2010 Jonas Huckestein, jonas.huckestein@me.com, http://thezukunft.com
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

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thezukunft.wave.connector.Participant;
import com.thezukunft.wave.connector.State;
import com.thezukunft.wave.connector.Wave;

/**
 * We need this wrapper class, because we want to be able to use Wave with gin, which is impossible using JSO
 *
 * @author Jonas Huckestein
 *
 */
@Singleton
public class WaveGINWrapper implements Wave {

  protected WaveFeatureImpl wave;
  protected EventBus eventBus;

  @Inject
  public WaveGINWrapper(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void setWave(WaveFeatureImpl wave) {
    this.wave = wave;
    wave.setEventBus(eventBus);
  }

  @Override
  public Participant getHost() {
    return wave.getHost();
  }

  @Override
  public Participant getParticipantById(String id) {
    return wave.getParticipantById(id);
  }

  @Override
  public JsArray<Participant> getParticipants() {
    return wave.getParticipants();
  }

  @Override
  public State getState() {
    return wave.getState();
  }

  @Override
  public long getTime() {
    return wave.getTime();
  }

  @Override
  public Participant getViewer() {
    return wave.getViewer();
  }

  @Override
  public boolean isInWaveContainer() {
    return wave.isInWaveContainer();
  }

  @Override
  public boolean isPlayback() {
    return wave.isPlayback();
  }

  @Override
  public void log(String log) {
    wave.log(log);
  }

  @Override
  public int getMode() {
    return wave.getMode();
  }

}
