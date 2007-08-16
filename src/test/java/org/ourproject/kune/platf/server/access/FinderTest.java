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
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.manager.ContentDescriptorManager;
import org.ourproject.kune.platf.server.manager.FolderManager;
import org.ourproject.kune.platf.server.manager.GroupManager;

public class FinderTest {

    private GroupManager groupManager;
    private FolderManager folderManager;
    private ContentDescriptorManager contentDescriptorManager;
    private FinderDefault contentManager;

    @Before
    public void createSession() {
	this.groupManager = createStrictMock(GroupManager.class);
	this.folderManager = createStrictMock(FolderManager.class);
	this.contentDescriptorManager = createStrictMock(ContentDescriptorManager.class);
	this.contentManager = new FinderDefault(groupManager, folderManager, contentDescriptorManager);
    }

    @Test
    public void testDefaultGroupContent() throws ContentNotFoundException {

	Group userGroup = new Group();
	Content descriptor = TestDomainHelper.createDescriptor(1l, "title", "content");
	userGroup.setDefaultContent(descriptor);

	Content content = contentManager.getContent(userGroup, new StateToken());
	assertSame(descriptor, content);
    }

    @Test
    public void testCompleteToken() throws ContentNotFoundException {
	Folder folder = TestDomainHelper.createFolderWithIdAndGroupAndTool(1, "groupShortName", "toolName");
	Content descriptor = new Content();
	descriptor.setId(1l);
	descriptor.setFolder(folder);

	expect(contentDescriptorManager.find(2l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	Content content = contentManager.getContent(null, new StateToken("groupShortName", "toolName", "1", "2"));
	assertSame(descriptor, content);
	verify(contentDescriptorManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndFolderMatch() throws ContentNotFoundException {
	Content descriptor = new Content();
	Folder folder = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName2");
	descriptor.setFolder(folder);
	expect(contentDescriptorManager.find(1l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	contentManager.getContent(null, new StateToken("groupShortName", "toolName", "5", "1"));
	verify(contentDescriptorManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndToolMatch() throws ContentNotFoundException {
	Content descriptor = new Content();
	Folder folder = TestDomainHelper.createFolderWithId(1);
	descriptor.setFolder(folder);
	expect(contentDescriptorManager.find(1l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	contentManager.getContent(null, new StateToken("groupShortName", "toolName", "5", "1"));
	verify(contentDescriptorManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndGrouplMatch() throws ContentNotFoundException {
	Content descriptor = new Content();
	Folder folder = TestDomainHelper.createFolderWithIdAndGroupAndTool(5, "groupOther", "toolName");
	descriptor.setFolder(folder);
	expect(contentDescriptorManager.find(1l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	contentManager.getContent(null, new StateToken("groupShortName", "toolName", "5", "1"));
	verify(contentDescriptorManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void voyAJoder() throws ContentNotFoundException {
	contentManager.getContent(null, new StateToken(null, "toolName", "1", "2"));
    }

    @Test
    public void testDocMissing() throws ContentNotFoundException {
	Folder folder = new Folder();
	expect(folderManager.find(1l)).andReturn(folder);

	replay(folderManager);
	Content content = contentManager.getContent(null, new StateToken("groupShortName", "toolName", "1", null));
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

	StateToken token = new StateToken("groupShortName", "toolName", null, null);
	Content content = contentManager.getContent(null, token);
	assertSame(folder, content.getFolder());
	verify(groupManager);
    }

    @Test
    public void getGroupDefaultContent() throws ContentNotFoundException {
	Group group = new Group();
	Content descriptor = new Content();
	group.setDefaultContent(descriptor);
	expect(groupManager.findByShortName("groupShortName")).andReturn(group);
	replay(groupManager);

	Content content = contentManager.getContent(null, new StateToken("groupShortName", null, null, null));
	assertSame(descriptor, content);
	verify(groupManager);
    }

    @Test
    public void testDefaultUserContent() throws ContentNotFoundException {
	Content content = new Content();
	Group group = new Group();
	group.setDefaultContent(content);
	Content response = contentManager.getContent(group, new StateToken());
	assertSame(content, response);
    }

    @Test(expected = ContentNotFoundException.class)
    public void testIds() throws ContentNotFoundException {
	Content descriptor = new Content();
	Folder folder = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName");
	descriptor.setFolder(folder);
	expect(contentDescriptorManager.find(1l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	contentManager.getContent(null, new StateToken("groupShortName", "toolName", "5", "1a"));
	verify(contentDescriptorManager);
    }

}
