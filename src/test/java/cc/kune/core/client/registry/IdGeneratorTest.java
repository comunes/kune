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
package cc.kune.core.client.registry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class IdGeneratorTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class IdGeneratorTest {

  /**
   * Test basic.
   */
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
