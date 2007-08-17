package org.ourproject.kune.chat.server.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

import com.google.inject.Inject;

public class Xmpp2ManagerTest {

    @Inject
    Xmpp2Manager manager;

    @Before
    public void init() {
	IntegrationTestHelper.createInjector().injectMembers(this);
    }

    @Test
    public void testConnection() {
	ChatConnection handler1 = manager.login("testUser1", "easy1");
	assertNotNull(handler1);
    }

    @Test(expected = ChatException.class)
    public void testUserDontExist() {
	manager.login("user", "passowrd");
    }

    @Test
    public void testBroadcast() {
	String roomName = "roomName";
	ChatConnection conn1 = manager.login("testUser1", "easy1");
	ChatConnection conn2 = manager.login("testUser2", "easy2");
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

    public static class OutputListener implements RoomListener {
	Log log = LogFactory.getLog(OutputListener.class);
	private final String name;
	private int hits;

	public OutputListener(final String name) {
	    this.name = name;
	    this.hits = 0;
	}

	public void onMessage(final String from, final String to, final String body) {
	    log.debug("Al listener " + name + "ha llegado: ");
	    log.debug(from + "- " + to + ": " + body);
	    hits++;
	}

	public int getHits() {
	    return hits;
	}

    }
}
