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
package cc.kune.core.server.rack.filters.rest;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TestRESTMethodFinder {
  private RESTMethodFinder finder;
  private MyTestService service;

  @Before
  public void createObjects() {
    this.finder = new DefaultRESTMethodFinder();
    this.service = new MyTestService();
  }

  @Test
  public void notEnoughParameters() {
    RESTMethod method = finder.findMethod("simpleMethod", new TestParameters(), MyTestService.class);
    assertNull(method);
  }

  @Test
  public void simpleTest() {
    RESTMethod method = finder.findMethod("simpleMethod", new TestParameters("name", "theName"),
        MyTestService.class);
    assertNotNull(method);
    assertTrue(method.invoke(service));
    assertEquals("the name: theName", method.getResponse().toString());
  }

  @Test
  public void shouldTakeMoreParamsMethod() {
    RESTMethod method = finder.findMethod("simpleMethod", new TestParameters("name", "theName", "value",
        "theValue"), MyTestService.class);
    assertNotNull(method);
    assertTrue(method.invoke(service));
    assertEquals("more params: theName: theValue", method.getResponse().toString());
  }

  @Test
  public void conversionTest() {
    RESTMethod method = finder.findMethod("convertIntMethod", new TestParameters("length", "12",
        "stamp", "13"), MyTestService.class);
    assertNotNull(method);
    assertTrue(method.invoke(service));
    assertEquals("the data: 12 13", method.getResponse().toString());
  }

  public static class MyTestService {
    @REST(params = { "name" })
    public String simpleMethod(final String name) {
      return "the name: " + name;
    }

    @REST(params = { "name", "value" })
    public String simpleMethod(final String name, final String value) {
      return "more params: " + name + ": " + value;
    }

    @REST(params = { "length", "stamp" })
    public String convertIntMethod(final int length, final long theStamp) {
      return "the data: " + length + " " + theStamp;
    }
  }

  public static class TestParameters implements Parameters {
    private final HashMap<String, String> map;

    public TestParameters(final String... pairs) {
      this.map = new HashMap<String, String>();
      for (int index = 0; index < pairs.length; index += 2) {
        map.put(pairs[index], pairs[index + 1]);
      }
    }

    public String get(final String name) {
      return map.get(name);
    }

    public int getSize() {
      return map.size();
    }
  }
}
