package org.ourproject.kune.blogs.server;

import static org.ourproject.kune.blogs.server.BlogServerTool.TYPE_BLOG;
import static org.ourproject.kune.blogs.server.BlogServerTool.TYPE_POST;
import static org.ourproject.kune.blogs.server.BlogServerTool.TYPE_ROOT;
import static org.ourproject.kune.blogs.server.BlogServerTool.TYPE_UPLOADEDFILE;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.ContainerNotPermittedException;
import cc.kune.core.client.errors.ContentNotPermittedException;

public class BlogServerToolTest { // extends PersistenceTest {

    private BlogServerTool serverTool;

    @Before
    public void before() {
        serverTool = new BlogServerTool(null, null, null, null);
    }

    @Test
    public void testCreateContainerInCorrectContainer() {
        serverTool.checkContainerTypeId(TYPE_ROOT, TYPE_BLOG);
    }

    @Test(expected = ContainerNotPermittedException.class)
    public void testCreateContainerInIncorrectContainer7() {
        serverTool.checkContainerTypeId(TYPE_BLOG, TYPE_BLOG);
    }

    @Test
    public void testCreateContentInCorrectContainer() {
        serverTool.checkContentTypeId(TYPE_BLOG, TYPE_POST);
        serverTool.checkContentTypeId(TYPE_BLOG, TYPE_UPLOADEDFILE);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer1() {
        serverTool.checkContentTypeId(TYPE_ROOT, TYPE_POST);
    }

    @Test(expected = ContentNotPermittedException.class)
    public void testCreateContentInIncorrectContainer8() {
        serverTool.checkContentTypeId(TYPE_ROOT, TYPE_UPLOADEDFILE);
    }
}