/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.kune.common.client.utils.Url;
import cc.kune.common.client.utils.UrlParam;

public class UrlTest {

    @Test
    public void testBooleanFalse() {
        assertEquals("test?param1=value1&param2=false", new Url("test", new UrlParam("param1", "value1"), new UrlParam(
                "param2", false)).toString());
    }

    @Test
    public void testBooleanTrue() {
        assertEquals("test?param1=value1&param2=true", new Url("test", new UrlParam("param1", "value1"), new UrlParam(
                "param2", true)).toString());
    }

    @Test
    public void testOneParam() {
        assertEquals("test?param1=value1", new Url("test", new UrlParam("param1", "value1")).toString());
    }

    @Test
    public void testOnlyBase() {
        assertEquals("test", new Url("test").toString());
    }

    @Test
    public void testThreeParams() {
        assertEquals("test?param1=value1&param2=value2&param3=value3",
                new Url("test", new UrlParam("param1", "value1"), new UrlParam("param2", "value2"), new UrlParam(
                        "param3", "value3")).toString());
    }

    @Test
    public void testTwoParams() {
        assertEquals("test?param1=value1&param2=value2", new Url("test", new UrlParam("param1", "value1"),
                new UrlParam("param2", "value2")).toString());
    }
}
