package org.ourproject.kune.platf.server.manager;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;

public class ContentManagerTest {

    private UserSession session;
    private GroupManager groupManager;
    private FolderManager folderManager;
    private ContentDescriptorManager contentDescriptorManager;
    private ContentManagerDefault contentManager;

    @Before
    public void createSession() {
	this.session = new UserSession();
	this.groupManager = createStrictMock(GroupManager.class);
	this.folderManager = createStrictMock(FolderManager.class);
	this.contentDescriptorManager = createStrictMock(ContentDescriptorManager.class);
	this.contentManager = new ContentManagerDefault(groupManager, folderManager, contentDescriptorManager);
    }

    @Test
    public void testDefaultGroupContent() throws ContentNotFoundException {

	User user = TestDomainHelper.createUser(1);
	user.setUserGroup(new Group());
	ContentDescriptor descriptor = TestDomainHelper.createDescriptor(1l, "title", "content");
	user.getUserGroup().setDefaultContent(descriptor);
	session.setUser(user);

	ContentDescriptor content = contentManager.getContent(session.getUser(), null, null, null, null);
	assertSame(descriptor, content);
    }

    @Test
    public void testCompleteToken() throws ContentNotFoundException {
	Folder folder = TestDomainHelper.createFolderWithIdAndGroupAndTool(1, "groupShortName", "toolName");
	ContentDescriptor descriptor = new ContentDescriptor();
	descriptor.setId(1l);
	descriptor.setFolder(folder);

	expect(contentDescriptorManager.get(2l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	ContentDescriptor content = contentManager
		.getContent(session.getUser(), "groupShortName", "toolName", "1", "2");
	assertSame(descriptor, content);
	verify(contentDescriptorManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndFolderMatch() throws ContentNotFoundException {
	ContentDescriptor descriptor = new ContentDescriptor();
	Folder folder = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName2");
	descriptor.setFolder(folder);
	expect(contentDescriptorManager.get(1l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	contentManager.getContent(session.getUser(), "groupShortName", "toolName", "5", "1");
	verify(contentDescriptorManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndToolMatch() throws ContentNotFoundException {
	ContentDescriptor descriptor = new ContentDescriptor();
	Folder folder = TestDomainHelper.createFolderWithId(1);
	descriptor.setFolder(folder);
	expect(contentDescriptorManager.get(1l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	contentManager.getContent(session.getUser(), "groupShortName", "toolName", "5", "1");
	verify(contentDescriptorManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndGrouplMatch() throws ContentNotFoundException {
	ContentDescriptor descriptor = new ContentDescriptor();
	Folder folder = TestDomainHelper.createFolderWithIdAndGroupAndTool(5, "groupOther", "toolName");
	descriptor.setFolder(folder);
	expect(contentDescriptorManager.get(1l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	contentManager.getContent(session.getUser(), "groupShortName", "toolName", "5", "1");
	verify(contentDescriptorManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void voyAJoder() throws ContentNotFoundException {
	contentManager.getContent(session.getUser(), null, "toolName", "1", "2");
    }

    @Test
    public void testDocMissing() throws ContentNotFoundException {
	Folder folder = new Folder();
	expect(folderManager.find(1l)).andReturn(folder);

	replay(folderManager);
	ContentDescriptor content = contentManager.getContent(session.getUser(), "groupShortName", "toolName", "1",
		null);
	assertNotNull(content);
	assertSame(folder, content.getFolder());
	verify(folderManager);
    }

    @Test
    public void testFolderMissing() throws ContentNotFoundException {
	Group group = new Group();
	ToolConfiguration config = group.setToolConfig("toolName", new ToolConfiguration());
	Folder folder = config.setRoot(new Folder());
	expect(groupManager.findByShortName("groupShortName")).andReturn(group);
	replay(groupManager);

	ContentDescriptor content = contentManager.getContent(session.getUser(), "groupShortName", "toolName", null,
		null);
	assertSame(folder, content.getFolder());
	verify(groupManager);
    }

    @Test
    public void getGroupDefaultContent() throws ContentNotFoundException {
	Group group = new Group();
	ContentDescriptor descriptor = new ContentDescriptor();
	group.setDefaultContent(descriptor);
	expect(groupManager.findByShortName("groupShortName")).andReturn(group);
	replay(groupManager);

	ContentDescriptor content = contentManager.getContent(session.getUser(), "groupShortName", null, null, null);
	assertSame(descriptor, content);
	verify(groupManager);
    }

    @Test
    public void testDefaultUserContent() throws ContentNotFoundException {
	User user = session.setUser(new User());
	ContentDescriptor contentDescriptor = new ContentDescriptor();
	Group group = new Group();

	user.setUserGroup(group);
	group.setDefaultContent(contentDescriptor);
	ContentDescriptor content = contentManager.getContent(session.getUser(), null, null, null, null);
	assertSame(contentDescriptor, content);
    }

    @Test(expected = ContentNotFoundException.class)
    public void testIds() throws ContentNotFoundException {
	ContentDescriptor descriptor = new ContentDescriptor();
	Folder folder = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName");
	descriptor.setFolder(folder);
	expect(contentDescriptorManager.get(1l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	contentManager.getContent(session.getUser(), "groupShortName", "toolName", "5", "1a");
	verify(contentDescriptorManager);
    }

}
