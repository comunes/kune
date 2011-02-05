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
package org.ourproject.kune.platf.client.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cc.kune.common.client.utils.TextUtils;

public class TextUtilsTest {
    @Test
    public void dontRemoveLastSlashIfNoExists() {
        final String text = "ab";
        assertEquals("ab", TextUtils.removeLastSlash(text));
    }

    @Test
    public void dontRemoveOtherSlashIfExists() {
        final String text = "/ab";
        assertEquals("/ab", TextUtils.removeLastSlash(text));
    }

    @Test
    public void matchDemoEmail() {
        final String email = "test100@emitedemo.ourproject.org";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchLocalhostEmail() {
        final String email = "me@localhost";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchSimpleEmail() {
        final String email = "me@example.com";
        assertTrue(email.matches(TextUtils.EMAIL_REGEXP));
    }

    @Test
    public void matchUrlWithHttp() {
        final String gnuUrl = "http://gnu.org";
        assertTrue(gnuUrl.matches(TextUtils.URL_REGEXP));
    }

    @Test
    public void matchUrlWithoutHttp() {
        final String gnuUrl = "gnu.org";
        assertTrue(!gnuUrl.matches(TextUtils.URL_REGEXP));
    }

    @Test
    public void notMatchWrongUrl() {
        final String someWrong = "some@email.com";
        assertTrue(!someWrong.matches(TextUtils.URL_REGEXP));
    }

    @Test
    public void removeLastSlashIfExists() {
        final String text = "/";
        assertEquals("", TextUtils.removeLastSlash(text));
    }

    @Test
    public void removeOnlytLastSlashIfExists() {
        final String text = "/ab/cd/";
        assertEquals("/ab/cd", TextUtils.removeLastSlash(text));
    }
}
