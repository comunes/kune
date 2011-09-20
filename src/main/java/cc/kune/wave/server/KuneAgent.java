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
import java.util.HashSet;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import org.waveprotocol.box.server.persistence.AccountStore;
import org.waveprotocol.wave.model.id.TokenGenerator;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.wave.api.Wavelet;

@SuppressWarnings("serial")
@Singleton
public class KuneAgent extends AbstractBaseRobotAgent implements KuneWaveManager {

  private static final Logger LOG = Logger.getLogger(KuneAgent.class.getName());

  private static final String NO_TITLE = "";
  public static final String ROBOT_URI = AGENT_PREFIX_URI + "/kune-agent";

  @Inject
  public KuneAgent(final Injector injector) {
    super(injector);
  }

  public KuneAgent(final String waveDomain, final AccountStore accountStore,
      final TokenGenerator tokenGenerator, final ServerFrontendAddressHolder frontendAddressHolder) {
    super(waveDomain, accountStore, tokenGenerator, frontendAddressHolder);
  }

  @Override
  public void addGadget(final WaveRef waveName, final String author, final String gadgetUrl) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addParticipants(final WaveRef waveName, final String author, final String userWhoAdd,
      final String... newParticipants) {
    // TODO Auto-generated method stub

  }

  @Override
  public WaveRef createWave(final String message, final ParticipantId... participants) {
    return createWave(NO_TITLE, message, participants);
  }

  @Override
  public WaveRef createWave(@Nonnull final String title, final String message,
      @Nonnull final ParticipantId... participantsArray) {
    return createWave(title, message, WITHOUT_GADGET, participantsArray);
  }

  @Override
  public WaveRef createWave(final String title, final String message, final String waveIdToCopy,
      final URL gadgetUrl, final ParticipantId... participantsArray) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public WaveRef createWave(final String title, final String message, final URL gadgetUrl,
      final ParticipantId... participantsArray) {
    // super.newWave(getWaveDomain(), participantsArray);
    final HashSet<String> parts = new HashSet<String>();
    for (final ParticipantId part : participantsArray) {
      parts.add(part.getAddress());
    }
    final Wavelet wave = newWave(getWaveDomain(), parts);
    return WaveRef.of(wave.getWaveId(), wave.getWaveletId());
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
  public void setTitle(final WaveRef waveName, final String title, final String author) {
    // TODO Auto-generated method stub

  }

}
