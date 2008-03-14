package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.SerializableException;

public class ContentServiceAddTest extends ContentServiceIntegrationTest {

    private StateDTO defaultContent;
    String groupName;

    @Before
    public void init() throws SerializableException {
        new IntegrationTestHelper(this);
        groupName = getDefSiteGroupName();
    }

    // @Test
    public void testAddRoom() throws SerializableException {
        doLogin();
        defaultContent = getDefaultContent();
        ContainerDTO parent = defaultContent.getFolder();
        String roomName = "testroom";
        StateDTO newState = contentService.addRoom(session.getHash(), groupName, parent.getId(), roomName);
        assertNotNull(newState);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void noLoggedInShouldThrowIllegalAccess() throws ContentNotFoundException, SerializableException {
        defaultContent = getDefaultContent();
        Long folderId = defaultContent.getFolder().getId();
        contentService.addContent(session.getHash(), groupName, folderId, "a name");
    }

    @Test
    public void testAddContent() throws SerializableException {
        doLogin();
        defaultContent = getDefaultContent();
        assertEquals(1, defaultContent.getFolder().getContents().size());
        AccessRightsDTO cntRights = defaultContent.getContentRights();
        AccessRightsDTO ctxRight = defaultContent.getFolderRights();

        String title = "New Content Title";
        StateDTO added = contentService.addContent(session.getHash(), groupName, defaultContent.getFolder().getId(),
                title);
        assertNotNull(added);
        List<ContentDTO> contents = added.getFolder().getContents();
        assertEquals(title, added.getTitle());
        assertEquals(2, contents.size());
        assertEquals(cntRights, added.getContentRights());
        assertEquals(ctxRight, added.getFolderRights());

        StateToken newState = added.getStateToken();
        StateDTO sameAgain = contentService.getContent(session.getHash(), groupName, newState);
        assertNotNull(sameAgain);
        assertEquals(2, sameAgain.getFolder().getContents().size());
    }

    @Test
    public void testAddFolder() throws SerializableException {
        doLogin();
        defaultContent = getDefaultContent();
        ContainerDTO parent = defaultContent.getFolder();
        String title = "folder name";
        StateDTO newState = contentService.addFolder(session.getHash(), groupName, parent.getId(), title);
        assertNotNull(newState);

        ContainerDTO parentAgain = getDefaultContent().getFolder();
        ContainerDTO child = parentAgain.getChilds().get(0);
        assertEquals(parent.getAbsolutePath().length + 1, child.getAbsolutePath().length);
        assertEquals(parent.getId(), child.getParentFolderId());

        assertEquals(parent.getId(), parentAgain.getId());
        assertEquals(1, parentAgain.getChilds().size());
    }

    @Test
    public void testAddTwoFolders() throws SerializableException {
        doLogin();
        defaultContent = getDefaultContent();
        ContainerDTO parent = defaultContent.getFolder();
        String title = "folder name";
        StateDTO newState = contentService.addFolder(session.getHash(), groupName, parent.getId(), title);
        assertNotNull(newState);

        StateDTO newState2 = contentService.addFolder(session.getHash(), groupName, parent.getId(), title);
        assertNotNull(newState2);

        ContainerDTO parentAgain = getDefaultContent().getFolder();

        assertEquals(parent.getId(), parentAgain.getId());
        assertEquals(2, parentAgain.getChilds().size());
    }

}
