package org.ourproject.kune.docs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.docs.server.DocumentServerTool;

public class DocumentToolTest {

    @Test
    public void clientAndServerSync() {
        assertEquals(DocumentServerTool.NAME, DocumentClientTool.NAME);
        assertEquals(DocumentServerTool.TYPE_ROOT, DocumentClientTool.TYPE_ROOT);
        assertEquals(DocumentServerTool.TYPE_FOLDER, DocumentClientTool.TYPE_FOLDER);
        assertEquals(DocumentServerTool.TYPE_DOCUMENT, DocumentClientTool.TYPE_DOCUMENT);
    }
}
