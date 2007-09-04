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
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.manager.GroupManager;

public class FinderTest {

    private GroupManager groupManager;
    private ContainerManager containerManager;
    private ContentManager contentManager;
    private FinderDefault finder;

    @Before
    public void createSession() {
	this.groupManager = createStrictMock(GroupManager.class);
	this.containerManager = createStrictMock(ContainerManager.class);
	this.contentManager = createStrictMock(ContentManager.class);
	this.finder = new FinderDefault(groupManager, containerManager, contentManager);
    }

    @Test
    public void testDefaultGroupContent() throws ContentNotFoundException, GroupNotFoundException {
	Group userGroup = new Group();
	Content descriptor = TestDomainHelper.createDescriptor(1l, "title", "content");
	userGroup.setDefaultContent(descriptor);

	Content content = finder.getContent(userGroup, new StateToken());
	assertSame(descriptor, content);
    }

    @Test
    public void testDefaultGroupContentHasDefLicense() throws ContentNotFoundException, GroupNotFoundException {
	Group userGroup = new Group();
	Content descriptor = TestDomainHelper.createDescriptor(1l, "title", "content");
	userGroup.setDefaultContent(descriptor);

	Content content = finder.getContent(userGroup, new StateToken());
	assertSame(userGroup.getDefaultLicense(), content.getLicense());
    }

    @Test
    public void testCompleteToken() throws ContentNotFoundException, GroupNotFoundException {
	Container container = TestDomainHelper.createFolderWithIdAndGroupAndTool(1, "groupShortName", "toolName");
	Content descriptor = new Content();
	descriptor.setId(1l);
	descriptor.setFolder(container);

	expect(contentManager.find(2l)).andReturn(descriptor);
	replay(contentManager);

	Content content = finder.getContent(null, new StateToken("groupShortName", "toolName", "1", "2"));
	assertSame(descriptor, content);
	verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndFolderMatch() throws ContentNotFoundException, GroupNotFoundException {
	Content descriptor = new Content();
	Container container = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName2");
	descriptor.setFolder(container);
	expect(contentManager.find(1l)).andReturn(descriptor);
	replay(contentManager);

	finder.getContent(null, new StateToken("groupShortName", "toolName", "5", "1"));
	verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndToolMatch() throws ContentNotFoundException, GroupNotFoundException {
	Content descriptor = new Content();
	Container container = TestDomainHelper.createFolderWithId(1);
	descriptor.setFolder(container);
	expect(contentManager.find(1l)).andReturn(descriptor);
	replay(contentManager);

	finder.getContent(null, new StateToken("groupShortName", "toolName", "5", "1"));
	verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndGrouplMatch() throws ContentNotFoundException, GroupNotFoundException {
	Content descriptor = new Content();
	Container container = TestDomainHelper.createFolderWithIdAndGroupAndTool(5, "groupOther", "toolName");
	descriptor.setFolder(container);
	expect(contentManager.find(1l)).andReturn(descriptor);
	replay(contentManager);

	finder.getContent(null, new StateToken("groupShortName", "toolName", "5", "1"));
	verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void voyAJoder() throws ContentNotFoundException, GroupNotFoundException {
	finder.getContent(null, new StateToken(null, "toolName", "1", "2"));
    }

    @Test
    public void testDocMissing() throws ContentNotFoundException, GroupNotFoundException {
	Container container = new Container();
	expect(containerManager.find(1l)).andReturn(container);

	replay(containerManager);
	Content content = finder.getContent(null, new StateToken("groupShortName", "toolName", "1", null));
	assertNotNull(content);
	assertSame(container, content.getFolder());
	verify(containerManager);
    }

    @Test
    public void testFolderMissing() throws ContentNotFoundException, GroupNotFoundException {
	Group group = new Group();
	ToolConfiguration config = group.setToolConfig("toolName", new ToolConfiguration());
	Container container = config.setRoot(new Container());
	expect(groupManager.findByShortName("groupShortName")).andReturn(group);
	replay(groupManager);

	StateToken token = new StateToken("groupShortName", "toolName", null, null);
	Content content = finder.getContent(null, token);
	assertSame(container, content.getFolder());
	verify(groupManager);
    }

    @Test
    public void getGroupDefaultContent() throws ContentNotFoundException, GroupNotFoundException {
	Group group = new Group();
	Content descriptor = new Content();
	group.setDefaultContent(descriptor);
	expect(groupManager.findByShortName("groupShortName")).andReturn(group);
	replay(groupManager);

	Content content = finder.getContent(null, new StateToken("groupShortName", null, null, null));
	assertSame(descriptor, content);
	verify(groupManager);
    }

    @Test
    public void testDefaultUserContent() throws ContentNotFoundException, GroupNotFoundException {
	Content content = new Content();
	Group group = new Group();
	group.setDefaultContent(content);
	Content response = finder.getContent(group, new StateToken());
	assertSame(content, response);
    }

    @Test(expected = ContentNotFoundException.class)
    public void testIds() throws ContentNotFoundException, GroupNotFoundException {
	Content descriptor = new Content();
	Container container = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName");
	descriptor.setFolder(container);
	expect(contentManager.find(1l)).andReturn(descriptor);
	replay(contentManager);

	finder.getContent(null, new StateToken("groupShortName", "toolName", "5", "1a"));
	verify(contentManager);
    }

}
