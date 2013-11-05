/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.server.rack.filters.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestRESTServiceDefinition {

  private RESTServiceDefinition definition;

  @Before
  public void createDefinition() {
    this.definition = new RESTServiceDefinition(SimpleRESTService.class);
  }

  @Test
  public void testMethodOrder() {
    assertEquals("three", definition.getMethods()[0].getName());
    assertEquals("two", definition.getMethods()[1].getName());
    assertEquals("one", definition.getMethods()[2].getName());
  }

  @Test
  public void testMethodCount() {
    assertEquals(3, definition.getMethods().length);
  }

  public static class SimpleRESTService {
    @REST(params = { "one", "two" })
    public void two(final String one, final String two) {
    }

    @REST(params = { "one", "two", "three" })
    public void three(final String one, final String two, final String three) {
    }

    @REST(params = { "one" })
    public void one(final String one) {
    }

  }
}
