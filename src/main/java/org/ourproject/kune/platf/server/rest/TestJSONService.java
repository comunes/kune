/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ourproject.kune.rack.filters.rest.REST;

import com.google.inject.Inject;

public class TestJSONService {

    @Inject
    public TestJSONService() {
    }

    @REST(params = { "value" }, format = "json")
    public String test(String value) {
        return "The value is " + value;
    }

    @REST(params = { "name", "value" }, format = "json")
    public List<SimpleObject> test2(String theName, String theValue) {
        int total = 5;
        ArrayList<SimpleObject> result = new ArrayList<SimpleObject>(total);
        for (int index = 0; index < total; index++) {
            result.add(new SimpleObject(theName + total, theValue + total, new Date()));
        }
        return result;
    }

    public static class SimpleObject {
        private String name;
        private String value;
        private Date date;

        public SimpleObject(String name, String value, Date date) {
            this.name = name;
            this.value = value;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

    }
}