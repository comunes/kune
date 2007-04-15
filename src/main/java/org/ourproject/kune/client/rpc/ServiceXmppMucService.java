package org.ourproject.kune.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ServiceXmppMucService extends RemoteService {
	void CreateRoom(String Owner, String RoomName);
	
	void JoinRoom(String RoomName, String UserName);
	
	void ChangeSubject(String subject);
}
