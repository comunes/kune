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
package cc.kune.core.client.registry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdGeneratorTest {

  @Test
  public void testBasic() {
    assertEquals("", IdGenerator.generate("", ""));
    assertEquals("", IdGenerator.generate(null, ""));
    assertEquals("", IdGenerator.generate("", null));
    assertEquals("", IdGenerator.generate(null, null));
    assertEquals("a", IdGenerator.generate("a", null));
    assertEquals("a", IdGenerator.generate("a", ""));
    assertEquals("b", IdGenerator.generate("", "b"));
    assertEquals("b", IdGenerator.generate(null, "b"));
    assertEquals("a" + IdGenerator.SEPARATOR + "b", IdGenerator.generate("a", "b"));
  }
}
