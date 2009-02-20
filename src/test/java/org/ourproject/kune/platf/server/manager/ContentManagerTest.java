package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.PersistencePreLoadedDataTest;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Content;

public class ContentManagerTest extends PersistencePreLoadedDataTest {

    @Test
    public void testBasicMimePersist() {
        final String mimetype = "application/pdf";
        createContentWithMime(mimetype);
    }

    @Test
    public void testBasicMimePersistWithoutSubtype() {
        final String mimetype = "application";
        createContentWithMime(mimetype);
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

    private void createContentWithMime(final String mimetype) {
        final Content cnt = contentManager.createContent("title", "body", user, container,
                DocumentServerTool.TYPE_UPLOADEDFILE);
        cnt.setMimeType(new BasicMimeType(mimetype));
        persist(cnt);
        final Content newCnt = contentManager.find(cnt.getId());
        assertEquals(mimetype, newCnt.getMimeType().toString());
    }
}
