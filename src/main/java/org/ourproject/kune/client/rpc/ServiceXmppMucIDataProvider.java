package org.ourproject.kune.client.rpc;

public interface ServiceXmppMucIDataProvider {
	void requestCreateRoom(String Owner, String RoomName, final ServiceXmppMucIResponse response);

	void requestJoinRoom(String RoomName, String UserName, final ServiceXmppMucIResponse response);
	
	void requestChangeSubject(String subject, final ServiceXmppMucIResponse response);
}
