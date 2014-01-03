/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import static cc.kune.docs.shared.DocsToolConstants.TYPE_DOCUMENT;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.CannotDeleteDefaultContentException;
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
import cc.kune.docs.shared.DocsToolConstants;
import cc.kune.trash.shared.TrashToolConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentServiceVariousTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentServiceVariousTest extends ContentServiceIntegrationTest {

  /** The default content. */
  private StateContentDTO defaultContent;

  /** The group short name. */
  private String groupShortName;

  /**
   * Adds the remove author.
   * 
   * @throws Exception
   *           the exception
   */
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

  /**
   * Check result.
   * 
   * @param cloudResult
   *          the cloud result
   */
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

  /**
   * Content rate and retrieve.
   * 
   * @throws Exception
   *           the exception
   */
  @Ignore
  @Test
  public void contentRateAndRetrieve() throws Exception {
    contentService.rateContent(getHash(), defaultContent.getStateToken(), 4.5);
    final StateContentDTO again = (StateContentDTO) contentService.getContent(getHash(),
        defaultContent.getStateToken());
    assertEquals(new Double(4.5), again.getCurrentUserRate());
    assertEquals(new Double(4.5), again.getRate());
    assertEquals(Integer.valueOf(1), again.getRateByUsers());
  }

  /**
   * Content set language.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void contentSetLanguage() throws Exception {
    contentService.setLanguage(getHash(), defaultContent.getStateToken(), "es");
    final StateContentDTO contentRetrieved = (StateContentDTO) contentService.getContent(getHash(),
        defaultContent.getStateToken());
    assertEquals("es", contentRetrieved.getLanguage().getCode());
  }

  /**
   * Creates the new content.
   * 
   * @return the state container dto
   */
  private StateContainerDTO createNewContent() {
    final StateContainerDTO added = contentService.addContent(session.getHash(),
        defaultContent.getStateToken(), "New Content Title", TYPE_DOCUMENT);
    assertNotNull(added);
    return added;
  }

  /**
   * Def admin dont show as participation.
   * 
   * @throws Exception
   *           the exception
   */
  @Ignore
  // FIXME: when State refactor do this test (with noLogin and without)
  public void defAdminDontShowAsParticipation() throws Exception {
    doLogin();
    final StateContentDTO content = (StateContentDTO) contentService.getContent(getHash(),
        new StateToken(getSiteAdminShortName()));
    assertEquals(0, content.getParticipation().getGroupsIsCollab().size());
    assertEquals(1, content.getParticipation().getGroupsIsAdmin().size());
  }

  /**
   * Def content remove.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = CannotDeleteDefaultContentException.class)
  public void defContentRemove() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();
    contentService.delContent(session.getHash(), defaultContent.getStateToken());
  }

  /**
   * Del and purge content.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void delAndPurgeContent() throws Exception {
    doLogin();
    final StateContainerDTO trash = getTrash();
    final StateContainerDTO added = createNewContent();
    final StateAbstractDTO retrievedContent = contentService.getContent(session.getHash(),
        added.getStateToken());
    assertNotNull(retrievedContent.getStateToken());
    final StateContainerDTO deleledContainer = contentService.delContent(session.getHash(),
        retrievedContent.getStateToken());
    assertEquals(1, defaultContent.getContainer().getContents().size());
    assertEquals(1, deleledContainer.getContainer().getContents().size());
    final StateContentDTO deletedContent = (StateContentDTO) contentService.getContent(
        session.getHash(),
        retrievedContent.getStateToken().setTool(TrashToolConstants.TOOL_NAME).setFolder(
            trash.getStateToken().getFolder()));
    assertEquals(TrashToolConstants.TOOL_NAME, deletedContent.getToolName());
    assertEquals(1, deletedContent.getContainer().getContents().size());
    final StateContainerDTO trashAfterPurge = contentService.purgeContent(session.getHash(),
        deletedContent.getStateToken());
    assertEquals(0, trashAfterPurge.getContainer().getContents().size());
  }

  /**
   * Del andy purge container.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void delAndyPurgeContainer() throws Exception {
    doLogin();
    final StateContainerDTO state = contentService.addFolder(session.getHash(),
        defaultContent.getStateToken(), "some folder", DocsToolConstants.TYPE_FOLDER);
    final ContainerDTO newFolder = state.getContainer();

    final StateContainerDTO trash = getTrash();
    assertEquals(0, trash.getContainer().getContents().size());
    assertEquals(0, trash.getContainer().getChilds().size());
    contentService.delContent(session.getHash(), newFolder.getStateToken());

    final StateContainerDTO trashAfterDel = getTrash();
    assertEquals(0, trashAfterDel.getContainer().getContents().size());
    assertEquals(1, trashAfterDel.getContainer().getChilds().size());
    final StateContainerDTO deletedFolder = (StateContainerDTO) contentService.getContent(
        session.getHash(),
        newFolder.getStateToken().setTool(TrashToolConstants.TOOL_NAME).setFolder(
            newFolder.getStateToken().getFolder()));
    contentService.purgeContent(session.getHash(), deletedFolder.getStateToken());
    final StateContainerDTO trashAfterPurge = getTrash();
    assertEquals(0, trashAfterPurge.getContainer().getContents().size());
    assertEquals(0, trashAfterPurge.getContainer().getChilds().size());
  }

  /**
   * Folder rename.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void folderRename() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();

    String newTitle = "folder new name";
    final String oldTitle = "some title";
    final StateContainerDTO newState = contentService.addFolder(session.getHash(),
        defaultContent.getStateToken(), oldTitle, DocsToolConstants.TYPE_FOLDER);

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

  /**
   * Folder rename other group fails.
   * 
   * @throws Exception
   *           the exception
   */
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

  /**
   * Folder root rename must fail.
   * 
   * @throws Exception
   *           the exception
   */
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

  /**
   * Gets the trash.
   * 
   * @return the trash
   */
  private StateContainerDTO getTrash() {
    final StateAbstractDTO trash = contentService.getContent(
        session.getHash(),
        defaultContent.getStateToken().copy().clearDocument().clearFolder().setTool(
            TrashToolConstants.TOOL_NAME));
    return (StateContainerDTO) trash;
  }

  /**
   * Inits the.
   * 
   * @throws Exception
   *           the exception
   */
  @Before
  public void init() throws Exception {
    new IntegrationTestHelper(true, this);
    doLogin();
    defaultContent = getSiteDefaultContent();
    groupShortName = defaultContent.getStateToken().getGroup();
  }

  /**
   * Sets the tags and results.
   * 
   * @throws Exception
   *           the exception
   */
  @Ignore
  @Test
  public void setTagsAndResults() throws Exception {
    contentService.setTags(getHash(), defaultContent.getStateToken(), "bfoo cfoa afoo2");
    final TagCloudResult cloudResult = contentService.getSummaryTags(getHash(),
        defaultContent.getStateToken());
    checkResult(cloudResult);
    checkResult(((StateContentDTO) contentService.getContent(getHash(), defaultContent.getStateToken())).getTagCloudResult());
  }

  /**
   * Sets the tags and retrieve.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void setTagsAndRetrieve() throws Exception {
    contentService.setTags(getHash(), defaultContent.getStateToken(), "foo foa foo");
    final String tagsRetrieved = getSiteDefaultContent().getTags();
    assertEquals("foo foa", tagsRetrieved);
  }

  /**
   * Test set as def content.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testSetAsDefContent() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();

    final StateContainerDTO added = createNewContent();

    final ContentSimpleDTO newDefContent = contentService.setAsDefaultContent(session.getHash(),
        added.getStateToken());

    assertFalse(defaultContent.getStateToken().equals(newDefContent.getStateToken()));
    assertTrue(added.getStateToken().equals(newDefContent.getStateToken()));
  }

  /**
   * Token rename.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void tokenRename() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();
    final ContainerDTO folder = defaultContent.getContainer();

    final String oldTitle = "some title";
    String newTitle = "folder new name";
    final StateContainerDTO newState = contentService.addFolder(session.getHash(),
        folder.getStateToken(), oldTitle, DocsToolConstants.TYPE_FOLDER);

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
