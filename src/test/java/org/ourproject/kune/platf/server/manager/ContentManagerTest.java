package org.ourproject.kune.platf.server.manager;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

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
    public void testDefaultContent() {

	User user = TestDomainHelper.createUser(1);
	ContentDescriptor defaultContentDescriptor = new ContentDescriptor();
	user.getUserGroup().setDefaultContent(defaultContentDescriptor);
	session.setUser(user);

	Content content = contentManager.getContent(session, null, null, null, null);
	assertSame(defaultContentDescriptor, content.getDescriptor());
	assertSame(defaultContentDescriptor.getFolder(), content.getFolder());
    }

    @Test
    public void testCompleteToken() {
	Folder folder = new Folder();
	ContentDescriptor descriptor = new ContentDescriptor();
	descriptor.setFolder(folder);

	expect(contentDescriptorManager.get(2l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	Content content = contentManager.getContent(session, "groupShortName", "toolName", "1", "2");
	assertSame(descriptor, content.getDescriptor());
	assertSame(folder, content.getFolder());
	verify(contentDescriptorManager);
    }

    @Test
    public void testDocMissing() {
	Folder folder = new Folder();
	expect(folderManager.get(1l)).andReturn(folder);

	replay(folderManager);
	Content content = contentManager.getContent(session, "groupShortName", "toolName", "1", null);
	assertSame(folder, content.getFolder());
	assertNull(content.getDescriptor());
	verify(folderManager);
    }

    @Test
    public void testFolderMissing() {
	Group group = new Group();
	Folder folder = group.setRootFolder("toolName", new Folder());
	expect(groupManager.get("groupShortName")).andReturn(group);
	replay(groupManager);

	Content content = contentManager.getContent(session, "groupShortName", "toolName", null, null);
	assertSame(folder, content.getFolder());
	verify(groupManager);
    }

    @Test
    public void getGroupDefaultContent() {
	Group group = new Group();
	ContentDescriptor ct = new ContentDescriptor();
	group.setDefaultContent(ct);
	expect(groupManager.get("groupShortName")).andReturn(group);
	replay(groupManager);

	Content content = contentManager.getContent(session, "groupShortName", null, null, null);
	assertSame(ct, content.getDescriptor());
	verify(groupManager);
    }

}
