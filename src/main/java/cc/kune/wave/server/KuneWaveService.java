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
package cc.kune.wave.server;

import java.net.URL;

import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import com.google.wave.api.Wavelet;

public interface KuneWaveService {
  public static final String NO_MESSAGE = "";
  public static final String NO_TITLE = "";
  public static final URL WITHOUT_GADGET = null;

  void addGadget(WaveRef waveName, String author, String gadgetUrl);

  void addParticipants(WaveRef waveName, String author, String userWhoAdd, String... newParticipants);

  WaveRef createWave(String message, ParticipantId... participants);

  WaveRef createWave(String title, String message, ParticipantId... participantsArray);

  WaveRef createWave(String title, String message, String waveIdToCopy, URL gadgetUrl,
      ParticipantId... participantsArray);

  WaveRef createWave(String title, String message, URL gadgetUrl, ParticipantId... participantsArray);

  void delParticipants(WaveRef waveName, String whoDel, String... participants);

  Wavelet fetchWave(WaveId waveId, WaveletId waveletId, String author);

  Wavelet fetchWave(WaveRef waveRef, String author);

  boolean isParticipant(Wavelet wavelet, String user);

  String render(Wavelet wavelet);

  String render(WaveRef waveRef, String author);

  void setTitle(WaveRef waveName, String title, String author);

}
