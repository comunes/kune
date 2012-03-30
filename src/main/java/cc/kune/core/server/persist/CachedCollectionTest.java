/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.core.server.persist;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CachedCollectionTest {

  private CachedCollection<String, String> cache;

  @Before
  public void before() {
    cache = new CachedCollection<String, String>(2);
  }

  @Test
  public void testBasicSize() {
    assertTrue(cache.size() == 0);
    final String first = "1";
    cache.put(first, "1b");
    final String snd = "2";
    cache.put(snd, "2b");
    final String third = "3";
    cache.put(third, "3b");
    assertNull(cache.get(first));
    assertNotNull(cache.get(snd));
    assertNotNull(cache.get(third));
    assertTrue(cache.size() == 2);
  }

}
