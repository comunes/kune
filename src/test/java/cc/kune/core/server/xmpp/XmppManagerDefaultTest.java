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
package cc.kune.core.server.xmpp;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.server.integration.IntegrationTestHelper;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class XmppManagerDefaultTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class XmppManagerDefaultTest {

  /**
   * The listener interface for receiving output events. The class that is
   * interested in processing a output event implements this interface, and the
   * object created with that class is registered with a component using the
   * component's <code>addOutputListener<code> method. When
   * the output event occurs, that object's appropriate
   * method is invoked.
   * 
   * @see OutputEvent
   */
  public static class OutputListener implements RoomListener {

    /** The hits. */
    private int hits;

    /** The name. */
    private final String name;

    /**
     * Instantiates a new output listener.
     * 
     * @param name
     *          the name
     */
    public OutputListener(final String name) {
      this.name = name;
      this.hits = 0;
    }

    /**
     * Gets the hits.
     * 
     * @return the hits
     */
    public int getHits() {
      return hits;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cc.kune.core.server.xmpp.RoomListener#onMessage(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public void onMessage(final String from, final String to, final String body) {
      log.debug("Al listener " + name + "ha llegado: ");
      log.debug(from + "- " + to + ": " + body);
      hits++;
    }

  }

  /** The log. */
  static Log log = LogFactory.getLog(XmppManagerDefaultTest.class);

  /** The manager. */
  @Inject
  XmppManager manager;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    IntegrationTestHelper.createInjector().injectMembers(this);
  }

  /**
   * Test broadcast.
   */
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

  /**
   * Test connection.
   */
  @Test
  public void testConnection() {
    final ChatConnection handler1 = manager.login("admin", "easyeasy", "test");
    assertNotNull(handler1);
  }

  /**
   * Test create room.
   */
  @Test
  public void testCreateRoom() {
    final ChatConnection handler1 = manager.login("admin", "easyeasy", "test");
    if (!manager.existRoom(handler1, "test-room")) {
      manager.createRoom(handler1, "test-room", "alias", "Always the same room");
    }
  }

  /**
   * Test get roster.
   */
  @Test
  public void testGetRoster() {
    final ChatConnection handler = manager.login("admin", "easyeasy", "test");
    assertNotNull(manager.getRoster(handler));
  }

  /**
   * Test send message.
   */
  @Test
  public void testSendMessage() {
    manager.sendMessage("admin", "test message");
  }

  /**
   * Test user dont exist.
   */
  @Test(expected = ChatException.class)
  public void testUserDontExist() {
    manager.login("user", "password", "test");
  }
}
