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

package cc.kune.events.server.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;

public class EventsCacheTest {
  private EventsCache cache;
  private Container container;
  private ArrayList<Map<String, String>> list;

  @Before
  public void before() {
    cache = new EventsCache();
    container = Mockito.mock(Container.class);
    Mockito.when(container.getStateToken()).thenReturn(new StateToken("kk"));
    list = new ArrayList<Map<String, String>>();
  }

  @Test
  public void testBasicAdd() {
    assertNull(cache.get(container));
    cache.put(container, list);
    cache.put(container, list);
    assertEquals(1, cache.size());
    cache.remove(container);
    assertNull(cache.get(container));
    assertEquals(0, cache.size());
  }

}
