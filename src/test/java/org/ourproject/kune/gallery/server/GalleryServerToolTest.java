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
package org.ourproject.kune.gallery.server;

import static org.ourproject.kune.gallery.server.GalleryServerTool.TYPE_ALBUM;
import static org.ourproject.kune.gallery.server.GalleryServerTool.TYPE_ROOT;
import static org.ourproject.kune.gallery.server.GalleryServerTool.TYPE_UPLOADEDFILE;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.ContainerNotPermittedException;

public class GalleryServerToolTest { // extends PersistenceTest {

    private GalleryServerTool serverTool;

    @Before
    public void before() {
        serverTool = new GalleryServerTool(null, null, null);
    }

    @Test
    public void testCreateContainerInCorrectContainer() {
        serverTool.checkContainerTypeId(TYPE_ROOT, TYPE_ALBUM);
        serverTool.checkContainerTypeId(TYPE_ALBUM, TYPE_ALBUM);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer1() {
        serverTool.checkContainerTypeId(TYPE_ROOT, TYPE_ROOT);
    }

    @Test
    public void testCreateContentInCorrectContainer() {
        serverTool.checkContentTypeId(TYPE_ROOT, TYPE_UPLOADEDFILE);
        serverTool.checkContentTypeId(TYPE_ALBUM, TYPE_UPLOADEDFILE);
    }

}