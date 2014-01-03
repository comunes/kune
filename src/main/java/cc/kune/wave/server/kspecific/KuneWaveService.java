/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.wave.server.kspecific;

import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.shared.utils.SimpleArgCallback;

import com.google.wave.api.Gadget;
import com.google.wave.api.Participants;
import com.google.wave.api.Wavelet;

public interface KuneWaveService {
  public static final SimpleArgCallback<WaveRef> DO_NOTHING_CBACK = new SimpleArgCallback<WaveRef>() {
    @Override
    public void onCallback(final WaveRef arg) {
      // Do nothing
    }
  };
  public static final String NO_MESSAGE = "";
  public static final String NO_TITLE = "";
  public static final WaveRef NO_WAVE_TO_COPY = null;
  public static final URL WITHOUT_GADGET = null;

  void addGadget(WaveRef waveName, String author, URL gadgetUrl);

  boolean addParticipants(WaveRef waveName, String author, String userWhoAdd, String... newParticipants);

  WaveRef createWave(String message, SimpleArgCallback<WaveRef> onCreate, ParticipantId... participants);

  WaveRef createWave(String title, String message, SimpleArgCallback<WaveRef> onCreate,
      ParticipantId... participantsArray);

  WaveRef createWave(String title, String message, SimpleArgCallback<WaveRef> onCreate,
      String... participantsArray);

  WaveRef createWave(String newtitle, String body, SimpleArgCallback<WaveRef> simpleArgCallback,
      URL gadgetUrl, Map<String, String> gadgetProperties, ParticipantId... participantsArray);

  WaveRef createWave(String title, String message, SimpleArgCallback<WaveRef> onCreate, URL gadgetUrl,
      ParticipantId... participantsArray);

  WaveRef createWave(String title, String message, WaveRef waveIdToCopy,
      SimpleArgCallback<WaveRef> onCreate, URL gadgetUrl, Map<String, String> gadgetProperties,
      ParticipantId... participantsArray);

  WaveRef createWave(String title, String message, WaveRef waveIdToCopy,
      SimpleArgCallback<WaveRef> onCreate, URL gadgetUrl, ParticipantId... participantsArray);

  boolean delParticipants(WaveRef waveName, String whoDel, Set<String> participants);

  boolean delParticipants(WaveRef waveName, String whoDel, String... participants);

  Wavelet fetchWave(WaveId waveId, WaveletId waveletId, String author);

  Wavelet fetchWave(WaveRef waveRef, String author);

  Gadget getGadget(WaveRef waveletName, String author, URL gadgetUrl);

  Participants getParticipants(WaveRef waveref, String author);

  String getTitle(WaveRef waveName, String author);

  boolean isParticipant(Wavelet wavelet, String user);

  String render(Wavelet wavelet);

  String render(WaveRef waveRef, String author);

  void setGadgetProperty(WaveRef waveletName, String author, URL gadgetUrl,
      Map<String, String> newProperties);

  void setTitle(WaveRef waveName, String title, String author);

}
