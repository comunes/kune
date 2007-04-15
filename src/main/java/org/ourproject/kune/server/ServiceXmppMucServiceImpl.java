package org.ourproject.kune.server;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ourproject.kune.client.rpc.ServiceXmppMucService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServiceXmppMucServiceImpl extends RemoteServiceServlet implements
		ServiceXmppMucService {
	
	private static final long serialVersionUID = 1L;
	
	public void CreateRoom(String Owner, String RoomName) {
		ConnectionConfiguration config = new ConnectionConfiguration(
				"ourproject.org", 5223);
		XMPPConnection conn = new XMPPConnection(config);
		try {
			conn.connect();
			conn.login("test2345", "test2345");
			System.out.println("Connecting : " + "test2345");
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Error : " + e.getXMPPError());
		}

		MultiUserChat muc = new MultiUserChat(conn, RoomName
				+ "@conference.ourproject.org");

		try {
			// Create the room
			muc.create(Owner);
			// Send an empty room configuration form which indicates that we
			// want
			// an instant room
			muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Error : " + e.getXMPPError());
		}
	}

	public void JoinRoom(String RoomName, String UserName) {
		ConnectionConfiguration config = new ConnectionConfiguration(
				"ourproject.org", 5222);
		XMPPConnection conn = new XMPPConnection(config);
		try {
			conn.connect();
			conn.login("test2345", "test2345");
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Error : " + e.getXMPPError());
		}

		MultiUserChat muc = new MultiUserChat(conn, RoomName
				+ "@conference.ourproject.org");

		try {
			// User joins the new room
			// The room service will decide the amount of history to send
			muc.join(UserName);
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Error : " + e.getXMPPError());
		}

	}
	
	public void ChangeSubject(String subject) {
		ConnectionConfiguration config = new ConnectionConfiguration(
				"ourproject.org", 5222);
		XMPPConnection conn = new XMPPConnection(config);
		try {
			conn.connect();
			conn.login("test2345", "test2345");
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Error : " + e.getXMPPError());
		}

		MultiUserChat muc = new MultiUserChat(conn, "kune"
				+ "@conference.ourproject.org");
		
		try {
			// User joins the new room
			// The room service will decide the amount of history to send
			muc.join("test2345");
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Error : " + e.getXMPPError());
		}
		
		try {
            muc.changeSubject(subject);
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Error : " + e.getXMPPError());
		}
	}
}
