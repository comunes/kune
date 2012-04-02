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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.shared.utils.SimpleArgCallback;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;
import com.google.wave.api.Element;
import com.google.wave.api.Gadget;
import com.google.wave.api.Wavelet;

public class KuneWaveServiceDefaultTest extends IntegrationTest {

  private static final String MESSAGE = "testing";
  private static final String NEW_PARTICIPANT = "newparti";
  private static final String NEW_PARTICIPANT2 = "newparti2";
  private static final String RICHTEXT_MESSAGE = "<b>" + MESSAGE + "</b>";
  private static final String SOME_OTHER_PROPERTY = "someOtherProperty";
  private static final String SOME_OTHER_VALUE = "someOtherValue";
  private static final String SOME_PROPERTY = "someProperty";
  private static final String SOME_VALUE = "someValue";
  private static final String TEST_GADGET = "http://wave-api.appspot.com/public/gadgets/areyouin/gadget.xml";
  private static final String TITLE = "title";
  private static final String TITLENEW = "titleNew";
  @Inject
  KuneWaveService manager;
  @Inject
  ParticipantUtils participantUtils;

  @Test
  @Ignore
  public void addAndDelMainParticipant() throws IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        manager.addParticipants(waveletName, getSiteAdminShortName(), NEW_PARTICIPANT);

        final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertEquals(2, fetchWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
        assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
        // Del all
        manager.delParticipants(waveletName, getSiteAdminShortName(), getSiteAdminShortName());
        // This fails because we don't have a way to access to than wave now ...
        // @domain don't work neither
        final Wavelet fetchedAfterDeleted = manager.fetchWave(waveletName, "");
        assertNotNull(fetchedAfterDeleted);
      }
    });
  }

  @Test
  public void addAndDelParticipantTwice() throws IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
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
        // Del main editor
        manager.delParticipants(waveletName, getSiteAdminShortName(), NEW_PARTICIPANT, NEW_PARTICIPANT,
            getSiteAdminShortName(), getSiteAdminShortName());
      }
    });
  }

  @Test
  public void addAndRemoveParticipant() throws IOException {
    doLogin();
    final String whoDels = getSiteAdminShortName();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        manager.addParticipants(waveletName, getSiteAdminShortName(), whoDels, NEW_PARTICIPANT,
            NEW_PARTICIPANT2);
        final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertEquals(3, fetchWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT2));
        assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
        manager.delParticipants(waveletName, whoDels, NEW_PARTICIPANT, NEW_PARTICIPANT2);
        final Wavelet fetchDelWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        assertNotNull(fetchDelWavelet);
        assertEquals(1, fetchDelWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchDelWavelet, getSiteAdminShortName()));
        assertFalse(manager.isParticipant(fetchDelWavelet, NEW_PARTICIPANT));
        assertFalse(manager.isParticipant(fetchDelWavelet, NEW_PARTICIPANT2));
        manager.addParticipants(waveletName, getSiteAdminShortName(), getSiteAdminShortName(),
            NEW_PARTICIPANT);
        // Del all (the last, the whoDels...)
        manager.delParticipants(waveletName, whoDels, NEW_PARTICIPANT, getSiteAdminShortName());
      }
    });
  }

  @Test
  public void addGadget() throws DefaultException, IOException {
    doLogin();
    final URL gadgetUrl = new URL(TEST_GADGET);
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        manager.addGadget(waveletName, getSiteAdminShortName(), gadgetUrl);
      }
    });
  }

  @Test
  public void addGadgetAndCheckProperties() throws DefaultException, IOException {
    doLogin();
    final URL gadgetUrl = new URL(TEST_GADGET);
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        manager.addGadget(waveletName, getSiteAdminShortName(), gadgetUrl);
        final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        final Gadget gadget = getFirstGadget(fetchWavelet, TEST_GADGET);
        assertEquals(gadget.getUrl(), TEST_GADGET);
        assertEquals(2, gadget.getProperties().size());
        assertEquals(3, fetchWavelet.getRootBlip().getElements().size());
        final Map<String, String> newProps = new HashMap<String, String>();
        newProps.put(SOME_PROPERTY, SOME_VALUE);
        newProps.put(SOME_OTHER_PROPERTY, SOME_OTHER_VALUE);
        final int numberProp = 50;
        for (int i = 1; i < numberProp; i++) {
          newProps.put(SOME_OTHER_PROPERTY + i, SOME_OTHER_VALUE + i);
        }
        // Removing some property
        newProps.put(SOME_PROPERTY, null);
        manager.setGadgetProperty(waveletName, getSiteAdminShortName(), gadgetUrl, newProps);
        final Wavelet updatedWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        final Gadget gadgetUpdated = getFirstGadget(updatedWavelet, TEST_GADGET);
        assertEquals(gadgetUpdated.getUrl(), TEST_GADGET);
        assertEquals(3, updatedWavelet.getRootBlip().getElements().size());
        assertEquals(numberProp + 2, gadgetUpdated.getProperties().size());
        assertEquals(null, gadgetUpdated.getProperties().get(SOME_PROPERTY));
        assertEquals(SOME_OTHER_VALUE, gadgetUpdated.getProperties().get(SOME_OTHER_PROPERTY));
        for (int i = 1; i < numberProp; i++) {
          assertEquals(SOME_OTHER_VALUE + i, gadgetUpdated.getProperties().get(SOME_OTHER_PROPERTY + i));
        }
      }
    });
  }

  @Test
  public void addingToSetMaintainsOrder() throws IOException {
    final String[] array = new String[] { NEW_PARTICIPANT, NEW_PARTICIPANT, NEW_PARTICIPANT2,
        NEW_PARTICIPANT2 };
    final Set<String> set = new TreeSet<String>(Arrays.asList(array));
    assertTrue(set.size() == 2);
    final Iterator<String> iterator = set.iterator();
    assertTrue(iterator.next().equals(NEW_PARTICIPANT));
    assertTrue(iterator.next().equals(NEW_PARTICIPANT2));
  }

  private void addParticipant(final String whoAdds) throws IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        manager.addParticipants(waveletName, getSiteAdminShortName(), whoAdds, NEW_PARTICIPANT);
        final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertEquals(2, fetchWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
        assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
      }
    });
  }

  @Test
  public void authorAddParticipant() throws DefaultException, IOException {
    final String whoAdds = getSiteAdminShortName();
    addParticipant(whoAdds);
  }

  @Before
  public void before() {
    new IntegrationTestHelper(true, this);
  }

  private void createTestWave(final SimpleArgCallback<WaveRef> callback) {
    manager.createWave(TITLE, RICHTEXT_MESSAGE, callback, participantUtils.of(getSiteAdminShortName()));
  }

  @Test
  public void createWave() throws DefaultException, IOException {
    doLogin();
    manager.createWave(RICHTEXT_MESSAGE, new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getAnnotations().size() > 0);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
      }
    }, participantUtils.of(getSiteAdminShortName()));
  }

  @Test
  public void createWaveWithGadget() throws DefaultException, IOException {
    doLogin();
    manager.createWave(TITLE, RICHTEXT_MESSAGE, new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
      }
    }, new URL(TEST_GADGET), participantUtils.of(getSiteAdminShortName()));
  }

  @Test
  public void createWaveWithTitle() throws DefaultException, IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
        assertEquals(TITLE, fetchWavelet.getTitle());
      }
    });
  }

  private Gadget getFirstGadget(final Wavelet fetchWavelet, final String testGadget) {
    final Collection<Element> elements = fetchWavelet.getRootBlip().getElements().values();
    // BlipContentRefs e = fetchWavelet.getRootBlip().first(ElementType.GADGET);
    for (final Element e : elements) {
      if (e.isGadget() && e.getProperty(Gadget.URL).equals(testGadget)) {
        return (Gadget) e;
      }
    }
    return null;
  }

  @Test
  public void otherMemberAddParticipant() throws DefaultException, IOException {
    final String whoAdds = "otherMember";
    addParticipant(whoAdds);
  }

  @Test
  public void renderWave() throws DefaultException, IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(manager.render(waveletName, getSiteAdminShortName()));
      }
    });
  }

  @Test
  public void setTitle() throws DefaultException, IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveletName) {
        assertNotNull(waveletName);
        manager.setTitle(waveletName, TITLENEW, getSiteAdminShortName());
        final Wavelet fetchWavelet = manager.fetchWave(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
        assertEquals(TITLENEW, fetchWavelet.getTitle());
      }
    });
  }

}
