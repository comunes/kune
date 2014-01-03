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
package cc.kune.lists.server;

import static org.junit.Assert.*;

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
import cc.kune.lists.shared.ListsToolConstants;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class ListsServiceTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ListsServiceTest extends IntegrationTest {

  /** The close list. */
  private StateContainerDTO closeList;

  /** The content service. */
  @Inject
  ContentService contentService;

  /** The lists service. */
  @Inject
  ListsService listsService;

  /** The open list. */
  private StateContainerDTO openList;

  /** The user group. */
  private GroupDTO userGroup;

  /**
   * Inits the.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Before
  public void init() throws DefaultException, IOException {
    new IntegrationTestHelper(true, this);
    final UserInfoDTO userInfoDTO = doLogin();
    userGroup = userInfoDTO.getUserGroup();
    final StateAbstractDTO rootList = contentService.getContent(getHash(), new StateToken(
        getDefSiteShortName(), ListsToolConstants.ROOT_NAME));
    openList = listsService.createList(getHash(), rootList.getStateToken(), "test list", "", true);
    closeList = listsService.createList(getHash(), rootList.getStateToken(), "test list", "", false);
  }

  /**
   * Post to close should fail test.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test(expected = AccessViolationException.class)
  public void postToCloseShouldFailTest() throws DefaultException, IOException {
    doLogout();
    doLoginWithDummyUser();
    listsService.newPost(getHash(), closeList.getStateToken(), "lalala");
  }

  /**
   * Post to open by others test.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void postToOpenByOthersTest() throws DefaultException, IOException {
    doLogout();
    doLoginWithDummyUser();
    final StateContentDTO newPost = listsService.newPost(getHash(), openList.getStateToken(), "lalala");
    // FIXME: check participation
    assertNotNull(newPost);
  }

  /**
   * Post to open close and later open by others test.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void postToOpenCloseAndLaterOpenByOthersTest() throws DefaultException, IOException {
    listsService.setPublic(token, openList.getStateToken(), false);
    listsService.setPublic(token, openList.getStateToken(), true);
    doLogout();
    doLoginWithDummyUser();
    final StateContentDTO newPost = listsService.newPost(getHash(), openList.getStateToken(), "lalala");
    assertNotNull(newPost);
  }

  /**
   * Subscribe several to close test.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void subscribeSeveralToCloseTest() throws DefaultException, IOException {
    subscribeUnsubsSeveral(closeList);
  }

  /**
   * Subscribe several to public test.
   */
  @Test
  public void subscribeSeveralToPublicTest() {
    subscribeUnsubsSeveral(openList);
  }

  /**
   * Subscribe to close should fail test.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test(expected = AccessViolationException.class)
  public void subscribeToCloseShouldFailTest() throws DefaultException, IOException {
    doLogout();
    doLoginWithDummyUser();
    listsService.subscribeMyselfToList(getHash(), closeList.getStateToken(), true);
  }

  /**
   * Subscribe to open and later close list should fail test.
   * 
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test(expected = AccessViolationException.class)
  public void subscribeToOpenAndLaterCloseListShouldFailTest() throws DefaultException, IOException {
    listsService.setPublic(token, openList.getStateToken(), false);
    doLogout();
    doLoginWithDummyUser();
    listsService.subscribeMyselfToList(getHash(), openList.getStateToken(), true);
  }

  /**
   * Subscribe unsubs several.
   * 
   * @param list
   *          the list
   */
  private void subscribeUnsubsSeveral(final StateContainerDTO list) {
    final AccessListsDTO initialAcl = list.getAccessLists();
    assertTrue(initialAcl.getAdmins().includes(userGroup));
    assertTrue(initialAcl.getEditors().getList().contains(userGroup));

    StateContainerDTO state = listsService.subscribeMyselfToList(getHash(), list.getStateToken(), true);
    AccessListsDTO acl = state.getAccessLists();
    assertTrue(acl.getEditors().includes(userGroup));
    assertTrue(acl.getAdmins().includes(userGroup));

    state = listsService.subscribeMyselfToList(getHash(), list.getStateToken(), false);
    acl = state.getAccessLists();
    assertFalse(acl.getEditors().includes(userGroup));
    assertTrue(acl.getAdmins().includes(userGroup));

    state = listsService.subscribeMyselfToList(getHash(), list.getStateToken(), true);
    acl = state.getAccessLists();
    assertTrue(acl.getEditors().includes(userGroup));
    assertTrue(acl.getEditors().getList().contains(userGroup));
    assertTrue(acl.getAdmins().includes(userGroup));
  }

}
