package org.ourproject.kune.gallery.server;

import static org.ourproject.kune.gallery.server.GalleryServerTool.TYPE_ALBUM;
import static org.ourproject.kune.gallery.server.GalleryServerTool.TYPE_ROOT;
import static org.ourproject.kune.gallery.server.GalleryServerTool.TYPE_UPLOADEDFILE;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.ContainerNotPermittedException;

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