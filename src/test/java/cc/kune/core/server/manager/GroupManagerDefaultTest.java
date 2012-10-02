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
package cc.kune.core.server.manager;

import static org.junit.Assert.*;

import java.util.TimeZone;

import javax.validation.ConstraintViolationException;

import org.apache.lucene.queryParser.ParseException;
import org.junit.Test;

import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupLongNameInUseException;
import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.core.client.errors.UserRegistrationException;
import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.SocialNetwork;

public class GroupManagerDefaultTest extends PersistencePreLoadedDataTest {

  private static final String PUBLIC_DESCRIP = "Some public descrip";

  @Test
  public void createdGroupShoudHaveValidSocialNetwork() throws Exception {
    final Group group = new Group("short", "longName", defLicense, GroupType.PROJECT);
    groupManager.createGroup(group, user, PUBLIC_DESCRIP);
    final SocialNetwork socialNetwork = group.getSocialNetwork();
    final AccessLists lists = socialNetwork.getAccessLists();
    assertTrue(lists.getAdmins().includes(user.getUserGroup()));
    assertTrue(lists.getEditors().isEmpty());
    assertTrue(lists.getViewers().isEmpty());
    closeTransaction();
  }

  @Test
  public void createGroup() throws Exception {
    final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
        GroupType.PROJECT);
    groupManager.createGroup(group, user, PUBLIC_DESCRIP);
    final Group otherGroup = groupManager.findByShortName("ysei");

    assertEquals(group.getLongName(), otherGroup.getLongName());
    assertEquals(group.getShortName(), otherGroup.getShortName());
    closeTransaction();
  }

  @Test
  public void createGroupAndSearch() throws Exception, ParseException {
    final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
        GroupType.PROJECT);
    groupManager.createGroup(group, user, PUBLIC_DESCRIP);
    closeTransaction();
    groupManager.reIndex();
    final SearchResult<Group> result = groupManager.search("ysei");
    assertEquals(1, result.getSize());
    assertEquals("ysei", result.getList().get(0).getShortName());
    rollbackTransaction();
  }

  @Test(expected = GroupLongNameInUseException.class)
  public void createGroupWithExistingLongName() throws Exception {
    final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
        GroupType.PROJECT);
    groupManager.createGroup(group, user, PUBLIC_DESCRIP);

    final Group group2 = new Group("ysei2", "Yellow Submarine Environmental Initiative", defLicense,
        GroupType.PROJECT);
    group2.setDefaultLicense(defLicense);
    groupManager.createGroup(group2, user, PUBLIC_DESCRIP);

    rollbackTransaction();
  }

  @Test(expected = GroupShortNameInUseException.class)
  public void createGroupWithExistingShortName() throws Exception {
    final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
        GroupType.PROJECT);
    groupManager.createGroup(group, user, PUBLIC_DESCRIP);

    final Group group2 = new Group("ysei", "Yellow Submarine Environmental Initiative 2", defLicense,
        GroupType.PROJECT);
    groupManager.createGroup(group2, user, PUBLIC_DESCRIP);

    rollbackTransaction();
  }

  private void createTestGroup(final int number) throws Exception {
    final Group g = new Group("ysei" + number, "Yellow Submarine Environmental Initiative " + number,
        defLicense, GroupType.PROJECT);
    groupManager.createGroup(g, user, PUBLIC_DESCRIP);
  }

  @Test(expected = EmailAddressInUseException.class)
  public void createUserExistingEmail() throws I18nNotFoundException, GroupShortNameInUseException,
      EmailAddressInUseException {
    userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en", "GB",
        "GMT", true);
    userManager.createUser("test2", "test 2 name", "test1@example.com", "some password", "en", "GB",
        "GMT", true);
  }

  @Test(expected = GroupLongNameInUseException.class)
  public void createUserExistingLongName() throws I18nNotFoundException, GroupShortNameInUseException,
      EmailAddressInUseException {
    userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en", "GB",
        "GMT", true);
    userManager.createUser("test2", "test 1 name", "test2@example.com", "some password", "en", "GB",
        "GMT", true);
  }

  @Test(expected = GroupShortNameInUseException.class)
  public void createUserExistingShortName() throws I18nNotFoundException, GroupShortNameInUseException,
      EmailAddressInUseException {
    userManager.createUser("test", "test 1 name", "test21@example.com", "some password", "en", "GB",
        "GMT", true);
    userManager.createUser("test", "test 2 name", "test22@example.com", "some password", "en", "GB",
        "GMT", true);
  }

  @Test(expected = EmailAddressInUseException.class)
  public void createUserWithExistingEmail() throws Exception {
    userManager.createUser("username2", "the user name 2", USER_EMAIL, "userPassword", "en", "GB",
        TimeZone.getDefault().getID(), true);
    rollbackTransaction();
  }

  @Test(expected = GroupLongNameInUseException.class)
  public void createUserWithExistingLongName() throws Exception {
    userManager.createUser("username2", USER_LONG_NAME, "email2@example.com", "userPassword", "en",
        "GB", TimeZone.getDefault().getID(), true);
    rollbackTransaction();
  }

  @Test(expected = GroupShortNameInUseException.class)
  public void createUserWithExistingShortName() throws Exception {
    userManager.createUser(USER_SHORT_NAME, "the user name 2", "email2@example.com", "userPassword",
        "en", "GB", TimeZone.getDefault().getID(), true);
    rollbackTransaction();
  }

  @Test(expected = UserRegistrationException.class)
  public void createUserWithIncorrectShortName() throws Exception {
    userManager.createUser("u s", "the user name 2", "email2@example.com", "userPassword", "en", "GB",
        TimeZone.getDefault().getID(), true);
    rollbackTransaction();
  }

  @Test(expected = ConstraintViolationException.class)
  public void createUserWithVeryShortName() throws Exception {
    userManager.createUser("us", "the user name 2", "email2@example.com", "userPassword", "en", "GB",
        TimeZone.getDefault().getID(), true);
    rollbackTransaction();
  }

  @Test
  public void groupSearchPagination() throws Exception, ParseException {
    for (int i = 1; i < 10; i++) {
      createTestGroup(i);
    }
    closeTransaction();
    groupManager.reIndex();
    final SearchResult<Group> result = groupManager.search("Yellow", 0, 5);
    assertEquals(9, result.getSize());
    assertEquals(5, result.getList().size());
    final SearchResult<Group> result2 = groupManager.search("Yellow", 5, 5);
    assertEquals(9, result2.getSize());
    assertEquals(4, result2.getList().size());
    rollbackTransaction();
  }

  @Test
  public void sameGroupHasSameHash() throws Exception {
    final Group group1 = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
        GroupType.PROJECT);
    final Group group2 = new Group("ysei", "", defLicense, GroupType.PROJECT);
    assertEquals(group1, group2);
    assertEquals(group1.hashCode(), group2.hashCode());
  }
}
