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

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class TestRESTMethodFinder.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TestRESTMethodFinder {

  /**
   * The Class MyTestService.
   * 
   * @author danigb@gmail.com
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class MyTestService {

    /**
     * Convert int method.
     * 
     * @param length
     *          the length
     * @param theStamp
     *          the the stamp
     * @return the string
     */
    @REST(params = { "length", "stamp" })
    public String convertIntMethod(final int length, final long theStamp) {
      return "the data: " + length + " " + theStamp;
    }

    /**
     * Simple method.
     * 
     * @param name
     *          the name
     * @return the string
     */
    @REST(params = { "name" })
    public String simpleMethod(final String name) {
      return "the name: " + name;
    }

    /**
     * Simple method.
     * 
     * @param name
     *          the name
     * @param value
     *          the value
     * @return the string
     */
    @REST(params = { "name", "value" })
    public String simpleMethod(final String name, final String value) {
      return "more params: " + name + ": " + value;
    }
  }

  /**
   * The Class TestParameters.
   * 
   * @author danigb@gmail.com
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class TestParameters implements Parameters {

    /** The map. */
    private final HashMap<String, String> map;

    /**
     * Instantiates a new test parameters.
     * 
     * @param pairs
     *          the pairs
     */
    public TestParameters(final String... pairs) {
      this.map = new HashMap<String, String>();
      for (int index = 0; index < pairs.length; index += 2) {
        map.put(pairs[index], pairs[index + 1]);
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.core.server.rack.filters.rest.Parameters#get(java.lang.String)
     */
    @Override
    public String get(final String name) {
      return map.get(name);
    }

    /**
     * Gets the size.
     * 
     * @return the size
     */
    public int getSize() {
      return map.size();
    }
  }

  /** The finder. */
  private RESTMethodFinder finder;

  /** The service. */
  private MyTestService service;

  /**
   * Conversion test.
   */
  @Test
  public void conversionTest() {
    final RESTMethod method = finder.findMethod("convertIntMethod", new TestParameters("length", "12",
        "stamp", "13"), MyTestService.class);
    assertNotNull(method);
    assertTrue(method.invoke(service).getSuccess());
    assertEquals("the data: 12 13", method.getResponse().toString());
  }

  /**
   * Creates the objects.
   */
  @Before
  public void createObjects() {
    this.finder = new DefaultRESTMethodFinder();
    this.service = new MyTestService();
  }

  /**
   * Not enough parameters.
   */
  @Test
  public void notEnoughParameters() {
    final RESTMethod method = finder.findMethod("simpleMethod", new TestParameters(),
        MyTestService.class);
    assertNull(method);
  }

  /**
   * Should take more params method.
   */
  @Test
  public void shouldTakeMoreParamsMethod() {
    final RESTMethod method = finder.findMethod("simpleMethod", new TestParameters("name", "theName",
        "value", "theValue"), MyTestService.class);
    assertNotNull(method);
    assertTrue(method.invoke(service).getSuccess());
    assertEquals("more params: theName: theValue", method.getResponse().toString());
  }

  /**
   * Simple test.
   */
  @Test
  public void simpleTest() {
    final RESTMethod method = finder.findMethod("simpleMethod", new TestParameters("name", "theName"),
        MyTestService.class);
    assertNotNull(method);
    assertTrue(method.invoke(service).getSuccess());
    assertEquals("the name: theName", method.getResponse().toString());
  }
}
