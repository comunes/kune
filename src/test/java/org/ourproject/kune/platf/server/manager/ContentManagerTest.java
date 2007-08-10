package org.ourproject.kune.platf.server.manager;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.User;

public class ContentManagerTest {

    private UserSession session;
    private GroupManager groupManager;
    private ContentDescriptorManager contentDescriptorManager;
    private ContentManagerDefault contentManager;

    @Before
    public void createSession() {
	this.session = new UserSession();
	this.groupManager = createStrictMock(GroupManager.class);
	this.contentDescriptorManager = createStrictMock(ContentDescriptorManager.class);
	this.contentManager = new ContentManagerDefault(groupManager, contentDescriptorManager);
    }

    @Test
    public void testDefaultContent() {

	User user = TestDomainHelper.createUser(1);
	ContentDescriptor defaultContent = new ContentDescriptor();
	user.getUserGroup().setDefaultContent(defaultContent);
	session.setUser(user);

	ContentDescriptor content = contentManager.getContent(session, null, null, null, null);
	assertSame(defaultContent, content);
    }

    @Test
    public void testCompleteToken() {
	ContentDescriptor descriptor = new ContentDescriptor();

	expect(contentDescriptorManager.get(2l)).andReturn(descriptor);
	replay(contentDescriptorManager);

	ContentDescriptor content = contentManager.getContent(session, "groupShortName", "toolName", "1", "2");
	assertSame(descriptor, content);
	verify(contentDescriptorManager);
    }
}
