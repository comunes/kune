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
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Nonnull;

import org.waveprotocol.box.server.robots.agent.AbstractBaseRobotAgent;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.shared.utils.SimpleArgCallback;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.wave.api.Gadget;
import com.google.wave.api.Participants;
import com.google.wave.api.Wavelet;

@SuppressWarnings("serial")
@Singleton
public class KuneAgent extends AbstractBaseRobotAgent implements KuneWaveService {

  // private static final Logger LOG =
  // Logger.getLogger(KuneAgent.class.getName());

  private static final String NO_TITLE = "";
  public static final String ROBOT_URI = AGENT_PREFIX_URI + "/kune-agent";

  @Inject
  public KuneAgent(final Injector injector) {
    super(injector);
  }

  // public KuneAgent(final String waveDomain, final AccountStore accountStore,
  // final TokenGenerator tokenGenerator, final ServerFrontendAddressHolder
  // frontendAddressHolder) {
  // super(waveDomain, accountStore, tokenGenerator, frontendAddressHolder);
  // }

  @Override
  public void addGadget(final WaveRef waveName, final String author, final URL gadgetUrl) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean addParticipants(final WaveRef waveName, final String author, final String userWhoAdd,
      final String... newParticipants) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public WaveRef createWave(final String message, final SimpleArgCallback<WaveRef> onCreate,
      final ParticipantId... participants) {
    return createWave(NO_TITLE, message, onCreate, participants);
  }

  @Override
  public WaveRef createWave(@Nonnull final String title, final String message,
      final SimpleArgCallback<WaveRef> onCreate, @Nonnull final ParticipantId... participantsArray) {
    return createWave(title, message, onCreate, WITHOUT_GADGET, participantsArray);
  }

  @Override
  public WaveRef createWave(final String title, final String message,
      final SimpleArgCallback<WaveRef> onCreate, final String... participantsArray) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public WaveRef createWave(final String newtitle, final String body,
      final SimpleArgCallback<WaveRef> simpleArgCallback, final URL gadgetUrl,
      final Map<String, String> gadgetProperties, final ParticipantId... participantsArray) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public WaveRef createWave(final String title, final String message,
      final SimpleArgCallback<WaveRef> onCreate, final URL gadgetUrl,
      final ParticipantId... participantsArray) {
    // super.newWave(getWaveDomain(), participantsArray);
    final HashSet<String> parts = new HashSet<String>();
    for (final ParticipantId part : participantsArray) {
      parts.add(part.getAddress());
    }
    // final Wavelet wave = newWave(getWaveDomain(), parts);
    return null;
  }

  @Override
  public WaveRef createWave(final String title, final String message, final WaveRef waveIdToCopy,
      final SimpleArgCallback<WaveRef> onCreate, final URL gadgetUrl,
      final Map<String, String> gadgetProperties, final ParticipantId... participantsArray) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public WaveRef createWave(final String title, final String message, final WaveRef waveIdToCopy,
      final SimpleArgCallback<WaveRef> onCreate, final URL gadgetUrl,
      final ParticipantId... participantsArray) {
    return null;
  }

  @Override
  public void delParticipants(final WaveRef waveName, final String whoDel, final String... participants) {
    // TODO Auto-generated method stub

  }

  @Override
  public Wavelet fetchWave(final WaveId waveId, final WaveletId waveletId, final String author) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Wavelet fetchWave(final WaveRef waveRef, final String author) {
    // // Preconditions.checkNotNull(author);
    // RobotAccountData account = null;
    // final String rpcUrl = "http://" + getFrontEndAddress() + "/robot/rpc";
    // try {
    // account = getAccountStore().getAccount(
    // ParticipantId.ofUnsafe(getRobotId() + "@" + getWaveDomain())).asRobot();
    // } catch (final PersistenceException e) {
    // LOG.log(Level.WARNING, "Cannot fetch account data for robot id: " +
    // getRobotId(), e);
    // }
    // if (account != null) {
    // setupOAuth(account.getId().getAddress(), account.getConsumerSecret(),
    // rpcUrl);
    // try {
    // return super.fetchWavelet(waveRef.getWaveId(), waveRef.getWaveletId(),
    // "http://"
    // + getFrontEndAddress() + "/robot/rpc");
    // } catch (final IOException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }
    return null;
  }

  @Override
  public Gadget getGadget(final WaveRef waveletName, final String author, final URL gadgetUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Participants getParticipants(final WaveRef waveref, final String author) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getRobotId() {
    return "kune-agent";
  }

  @Override
  protected String getRobotName() {
    return "Kune Agent";
  }

  @Override
  public String getRobotUri() {
    return ROBOT_URI;
  }

  @Override
  public String getTitle(final WaveRef waveName, final String author) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isParticipant(final Wavelet wavelet, final String user) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String render(final Wavelet wavelet) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String render(final WaveRef waveRef, final String author) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setGadgetProperty(final WaveRef waveletName, final String author, final URL gadgetUrl,
      final Map<String, String> newProperties) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setTitle(final WaveRef waveName, final String title, final String author) {
    // TODO Auto-generated method stub

  }

}
