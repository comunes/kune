/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.notifier;

import org.junit.Before;

import cc.kune.domain.User;

public abstract class AbstractPendingNotificationTest {

  protected SimpleDestinationProvider diferentProvider;
  protected User otherDiferentUser;
  protected User sameUser;
  protected SimpleDestinationProvider someSimilarUserProvider;
  protected SimpleDestinationProvider someSimilarUserProvider2;
  protected SimpleDestinationProvider someUserProvider;
  protected User user;

  @Before
  public void before() {
    user = new User("test1", "test1 name", "falseEmail@", "somediggest".getBytes(),
        "some salt".getBytes(), null, null, null);
    sameUser = new User("test1", "test1 name", "falseEmail@", "somediggest".getBytes(),
        "some salt".getBytes(), null, null, null);
    otherDiferentUser = new User("test2", "test2 name", "falseEmail@", "somediggest".getBytes(),
        "some salt".getBytes(), null, null, null);
    someUserProvider = new SimpleDestinationProvider(user);
    someSimilarUserProvider = new SimpleDestinationProvider(user);
    someSimilarUserProvider2 = new SimpleDestinationProvider(sameUser);
    diferentProvider = new SimpleDestinationProvider(otherDiferentUser);
  }

}
