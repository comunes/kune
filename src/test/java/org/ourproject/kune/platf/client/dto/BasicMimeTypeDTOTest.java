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
package org.ourproject.kune.platf.client.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.kune.core.shared.dto.BasicMimeTypeDTO;

public class BasicMimeTypeDTOTest {

    @Test
    public void testNoSubtype() {
        final BasicMimeTypeDTO mime = new BasicMimeTypeDTO("somesimplemime");
        assertEquals("somesimplemime", mime.getType());
        assertEquals(null, mime.getSubtype());
        assertEquals("somesimplemime", mime.toString());
    }

    @Test
    public void testNoSubtypeWithSlash() {
        final BasicMimeTypeDTO mime = new BasicMimeTypeDTO("somesimplemime/");
        assertEquals("somesimplemime", mime.getType());
        assertEquals(null, mime.getSubtype());
        assertEquals("somesimplemime", mime.toString());
    }

    @Test
    public void testNull() {
        final BasicMimeTypeDTO mime = new BasicMimeTypeDTO(null);
        assertEquals(null, mime.getType());
        assertEquals(null, mime.getSubtype());
    }

    @Test
    public void testSeveralSlashs() {
        final BasicMimeTypeDTO mime = new BasicMimeTypeDTO("somesimplemime/subtype/thisisposible?");
        assertEquals("somesimplemime", mime.getType());
        assertEquals("subtype/thisisposible?", mime.getSubtype());
        assertEquals("somesimplemime/subtype/thisisposible?", mime.toString());
    }

    @Test
    public void testSubtype() {
        final BasicMimeTypeDTO mime = new BasicMimeTypeDTO("somesimplemime/subtype");
        assertEquals("somesimplemime", mime.getType());
        assertEquals("subtype", mime.getSubtype());
        assertEquals("somesimplemime/subtype", mime.toString());
    }
}
