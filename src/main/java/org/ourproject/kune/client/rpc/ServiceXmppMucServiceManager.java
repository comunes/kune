package org.ourproject.kune.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * NOTA: desde mi punto de vista, esta clase no hace falta en absoluto
 * (con su amiga ServiceXmppMucIResponse)
 * lo único que hace es duplicar el comportamiento de 
 * AsyncCallback sin aportar nada nuevo...
 * @author vicente
 *
 */
public class ServiceXmppMucServiceManager implements
		ServiceXmppMucIDataProvider {

	public static ServiceXmppMucServiceManager INSTANCE = new ServiceXmppMucServiceManager();

	private ServiceXmppMucServiceAsync service;

	private ServiceXmppMucServiceManager() {
		service = (ServiceXmppMucServiceAsync) GWT
				.create(ServiceXmppMucService.class);
		ServiceDefTarget target = (ServiceDefTarget) service;
		String staticResponseURL = GWT.getModuleBaseURL();
		staticResponseURL += "/ServiceXmppMuc";
		target.setServiceEntryPoint(staticResponseURL);
	}

	public void requestCreateRoom(String Owner, String RoomName,
			final ServiceXmppMucIResponse response) {
		service.CreateRoom(Owner, RoomName, new AsyncCallback() {
			public void onFailure(Throwable caught) {
				response.failed(caught);
			}

			public void onSuccess(Object result) {
				response.accept(result);
			}
		});
	}

	public void requestJoinRoom(String RoomName, String UserName,
			final ServiceXmppMucIResponse response) {
		service.JoinRoom(RoomName, UserName, new AsyncCallback() {
			public void onFailure(Throwable caught) {
				response.failed(caught);
			}

			public void onSuccess(Object result) {
				response.accept(result);
			}
		});
	}
	
	public void requestChangeSubject(String subject, 
			final ServiceXmppMucIResponse response) {
		service.ChangeSubject(subject, new AsyncCallback() {
			public void onFailure(Throwable caught) {
				response.failed(caught);
			}

			public void onSuccess(Object result) {
				response.accept(result);
			}
		});
	}
	
}
