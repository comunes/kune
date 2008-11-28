package org.ourproject.kune.docs.server;

import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_DOCUMENT;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_FOLDER;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_ROOT;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_UPLOADEDFILE;

import org.junit.Before;
import org.junit.Test;

public class DocumentServerToolTest { // extends PersistenceTest {

    private DocumentServerTool serverTool;

    @Before
    public void before() {
        serverTool = new DocumentServerTool(null, null, null, null);
    }

    @Test
    public void testCreateContainerInCorrectContainer() {
        serverTool.checkContainerTypeId(TYPE_ROOT, TYPE_FOLDER);
        serverTool.checkContainerTypeId(TYPE_FOLDER, TYPE_FOLDER);
    }

    @Test
    public void testCreateContentInCorrectContainer() {
        serverTool.checkContentTypeId(TYPE_ROOT, TYPE_DOCUMENT);
        serverTool.checkContentTypeId(TYPE_ROOT, TYPE_UPLOADEDFILE);
        serverTool.checkContentTypeId(TYPE_FOLDER, TYPE_DOCUMENT);
        serverTool.checkContentTypeId(TYPE_FOLDER, TYPE_UPLOADEDFILE);
    }

}