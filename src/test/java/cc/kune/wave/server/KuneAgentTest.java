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
package cc.kune.wave.server;

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

import cc.kune.wave.server.kspecific.KuneAgent;

import com.google.common.collect.Lists;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneAgentTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneAgentTest {
  // private static final String MESSAGE = "testing";
  // private static final String NEW_PARTICIPANT = "newparti";
  // private static final String RICHTEXT_MESSAGE = "<b>" + MESSAGE + "</b>";
  // private static final String TEST_GADGET =
  // "http://wave-api.appspot.com/public/gadgets/areyouin/gadget.xml";
  // private static final String TITLE = "title";
  // private static final String TITLENEW = "titleNew";

  /** The manager. */
  KuneAgent manager;

  /**
   * Before.
   * 
   * @throws PersistenceException
   *           the persistence exception
   * @throws InvalidParticipantAddress
   *           the invalid participant address
   */
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

  /**
   * Test basic creation.
   * 
   * @throws InvalidParticipantAddress
   *           the invalid participant address
   */
  @Ignore
  @Test
  public void testBasicCreation() throws InvalidParticipantAddress {
    // final WaveRef waveletName = manager.createWave(RICHTEXT_MESSAGE, null,
    // ParticipantId.of(getSiteAdminShortName()));
    // assertNotNull(waveletName);
    // final Wavelet fetchWavelet = manager.fetchWave(waveletName,
    // getSiteAdminShortName());
    // assertNotNull(fetchWavelet);
    // assertTrue(fetchWavelet.getRootBlip().getAnnotations().size() > 0);
    // assertEquals("", fetchWavelet.getRootBlip().getContent());
    // assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
  }
}
