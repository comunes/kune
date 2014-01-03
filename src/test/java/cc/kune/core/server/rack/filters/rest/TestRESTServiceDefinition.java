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
package cc.kune.core.server.rack.filters.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class TestRESTServiceDefinition.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TestRESTServiceDefinition {

  /** The definition. */
  private RESTServiceDefinition definition;

  /**
   * Creates the definition.
   */
  @Before
  public void createDefinition() {
    this.definition = new RESTServiceDefinition(SimpleRESTService.class);
  }

  /**
   * Test method order.
   */
  @Test
  public void testMethodOrder() {
    assertEquals("three", definition.getMethods()[0].getName());
    assertEquals("two", definition.getMethods()[1].getName());
    assertEquals("one", definition.getMethods()[2].getName());
  }

  /**
   * Test method count.
   */
  @Test
  public void testMethodCount() {
    assertEquals(3, definition.getMethods().length);
  }

  /**
   * The Class SimpleRESTService.
   * 
   * @author danigb@gmail.com
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class SimpleRESTService {

    /**
     * Two.
     * 
     * @param one
     *          the one
     * @param two
     *          the two
     */
    @REST(params = { "one", "two" })
    public void two(final String one, final String two) {
    }

    /**
     * Three.
     * 
     * @param one
     *          the one
     * @param two
     *          the two
     * @param three
     *          the three
     */
    @REST(params = { "one", "two", "three" })
    public void three(final String one, final String two, final String three) {
    }

    /**
     * One.
     * 
     * @param one
     *          the one
     */
    @REST(params = { "one" })
    public void one(final String one) {
    }

  }
}
