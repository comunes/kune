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

import java.util.ArrayList;

import org.junit.Test;

import cc.kune.common.client.utils.TextUtils;

public class KuneStringUtilsTest {

    @Test
    public void testTagNull() {
        final String tagsString = null;
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals(0, tags.size());
    }

    @Test
    public void testTagStripsSimple() {
        final String tagsString = "ab cd";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

    @Test
    public void testTagStripsWithCommas() {
        final String tagsString = "ab,cd";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

    @Test
    public void testTagStripsWithCommasAndSpaces() {
        final String tagsString = "ab, cd";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

    @Test
    public void testTagStripsWithQuotes() {
        final String tagsString = "ab \"cd\"";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

    @Test
    public void testTagStripsWithSpaces() {
        final String tagsString = "    ab       cd    ";
        final ArrayList<String> tags = TextUtils.splitTags(tagsString);
        assertEquals("ab", tags.get(0));
        assertEquals("cd", tags.get(1));
    }

}
