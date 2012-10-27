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
package cc.kune.core.server.xmpp;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.server.integration.IntegrationTestHelper;

import com.google.inject.Inject;

public class XmppManagerDefaultTest {
  public static class OutputListener implements RoomListener {
    private int hits;
    private final String name;

    public OutputListener(final String name) {
      this.name = name;
      this.hits = 0;
    }

    public int getHits() {
      return hits;
    }

    @Override
    public void onMessage(final String from, final String to, final String body) {
      log.debug("Al listener " + name + "ha llegado: ");
      log.debug(from + "- " + to + ": " + body);
      hits++;
    }

  }

  static Log log = LogFactory.getLog(XmppManagerDefaultTest.class);

  @Inject
  XmppManager manager;

  @Before
  public void init() {
    IntegrationTestHelper.createInjector().injectMembers(this);
  }

  @Ignore
  public void testBroadcast() {
    final String roomName = "roomName";
    final ChatConnection conn1 = manager.login("testUser1", "easy1", "test");
    final ChatConnection conn2 = manager.login("testUser2", "easy2", "test");
    final Room room1 = manager.createRoom(conn1, roomName, "user1Alias", "subject");
    final OutputListener listener1 = new OutputListener("1");
    room1.setListener(listener1);
    final Room room2 = manager.joinRoom(conn2, roomName, "user2Alias");
    final OutputListener listener2 = new OutputListener("2");
    room2.setListener(listener2);

    manager.sendMessage(room1, "usuario1 dice uno");
    manager.sendMessage(room2, "usuario2 dice dos");
    manager.sendMessage(room1, "usuario1 dice tres");
    manager.sendMessage(room2, "usuario2 dice cuatro");
    try {
      Thread.sleep(4000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals(4, listener1.getHits());
    assertEquals(4, listener2.getHits());
  }

  @Test
  public void testConnection() {
    final ChatConnection handler1 = manager.login("admin", "easyeasy", "test");
    assertNotNull(handler1);
  }

  @Test
  public void testCreateRoom() {
    final ChatConnection handler1 = manager.login("admin", "easyeasy", "test");
    if (!manager.existRoom(handler1, "test-room")) {
      manager.createRoom(handler1, "test-room", "alias", "Always the same room");
    }
  }

  @Test
  public void testGetRoster() {
    final ChatConnection handler = manager.login("admin", "easyeasy", "test");
    assertNotNull(manager.getRoster(handler));
  }

  @Test
  public void testSendMessage() {
    manager.sendMessage("admin", "test message");
  }

  @Test(expected = ChatException.class)
  public void testUserDontExist() {
    manager.login("user", "password", "test");
  }
}
