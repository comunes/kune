package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

public class ContentServiceVariousTest extends ContentServiceIntegrationTest {

    private StateContentDTO defaultContent;
    private String groupShortName;

    @Test
    public void addRemoveAuthor() throws Exception {
        final List<UserSimpleDTO> authors = defaultContent.getAuthors();
        assertEquals(1, authors.size());
        final UserSimpleDTO author = authors.get(0);
        final String authorShortName = author.getShortName();
        contentService.removeAuthor(getHash(), defaultContent.getStateToken(), authorShortName);
        final List<UserSimpleDTO> authors2 = getDefaultContent().getAuthors();
        assertEquals(0, authors2.size());
        contentService.addAuthor(getHash(), defaultContent.getStateToken(), authorShortName);
        final List<UserSimpleDTO> authors3 = getDefaultContent().getAuthors();
        assertEquals(1, authors3.size());
        contentService.addAuthor(getHash(), defaultContent.getStateToken(), authorShortName);
        final List<UserSimpleDTO> authors4 = getDefaultContent().getAuthors();
        assertEquals(1, authors4.size());
    }

    @Test
    public void contentRateAndRetrieve() throws Exception {
        contentService.rateContent(getHash(), defaultContent.getStateToken(), 4.5);
        final StateContentDTO again = (StateContentDTO) contentService.getContent(getHash(),
                defaultContent.getStateToken());
        assertEquals(true, again.isRateable());
        assertEquals(new Double(4.5), again.getCurrentUserRate());
        assertEquals(new Double(4.5), again.getRate());
        assertEquals(new Integer(1), again.getRateByUsers());
    }

    @Test
    public void contentSetLanguage() throws Exception {
        contentService.setLanguage(getHash(), defaultContent.getStateToken(), "es");
        final StateContentDTO contentRetrieved = (StateContentDTO) contentService.getContent(getHash(),
                defaultContent.getStateToken());
        assertEquals("es", contentRetrieved.getLanguage().getCode());
    }

    @Ignore
    // FIXME: when State refactor do this test (with noLogin and without)
    public void defAdminDontShowAsParticipation() throws Exception {
        doLogin();
        final StateContentDTO content = (StateContentDTO) contentService.getContent(getHash(), new StateToken(
                getSiteAdminShortName()));
        assertEquals(0, content.getParticipation().getGroupsIsCollab().size());
        assertEquals(1, content.getParticipation().getGroupsIsAdmin().size());
    }

    @Test
    public void folderRename() throws Exception {
        doLogin();
        defaultContent = getDefaultContent();

        final String oldTitle = "some title";
        String newTitle = "folder new name";
        final StateContainerDTO newState = contentService.addFolder(session.getHash(),
                defaultContent.getStateToken(), oldTitle, DocumentClientTool.TYPE_FOLDER);

        final ContainerDTO newFolder = newState.getContainer();

        assertEquals(oldTitle, newFolder.getName());

        final StateToken folderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
                newFolder.getId().toString(), null);
        String result = contentService.renameContainer(getHash(), folderToken, newTitle);

        assertEquals(newTitle, result);

        final StateToken newFolderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
                newFolder.getId().toString(), null);
        StateContainerDTO folderAgain = (StateContainerDTO) contentService.getContent(getHash(),
                newFolderToken);

        assertEquals(newTitle, folderAgain.getContainer().getName());

        newTitle = "folder last name";

        result = contentService.renameContainer(getHash(), newFolderToken, newTitle);

        folderAgain = (StateContainerDTO) contentService.getContent(getHash(), newFolderToken);

        assertEquals(newTitle, folderAgain.getContainer().getName());
    }

    @Test(expected = AccessViolationException.class)
    public void folderRenameOtherGroupFails() throws Exception {
        doLogin();
        defaultContent = getDefaultContent();
        final ContainerDTO folder = defaultContent.getContainer();
        final StateToken folderToken = new StateToken("otherGroup", defaultContent.getStateToken().getTool(),
                folder.getId().toString(), null);

        final String newTitle = "folder new name";
        contentService.renameContainer(getHash(), folderToken, newTitle);
    }

    @Test(expected = RuntimeException.class)
    public void folderRootRenameMustFail() throws Exception {
        doLogin();
        defaultContent = getDefaultContent();
        final ContainerDTO folder = defaultContent.getContainer();

        final String newTitle = "folder new name";
        final StateToken folderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
                folder.getId().toString(), null);
        final String result = contentService.renameContainer(getHash(), folderToken, newTitle);

        assertEquals(newTitle, result);

        final ContainerDTO folderAgain = getDefaultContent().getContainer();

        assertEquals(newTitle, folderAgain.getName());
    }

    @Before
    public void init() throws Exception {
        new IntegrationTestHelper(this);
        doLogin();
        defaultContent = getDefaultContent();
        groupShortName = defaultContent.getStateToken().getGroup();
    }

    @Test
    public void setTagsAndResults() throws Exception {
        contentService.setTags(getHash(), defaultContent.getStateToken(), "bfoo cfoa afoo2");
        final List<TagResultDTO> summaryTags = contentService.getSummaryTags(getHash(), defaultContent.getStateToken());
        assertEquals(3, summaryTags.size());

        TagResultDTO tagResultDTO = summaryTags.get(0);
        assertEquals("afoo2", tagResultDTO.getName());
        assertEquals(1, (long) tagResultDTO.getCount());

        tagResultDTO = summaryTags.get(1);
        assertEquals("bfoo", tagResultDTO.getName());
        assertEquals(1, (long) tagResultDTO.getCount());

        tagResultDTO = summaryTags.get(2);
        assertEquals("cfoa", tagResultDTO.getName());
        assertEquals(1, (long) tagResultDTO.getCount());
    }

    @Test
    public void setTagsAndRetrieve() throws Exception {
        contentService.setTags(getHash(), defaultContent.getStateToken(), "foo foa foo");
        final String tagsRetrieved = getDefaultContent().getTags();
        assertEquals("foo foa", tagsRetrieved);
    }

    @Test
    public void testSetAsDefContent() throws Exception {
        doLogin();
        defaultContent = getDefaultContent();

        final StateContainerDTO added = contentService.addContent(session.getHash(),
                defaultContent.getStateToken(), "New Content Title");
        assertNotNull(added);

        final ContentSimpleDTO newDefContent = contentService.setAsDefaultContent(session.getHash(),
                added.getStateToken());

        assertTrue(!defaultContent.getStateToken().equals(newDefContent.getStateToken()));
        assertTrue(added.getStateToken().equals(newDefContent.getStateToken()));
    }

    @Test
    public void tokenRename() throws Exception {
        doLogin();
        defaultContent = getDefaultContent();
        final ContainerDTO folder = defaultContent.getContainer();

        final String oldTitle = "some title";
        String newTitle = "folder new name";
        final StateContainerDTO newState = contentService.addFolder(session.getHash(), folder.getStateToken(),
                oldTitle, DocumentClientTool.TYPE_FOLDER);

        final ContainerDTO newFolder = newState.getContainer();

        assertEquals(oldTitle, newFolder.getName());

        newTitle = "folder last name";

        final String result = contentService.renameContainer(getHash(), newState.getStateToken(), newTitle);

        assertEquals(newTitle, result);

        final StateContainerDTO folderAgain = (StateContainerDTO) contentService.getContent(getHash(),
                newState.getStateToken());

        assertEquals(newTitle, folderAgain.getContainer().getName());
    }

}
