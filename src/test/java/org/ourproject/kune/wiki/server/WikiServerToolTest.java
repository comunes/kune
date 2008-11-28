package org.ourproject.kune.wiki.server;

import static org.ourproject.kune.wiki.server.WikiServerTool.TYPE_FOLDER;
import static org.ourproject.kune.wiki.server.WikiServerTool.TYPE_ROOT;
import static org.ourproject.kune.wiki.server.WikiServerTool.TYPE_UPLOADEDFILE;
import static org.ourproject.kune.wiki.server.WikiServerTool.TYPE_WIKIPAGE;

import org.junit.Before;
import org.junit.Test;

public class WikiServerToolTest { // extends PersistenceTest {

    private WikiServerTool serverTool;

    @Before
    public void before() {
        serverTool = new WikiServerTool(null, null, null, null);
    }

    @Test
    public void testCreateContainerInCorrectContainer() {
        serverTool.checkContainerTypeId(TYPE_ROOT, TYPE_FOLDER);
        serverTool.checkContainerTypeId(TYPE_FOLDER, TYPE_FOLDER);
    }

    @Test
    public void testCreateContentInCorrectContainer() {
        serverTool.checkContentTypeId(TYPE_ROOT, TYPE_WIKIPAGE);
        serverTool.checkContentTypeId(TYPE_ROOT, TYPE_UPLOADEDFILE);
        serverTool.checkContentTypeId(TYPE_FOLDER, TYPE_WIKIPAGE);
        serverTool.checkContentTypeId(TYPE_FOLDER, TYPE_UPLOADEDFILE);
    }

}