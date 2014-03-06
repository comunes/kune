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
package cc.kune.gspace.client.share;

import static cc.kune.core.shared.dto.GroupListDTO.*;
import static cc.kune.docs.shared.DocsToolConstants.TYPE_DOCUMENT;
import static cc.kune.wiki.shared.WikiToolConstants.TYPE_WIKIPAGE;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupListDTO;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.gspace.client.actions.share.ShareMenu;
import cc.kune.lists.shared.ListsToolConstants;

public class ShareDialogHelperTest {
  private static final String DOMAIN = "example.com";
  private static final String EVERYONE_IN_WAVE = "@" + DOMAIN;
  private static final String SOMEBODY1 = "somebody1@example.com";
  private static final String SOMEBODY2 = "somebody2@example.com";
  private GroupDTO currentGroup;
  private String currentUser;
  private GroupDTO group1;
  private String group1address;
  private GroupDTO group2;
  private String group2address;
  private GroupDTO group3;
  private ShareDialogHelper helper;
  private ArrayList<String> participants;
  private ShareToListView shareToList;
  private InOrder shareToListInOrder;
  private ShareToOthersView shareToOthers;
  private ShareToTheNetView shareToTheNet;

  private AccessListsDTO acl(final GroupListDTO adminList, final GroupListDTO editorList,
      final GroupListDTO viewerList) {
    final AccessListsDTO acl = acl(NORMAL, NORMAL, NORMAL);
    acl.setAdmins(adminList);
    acl.setEditors(editorList);
    acl.setViewers(viewerList);
    return acl;
  }

  private AccessListsDTO acl(final GroupListDTO adminList, final GroupListDTO editorList,
      final String viewMode) {
    final AccessListsDTO acl = acl(NORMAL, NORMAL, viewMode);
    acl.setAdmins(adminList);
    acl.setEditors(editorList);
    return acl;
  }

  private AccessListsDTO acl(final GroupListDTO adminList, final String editMode, final String viewMode) {
    final AccessListsDTO acl = acl(NORMAL, editMode, viewMode);
    acl.setAdmins(adminList);
    return acl;
  }

  @SuppressWarnings("unused")
  private AccessListsDTO acl(final String adminMode, final String editorMode, final GroupListDTO viewList) {
    final AccessListsDTO acl = acl(adminMode, editorMode, NORMAL);
    acl.setViewers(viewList);
    return acl;
  }

  private AccessListsDTO acl(final String adminMode, final String editMode, final String viewMode) {
    final GroupListDTO adminsList = new GroupListDTO();
    final GroupListDTO editorsList = new GroupListDTO();
    final GroupListDTO viewList = new GroupListDTO();
    adminsList.setMode(adminMode);
    editorsList.setMode(editMode);
    viewList.setMode(viewMode);
    final AccessListsDTO acl = new AccessListsDTO(adminsList, editorsList, viewList);
    return acl;
  }

  @Before
  public void before() {
    shareToOthers = Mockito.mock(ShareToOthersView.class);
    shareToList = Mockito.mock(ShareToListView.class);
    shareToTheNet = Mockito.mock(ShareToTheNetView.class);
    final ShareMenu menuBtn = Mockito.mock(ShareMenu.class);
    currentGroup = new GroupDTO("current", "current", GroupType.PROJECT);
    group1 = new GroupDTO("shortname1", "longname 1", GroupType.PROJECT);
    group2 = new GroupDTO("shortname2", "longname 2", GroupType.PROJECT);
    group3 = new GroupDTO("shortname3", "longname 3", GroupType.PROJECT);
    helper = new ShareDialogHelper(shareToList, shareToTheNet, shareToOthers, menuBtn);
    helper.init(DOMAIN);
    shareToListInOrder = Mockito.inOrder(shareToList);
    participants = new ArrayList<String>();
    participants.add(SOMEBODY1);
    participants.add(SOMEBODY2);
    group1address = group1.getShortName() + "@" + DOMAIN;
    currentUser = group1address;
    group2address = group2.getShortName() + "@" + DOMAIN;
  }

  private GroupListDTO list(final GroupDTO... groups) {
    final Set<GroupDTO> set = new LinkedHashSet<GroupDTO>();
    for (final GroupDTO group : groups) {
      set.add(group);
    }
    final GroupListDTO gList = new GroupListDTO(set);
    gList.setMode(NORMAL);
    return gList;
  }

  @Test
  public void whenEveryone() {
    final AccessListsDTO acl = acl(EVERYONE, EVERYONE, EVERYONE);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT);
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(true);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(false);
  }

  @Test
  public void whenShareDocToEmptyListNobody() {
    final AccessListsDTO acl = acl(list(currentGroup, group1, group2), list(), NOBODY);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareDocToEveryOneEveryOne() {
    final AccessListsDTO acl = acl(list(currentGroup, group1, group2, group3), EVERYONE, EVERYONE);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group3);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addVisibleByAnyone();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(true);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(false);
  }

  @Test
  public void whenShareDocToListEveryone() {
    final AccessListsDTO acl = acl(list(currentGroup, group1), list(), EVERYONE);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT);
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(true);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareDocToListListEmpty() {
    final AccessListsDTO acl = acl(list(currentGroup, group1), list(group2), list());
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareDocToListListList() {
    final AccessListsDTO acl = acl(list(group1), list(group2), list(group3));
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addViewer(group3);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareDocToListNobody() {
    final AccessListsDTO acl = acl(list(group1), list(group2), NOBODY);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareDocToListNobodyAndEveryoneParticipants() {
    final AccessListsDTO acl = acl(list(currentGroup, group1), list(group2), NOBODY);
    participants.add(EVERYONE_IN_WAVE);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT, group1address, participants);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY1, false);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY2, false);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addParticipant(EVERYONE_IN_WAVE);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareDocToListNobodyAndParticipants() {
    final AccessListsDTO acl = acl(list(currentGroup, group1), list(group2), NOBODY);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT, group1address, participants);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY1, false);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY2, false);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareDocToNobodyNobody() {
    final AccessListsDTO acl = acl(list(currentGroup, group1, group2), NOBODY, NOBODY);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareDocToNobodyNobodyAndParticipants() {
    final AccessListsDTO acl = acl(list(currentGroup, group1), NOBODY, NOBODY);
    helper.setState(currentGroup, currentUser, acl, TYPE_DOCUMENT, group1address, participants);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY1, false);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY2, false);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareListToGroupListAndClosed() {
    final AccessListsDTO acl = acl(list(group1), list(group1, group2), NOBODY);
    helper.setState(currentGroup, currentUser, acl, ListsToolConstants.TYPE_LIST);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditor(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareListToGroupListAndPublic() {
    final AccessListsDTO acl = acl(list(group1), EVERYONE, EVERYONE);
    helper.setState(currentGroup, currentUser, acl, ListsToolConstants.TYPE_LIST);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotVisibleByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addVisibleByAnyone();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(true);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareWikiEditable() {
    final AccessListsDTO acl = acl(list(group1), list(group2), NOBODY);
    participants.add(EVERYONE_IN_WAVE);
    participants.add(group1address);
    participants.add(group2address);
    helper.setState(currentGroup, currentUser, acl, TYPE_WIKIPAGE, group1address, participants);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(group1address, true);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(group2address, false);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenShareWikiNotEditable() {
    final AccessListsDTO acl = acl(list(group1), list(group2), NOBODY);
    participants.add(group2address);
    helper.setState(currentGroup, currentUser, acl, TYPE_WIKIPAGE, group1address, participants);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(currentGroup);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addAdmin(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(group2address, false);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

}
