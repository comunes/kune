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
package cc.kune.lists.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.lists.client.rpc.ListsService;
import cc.kune.lists.shared.ListsConstants;

import com.google.inject.Inject;

public class ListsServiceTest extends IntegrationTest {
  private StateContainerDTO closeList;
  @Inject
  ContentService contentService;
  @Inject
  ListsService listsService;
  private StateContainerDTO openList;
  private GroupDTO userGroup;

  @Before
  public void init() throws DefaultException, IOException {
    new IntegrationTestHelper(this);
    final UserInfoDTO userInfoDTO = doLogin();
    userGroup = userInfoDTO.getUserGroup();
    final StateAbstractDTO rootList = contentService.getContent(getHash(), new StateToken(
        getDefSiteShortName(), ListsConstants.ROOT_NAME));
    openList = listsService.createList(getHash(), rootList.getStateToken(), "test list", "", true);
    closeList = listsService.createList(getHash(), rootList.getStateToken(), "test list", "", false);
  }

  @Test(expected = AccessViolationException.class)
  public void postToCloseShouldFailTest() throws DefaultException, IOException {
    doLogout();
    doLoginWithDummyUser();
    listsService.newPost(getHash(), closeList.getStateToken(), "lalala");
  }

  @Test
  public void postToOpenByOthersTest() throws DefaultException, IOException {
    doLogout();
    doLoginWithDummyUser();
    final StateContentDTO newPost = listsService.newPost(getHash(), openList.getStateToken(), "lalala");
    // FIXME: check participation
    assertNotNull(newPost);
  }

  @Test
  public void postToOpenCloseAndLaterOpenByOthersTest() throws DefaultException, IOException {
    listsService.setPublic(token, openList.getStateToken(), false);
    listsService.setPublic(token, openList.getStateToken(), true);
    doLogout();
    doLoginWithDummyUser();
    final StateContentDTO newPost = listsService.newPost(getHash(), openList.getStateToken(), "lalala");
    assertNotNull(newPost);
  }

  @Test
  public void subscribeSeveralToCloseTest() throws DefaultException, IOException {
    subscribeUnsubsSeveral(closeList);
  }

  @Test
  public void subscribeSeveralToPublicTest() {
    subscribeUnsubsSeveral(openList);
  }

  @Test(expected = AccessViolationException.class)
  public void subscribeToCloseShouldFailTest() throws DefaultException, IOException {
    doLogout();
    doLoginWithDummyUser();
    listsService.subscribeToList(getHash(), closeList.getStateToken(), true);
  }

  @Test(expected = AccessViolationException.class)
  public void subscribeToOpenAndLaterCloseListShouldFailTest() throws DefaultException, IOException {
    listsService.setPublic(token, openList.getStateToken(), false);
    doLogout();
    doLoginWithDummyUser();
    listsService.subscribeToList(getHash(), openList.getStateToken(), true);
  }

  private void subscribeUnsubsSeveral(final StateContainerDTO list) {
    final AccessListsDTO initialAcl = list.getAccessLists();
    assertTrue(initialAcl.getAdmins().includes(userGroup));
    assertFalse(initialAcl.getEditors().getList().contains(userGroup));

    StateContainerDTO state = listsService.subscribeToList(getHash(), list.getStateToken(), true);
    AccessListsDTO acl = state.getAccessLists();
    assertTrue(acl.getEditors().includes(userGroup));
    assertTrue(acl.getAdmins().includes(userGroup));

    state = listsService.subscribeToList(getHash(), list.getStateToken(), false);
    acl = state.getAccessLists();
    assertFalse(acl.getEditors().includes(userGroup));
    assertTrue(acl.getAdmins().includes(userGroup));

    state = listsService.subscribeToList(getHash(), list.getStateToken(), true);
    acl = state.getAccessLists();
    assertTrue(acl.getEditors().includes(userGroup));
    assertTrue(acl.getEditors().getList().contains(userGroup));
    assertTrue(acl.getAdmins().includes(userGroup));
  }

}
