package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.PersistencePreLoadedDataTest;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

public class ContentManagerTest extends PersistencePreLoadedDataTest {

    private static final String MIMETYPE = "image";
    private static final String BODY = "body";
    private static final String TITLE = "title";

    @Test
    public void testBasicBodySearch() {
        createContent();
        SearchResult<Content> search = contentManager.search(BODY);
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
        SearchResult<Content> search = contentManager.search(new String[] { MIMETYPE },
                new String[] { "mimeType.mimetype" }, 0, 10);
        assertEquals(1, search.getSize());
    }

    @Test
    public void testBasicSearchWithQueriesAndFields() {
        createContentWithMimeAndCheck(MIMETYPE);
        SearchResult<Content> search = contentManager.search(new String[] { BODY },
                new String[] { "lastRevision.body" }, 0, 10);
        contentManager.reIndex();
        assertEquals(1, search.getSize());
    }

    @Test
    public void testBasicTitleSearch() {
        createContent();
        SearchResult<Content> search = contentManager.search(TITLE);
        contentManager.reIndex();
        assertEquals(1, search.getSize());
    }

    @Test
    public void testMimeSearch() {
        createContentWithMimeAndCheck(MIMETYPE + "/png");
        contentManager.reIndex();
        SearchResult<Content> search = contentManager.searchMime(BODY, 0, 10, MIMETYPE);
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
}
