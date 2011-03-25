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
package org.ourproject.kune.chat.server.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.xmpp.ChatConnection;
import cc.kune.core.server.xmpp.ChatException;
import cc.kune.core.server.xmpp.Room;
import cc.kune.core.server.xmpp.RoomListener;
import cc.kune.core.server.xmpp.XmppManager;

import com.google.inject.Inject;

public class XmppManagerTest {
    public static class OutputListener implements RoomListener {
        private final String name;
        private int hits;

        public OutputListener(final String name) {
            this.name = name;
            this.hits = 0;
        }

        public int getHits() {
            return hits;
        }

        public void onMessage(final String from, final String to, final String body) {
            log.debug("Al listener " + name + "ha llegado: ");
            log.debug(from + "- " + to + ": " + body);
            hits++;
        }

    }

    static Log log = LogFactory.getLog(XmppManagerTest.class);

    @Inject
    XmppManager manager;

    @Before
    public void init() {
        IntegrationTestHelper.createInjector().injectMembers(this);
    }

    @Ignore
    public void testBroadcast() {
        String roomName = "roomName";
        ChatConnection conn1 = manager.login("testUser1", "easy1", "test");
        ChatConnection conn2 = manager.login("testUser2", "easy2", "test");
        Room room1 = manager.createRoom(conn1, roomName, "user1Alias");
        OutputListener listener1 = new OutputListener("1");
        room1.setListener(listener1);
        Room room2 = manager.joinRoom(conn2, roomName, "user2Alias");
        OutputListener listener2 = new OutputListener("2");
        room2.setListener(listener2);

        manager.sendMessage(room1, "usuario1 dice uno");
        manager.sendMessage(room2, "usuario2 dice dos");
        manager.sendMessage(room1, "usuario1 dice tres");
        manager.sendMessage(room2, "usuario2 dice cuatro");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(4, listener1.getHits());
        assertEquals(4, listener2.getHits());
    }

    @Test
    public void testConnection() {
        ChatConnection handler1 = manager.login("admin", "easyeasy", "test");
        assertNotNull(handler1);
    }

    @Test
    public void testGetRoster() {
        ChatConnection handler = manager.login("admin", "easyeasy", "test");
        assertNotNull(manager.getRoster(handler));
    }

    @Test
    public void testSendMessage() {
        manager.sendMessage("admin", "test message");
    }

    @Test(expected = ChatException.class)
    public void testUserDontExist() {
        manager.login("user", "passowrd", "test");
    }
}
