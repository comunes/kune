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
package cc.kune.core.server.manager;

import static org.junit.Assert.*;

import java.util.TimeZone;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.lucene.queryParser.ParseException;
import org.junit.Test;

import cc.kune.core.client.errors.GroupShortNameInUseException;
import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class UserManagerDefaultTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserManagerDefaultTest extends PersistencePreLoadedDataTest {

  /** The group finder. */
  @Inject
  Group groupFinder;

  /**
   * Email correct.
   */
  @Test
  public void emailCorrect() {
    user = new User("test1", "test1 name", "test@example.com", "somediggest".getBytes(),
        "some salt".getBytes(), english, gb, getTimeZone());
    persist(user);
  }

  /**
   * Email empty.
   */
  @Test(expected = PersistenceException.class)
  public void emailEmpty() {
    user = new User("test1", "test1 name", null, "somediggest".getBytes(), "some salt".getBytes(),
        english, gb, getTimeZone());
    persist(user);
  }

  /**
   * Email incorrect.
   */
  @Test(expected = ConstraintViolationException.class)
  public void emailIncorrect() {
    user = new User("test1", "test1 name", "falseEmail@", "somediggest".getBytes(),
        "some salt".getBytes(), english, gb, getTimeZone());
    persist(user);
  }

  /**
   * Gets the time zone.
   * 
   * @return the time zone
   */
  private TimeZone getTimeZone() {
    return TimeZone.getDefault();
  }

  /**
   * Login incorrect.
   */
  @Test
  public void loginIncorrect() {
    final User result = userManager.login("test", "test");
    assertNull(result);
  }

  /**
   * Login with email correct.
   */
  @Test
  public void loginWithEmailCorrect() {
    final User result = userManager.login(USER_EMAIL, USER_PASSWORD);
    assertNotNull(result.getId());
  }

  /**
   * Login with nick correct.
   */
  @Test
  public void loginWithNickCorrect() {
    final User result = userManager.login(USER_SHORT_NAME, USER_PASSWORD);
    assertNotNull(result.getId());
  }

  /**
   * This was not working:
   * http://opensource.atlassian.com/projects/hibernate/browse/EJB-382
   * 
   * @throws I18nNotFoundException
   *           the i18n not found exception
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

  /**
   * User name length incorrect.
   */
  @Test(expected = ConstraintViolationException.class)
  public void userNameLengthIncorrect() {
    user = new User("test1", "te", "test@example.com", "diggest".getBytes(), "salt".getBytes(), english,
        gb, getTimeZone());
    persist(user);
  }

  /**
   * User search.
   * 
   * @throws Exception
   *           the exception
   * @throws ParseException
   *           the parse exception
   */
  @Test
  public void userSearch() throws Exception, ParseException {
    closeTransaction();
    userManager.reIndex();
    final SearchResult<User> result = userManager.search(USER_SHORT_NAME);
    assertEquals(1, result.getSize());
    assertEquals(USER_SHORT_NAME, result.getList().get(0).getShortName());
    rollbackTransaction();
  }

  /**
   * User short name incorrect.
   */
  @Test(expected = ConstraintViolationException.class)
  public void userShortNameIncorrect() {
    user = new User("test1A", "test1 name", "test@example.com", "diggest".getBytes(), "salt".getBytes(),
        english, gb, getTimeZone());
    persist(user);
  }

  /**
   * Visibility persist.
   */
  @Test
  public void visibilityPersist() {
    user = new User("test1", "test1 name", "test@example.com", "somediggest".getBytes(),
        "some salt".getBytes(), english, gb, getTimeZone());
    for (final UserSNetVisibility visibility : UserSNetVisibility.values()) {
      user.setSNetVisibility(visibility);
      persist(user);
      assertEquals(user.getSNetVisibility(), visibility);
    }
  }

}
