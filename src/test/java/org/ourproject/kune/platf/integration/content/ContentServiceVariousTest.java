package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.StateToken;
import cc.kune.core.shared.dto.TagCloudResultDTO;
import cc.kune.core.shared.dto.TagCountDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

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
        final List<UserSimpleDTO> authors2 = getSiteDefaultContent().getAuthors();
        assertEquals(0, authors2.size());
        contentService.addAuthor(getHash(), defaultContent.getStateToken(), authorShortName);
        final List<UserSimpleDTO> authors3 = getSiteDefaultContent().getAuthors();
        assertEquals(1, authors3.size());
        contentService.addAuthor(getHash(), defaultContent.getStateToken(), authorShortName);
        final List<UserSimpleDTO> authors4 = getSiteDefaultContent().getAuthors();
        assertEquals(1, authors4.size());
    }

    @Test
    public void contentRateAndRetrieve() throws Exception {
        contentService.rateContent(getHash(), defaultContent.getStateToken(), 4.5);
        final StateContentDTO again = (StateContentDTO) contentService.getContent(getHash(),
                defaultContent.getStateToken());
        assertEquals(new Double(4.5), again.getCurrentUserRate());
        assertEquals(new Double(4.5), again.getRate());
        assertEquals(Integer.valueOf(1), again.getRateByUsers());
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
        defaultContent = getSiteDefaultContent();

        final String oldTitle = "some title";
        String newTitle = "folder new name";
        final StateContainerDTO newState = contentService.addFolder(session.getHash(), defaultContent.getStateToken(),
                oldTitle, DocumentClientTool.TYPE_FOLDER);

        final ContainerDTO newFolder = newState.getContainer();

        assertEquals(oldTitle, newFolder.getName());

        final StateToken folderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
                newFolder.getId().toString(), null);
        final StateAbstractDTO result = contentService.renameContainer(getHash(), folderToken, newTitle);

        assertEquals(newTitle, result.getTitle());

        final StateToken newFolderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
                newFolder.getId().toString(), null);
        StateContainerDTO folderAgain = (StateContainerDTO) contentService.getContent(getHash(), newFolderToken);

        assertEquals(newTitle, folderAgain.getContainer().getName());

        newTitle = "folder last name";

        contentService.renameContainer(getHash(), newFolderToken, newTitle);

        folderAgain = (StateContainerDTO) contentService.getContent(getHash(), newFolderToken);

        assertEquals(newTitle, folderAgain.getContainer().getName());
    }

    @Test(expected = AccessViolationException.class)
    public void folderRenameOtherGroupFails() throws Exception {
        doLogin();
        defaultContent = getSiteDefaultContent();
        final ContainerDTO folder = defaultContent.getContainer();
        final StateToken folderToken = new StateToken("otherGroup", defaultContent.getStateToken().getTool(),
                folder.getId().toString(), null);

        final String newTitle = "folder new name";
        contentService.renameContainer(getHash(), folderToken, newTitle);
    }

    @Test(expected = RuntimeException.class)
    public void folderRootRenameMustFail() throws Exception {
        doLogin();
        defaultContent = getSiteDefaultContent();
        final ContainerDTO folder = defaultContent.getContainer();

        final String newTitle = "folder new name";
        final StateToken folderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
                folder.getId().toString(), null);
        final StateAbstractDTO result = contentService.renameContainer(getHash(), folderToken, newTitle);

        assertEquals(newTitle, result.getTitle());

        final ContainerDTO folderAgain = getSiteDefaultContent().getContainer();

        assertEquals(newTitle, folderAgain.getName());
    }

    @Before
    public void init() throws Exception {
        new IntegrationTestHelper(this);
        doLogin();
        defaultContent = getSiteDefaultContent();
        groupShortName = defaultContent.getStateToken().getGroup();
    }

    @Test
    public void setTagsAndResults() throws Exception {
        contentService.setTags(getHash(), defaultContent.getStateToken(), "bfoo cfoa afoo2");
        final TagCloudResultDTO cloudResultDTO = contentService.getSummaryTags(getHash(),
                defaultContent.getStateToken());
        checkResult(cloudResultDTO);
        checkResult(((StateContentDTO) contentService.getContent(getHash(), defaultContent.getStateToken())).getTagCloudResult());
    }

    @Test
    public void setTagsAndRetrieve() throws Exception {
        contentService.setTags(getHash(), defaultContent.getStateToken(), "foo foa foo");
        final String tagsRetrieved = getSiteDefaultContent().getTags();
        assertEquals("foo foa", tagsRetrieved);
    }

    @Test
    public void testSetAsDefContent() throws Exception {
        doLogin();
        defaultContent = getSiteDefaultContent();

        final StateContainerDTO added = contentService.addContent(session.getHash(), defaultContent.getStateToken(),
                "New Content Title", DocumentServerTool.TYPE_DOCUMENT);
        assertNotNull(added);

        final ContentSimpleDTO newDefContent = contentService.setAsDefaultContent(session.getHash(),
                added.getStateToken());

        assertFalse(defaultContent.getStateToken().equals(newDefContent.getStateToken()));
        assertTrue(added.getStateToken().equals(newDefContent.getStateToken()));
    }

    @Test
    public void tokenRename() throws Exception {
        doLogin();
        defaultContent = getSiteDefaultContent();
        final ContainerDTO folder = defaultContent.getContainer();

        final String oldTitle = "some title";
        String newTitle = "folder new name";
        final StateContainerDTO newState = contentService.addFolder(session.getHash(), folder.getStateToken(),
                oldTitle, DocumentClientTool.TYPE_FOLDER);

        final ContainerDTO newFolder = newState.getContainer();

        assertEquals(oldTitle, newFolder.getName());

        newTitle = "folder last name";

        final StateAbstractDTO result = contentService.renameContainer(getHash(), newState.getStateToken(), newTitle);

        assertEquals(newTitle, result.getTitle());

        final StateContainerDTO folderAgain = (StateContainerDTO) contentService.getContent(getHash(),
                newState.getStateToken());

        assertEquals(newTitle, folderAgain.getContainer().getName());
    }

    private void checkResult(final TagCloudResultDTO cloudResultDTO) {
        assertNotNull(cloudResultDTO.getTagCountList());
        final List<TagCountDTO> summaryTags = cloudResultDTO.getTagCountList();
        assertEquals(3, summaryTags.size());

        TagCountDTO tagResultDTO = summaryTags.get(0);
        assertEquals("afoo2", tagResultDTO.getName());
        assertEquals(1, (long) tagResultDTO.getCount());

        tagResultDTO = summaryTags.get(1);
        assertEquals("bfoo", tagResultDTO.getName());
        assertEquals(1, (long) tagResultDTO.getCount());

        tagResultDTO = summaryTags.get(2);
        assertEquals("cfoa", tagResultDTO.getName());
        assertEquals(1, (long) tagResultDTO.getCount());
    }

}
