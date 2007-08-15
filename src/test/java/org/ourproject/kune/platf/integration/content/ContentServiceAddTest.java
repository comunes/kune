package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class ContentServiceAddTest {
    @Inject
    ContentService contentService;
    @Inject
    SiteBarService loginService;
    @Inject
    DatabaseProperties properties;
    @Inject
    UserSession session;

    private ContentDTO defaultContent;
    private String groupName;

    @Before
    public void init() throws SerializableException {
	new IntegrationTestHelper(this);
	groupName = properties.getDefaultSiteShortName();
	loginService.login(groupName, properties.getDefaultSiteAdminPassword());
	defaultContent = contentService.getContent(session.getHash(), new StateToken());
    }

    @Test
    public void testAddContent() throws SerializableException {
	assertEquals(1, defaultContent.getFolder().getContents().size());
	AccessRightsDTO cntRights = defaultContent.getContentRights();
	AccessRightsDTO ctxRight = defaultContent.getFolderRights();

	String title = "New Content Title";
	ContentDTO added = contentService.addContent(session.getHash(), defaultContent.getFolder().getId(), title);
	assertNotNull(added);
	List contents = added.getFolder().getContents();
	assertEquals(title, added.getTitle());
	assertEquals(2, contents.size());
	assertEquals(cntRights, added.getContentRights());
	assertEquals(ctxRight, added.getFolderRights());

	StateToken newState = added.getState();
	ContentDTO sameAgain = contentService.getContent(session.getHash(), newState);
	assertNotNull(sameAgain);
	assertEquals(2, sameAgain.getFolder().getContents().size());
    }

    public void testAddFolder() throws SerializableException {
	FolderDTO parent = defaultContent.getFolder();
	String title = "folder name";
	ContentDTO newState = contentService.addFolder(session.getHash(), groupName, parent.getId(), title);
	assertNotNull(newState);
	FolderDTO child = newState.getFolder();
	assertEquals(parent.getAbsolutePath() + FolderDTO.SEP + title, child.getAbsolutePath());
	assertEquals(1, parent.getChilds().size());
	assertEquals(parent.getId(), child.getParentFolderId());
	ContentDTO defaultAgain = contentService.getContent(session.getHash(), new StateToken());
	FolderDTO parentAgain = defaultAgain.getFolder();
	assertEquals(1, parentAgain.getChilds().size());
    }
}
