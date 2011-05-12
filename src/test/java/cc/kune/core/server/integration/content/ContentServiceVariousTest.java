/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.integration.content;

import static cc.kune.docs.shared.DocsConstants.TYPE_DOCUMENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.TagCount;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.docs.shared.DocsConstants;

public class ContentServiceVariousTest extends ContentServiceIntegrationTest {

  private StateContentDTO defaultContent;
  private String groupShortName;

  @Ignore
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

  private void checkResult(final TagCloudResult cloudResult) {
    assertNotNull(cloudResult.getTagCountList());
    final List<TagCount> summaryTags = cloudResult.getTagCountList();
    assertEquals(3, summaryTags.size());

    TagCount tagResult = summaryTags.get(0);
    assertEquals("afoo2", tagResult.getName());
    assertEquals(1, (long) tagResult.getCount());

    tagResult = summaryTags.get(1);
    assertEquals("bfoo", tagResult.getName());
    assertEquals(1, (long) tagResult.getCount());

    tagResult = summaryTags.get(2);
    assertEquals("cfoa", tagResult.getName());
    assertEquals(1, (long) tagResult.getCount());
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
    final StateContentDTO content = (StateContentDTO) contentService.getContent(getHash(),
        new StateToken(getSiteAdminShortName()));
    assertEquals(0, content.getParticipation().getGroupsIsCollab().size());
    assertEquals(1, content.getParticipation().getGroupsIsAdmin().size());
  }

  @Test
  public void folderRename() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();

    final String oldTitle = "some title";
    String newTitle = "folder new name";
    final StateContainerDTO newState = contentService.addFolder(session.getHash(),
        defaultContent.getStateToken(), oldTitle, DocsConstants.TYPE_FOLDER);

    final ContainerDTO newFolder = newState.getContainer();

    assertEquals(oldTitle, newFolder.getName());

    final StateToken folderToken = new StateToken(groupShortName,
        defaultContent.getStateToken().getTool(), newFolder.getId().toString(), null);
    final StateAbstractDTO result = contentService.renameContainer(getHash(), folderToken, newTitle);

    assertEquals(newTitle, result.getTitle());

    final StateToken newFolderToken = new StateToken(groupShortName,
        defaultContent.getStateToken().getTool(), newFolder.getId().toString(), null);
    StateContainerDTO folderAgain = (StateContainerDTO) contentService.getContent(getHash(),
        newFolderToken);

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
    final StateToken folderToken = new StateToken("otherGroup",
        defaultContent.getStateToken().getTool(), folder.getId().toString(), null);

    final String newTitle = "folder new name";
    contentService.renameContainer(getHash(), folderToken, newTitle);
  }

  @Test(expected = RuntimeException.class)
  public void folderRootRenameMustFail() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();
    final ContainerDTO folder = defaultContent.getContainer();

    final String newTitle = "folder new name";
    final StateToken folderToken = new StateToken(groupShortName,
        defaultContent.getStateToken().getTool(), folder.getId().toString(), null);
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
    final TagCloudResult cloudResult = contentService.getSummaryTags(getHash(),
        defaultContent.getStateToken());
    checkResult(cloudResult);
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

    final StateContainerDTO added = contentService.addContent(session.getHash(),
        defaultContent.getStateToken(), "New Content Title", TYPE_DOCUMENT);
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
    final StateContainerDTO newState = contentService.addFolder(session.getHash(),
        folder.getStateToken(), oldTitle, DocsConstants.TYPE_FOLDER);

    final ContainerDTO newFolder = newState.getContainer();

    assertEquals(oldTitle, newFolder.getName());

    newTitle = "folder last name";

    final StateAbstractDTO result = contentService.renameContainer(getHash(), newState.getStateToken(),
        newTitle);

    assertEquals(newTitle, result.getTitle());

    final StateContainerDTO folderAgain = (StateContainerDTO) contentService.getContent(getHash(),
        newState.getStateToken());

    assertEquals(newTitle, folderAgain.getContainer().getName());
  }

}
