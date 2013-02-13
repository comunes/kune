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
package com.thezukunft.wave.connectormock;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Random;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onetwopoll.gwt.framework.EventBus;
import com.thezukunft.wave.connector.Participant;
import com.thezukunft.wave.connector.ParticipantUpdateEvent;
import com.thezukunft.wave.connector.State;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.Wave;

@Singleton
public class WaveMock implements Wave {

  protected JsArray<Participant> participants;
  protected Participant host;
  protected StateMock state;
  protected long time;
  protected Participant viewer;
  protected boolean inWaveContainer;
  protected boolean playback;
  private EventBus eventBus;
  protected int mode;

  @Inject
  public WaveMock(EventBus eventBus) {
    this.eventBus = eventBus;
    participants = JsArray.createArray().cast();
    time = 0;
    viewer = null;
    inWaveContainer = true;
    playback = false;
    state = new StateMock();
    state.setWave(this);
  }

  public void initRandomParticipants() {

    // init some viewer and add it
    ParticipantMock p = JavaScriptObject.createObject().cast();
    p.setupMock();
    p.setDisplayName("Robin the Simple");
    p.setId("1a");
    p.setThumbnailUrl(GWT.getModuleBaseURL() + "robin.jpg");
    participants.push(p);

    // init some host and add it 50% of the time
    ParticipantMock h = JavaScriptObject.createObject().cast();
    h.setupMock();
    h.setDisplayName("Batman the Host");
    h.setId("2b");
    h.setThumbnailUrl(GWT.getModuleBaseURL() + "/batman.jpg");
    participants.push(h);

    viewer = Random.nextBoolean() ? p : h;
    host = Random.nextBoolean() ? h : p;

    eventBus.fireEvent(new ParticipantUpdateEvent(this));
  }

  public void setParticipants(JsArray<Participant> participants) {
    this.participants = participants;
  }

  public void setHost(Participant host) {
    this.host = host;
  }

  public void setState(StateMock state) {
    this.state = state;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public void setViewer(Participant viewer) {
    this.viewer = viewer;
  }

  public void setInWaveContainer(boolean inWaveContainer) {
    this.inWaveContainer = inWaveContainer;
  }

  public void setPlayback(boolean playback) {
    this.playback = playback;
  }

  @Override
  public Participant getHost() {
    return host;
  }

  @Override
  public Participant getParticipantById(String id) {

    for (int i = 0; i < participants.length(); i++) {
      if (participants.get(i).getId().equals(id))
        return participants.get(i);
    }
    return null;
  }

  @Override
  public JsArray<Participant> getParticipants() {
    return participants;
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public long getTime() {
    return time;
  }

  @Override
  public Participant getViewer() {
    return viewer;
  }

  @Override
  public boolean isInWaveContainer() {
    return inWaveContainer;
  }

  @Override
  public boolean isPlayback() {
    return playback;
  }

  @Override
  public void log(String log) {
    GWT.log(log, null);
  }

  public void manualStateChange() {
    eventBus.fireEvent(new StateUpdateEvent(this));
  }

  @Override
  public int getMode() {
    return mode;
  }

  public void setMode(int mode) {
    this.mode = mode;
  }

}
