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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.TimeZone;

import org.apache.lucene.queryParser.ParseException;
import org.hibernate.validator.InvalidStateException;
import org.junit.Test;

import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.GroupNameInUseException;
import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.core.client.errors.UserRegistrationException;
import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.core.server.manager.impl.SearchResult;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.User;

public class GroupManagerTest extends PersistencePreLoadedDataTest {

    @Test
    public void createdGroupShoudHaveValidSocialNetwork() throws Exception {
        final Group group = new Group("short", "longName", defLicense, GroupType.PROJECT);
        groupManager.createGroup(group, user);
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
        groupManager.createGroup(group, user);
        final Group otherGroup = groupManager.findByShortName("ysei");

        assertEquals(group.getLongName(), otherGroup.getLongName());
        assertEquals(group.getShortName(), otherGroup.getShortName());
        closeTransaction();
    }

    @Test
    public void createGroupAndSearch() throws Exception, ParseException {
        final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group, user);
        groupManager.reIndex();
        final SearchResult<Group> result = groupManager.search("ysei");
        assertEquals(1, result.getSize());
        assertEquals("ysei", result.getList().get(0).getShortName());
        rollbackTransaction();
    }

    @Test(expected = GroupNameInUseException.class)
    public void createGroupWithExistingLongName() throws Exception {
        final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group, user);

        final Group group2 = new Group("ysei2", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        group2.setDefaultLicense(defLicense);
        groupManager.createGroup(group2, user);

        rollbackTransaction();
    }

    @Test(expected = GroupNameInUseException.class)
    public void createGroupWithExistingShortName() throws Exception {
        final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group, user);

        final Group group2 = new Group("ysei", "Yellow Submarine Environmental Initiative 2", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group2, user);

        rollbackTransaction();
    }

    private void createTestGroup(final int number) throws Exception {
        final Group g = new Group("ysei" + number, "Yellow Submarine Environmental Initiative " + number, defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(g, user);
    }

    @Test(expected = EmailAddressInUseException.class)
    public void createUserExistingEmail() throws I18nNotFoundException, GroupNameInUseException,
            EmailAddressInUseException {
        final User user1 = userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en",
                "GB", "GMT");
        groupManager.createUserGroup(user1);
        final User user2 = userManager.createUser("test2", "test 2 name", "test1@example.com", "some password", "en",
                "GB", "GMT");
        groupManager.createUserGroup(user2);
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserExistingLongName() throws I18nNotFoundException, GroupNameInUseException,
            EmailAddressInUseException {
        final User user1 = userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en",
                "GB", "GMT");
        groupManager.createUserGroup(user1);
        final User user2 = userManager.createUser("test2", "test 1 name", "test2@example.com", "some password", "en",
                "GB", "GMT");
        groupManager.createUserGroup(user2);
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserExistingShortName() throws I18nNotFoundException, GroupNameInUseException,
            EmailAddressInUseException {
        final User user1 = userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en",
                "GB", "GMT");
        groupManager.createUserGroup(user1);
        final User user2 = userManager.createUser("test", "test 2 name", "test2@example.com", "some password", "en",
                "GB", "GMT");
        groupManager.createUserGroup(user2);
    }

    @Test(expected = EmailAddressInUseException.class)
    public void createUserWithExistingEmail() throws Exception {
        final User user2 = userManager.createUser("username2", "the user name 2", USER_EMAIL, "userPassword", "en",
                "GB", TimeZone.getDefault().getID());
        groupManager.createUserGroup(user2);
        rollbackTransaction();
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserWithExistingLongName() throws Exception {
        final User user2 = userManager.createUser("username2", USER_LONG_NAME, "email2@example.com", "userPassword",
                "en", "GB", TimeZone.getDefault().getID());
        groupManager.createUserGroup(user2);
        rollbackTransaction();
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserWithExistingShortName() throws Exception {
        final User user2 = userManager.createUser(USER_SHORT_NAME, "the user name 2", "email2@example.com",
                "userPassword", "en", "GB", TimeZone.getDefault().getID());
        groupManager.createUserGroup(user2);
        rollbackTransaction();
    }

    @Test(expected = UserRegistrationException.class)
    public void createUserWithIncorrectShortName() throws Exception {
        final User user2 = userManager.createUser("u s", "the user name 2", "email2@example.com", "userPassword", "en",
                "GB", TimeZone.getDefault().getID());
        groupManager.createUserGroup(user2);
        rollbackTransaction();
    }

    @Test(expected = InvalidStateException.class)
    public void createUserWithVeryShortName() throws Exception {
        final User user2 = userManager.createUser("us", "the user name 2", "email2@example.com", "userPassword", "en",
                "GB", TimeZone.getDefault().getID());
        groupManager.createUserGroup(user2);
        rollbackTransaction();
    }

    @Test
    public void groupSearchPagination() throws Exception, ParseException {
        for (int i = 1; i < 10; i++) {
            createTestGroup(i);
        }
        groupManager.reIndex();
        final SearchResult<Group> result = groupManager.search("Yellow", 0, 5);
        assertEquals(9, result.getSize());
        assertEquals(5, result.getList().size());
        final SearchResult<Group> result2 = groupManager.search("Yellow", 5, 5);
        assertEquals(9, result2.getSize());
        assertEquals(4, result2.getList().size());
        rollbackTransaction();
    }
}
