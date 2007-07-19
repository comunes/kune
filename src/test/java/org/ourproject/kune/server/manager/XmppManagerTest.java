package org.ourproject.kune.server.manager;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.ourproject.kune.server.xmpp.ServerGroupChat;

import junit.framework.TestCase;

public class XmppManagerTest extends TestCase {
    private XmppManager xmppManager;

    public void testConnect() {
        XMPPConnection conn = null;
        XMPPConnection conn2 = null;
        XMPPConnection conn3 = null;
        ServerGroupChat chat = null;
        ServerGroupChat chat2 = null;
        try {
            conn = xmppManager.connectAndLogin("asdadaldkad", "test3", "test3");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }
        assertNotNull("connected and logged", conn);

        try {
            conn3 = xmppManager.connectAndLogin("dasf3dadcgfa", "test", "test");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }
        assertNotNull("connected and logged", conn3);

        try {
            conn = xmppManager.connectAndLogin("asdadaldkad", "test3", "test3");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }
        assertNotNull("connected and logged", conn);

        try {
            conn = xmppManager.getConnection("asdadaldkad");
        } catch (XMPPException e1) {
            e1.printStackTrace();
            fail(e1.toString());
        }
        assertNotNull("connected and logged", conn);

        try {
            conn2 = xmppManager.getConnection("asdadaldkad2");
        } catch (XMPPException e) {
            assertNull("not connected session", conn2);
        }

        try {
            xmppManager.createRoom("asdadaldkad", "junitroom", "test3");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }

        try {
            chat = xmppManager.getGroupChat("asdadaldkad", "junitroom") ;
        }  catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }

        try {
            chat2 = xmppManager.getGroupChat("dasf3dadcgfa", "junitroom") ;
        }  catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }

        try {
            chat.join("asdadaldkad", "test3");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }

        try {
            chat2.join("dasf3dadcgfa", "test");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }

        try {
            chat.sendMessage("tests message");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }

        try {
            xmppManager.leaveRoom("asdadaldkad", "junitroom");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }

        try {
            xmppManager.leaveRoom("dasf3dadcgfa", "junitroom");
        } catch (XMPPException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createXmppManager();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private void createXmppManager() {
        xmppManager = new XmppManagerImpl();
    }

}
