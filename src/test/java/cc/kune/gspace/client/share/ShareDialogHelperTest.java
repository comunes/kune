package cc.kune.gspace.client.share;

import static cc.kune.core.shared.dto.GroupListDTO.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupListDTO;
import cc.kune.core.shared.dto.GroupType;

import com.google.gwt.dev.util.collect.HashSet;

public class ShareDialogHelperTest {

  private static final String EVERYONE_IN_WAVE = "@example.com";
  private static final String SOMEBODY1 = "somebody1@example.com";
  private static final String SOMEBODY2 = "somebody2@example.com";
  private GroupDTO group1;
  private GroupDTO group2;
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
    group1 = new GroupDTO("shortname1", "longname 1", GroupType.PROJECT);
    group2 = new GroupDTO("shortname2", "longname 2", GroupType.PROJECT);
    group3 = new GroupDTO("shortname3", "longname 3", GroupType.PROJECT);
    helper = new ShareDialogHelper(shareToList, shareToTheNet, shareToOthers);
    helper.init(EVERYONE_IN_WAVE);
    shareToListInOrder = Mockito.inOrder(shareToList);
    participants = new ArrayList<String>();
    participants.add(SOMEBODY1);
    participants.add(SOMEBODY2);
  }

  private GroupListDTO list(final GroupDTO... groups) {
    final HashSet<GroupDTO> set = new HashSet<GroupDTO>();
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
    helper.setState(group1, acl);
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(true);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(false);
  }

  @Test
  public void whenListEmptyListNobody() {
    final AccessListsDTO acl = acl(list(group1, group2), list(), NOBODY);
    helper.setState(group1, acl);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenListEveryOneEveryOne() {
    final AccessListsDTO acl = acl(list(group1, group2, group3), EVERYONE, EVERYONE);
    helper.setState(group1, acl);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group3);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addVisibleByAnyone();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(true);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(false);
  }

  @Test
  public void whenListListEveryone() {
    final AccessListsDTO acl = acl(list(group1), list(), EVERYONE);
    helper.setState(group1, acl);
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(true);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenListListListEmpty() {
    final AccessListsDTO acl = acl(list(group1), list(group2), list());
    helper.setState(group1, acl);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenListListListList() {
    final AccessListsDTO acl = acl(list(group1), list(group2), list(group3));
    helper.setState(group1, acl);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addViewer(group3);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenListListNobody() {
    final AccessListsDTO acl = acl(list(group1), list(group2), NOBODY);
    helper.setState(group1, acl);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenListListNobodyAndEveryoneParticipants() {
    final AccessListsDTO acl = acl(list(group1), list(group2), NOBODY);
    participants.add(EVERYONE_IN_WAVE);
    helper.setState(group1, acl, participants);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY2);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addParticipant(EVERYONE_IN_WAVE);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenListListNobodyAndParticipants() {
    final AccessListsDTO acl = acl(list(group1), list(group2), NOBODY);
    helper.setState(group1, acl, participants);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY2);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addEditableByAnyone();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenListNobodyNobody() {
    final AccessListsDTO acl = acl(list(group1, group2), NOBODY, NOBODY);
    helper.setState(group1, acl);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addAdmin(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

  @Test
  public void whenListNobodyNobodyAndParticipants() {
    final AccessListsDTO acl = acl(list(group1), NOBODY, NOBODY);
    helper.setState(group1, acl, participants);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addOwner(group1);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addEditor(group2);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY1);
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addParticipant(SOMEBODY2);
    shareToListInOrder.verify(shareToList, Mockito.times(0)).addNotEditableByOthers();
    shareToListInOrder.verify(shareToList, Mockito.times(1)).addNotVisibleByOthers();
    Mockito.verify(shareToTheNet, Mockito.times(1)).setVisible(false);
    Mockito.verify(shareToOthers, Mockito.times(1)).setVisible(true);
  }

}
