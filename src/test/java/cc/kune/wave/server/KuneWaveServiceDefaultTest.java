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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;

import com.google.inject.Inject;
import com.google.wave.api.Wavelet;

public class KuneWaveServiceDefaultTest extends IntegrationTest {

  private static final String MESSAGE = "testing";
  private static final String NEW_PARTICIPANT = "newparti";
  private static final String RICHTEXT_MESSAGE = "<b>" + MESSAGE + "</b>";
  private static final String TEST_GADGET = "http://wave-api.appspot.com/public/gadgets/areyouin/gadget.xml";
  private static final String TITLE = "title";
  private static final String TITLENEW = "titleNew";
  @Inject
  KuneWaveService manager;
  @Inject
  ParticipantUtils participantUtils;

  @Test
  public void addAndRemoveParticipant() throws IOException {
    doLogin();
    final String whoDels = getSiteAdminShortName();
    final WaveRef waveletName = createTestWave();
    assertNotNull(waveletName);
    manager.addParticipants(waveletName, getSiteAdminShortName(), whoDels, NEW_PARTICIPANT);
    final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchWavelet);
    assertEquals(2, fetchWavelet.getParticipants().size());
    assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
    assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
    manager.delParticipants(waveletName, whoDels, NEW_PARTICIPANT);
    final Wavelet fetchDelWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchDelWavelet);
    assertEquals(1, fetchDelWavelet.getParticipants().size());
    assertTrue(manager.isParticipant(fetchDelWavelet, getSiteAdminShortName()));
    assertFalse(manager.isParticipant(fetchDelWavelet, NEW_PARTICIPANT));
    manager.addParticipants(waveletName, getSiteAdminShortName(), whoDels, NEW_PARTICIPANT);
    // Del all
    manager.delParticipants(waveletName, whoDels, getSiteAdminShortName(), NEW_PARTICIPANT);
  }

  @Test
  public void addGadget() throws DefaultException, IOException {
    doLogin();
    final WaveRef waveletName = createTestWave();
    assertNotNull(waveletName);
    manager.addGadget(waveletName, getSiteAdminShortName(), new URL(TEST_GADGET));
  }

  private void addParticipant(final String whoAdds) throws IOException {
    doLogin();
    final WaveRef waveletName = createTestWave();
    assertNotNull(waveletName);
    manager.addParticipants(waveletName, getSiteAdminShortName(), whoAdds, NEW_PARTICIPANT);
    final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchWavelet);
    assertEquals(2, fetchWavelet.getParticipants().size());
    assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
    assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
  }

  @Test
  public void addParticipantTwice() throws IOException {
    doLogin();
    final WaveRef waveletName = createTestWave();
    assertNotNull(waveletName);
    manager.addParticipants(waveletName, getSiteAdminShortName(), getSiteAdminShortName(),
        NEW_PARTICIPANT);
    manager.addParticipants(waveletName, getSiteAdminShortName(), getSiteAdminShortName(),
        NEW_PARTICIPANT, NEW_PARTICIPANT);
    manager.addParticipants(waveletName, getSiteAdminShortName(), getSiteAdminShortName(),
        NEW_PARTICIPANT, NEW_PARTICIPANT, NEW_PARTICIPANT);
    final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchWavelet);
    assertEquals(2, fetchWavelet.getParticipants().size());
    assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
    assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
  }

  @Test
  public void authorAddParticipant() throws DefaultException, IOException {
    final String whoAdds = getSiteAdminShortName();
    addParticipant(whoAdds);
  }

  @Before
  public void before() {
    new IntegrationTestHelper(this);
  }

  private WaveRef createTestWave() {
    return manager.createWave(TITLE, RICHTEXT_MESSAGE, participantUtils.of(getSiteAdminShortName()));
  }

  @Test
  public void createWave() throws DefaultException, IOException {
    doLogin();
    final WaveRef waveletName = manager.createWave(RICHTEXT_MESSAGE,
        participantUtils.of(getSiteAdminShortName()));
    assertNotNull(waveletName);
    final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchWavelet);
    assertTrue(fetchWavelet.getRootBlip().getAnnotations().size() > 0);
    assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
  }

  @Test
  public void createWaveWithGadget() throws DefaultException, IOException {
    doLogin();
    final WaveRef waveletName = manager.createWave(TITLE, RICHTEXT_MESSAGE, new URL(TEST_GADGET),
        participantUtils.of(getSiteAdminShortName()));
    assertNotNull(waveletName);
    final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchWavelet);
    assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
  }

  @Test
  public void createWaveWithTitle() throws DefaultException, IOException {
    doLogin();
    final WaveRef waveletName = createTestWave();
    assertNotNull(waveletName);
    final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchWavelet);
    assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
    assertEquals(TITLE, fetchWavelet.getTitle());
  }

  @Test
  public void otherMemberAddParticipant() throws DefaultException, IOException {
    final String whoAdds = "otherMember";
    addParticipant(whoAdds);
  }

  @Test
  public void renderWave() throws DefaultException, IOException {
    doLogin();
    final WaveRef waveletName = createTestWave();
    assertNotNull(manager.render(waveletName, getSiteAdminShortName()));
  }

  @Test
  public void setTitle() throws DefaultException, IOException {
    doLogin();
    final WaveRef waveletName = createTestWave();
    assertNotNull(waveletName);
    manager.setTitle(waveletName, TITLENEW, getSiteAdminShortName());
    final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
    assertNotNull(fetchWavelet);
    assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
    assertEquals(TITLENEW, fetchWavelet.getTitle());
  }

}
