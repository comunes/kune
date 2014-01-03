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
package cc.kune.core.server.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.kune.core.server.rack.filters.rest.REST;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TestJSONService {

  public static class SimpleObject {
    private Date date;
    private String name;
    private String value;

    public SimpleObject(final String name, final String value, final Date date) {
      this.name = name;
      this.value = value;
      this.date = date;
    }

    public Date getDate() {
      return date;
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }

    public void setDate(final Date date) {
      this.date = date;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public void setValue(final String value) {
      this.value = value;
    }

  }

  @Inject
  public TestJSONService() {
  }

  @REST(params = { "value" }, format = "json")
  public String test(final String value) {
    return "The value is " + value;
  }

  @REST(params = { "name", "value" }, format = "json")
  public List<SimpleObject> test2(final String theName, final String theValue) {
    final int total = 5;
    final ArrayList<SimpleObject> result = new ArrayList<SimpleObject>(total);
    for (int index = 0; index < total; index++) {
      result.add(new SimpleObject(theName + total, theValue + total, new Date()));
    }
    return result;
  }
}
