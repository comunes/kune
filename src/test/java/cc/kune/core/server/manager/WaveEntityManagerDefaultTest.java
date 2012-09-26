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
package cc.kune.core.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityExistsException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.PersistenceTest;
import cc.kune.domain.WaveEntity;
import cc.kune.domain.WaveRefKey;
import cc.kune.domain.finders.WaveEntityFinder;

import com.google.inject.Inject;

public class WaveEntityManagerDefaultTest extends PersistenceTest {

  private static final String DOMAIN = "domain";
  private static final String WAVE_ID = "waveId";
  private static final String WAVELET_ID = "waveletId";
  @Inject
  WaveEntityFinder finder;
  private WaveEntity wave;
  @Inject
  WaveEntityManager waveEntityManager;

  @After
  public void close() {
    if (getTransaction().isActive()) {
      getTransaction().rollback();
    }
  }

  @Test(expected = EntityExistsException.class)
  public void donAllowDuplicates() {
    final WaveEntity wave2 = new WaveEntity(DOMAIN, WAVE_ID, WAVELET_ID, 0L);
    waveEntityManager.persist(wave2);
  }

  @Before
  public void insertData() {
    openTransaction();
    assertTrue(finder.count() == 0);
    wave = new WaveEntity(DOMAIN, WAVE_ID, WAVELET_ID, 0L);
    waveEntityManager.persist(wave);
    wave.setLastModifiedTime(1L);
    waveEntityManager.merge(wave);
    assertTrue(finder.count() == 1);
  }

  @Test
  public void testWaveRetrieve() {
    closeTransaction();
    final WaveEntity waveRetrieved = waveEntityManager.find(WaveRefKey.of(DOMAIN, WAVE_ID, WAVELET_ID));
    assertNotNull(waveRetrieved);
    assertEquals(waveRetrieved.getDomain(), DOMAIN);
    assertEquals(waveRetrieved.getWaveId(), WAVE_ID);
    assertEquals(waveRetrieved.getWaveletId(), WAVELET_ID);
    assertEquals(1L, (long) waveRetrieved.getLastModifiedTime());
  }
}
