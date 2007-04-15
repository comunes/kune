package org.ourproject.kune.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceXmppMucServiceAsync {
	void CreateRoom(String Owner, String RoomName, AsyncCallback callback);

	void JoinRoom(String RoomName, String UserName, AsyncCallback callback);
	
	void ChangeSubject(String subject, AsyncCallback callback);
}
