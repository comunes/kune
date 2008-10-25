package org.ourproject.kune.docs.server;

import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_BLOG;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_DOCUMENT;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_FOLDER;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_GALLERY;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_POST;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_ROOT;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_UPLOADEDFILE;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_WIKI;
import static org.ourproject.kune.docs.server.DocumentServerTool.TYPE_WIKIPAGE;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.ContainerNotPermittedException;
import org.ourproject.kune.platf.client.errors.ContentNotPermittedException;

public class DocumentServerToolTest { // extends PersistenceTest {

    private DocumentServerTool dcServerTool;

    @Before
    public void before() {
        dcServerTool = new DocumentServerTool(null, null, null, null);
    }

    @Test
    public void testCreateContainerInCorrectContainer() {
        dcServerTool.checkContainerTypeId(TYPE_ROOT, TYPE_FOLDER);
        dcServerTool.checkContainerTypeId(TYPE_FOLDER, TYPE_FOLDER);
        dcServerTool.checkContainerTypeId(TYPE_ROOT, TYPE_WIKI);
        dcServerTool.checkContainerTypeId(TYPE_ROOT, TYPE_BLOG);
        dcServerTool.checkContainerTypeId(TYPE_ROOT, TYPE_GALLERY);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer1() {
        dcServerTool.checkContainerTypeId(TYPE_FOLDER, TYPE_WIKI);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer10() {
        // At the moment, not permitted (create a gallery_folder)
        dcServerTool.checkContainerTypeId(TYPE_GALLERY, TYPE_FOLDER);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer11() {
        // At the moment, not permitted (create a wiki_folder)
        dcServerTool.checkContainerTypeId(TYPE_WIKI, TYPE_FOLDER);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer12() {
        dcServerTool.checkContainerTypeId(TYPE_BLOG, TYPE_FOLDER);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer13() {
        dcServerTool.checkContainerTypeId(TYPE_GALLERY, TYPE_WIKI);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer14() {
        dcServerTool.checkContainerTypeId(TYPE_WIKI, TYPE_WIKI);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer15() {
        dcServerTool.checkContainerTypeId(TYPE_GALLERY, TYPE_GALLERY);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer2() {
        dcServerTool.checkContainerTypeId(TYPE_BLOG, TYPE_WIKI);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer3() {
        dcServerTool.checkContainerTypeId(TYPE_FOLDER, TYPE_WIKI);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer4() {
        dcServerTool.checkContainerTypeId(TYPE_BLOG, TYPE_GALLERY);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer5() {
        dcServerTool.checkContainerTypeId(TYPE_FOLDER, TYPE_GALLERY);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer6() {
        dcServerTool.checkContainerTypeId(TYPE_WIKI, TYPE_GALLERY);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer7() {
        dcServerTool.checkContainerTypeId(TYPE_BLOG, TYPE_BLOG);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer8() {
        dcServerTool.checkContainerTypeId(TYPE_FOLDER, TYPE_BLOG);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer9() {
        dcServerTool.checkContainerTypeId(TYPE_GALLERY, TYPE_BLOG);
    }

    @Test
    public void testCreateContentInCorrectContainer() {
        dcServerTool.checkContentTypeId(TYPE_BLOG, TYPE_POST);
        dcServerTool.checkContentTypeId(TYPE_WIKI, TYPE_WIKIPAGE);
        dcServerTool.checkContentTypeId(TYPE_ROOT, TYPE_DOCUMENT);
        dcServerTool.checkContentTypeId(TYPE_FOLDER, TYPE_DOCUMENT);
        dcServerTool.checkContentTypeId(TYPE_ROOT, TYPE_UPLOADEDFILE);
        dcServerTool.checkContentTypeId(TYPE_FOLDER, TYPE_UPLOADEDFILE);
        dcServerTool.checkContentTypeId(TYPE_GALLERY, TYPE_UPLOADEDFILE);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer1() {
        dcServerTool.checkContentTypeId(TYPE_ROOT, TYPE_POST);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer10() {
        dcServerTool.checkContentTypeId(TYPE_ROOT, TYPE_WIKIPAGE);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer11() {
        dcServerTool.checkContentTypeId(TYPE_FOLDER, TYPE_WIKIPAGE);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer12() {
        dcServerTool.checkContentTypeId(TYPE_GALLERY, TYPE_WIKIPAGE);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer13() {
        dcServerTool.checkContentTypeId(TYPE_BLOG, TYPE_WIKIPAGE);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer2() {
        dcServerTool.checkContentTypeId(TYPE_FOLDER, TYPE_POST);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer3() {
        dcServerTool.checkContentTypeId(TYPE_GALLERY, TYPE_POST);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer4() {
        dcServerTool.checkContentTypeId(TYPE_WIKI, TYPE_POST);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer5() {
        dcServerTool.checkContentTypeId(TYPE_GALLERY, TYPE_DOCUMENT);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer6() {
        dcServerTool.checkContentTypeId(TYPE_WIKI, TYPE_DOCUMENT);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer7() {
        dcServerTool.checkContentTypeId(TYPE_BLOG, TYPE_DOCUMENT);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer8() {
        dcServerTool.checkContentTypeId(TYPE_BLOG, TYPE_UPLOADEDFILE);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer9() {
        dcServerTool.checkContentTypeId(TYPE_WIKI, TYPE_UPLOADEDFILE);
    }

}