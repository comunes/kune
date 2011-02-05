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
package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.PersistencePreLoadedDataTest;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Content;

public class ContentManagerTest extends PersistencePreLoadedDataTest {

    private static final String BODY = "body";
    private static final String MIMETYPE = "image";
    private static final String TITLE = "title";

    private void createContent() {
        final Content cnt = contentManager.createContent(TITLE, BODY, user, container,
                DocumentServerTool.TYPE_UPLOADEDFILE);
        persist(cnt);
    }

    private void createContentWithMimeAndCheck(final String mimetype) {
        final Content cnt = contentManager.createContent(TITLE, BODY, user, container,
                DocumentServerTool.TYPE_UPLOADEDFILE);
        cnt.setMimeType(new BasicMimeType(mimetype));
        persist(cnt);
        final Content newCnt = contentManager.find(cnt.getId());
        assertEquals(mimetype, newCnt.getMimeType().toString());
    }

    @Test
    public void testBasicBodySearch() {
        createContent();
        final SearchResult<Content> search = contentManager.search(BODY);
        contentManager.reIndex();
        assertEquals(1, search.getSize());
    }

    @Test
    public void testBasicMimePersist() {
        final String mimetype = "application/pdf";
        createContentWithMimeAndCheck(mimetype);
    }

    @Test
    public void testBasicMimePersistWithoutSubtype() {
        final String mimetype = "application";
        createContentWithMimeAndCheck(mimetype);
    }

    @Test
    public void testBasicMimeSearchWithQueriesAndFields() {
        createContentWithMimeAndCheck(MIMETYPE + "/png");
        contentManager.reIndex();
        final SearchResult<Content> search = contentManager.search(new String[] { MIMETYPE },
                new String[] { "mimeType.mimetype" }, 0, 10);
        assertEquals(1, search.getSize());
    }

    @Test
    public void testBasicSearchWithQueriesAndFields() {
        createContentWithMimeAndCheck(MIMETYPE);
        final SearchResult<Content> search = contentManager.search(new String[] { BODY },
                new String[] { "lastRevision.body" }, 0, 10);
        contentManager.reIndex();
        assertEquals(1, search.getSize());
    }

    @Test
    public void testBasicTitleSearch() {
        createContent();
        final SearchResult<Content> search = contentManager.search(TITLE);
        contentManager.reIndex();
        assertEquals(1, search.getSize());
    }

    @Test
    public void testtMimeSearch() {
        createContentWithMimeAndCheck(MIMETYPE + "/png");
        contentManager.reIndex();
        SearchResult<Content> search = contentManager.searchMime(BODY, 0, 10, "asb", MIMETYPE);
        assertEquals(1, search.getSize());
    }

    /**
     * This normally fails with mysql (not configured for utf-8), see the
     * INSTALL mysql section
     */
    @Test
    public void testUTF8Persist() {
        final Content cnt = contentManager.createContent("汉语/漢語", "汉语/漢語", user, container,
                DocumentServerTool.TYPE_DOCUMENT);
        final Content newCnt = contentManager.find(cnt.getId());
        assertEquals("汉语/漢語", newCnt.getTitle());
    }
}
