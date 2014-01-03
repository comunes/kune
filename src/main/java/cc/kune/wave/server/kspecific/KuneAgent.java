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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

// TODO: Auto-generated Javadoc
/**
 * The Class KuneAgent.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@SuppressWarnings("serial")
@Singleton
public class KuneAgent extends AbstractBaseRobotAgent implements KuneWaveService {

  // private static final Logger LOG =
  // Logger.getLogger(KuneAgent.class.getName());

  /** The Constant NO_TITLE. */
  private static final String NO_TITLE = "";
  
  /** The Constant ROBOT_URI. */
  public static final String ROBOT_URI = AGENT_PREFIX_URI + "/kune-agent";

  /**
   * Instantiates a new kune agent.
   *
   * @param injector the injector
   */
  @Inject
  public KuneAgent(final Injector injector) {
    super(injector);
  }

  // public KuneAgent(final String waveDomain, final AccountStore accountStore,
  // final TokenGenerator tokenGenerator, final ServerFrontendAddressHolder
  // frontendAddressHolder) {
  // super(waveDomain, accountStore, tokenGenerator, frontendAddressHolder);
  // }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#addGadget(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String, java.net.URL)
   */
  @Override
  public void addGadget(final WaveRef waveName, final String author, final URL gadgetUrl) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#addParticipants(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String, java.lang.String, java.lang.String[])
   */
  @Override
  public boolean addParticipants(final WaveRef waveName, final String author, final String userWhoAdd,
      final String... newParticipants) {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#createWave(java.lang.String, cc.kune.common.shared.utils.SimpleArgCallback, org.waveprotocol.wave.model.wave.ParticipantId[])
   */
  @Override
  public WaveRef createWave(final String message, final SimpleArgCallback<WaveRef> onCreate,
      final ParticipantId... participants) {
    return createWave(NO_TITLE, message, onCreate, participants);
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#createWave(java.lang.String, java.lang.String, cc.kune.common.shared.utils.SimpleArgCallback, org.waveprotocol.wave.model.wave.ParticipantId[])
   */
  @Override
  public WaveRef createWave(@Nonnull final String title, final String message,
      final SimpleArgCallback<WaveRef> onCreate, @Nonnull final ParticipantId... participantsArray) {
    return createWave(title, message, onCreate, WITHOUT_GADGET, participantsArray);
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#createWave(java.lang.String, java.lang.String, cc.kune.common.shared.utils.SimpleArgCallback, java.lang.String[])
   */
  @Override
  public WaveRef createWave(final String title, final String message,
      final SimpleArgCallback<WaveRef> onCreate, final String... participantsArray) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#createWave(java.lang.String, java.lang.String, cc.kune.common.shared.utils.SimpleArgCallback, java.net.URL, java.util.Map, org.waveprotocol.wave.model.wave.ParticipantId[])
   */
  @Override
  public WaveRef createWave(final String newtitle, final String body,
      final SimpleArgCallback<WaveRef> simpleArgCallback, final URL gadgetUrl,
      final Map<String, String> gadgetProperties, final ParticipantId... participantsArray) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#createWave(java.lang.String, java.lang.String, cc.kune.common.shared.utils.SimpleArgCallback, java.net.URL, org.waveprotocol.wave.model.wave.ParticipantId[])
   */
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

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#createWave(java.lang.String, java.lang.String, org.waveprotocol.wave.model.waveref.WaveRef, cc.kune.common.shared.utils.SimpleArgCallback, java.net.URL, java.util.Map, org.waveprotocol.wave.model.wave.ParticipantId[])
   */
  @Override
  public WaveRef createWave(final String title, final String message, final WaveRef waveIdToCopy,
      final SimpleArgCallback<WaveRef> onCreate, final URL gadgetUrl,
      final Map<String, String> gadgetProperties, final ParticipantId... participantsArray) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#createWave(java.lang.String, java.lang.String, org.waveprotocol.wave.model.waveref.WaveRef, cc.kune.common.shared.utils.SimpleArgCallback, java.net.URL, org.waveprotocol.wave.model.wave.ParticipantId[])
   */
  @Override
  public WaveRef createWave(final String title, final String message, final WaveRef waveIdToCopy,
      final SimpleArgCallback<WaveRef> onCreate, final URL gadgetUrl,
      final ParticipantId... participantsArray) {
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#delParticipants(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String, java.lang.String[])
   */
  @Override
  public boolean delParticipants(final WaveRef waveName, final String whoDel,
      final Set<String> participants) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean delParticipants(final WaveRef waveName, final String whoDel,
      final String... participants) {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#fetchWave(org.waveprotocol.wave.model.id.WaveId, org.waveprotocol.wave.model.id.WaveletId, java.lang.String)
   */
  @Override
  public Wavelet fetchWave(final WaveId waveId, final WaveletId waveletId, final String author) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#fetchWave(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String)
   */
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

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#getGadget(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String, java.net.URL)
   */
  @Override
  public Gadget getGadget(final WaveRef waveletName, final String author, final URL gadgetUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#getParticipants(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String)
   */
  @Override
  public Participants getParticipants(final WaveRef waveref, final String author) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.box.server.robots.agent.AbstractBaseRobotAgent#getRobotId()
   */
  @Override
  public String getRobotId() {
    return "kune-agent";
  }

  /* (non-Javadoc)
   * @see com.google.wave.api.AbstractRobot#getRobotName()
   */
  @Override
  protected String getRobotName() {
    return "Kune Agent";
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.box.server.robots.agent.AbstractBaseRobotAgent#getRobotUri()
   */
  @Override
  public String getRobotUri() {
    return ROBOT_URI;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#getTitle(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String)
   */
  @Override
  public String getTitle(final WaveRef waveName, final String author) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#isParticipant(com.google.wave.api.Wavelet, java.lang.String)
   */
  @Override
  public boolean isParticipant(final Wavelet wavelet, final String user) {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#render(com.google.wave.api.Wavelet)
   */
  @Override
  public String render(final Wavelet wavelet) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#render(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String)
   */
  @Override
  public String render(final WaveRef waveRef, final String author) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#setGadgetProperty(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String, java.net.URL, java.util.Map)
   */
  @Override
  public void setGadgetProperty(final WaveRef waveletName, final String author, final URL gadgetUrl,
      final Map<String, String> newProperties) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see cc.kune.wave.server.kspecific.KuneWaveService#setTitle(org.waveprotocol.wave.model.waveref.WaveRef, java.lang.String, java.lang.String)
   */
  @Override
  public void setTitle(final WaveRef waveName, final String title, final String author) {
    // TODO Auto-generated method stub

  }

}
