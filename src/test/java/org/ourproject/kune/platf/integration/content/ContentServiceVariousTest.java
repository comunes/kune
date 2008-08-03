package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

public class ContentServiceVariousTest extends ContentServiceIntegrationTest {

    private StateDTO defaultContent;
    private String groupShortName;
    private String defDocument;

    @Test
    public void addRemoveAuthor() throws Exception {
	final List<UserSimpleDTO> authors = defaultContent.getAuthors();
	assertEquals(1, authors.size());
	final UserSimpleDTO author = authors.get(0);
	final String authorShortName = author.getShortName();
	contentService.removeAuthor(getHash(), groupShortName, defDocument, authorShortName);
	final List<UserSimpleDTO> authors2 = getDefaultContent().getAuthors();
	assertEquals(0, authors2.size());
	contentService.addAuthor(getHash(), groupShortName, defDocument, authorShortName);
	final List<UserSimpleDTO> authors3 = getDefaultContent().getAuthors();
	assertEquals(1, authors3.size());
	contentService.addAuthor(getHash(), groupShortName, defDocument, authorShortName);
	final List<UserSimpleDTO> authors4 = getDefaultContent().getAuthors();
	assertEquals(1, authors4.size());
    }

    @Test
    public void contentRateAndRetrieve() throws Exception {
	contentService.rateContent(getHash(), groupShortName, defDocument, 4.5);
	final StateDTO again = contentService.getContent(getHash(), groupShortName, defaultContent.getStateToken());
	assertEquals(true, again.isRateable());
	assertEquals(new Double(4.5), again.getCurrentUserRate());
	assertEquals(new Double(4.5), again.getRate());
	assertEquals(new Integer(1), again.getRateByUsers());
    }

    @Test
    public void contentSetLanguage() throws Exception {
	contentService.setLanguage(getHash(), groupShortName, defDocument, "es");
	final StateDTO contentRetrieved = contentService.getContent(getHash(), groupShortName, defaultContent
		.getStateToken());
	assertEquals("es", contentRetrieved.getLanguage().getCode());
    }

    @Test
    public void folderRename() throws Exception {
	doLogin();
	defaultContent = getDefaultContent();
	final ContainerDTO folder = defaultContent.getFolder();

	final String oldTitle = "some title";
	String newTitle = "folder new name";
	final StateDTO newState = contentService.addFolder(session.getHash(), groupShortName, folder.getId(), oldTitle);

	final ContainerDTO newFolder = newState.getFolder().getChilds().get(0);

	assertEquals(oldTitle, newFolder.getName());

	final StateToken folderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
		newFolder.getId().toString(), null);
	String result = contentService.rename(getHash(), groupShortName, folderToken.getEncoded(), newTitle);

	assertEquals(newTitle, result);

	final StateToken newFolderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
		newFolder.getId().toString(), null);
	StateDTO folderAgain = contentService.getContent(getHash(), groupShortName, newFolderToken);

	assertEquals(newTitle, folderAgain.getFolder().getName());

	newTitle = "folder last name";

	result = contentService.rename(getHash(), groupShortName, newFolderToken.getEncoded(), newTitle);

	folderAgain = contentService.getContent(getHash(), groupShortName, newFolderToken);

	assertEquals(newTitle, folderAgain.getFolder().getName());

    }

    @Test(expected = AccessViolationException.class)
    public void folderRenameOtherGroupFails() throws Exception {
	doLogin();
	defaultContent = getDefaultContent();
	final ContainerDTO folder = defaultContent.getFolder();
	final StateToken folderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(), folder
		.getId().toString(), null);

	final String newTitle = "folder new name";
	contentService.rename(getHash(), super.getSiteAdminShortName(), folderToken.getEncoded(), newTitle);
    }

    @Test(expected = RuntimeException.class)
    public void folderRootRenameMustFail() throws Exception {
	doLogin();
	defaultContent = getDefaultContent();
	final ContainerDTO folder = defaultContent.getFolder();

	final String newTitle = "folder new name";
	final StateToken folderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(), folder
		.getId().toString(), null);
	final String result = contentService.rename(getHash(), groupShortName, folderToken.getEncoded(), newTitle);

	assertEquals(newTitle, result);

	final ContainerDTO folderAgain = getDefaultContent().getFolder();

	assertEquals(newTitle, folderAgain.getName());
    }

    @Before
    public void init() throws Exception {
	new IntegrationTestHelper(this);
	doLogin();
	defaultContent = getDefaultContent();
	groupShortName = defaultContent.getStateToken().getGroup();
	defDocument = defaultContent.getStateToken().getDocument();
    }

    @Test
    public void setTagsAndResults() throws Exception {
	contentService.setTags(getHash(), groupShortName, defDocument, "bfoo cfoa afoo2");
	final List<TagResultDTO> summaryTags = contentService.getSummaryTags(getHash(), groupShortName);
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
	contentService.setTags(getHash(), groupShortName, defDocument, "foo foa foo");
	final String tagsRetrieved = getDefaultContent().getTags();
	assertEquals("foo foa", tagsRetrieved);
    }

    @Test
    public void tokenRename() throws Exception {
	doLogin();
	defaultContent = getDefaultContent();
	final ContainerDTO folder = defaultContent.getFolder();

	final String oldTitle = "some title";
	String newTitle = "folder new name";
	final StateDTO newState = contentService.addFolder(session.getHash(), groupShortName, folder.getId(), oldTitle);

	final ContainerDTO newFolder = newState.getFolder().getChilds().get(0);

	assertEquals(oldTitle, newFolder.getName());

	final StateToken newFolderToken = new StateToken(groupShortName, defaultContent.getStateToken().getTool(),
		newFolder.getId().toString(), null);

	newTitle = "folder last name";

	final String result = contentService.rename(getHash(), groupShortName, newFolderToken.getEncoded(), newTitle);

	assertEquals(newTitle, result);

	final StateDTO folderAgain = contentService.getContent(getHash(), groupShortName, newFolderToken);

	assertEquals(newTitle, folderAgain.getFolder().getName());
    }

}
