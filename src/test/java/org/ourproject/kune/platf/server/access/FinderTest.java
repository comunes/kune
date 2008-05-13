package org.ourproject.kune.platf.server.access;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.content.CommentManager;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.RateManager;

public class FinderTest {

    private GroupManager groupManager;
    private ContainerManager containerManager;
    private ContentManager contentManager;
    private RateManager rateManager;
    private FinderServiceDefault finder;
    private CommentManager commentManager;

    @Test(expected = ContentNotFoundException.class)
    public void contentAndFolderMatch() throws Exception {
        final Content descriptor = new Content();
        final Container container = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName2");
        descriptor.setContainer(container);
        expect(contentManager.find(1l)).andReturn(descriptor);
        replay(contentManager);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
        verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndGrouplMatch() throws Exception {
        final Content descriptor = new Content();
        final Container container = TestDomainHelper.createFolderWithIdAndGroupAndTool(5, "groupOther", "toolName");
        descriptor.setContainer(container);
        expect(contentManager.find(1l)).andReturn(descriptor);
        replay(contentManager);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
        verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndToolMatch() throws Exception {
        final Content descriptor = new Content();
        final Container container = TestDomainHelper.createFolderWithId(1);
        descriptor.setContainer(container);
        expect(contentManager.find(1l)).andReturn(descriptor);
        replay(contentManager);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
        verify(contentManager);
    }

    @Before
    public void createSession() {
        this.groupManager = createStrictMock(GroupManager.class);
        this.containerManager = createStrictMock(ContainerManager.class);
        this.contentManager = createStrictMock(ContentManager.class);
        this.rateManager = createStrictMock(RateManager.class);
        this.commentManager = createStrictMock(CommentManager.class);
        this.finder = new FinderServiceDefault(groupManager, containerManager, contentManager, rateManager,
                commentManager);
    }

    @Test
    public void getGroupDefaultContent() throws Exception {
        final Group group = new Group();
        final Content descriptor = new Content();
        group.setDefaultContent(descriptor);
        expect(groupManager.findByShortName("groupShortName")).andReturn(group);
        replay(groupManager);

        final Content content = finder.getContent(new StateToken("groupShortName", null, null, null), null);
        assertSame(descriptor, content);
        verify(groupManager);
    }

    @Test
    public void testCompleteToken() throws Exception {
        final Container container = TestDomainHelper.createFolderWithIdAndGroupAndTool(1, "groupShortName", "toolName");
        final Content descriptor = new Content();
        descriptor.setId(1l);
        descriptor.setContainer(container);

        expect(contentManager.find(2l)).andReturn(descriptor);
        replay(contentManager);

        final Content content = finder.getContent(new StateToken("groupShortName", "toolName", "1", "2"), null);
        assertSame(descriptor, content);
        verify(contentManager);
    }

    @Test
    public void testDefaultGroupContent() throws Exception {
        final Group userGroup = new Group();
        final Content descriptor = TestDomainHelper.createDescriptor(1l, "title", "content");
        userGroup.setDefaultContent(descriptor);

        final Content content = finder.getContent(new StateToken(), userGroup);
        assertSame(descriptor, content);
    }

    @Test
    public void testDefaultGroupContentHasDefLicense() throws Exception {
        final Group userGroup = new Group();
        final Content descriptor = TestDomainHelper.createDescriptor(1l, "title", "content");
        userGroup.setDefaultContent(descriptor);

        final Content content = finder.getContent(new StateToken(), userGroup);
        assertSame(userGroup.getDefaultLicense(), content.getLicense());
    }

    @Test
    public void testDefaultUserContent() throws Exception {
        final Content content = new Content();
        final Group group = new Group();
        group.setDefaultContent(content);
        final Content response = finder.getContent(new StateToken(), group);
        assertSame(content, response);
    }

    @Test
    public void testDocMissing() throws Exception {
        final Container container = new Container();
        expect(containerManager.find(1l)).andReturn(container);

        replay(containerManager);
        final Content content = finder.getContent(new StateToken("groupShortName", "toolName", "1", null), null);
        assertNotNull(content);
        assertSame(container, content.getContainer());
        verify(containerManager);
    }

    @Test
    public void testFolderMissing() throws Exception {
        final Group group = new Group();
        final ToolConfiguration config = group.setToolConfig("toolName", new ToolConfiguration());
        final Container container = config.setRoot(new Container());
        expect(groupManager.findByShortName("groupShortName")).andReturn(group);
        replay(groupManager);

        final StateToken token = new StateToken("groupShortName", "toolName", null, null);
        final Content content = finder.getContent(token, null);
        assertSame(container, content.getContainer());
        verify(groupManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void testIds() throws Exception {
        final Content descriptor = new Content();
        final Container container = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName");
        descriptor.setContainer(container);
        expect(contentManager.find(1l)).andReturn(descriptor);
        replay(contentManager);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1a"), null);
        verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void voyAJoder() throws Exception {
        finder.getContent(new StateToken(null, "toolName", "1", "2"), null);
    }

}
