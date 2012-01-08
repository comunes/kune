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
package cc.kune.core.client.state.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import cc.kune.core.shared.domain.utils.StateToken;

public class StateTokenTest {

    @Test
    public void checkAllEmpty() {
        final StateToken token = new StateToken("");
        assertNull(token.getGroup());
        assertNull(token.getTool());
        assertNull(token.getFolder());
        assertNull(token.getDocument());
    }

    @Test
    public void checkEquals() {
        final StateToken token1 = new StateToken("abc", "da", "1", "1");
        final StateToken token2 = new StateToken("abc", "da", "1", "1");
        assertEquals(token1, token2);
    }

    @Test
    public void checkEqualsEncoded() {
        final StateToken token1 = new StateToken("abc.da.1.1");
        final StateToken token2 = new StateToken("abc.da.1.1");
        assertEquals(token1, token2);
    }

    @Test
    public void checkNoEquals() {
        final StateToken token1 = new StateToken("abc", "da", "1", "1");
        final StateToken token2 = new StateToken("abc", "da", 1L);
        assertFalse(token1.equals(token2));
    }

    @Test
    public void parseEncodedNotCatched() {
        final StateToken token1 = new StateToken();
        final StateToken token2 = new StateToken();
        token1.getEncoded();
        token2.getEncoded();
        token1.setEncoded("abc.da.1.1");
        token2.setEncoded("abc.da.1.2");
        assertFalse(token1.equals(token2));
    }
}
