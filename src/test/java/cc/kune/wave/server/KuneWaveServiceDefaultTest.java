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

import static org.junit.Assert.*;

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
import cc.kune.wave.server.kspecific.ParticipantUtils;

import com.google.inject.Inject;
import com.google.wave.api.Element;
import com.google.wave.api.Gadget;
import com.google.wave.api.Wavelet;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneWaveServiceDefaultTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneWaveServiceDefaultTest extends IntegrationTest {

  /** The Constant MESSAGE. */
  private static final String MESSAGE = "testing";

  /** The Constant NEW_PARTICIPANT. */
  private static final String NEW_PARTICIPANT = "newparti";

  /** The Constant NEW_PARTICIPANT2. */
  private static final String NEW_PARTICIPANT2 = "newparti2";

  /** The Constant RICHTEXT_MESSAGE. */
  private static final String RICHTEXT_MESSAGE = "<b>" + MESSAGE + "</b>";

  /** The Constant SOME_OTHER_PROPERTY. */
  private static final String SOME_OTHER_PROPERTY = "someOtherProperty";

  /** The Constant SOME_OTHER_VALUE. */
  private static final String SOME_OTHER_VALUE = "someOtherValue";

  /** The Constant SOME_PROPERTY. */
  private static final String SOME_PROPERTY = "someProperty";

  /** The Constant SOME_VALUE. */
  private static final String SOME_VALUE = "someValue";

  /** The Constant TEST_GADGET. */
  private static final String TEST_GADGET = "http://wave-api.appspot.com/public/gadgets/areyouin/gadget.xml";

  /** The Constant TITLE. */
  private static final String TITLE = "title";

  /** The Constant TITLENEW. */
  private static final String TITLENEW = "titleNew";

  /** The manager. */
  @Inject
  KuneWaveService manager;

  /** The participant utils. */
  @Inject
  ParticipantUtils participantUtils;

  /**
   * Adds the and del main participant.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  @Ignore
  public void addAndDelMainParticipant() throws IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveRef) {
        assertNotNull(waveRef);
        manager.addParticipants(waveRef, getSiteAdminShortName(), NEW_PARTICIPANT);

        final Wavelet fetchWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertEquals(2, fetchWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
        assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
        // Del all
        manager.delParticipants(waveRef, getSiteAdminShortName(), getSiteAdminShortName());
        // This fails because we don't have a way to access to than wave now ...
        // @domain don't work neither
        final Wavelet fetchedAfterDeleted = manager.fetchWave(waveRef, "");
        assertNotNull(fetchedAfterDeleted);
      }
    });
  }

  /**
   * Adds the and del participant twice.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void addAndDelParticipantTwice() throws IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveRef) {
        assertNotNull(waveRef);
        manager.addParticipants(waveRef, getSiteAdminShortName(), getSiteAdminShortName(),
            NEW_PARTICIPANT);
        manager.addParticipants(waveRef, getSiteAdminShortName(), getSiteAdminShortName(),
            NEW_PARTICIPANT, NEW_PARTICIPANT);
        manager.addParticipants(waveRef, getSiteAdminShortName(), getSiteAdminShortName(),
            NEW_PARTICIPANT, NEW_PARTICIPANT, NEW_PARTICIPANT);
        final Wavelet fetchWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertEquals(2, fetchWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
        assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
        // Del main editor
        manager.delParticipants(waveRef, getSiteAdminShortName(), NEW_PARTICIPANT, NEW_PARTICIPANT,
            getSiteAdminShortName(), getSiteAdminShortName());
      }
    });
  }

  /**
   * Adds the and remove participant.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void addAndRemoveParticipant() throws IOException {
    doLogin();
    final String whoDels = getSiteAdminShortName();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveRef) {
        assertNotNull(waveRef);
        manager.addParticipants(waveRef, getSiteAdminShortName(), whoDels, NEW_PARTICIPANT,
            NEW_PARTICIPANT2);
        final Wavelet fetchWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertEquals(3, fetchWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT2));
        assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
        manager.delParticipants(waveRef, whoDels, NEW_PARTICIPANT, NEW_PARTICIPANT2);
        final Wavelet fetchDelWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
        assertNotNull(fetchDelWavelet);
        assertEquals(1, fetchDelWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchDelWavelet, getSiteAdminShortName()));
        assertFalse(manager.isParticipant(fetchDelWavelet, NEW_PARTICIPANT));
        assertFalse(manager.isParticipant(fetchDelWavelet, NEW_PARTICIPANT2));
        manager.addParticipants(waveRef, getSiteAdminShortName(), getSiteAdminShortName(),
            NEW_PARTICIPANT);
        // Del all (the last, the whoDels...)
        manager.delParticipants(waveRef, whoDels, NEW_PARTICIPANT, getSiteAdminShortName());
      }
    });
  }

  /**
   * Adds the gadget.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Adds the gadget and check properties.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void addGadgetAndCheckProperties() throws DefaultException, IOException {
    doLogin();
    final URL gadgetUrl = new URL(TEST_GADGET);
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveRef) {
        assertNotNull(waveRef);
        manager.addGadget(waveRef, getSiteAdminShortName(), gadgetUrl);
        final Wavelet fetchWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
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
        manager.setGadgetProperty(waveRef, getSiteAdminShortName(), gadgetUrl, newProps);
        final Wavelet updatedWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
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

  /**
   * Adding to set maintains order.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Adds the participant.
   * 
   * @param whoAdds
   *          the who adds
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  private void addParticipant(final String whoAdds) throws IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveRef) {
        assertNotNull(waveRef);
        manager.addParticipants(waveRef, getSiteAdminShortName(), whoAdds, NEW_PARTICIPANT);
        final Wavelet fetchWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertEquals(2, fetchWavelet.getParticipants().size());
        assertTrue(manager.isParticipant(fetchWavelet, NEW_PARTICIPANT));
        assertTrue(manager.isParticipant(fetchWavelet, getSiteAdminShortName()));
      }
    });
  }

  /**
   * Author add participant.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void authorAddParticipant() throws DefaultException, IOException {
    final String whoAdds = getSiteAdminShortName();
    addParticipant(whoAdds);
  }

  /**
   * Before.
   */
  @Before
  public void before() {
    new IntegrationTestHelper(true, this);
  }

  /**
   * Creates the test wave.
   * 
   * @param callback
   *          the callback
   */
  private void createTestWave(final SimpleArgCallback<WaveRef> callback) {
    manager.createWave(TITLE, RICHTEXT_MESSAGE, callback, participantUtils.of(getSiteAdminShortName()));
  }

  /**
   * Creates the wave.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void createWave() throws DefaultException, IOException {
    doLogin();
    manager.createWave(RICHTEXT_MESSAGE, new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveRef) {
        assertNotNull(waveRef);
        final Wavelet fetchWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getAnnotations().size() > 0);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
      }
    }, participantUtils.of(getSiteAdminShortName()));
  }

  /**
   * Creates the wave with gadget.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Creates the wave with title.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void createWaveWithTitle() throws DefaultException, IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveRef) {
        assertNotNull(waveRef);
        final Wavelet fetchWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
        assertEquals(TITLE, fetchWavelet.getTitle());
      }
    });
  }

  /**
   * Gets the first gadget.
   * 
   * @param fetchWavelet
   *          the fetch wavelet
   * @param testGadget
   *          the test gadget
   * @return the first gadget
   */
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

  /**
   * Other member add participant.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void otherMemberAddParticipant() throws DefaultException, IOException {
    final String whoAdds = "otherMember";
    addParticipant(whoAdds);
  }

  /**
   * Render wave.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Sets the title.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void setTitle() throws DefaultException, IOException {
    doLogin();
    createTestWave(new SimpleArgCallback<WaveRef>() {
      @Override
      public void onCallback(final WaveRef waveRef) {
        assertNotNull(waveRef);
        manager.setTitle(waveRef, TITLENEW, getSiteAdminShortName());
        final Wavelet fetchWavelet = manager.fetchWave(waveRef, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
        assertEquals(TITLENEW, fetchWavelet.getTitle());
      }
    });
  }

}
