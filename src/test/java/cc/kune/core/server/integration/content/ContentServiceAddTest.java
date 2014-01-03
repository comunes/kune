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
import static cc.kune.wiki.shared.WikiToolConstants.TOOL_NAME;
import static cc.kune.wiki.shared.WikiToolConstants.TYPE_WIKIPAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.docs.shared.DocsToolConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentServiceAddTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentServiceAddTest extends ContentServiceIntegrationTest {

  /** The default content. */
  private StateContentDTO defaultContent;

  /**
   * Inits the.
   * 
   * @throws Exception
   *           the exception
   */
  @Before
  public void init() throws Exception {
    new IntegrationTestHelper(true, this);
  }

  /**
   * No logged in should throw illegal access.
   * 
   * @throws ContentNotFoundException
   *           the content not found exception
   * @throws Exception
   *           the exception
   */
  @Test(expected = UserMustBeLoggedException.class)
  public void noLoggedInShouldThrowIllegalAccess() throws ContentNotFoundException, Exception {
    defaultContent = getSiteDefaultContent();
    contentService.addContent(session.getHash(), defaultContent.getContainer().getStateToken(),
        "a name", TYPE_DOCUMENT);
  }

  /**
   * Test add content.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testAddContent() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();
    assertEquals(1, defaultContent.getContainer().getContents().size());
    final AccessRights cntRights = defaultContent.getContentRights();
    final AccessRights ctxRights = defaultContent.getContainerRights();
    final AccessRights groupRights = defaultContent.getGroupRights();

    final String title = "New Content Title";
    final StateContentDTO added = contentService.addContent(session.getHash(),
        defaultContent.getContainer().getStateToken(), title, TYPE_DOCUMENT);
    assertNotNull(added);
    final List<ContentSimpleDTO> contents = added.getContainer().getContents();
    assertEquals(title, added.getTitle());
    assertEquals(2, contents.size());
    assertEquals(cntRights, added.getContentRights());
    assertEquals(ctxRights, added.getContainerRights());
    assertEquals(groupRights, added.getGroupRights());
    assertNotNull(added.getGroupMembers());
    assertNotNull(added.getParticipation());
    assertNotNull(added.getAccessLists());

    final StateToken newState = added.getStateToken();
    final StateContentDTO sameAgain = (StateContentDTO) contentService.getContent(session.getHash(),
        newState);
    assertNotNull(sameAgain);
    assertEquals(2, sameAgain.getContainer().getContents().size());
  }

  /**
   * Test add folder.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testAddFolder() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();
    final ContainerDTO parent = defaultContent.getContainer();
    final String title = "folder name";
    final StateContainerDTO newState = contentService.addFolder(session.getHash(),
        parent.getStateToken(), title, DocsToolConstants.TYPE_FOLDER);
    assertNotNull(newState);
    assertNotNull(newState.getGroupMembers());
    assertNotNull(newState.getParticipation());
    assertNotNull(newState.getAccessLists());
    assertNotNull(newState.getContainerRights());
    assertNotNull(newState.getGroupRights());
    assertNotNull(newState.getRootContainer().getContents().get(0).getRights());

    final ContainerDTO parentAgain = getSiteDefaultContent().getContainer();
    final ContainerSimpleDTO child = parentAgain.getChilds().get(0);
    assertEquals(parent.getStateToken().getFolder(), child.getParentToken().getFolder());

    assertEquals(parent.getId(), parentAgain.getId());
    assertEquals(1, parentAgain.getChilds().size());
  }

  // @Test
  /**
   * Test add room.
   * 
   * @throws Exception
   *           the exception
   */
  public void testAddRoom() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();
    final ContainerDTO parent = defaultContent.getContainer();
    final String roomName = "testroom";
    final StateContainerDTO newState = contentService.addRoom(session.getHash(), parent.getStateToken(),
        roomName);
    assertNotNull(newState);
  }

  /**
   * Test add two folders.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testAddTwoFolders() throws Exception {
    doLogin();
    defaultContent = getSiteDefaultContent();
    final ContainerDTO parent = defaultContent.getContainer();
    final String title = "folder name";
    final StateContainerDTO newState = contentService.addFolder(session.getHash(),
        parent.getStateToken(), title, DocsToolConstants.TYPE_FOLDER);
    assertNotNull(newState);

    final StateContainerDTO newState2 = contentService.addFolder(session.getHash(),
        parent.getStateToken(), title, DocsToolConstants.TYPE_FOLDER);
    assertNotNull(newState2);

    final ContainerDTO parentAgain = getSiteDefaultContent().getContainer();

    assertEquals(parent.getId(), parentAgain.getId());
    assertEquals(2, parentAgain.getChilds().size());
  }

  /**
   * Test add wiki content.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testAddWikiContent() throws Exception {
    doLogin();

    final StateToken wikiToken = new StateToken(super.getDefSiteShortName(), TOOL_NAME);
    final StateContainerDTO wiki = (StateContainerDTO) contentService.getContent(session.getHash(),
        wikiToken);

    final String title = "New wikipage";
    final StateContentDTO added = contentService.addContent(session.getHash(), wiki.getStateToken(),
        title, TYPE_WIKIPAGE);
    assertNotNull(added);
    final ContainerDTO wikiContainer = added.getContainer();
    final List<ContentSimpleDTO> contents = wikiContainer.getContents();
    assertEquals(title, added.getTitle());
    assertEquals(1, contents.size());
    doLogout();

    doLoginWithDummyUser();
    // contentService.save(getHash(), added.getStateToken(), "some new test");
    // assertEquals(cntRights, added.getContentRights());
    // assertEquals(ctxRights, added.getContainerRights());
    // assertEquals(groupRights, added.getGroupRights());
    // assertNotNull(added.getGroupMembers());
    // assertNotNull(added.getParticipation());
    // assertNotNull(added.getAccessLists());
  }
}
