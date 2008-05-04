package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

public class ContentServiceAddTest extends ContentServiceIntegrationTest {

    String groupName;
    private StateDTO defaultContent;

    @Before
    public void init() throws Exception {
	new IntegrationTestHelper(this);
	groupName = getDefSiteGroupName();
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void noLoggedInShouldThrowIllegalAccess() throws ContentNotFoundException, Exception {
	defaultContent = getDefaultContent();
	final Long folderId = defaultContent.getFolder().getId();
	contentService.addContent(session.getHash(), groupName, folderId, "a name");
    }

    @Test
    public void testAddContent() throws Exception {
	doLogin();
	defaultContent = getDefaultContent();
	assertEquals(1, defaultContent.getFolder().getContents().size());
	final AccessRightsDTO cntRights = defaultContent.getContentRights();
	final AccessRightsDTO ctxRight = defaultContent.getFolderRights();

	final String title = "New Content Title";
	final StateDTO added = contentService.addContent(session.getHash(), groupName, defaultContent.getFolder()
		.getId(), title);
	assertNotNull(added);
	final List<ContentDTO> contents = added.getFolder().getContents();
	assertEquals(title, added.getTitle());
	assertEquals(2, contents.size());
	assertEquals(cntRights, added.getContentRights());
	assertEquals(ctxRight, added.getFolderRights());

	final StateToken newState = added.getStateToken();
	final StateDTO sameAgain = contentService.getContent(session.getHash(), groupName, newState);
	assertNotNull(sameAgain);
	assertEquals(2, sameAgain.getFolder().getContents().size());
    }

    @Test
    public void testAddFolder() throws Exception {
	doLogin();
	defaultContent = getDefaultContent();
	final ContainerDTO parent = defaultContent.getFolder();
	final String title = "folder name";
	final StateDTO newState = contentService.addFolder(session.getHash(), groupName, parent.getId(), title);
	assertNotNull(newState);

	final ContainerDTO parentAgain = getDefaultContent().getFolder();
	final ContainerDTO child = parentAgain.getChilds().get(0);
	assertEquals(parent.getAbsolutePath().length + 1, child.getAbsolutePath().length);
	assertEquals(parent.getId(), child.getParentFolderId());

	assertEquals(parent.getId(), parentAgain.getId());
	assertEquals(1, parentAgain.getChilds().size());
    }

    // @Test
    public void testAddRoom() throws Exception {
	doLogin();
	defaultContent = getDefaultContent();
	final ContainerDTO parent = defaultContent.getFolder();
	final String roomName = "testroom";
	final StateDTO newState = contentService.addRoom(session.getHash(), groupName, parent.getId(), roomName);
	assertNotNull(newState);
    }

    @Test
    public void testAddTwoFolders() throws Exception {
	doLogin();
	defaultContent = getDefaultContent();
	final ContainerDTO parent = defaultContent.getFolder();
	final String title = "folder name";
	final StateDTO newState = contentService.addFolder(session.getHash(), groupName, parent.getId(), title);
	assertNotNull(newState);

	final StateDTO newState2 = contentService.addFolder(session.getHash(), groupName, parent.getId(), title);
	assertNotNull(newState2);

	final ContainerDTO parentAgain = getDefaultContent().getFolder();

	assertEquals(parent.getId(), parentAgain.getId());
	assertEquals(2, parentAgain.getChilds().size());
    }

}
