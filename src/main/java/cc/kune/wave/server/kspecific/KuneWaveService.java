/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.shared.utils.SimpleArgCallback;

import com.google.wave.api.Gadget;
import com.google.wave.api.Participants;
import com.google.wave.api.Wavelet;

// TODO: Auto-generated Javadoc
/**
 * The Interface KuneWaveService.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface KuneWaveService {
  
  /** The Constant DO_NOTHING_CBACK. */
  public static final SimpleArgCallback<WaveRef> DO_NOTHING_CBACK = new SimpleArgCallback<WaveRef>() {
    @Override
    public void onCallback(final WaveRef arg) {
      // Do nothing
    }
  };
  
  /** The Constant NO_MESSAGE. */
  public static final String NO_MESSAGE = "";
  
  /** The Constant NO_TITLE. */
  public static final String NO_TITLE = "";
  
  /** The Constant NO_WAVE_TO_COPY. */
  public static final WaveRef NO_WAVE_TO_COPY = null;
  
  /** The Constant WITHOUT_GADGET. */
  public static final URL WITHOUT_GADGET = null;

  /**
   * Adds the gadget.
   *
   * @param waveName the wave name
   * @param author the author
   * @param gadgetUrl the gadget url
   */
  void addGadget(WaveRef waveName, String author, URL gadgetUrl);

  /**
   * Adds the participants.
   *
   * @param waveName the wave name
   * @param author the author
   * @param userWhoAdd the user who add
   * @param newParticipants the new participants
   * @return true, if successful
   */
  boolean addParticipants(WaveRef waveName, String author, String userWhoAdd, String... newParticipants);

  /**
   * Creates the wave.
   *
   * @param message the message
   * @param onCreate the on create
   * @param participants the participants
   * @return the wave ref
   */
  WaveRef createWave(String message, SimpleArgCallback<WaveRef> onCreate, ParticipantId... participants);

  /**
   * Creates the wave.
   *
   * @param title the title
   * @param message the message
   * @param onCreate the on create
   * @param participantsArray the participants array
   * @return the wave ref
   */
  WaveRef createWave(String title, String message, SimpleArgCallback<WaveRef> onCreate,
      ParticipantId... participantsArray);

  /**
   * Creates the wave.
   *
   * @param title the title
   * @param message the message
   * @param onCreate the on create
   * @param participantsArray the participants array
   * @return the wave ref
   */
  WaveRef createWave(String title, String message, SimpleArgCallback<WaveRef> onCreate,
      String... participantsArray);

  /**
   * Creates the wave.
   *
   * @param newtitle the newtitle
   * @param body the body
   * @param simpleArgCallback the simple arg callback
   * @param gadgetUrl the gadget url
   * @param gadgetProperties the gadget properties
   * @param participantsArray the participants array
   * @return the wave ref
   */
  WaveRef createWave(String newtitle, String body, SimpleArgCallback<WaveRef> simpleArgCallback,
      URL gadgetUrl, Map<String, String> gadgetProperties, ParticipantId... participantsArray);

  /**
   * Creates the wave.
   *
   * @param title the title
   * @param message the message
   * @param onCreate the on create
   * @param gadgetUrl the gadget url
   * @param participantsArray the participants array
   * @return the wave ref
   */
  WaveRef createWave(String title, String message, SimpleArgCallback<WaveRef> onCreate, URL gadgetUrl,
      ParticipantId... participantsArray);

  /**
   * Creates the wave.
   *
   * @param title the title
   * @param message the message
   * @param waveIdToCopy the wave id to copy
   * @param onCreate the on create
   * @param gadgetUrl the gadget url
   * @param gadgetProperties the gadget properties
   * @param participantsArray the participants array
   * @return the wave ref
   */
  WaveRef createWave(String title, String message, WaveRef waveIdToCopy,
      SimpleArgCallback<WaveRef> onCreate, URL gadgetUrl, Map<String, String> gadgetProperties,
      ParticipantId... participantsArray);

  /**
   * Creates the wave.
   *
   * @param title the title
   * @param message the message
   * @param waveIdToCopy the wave id to copy
   * @param onCreate the on create
   * @param gadgetUrl the gadget url
   * @param participantsArray the participants array
   * @return the wave ref
   */
  WaveRef createWave(String title, String message, WaveRef waveIdToCopy,
      SimpleArgCallback<WaveRef> onCreate, URL gadgetUrl, ParticipantId... participantsArray);

  /**
   * Del participants.
   *
   * @param waveName the wave name
   * @param whoDel the who del
   * @param participants the participants
   */
  void delParticipants(WaveRef waveName, String whoDel, String... participants);

  /**
   * Fetch wave.
   *
   * @param waveId the wave id
   * @param waveletId the wavelet id
   * @param author the author
   * @return the wavelet
   */
  Wavelet fetchWave(WaveId waveId, WaveletId waveletId, String author);

  /**
   * Fetch wave.
   *
   * @param waveRef the wave ref
   * @param author the author
   * @return the wavelet
   */
  Wavelet fetchWave(WaveRef waveRef, String author);

  /**
   * Gets the gadget.
   *
   * @param waveletName the wavelet name
   * @param author the author
   * @param gadgetUrl the gadget url
   * @return the gadget
   */
  Gadget getGadget(WaveRef waveletName, String author, URL gadgetUrl);

  /**
   * Gets the participants.
   *
   * @param waveref the waveref
   * @param author the author
   * @return the participants
   */
  Participants getParticipants(WaveRef waveref, String author);

  /**
   * Gets the title.
   *
   * @param waveName the wave name
   * @param author the author
   * @return the title
   */
  String getTitle(WaveRef waveName, String author);

  /**
   * Checks if is participant.
   *
   * @param wavelet the wavelet
   * @param user the user
   * @return true, if is participant
   */
  boolean isParticipant(Wavelet wavelet, String user);

  /**
   * Render.
   *
   * @param wavelet the wavelet
   * @return the string
   */
  String render(Wavelet wavelet);

  /**
   * Render.
   *
   * @param waveRef the wave ref
   * @param author the author
   * @return the string
   */
  String render(WaveRef waveRef, String author);

  /**
   * Sets the gadget property.
   *
   * @param waveletName the wavelet name
   * @param author the author
   * @param gadgetUrl the gadget url
   * @param newProperties the new properties
   */
  void setGadgetProperty(WaveRef waveletName, String author, URL gadgetUrl,
      Map<String, String> newProperties);

  /**
   * Sets the title.
   *
   * @param waveName the wave name
   * @param title the title
   * @param author the author
   */
  void setTitle(WaveRef waveName, String title, String author);

}
