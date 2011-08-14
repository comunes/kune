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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.TimeZone;

import javax.persistence.PersistenceException;

import org.apache.lucene.queryParser.ParseException;
import org.hibernate.validator.InvalidStateException;
import org.junit.Test;

import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.core.server.manager.impl.SearchResult;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class UserManagerDefaultTest extends PersistencePreLoadedDataTest {
  @Inject
  Group groupFinder;

  @Test
  public void emailCorrect() {
    user = new User("test1", "test1 name", "test@example.com", "some passwd", "somediggest".getBytes(),
        "some salt".getBytes(), english, gb, getTimeZone());
    persist(user);
  }

  @Test(expected = PersistenceException.class)
  public void emailEmpty() {
    user = new User("test1", "test1 name", null, "some passwd", "somediggest".getBytes(),
        "some salt".getBytes(), english, gb, getTimeZone());
    persist(user);
  }

  @Test(expected = InvalidStateException.class)
  public void emailIncorrect() {
    user = new User("test1", "test1 name", "falseEmail@", "some passwd", "somediggest".getBytes(),
        "some salt".getBytes(), english, gb, getTimeZone());
    persist(user);
  }

  private TimeZone getTimeZone() {
    return TimeZone.getDefault();
  }

  @Test
  public void loginIncorrect() {
    final User result = userManager.login("test", "test");
    assertNull(result);
  }

  @Test
  public void loginWithEmailCorrect() {
    final User result = userManager.login(USER_EMAIL, USER_PASSWORD);
    assertNotNull(result.getId());
  }

  @Test
  public void loginWithNickCorrect() {
    final User result = userManager.login(USER_SHORT_NAME, USER_PASSWORD);
    assertNotNull(result.getId());
  }

  @Test(expected = InvalidStateException.class)
  public void passwdLengthIncorrect() {
    user = new User("test1", "test1 name", "test@example.com", "pass", "diggest".getBytes(),
        "salt".getBytes(), english, gb, getTimeZone());
    persist(user);
  }

  /**
   * This was not working:
   * http://opensource.atlassian.com/projects/hibernate/browse/EJB-382
   */
  @Test(expected = GroupShortNameInUseException.class)
  public void testUserExist() throws I18nNotFoundException {
    final User user1 = userManager.createUser("test", "test 1 name", "test1@example.com",
        "some password", "en", "GB", "GMT", true);
    persist(user1);
    final User user2 = userManager.createUser("test", "test 1 name", "test1@example.com",
        "some password", "en", "GB", "GMT", true);
    persist(user2);
  }

  @Test(expected = InvalidStateException.class)
  public void userNameLengthIncorrect() {
    user = new User("test1", "te", "test@example.com", "some passwd", "diggest".getBytes(),
        "salt".getBytes(), english, gb, getTimeZone());
    persist(user);
  }

  @Test
  public void userSearch() throws Exception, ParseException {
    userManager.reIndex();
    final SearchResult<User> result = userManager.search(USER_SHORT_NAME);
    assertEquals(1, result.getSize());
    assertEquals(USER_SHORT_NAME, result.getList().get(0).getShortName());
    rollbackTransaction();
  }

  @Test(expected = InvalidStateException.class)
  public void userShortNameIncorrect() {
    user = new User("test1A", "test1 name", "test@example.com", "some passwd", "diggest".getBytes(),
        "salt".getBytes(), english, gb, getTimeZone());
    persist(user);
  }

  @Test
  public void visibilityPersist() {
    user = new User("test1", "test1 name", "test@example.com", "some passwd", "somediggest".getBytes(),
        "some salt".getBytes(), english, gb, getTimeZone());
    for (final UserSNetVisibility visibility : UserSNetVisibility.values()) {
      user.setSNetVisibility(visibility);
      persist(user);
      assertEquals(user.getSNetVisibility(), visibility);
    }
  }

}
