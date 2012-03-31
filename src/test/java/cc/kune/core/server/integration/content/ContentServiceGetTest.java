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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.StateNoContentDTO;

public class ContentServiceGetTest extends ContentServiceIntegrationTest {

  @Test
  public void contentOfUserWithNoHomePage() throws Exception {
    final String userHash = doLogin().getUserHash();
    final StateAbstractDTO response = contentService.getContent(userHash, new StateToken(
        getSiteAdminShortName()));
    assertTrue(response instanceof StateNoContentDTO);
  }

  @Test
  public void contentWithLoggedUserIsEditable() throws Exception {
    final String userHash = doLogin().getUserHash();
    final StateContentDTO response = (StateContentDTO) contentService.getContent(userHash,
        getSiteDefaultContent().getStateToken());
    assertNotNull(response.getContentRights());
    assertTrue(response.getContentRights().isEditable());
    assertEquals(1, response.getAccessLists().getAdmins().getList().size());
  }

  @Before
  public void create() {
    new IntegrationTestHelper(this);
  }

  @Test
  public void defaultCountentShouldExist() throws Exception {
    final StateContentDTO content = (StateContentDTO) contentService.getContent(null, new StateToken());
    assertNotNull(content);
    assertNotNull(content.getGroup());
    assertNotNull(content.getContainer());
    assertNotNull(content.getContainer().getId());
    assertNotNull(content.getToolName());
    assertNotNull(content.getDocumentId());
  }

  @Test
  public void defContentOfUserWithNoHomePage() throws Exception {
    doLogin();
    final StateAbstractDTO response = contentService.getContent(session.getHash(), new StateToken());
    assertEquals(response.getStateToken(), getSiteDefaultContent().getStateToken());
  }

  @Test
  public void noContentNotLogged() throws Exception {
    final StateContentDTO response = (StateContentDTO) contentService.getContent(null, new StateToken());
    assertNotNull(response);
  }

  @Test(expected = ContentNotFoundException.class)
  public void nonExistentContent() throws Exception {
    contentService.getContent(null, new StateToken("foo foo foo"));
  }

  @Test(expected = ContentNotFoundException.class)
  public void nonExistentContent2() throws Exception {
    contentService.getContent(null, new StateToken("site.foofoo"));
  }

  @Test(expected = ContentNotFoundException.class)
  public void nonExistentContent3() throws Exception {
    contentService.getContent(null, new StateToken("site.docs.foofoo"));
  }

  @Test(expected = ContentNotFoundException.class)
  public void nonExistentContent4() throws Exception {
    final StateContainerDTO stateDTO = getSiteDefaultContent();
    contentService.getContent(null, stateDTO.getStateToken().copy().setDocument("dadaas"));
  }

  @Test(expected = ContentNotFoundException.class)
  public void nonExistentContent5() throws Exception {
    contentService.getContent(null, new StateToken("comm3.docs.19"));
  }

  @Test
  public void notLoggedUserShouldNotEditDefaultDoc() throws Exception {
    final StateContentDTO content = (StateContentDTO) contentService.getContent(null, new StateToken());
    assertFalse(content.getContentRights().isAdministrable());
    assertFalse(content.getContentRights().isEditable());
    assertTrue(content.getContentRights().isVisible());
    assertFalse(content.getContainerRights().isAdministrable());
    assertFalse(content.getContainerRights().isEditable());
    assertTrue(content.getContainerRights().isVisible());
  }

  @Test
  public void unknownContent() throws Exception {
    final StateContainerDTO content = (StateContainerDTO) contentService.getContent(null,
        new StateToken("site.docs"));
    assertNotNull(content);
    assertNotNull(content.getGroup());
    assertNotNull(content.getContainer());
    assertNotNull(content.getContainer().getId());
    assertNotNull(content.getToolName());
  }
}
