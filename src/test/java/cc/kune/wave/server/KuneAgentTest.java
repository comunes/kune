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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.waveprotocol.box.server.account.AccountData;
import org.waveprotocol.box.server.account.RobotAccountData;
import org.waveprotocol.box.server.persistence.AccountStore;
import org.waveprotocol.box.server.persistence.PersistenceException;
import org.waveprotocol.box.server.robots.agent.AbstractBaseRobotAgent.ServerFrontendAddressHolder;
import org.waveprotocol.wave.model.id.TokenGenerator;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.wave.server.kspecific.KuneAgent;

import com.google.common.collect.Lists;
import com.google.wave.api.Wavelet;

public class KuneAgentTest {
  private static final String MESSAGE = "testing";
  private static final String NEW_PARTICIPANT = "newparti";
  private static final String RICHTEXT_MESSAGE = "<b>" + MESSAGE + "</b>";
  private static final String TEST_GADGET = "http://wave-api.appspot.com/public/gadgets/areyouin/gadget.xml";
  private static final String TITLE = "title";
  private static final String TITLENEW = "titleNew";

  KuneAgent manager;

  @Before
  public void before() throws PersistenceException, InvalidParticipantAddress {
    final ServerFrontendAddressHolder frontendAddressHolder = Mockito.mock(ServerFrontendAddressHolder.class);
    Mockito.when(frontendAddressHolder.getAddresses()).thenReturn(Lists.newArrayList("localhost:9898"));
    final TokenGenerator tokenGenerator = Mockito.mock(TokenGenerator.class);
    Mockito.when(tokenGenerator.generateToken(Mockito.anyInt())).thenReturn("abcde");
    final AccountStore accountStore = Mockito.mock(AccountStore.class);
    final AccountData accountData = Mockito.mock(AccountData.class);
    final RobotAccountData accountRobotData = Mockito.mock(RobotAccountData.class);
    final ParticipantId participantId = ParticipantId.of("kune-agent@example.com");

    Mockito.when(accountStore.getAccount((ParticipantId) Mockito.anyObject())).thenReturn(accountData);
    Mockito.when(accountData.asRobot()).thenReturn(accountRobotData);
    Mockito.when(accountRobotData.getUrl()).thenReturn(KuneAgent.ROBOT_URI);
    Mockito.when(accountRobotData.getId()).thenReturn(participantId);
    Mockito.when(accountRobotData.getConsumerSecret()).thenReturn("someconsumer");
    // manager = new KuneAgent("example.com", accountStore, tokenGenerator,
    // frontendAddressHolder);
  }

  private String getSiteAdminShortName() {
    // TODO Auto-generated method stub

    return "admin@example.com";
  }

  @Ignore
  @Test
  public void testBasicCreation() throws InvalidParticipantAddress {
    final WaveRef waveletName = manager.createWave(RICHTEXT_MESSAGE,
        ParticipantId.of(getSiteAdminShortName()));
    assertNotNull(waveletName);
    final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchWavelet);
    assertTrue(fetchWavelet.getRootBlip().getAnnotations().size() > 0);
    assertEquals("", fetchWavelet.getRootBlip().getContent());
    assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
  }
}
