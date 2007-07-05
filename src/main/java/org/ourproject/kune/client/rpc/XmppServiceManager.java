/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

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
public class XmppServiceManager implements
		XmppIDataProvider {

	public static XmppServiceManager INSTANCE = new XmppServiceManager();

	private XmppServiceAsync service;

	private XmppServiceManager() {
		service = (XmppServiceAsync) GWT
				.create(XmppService.class);
		ServiceDefTarget target = (ServiceDefTarget) service;
		String staticResponseURL = GWT.getModuleBaseURL();
		staticResponseURL += "/ServiceXmpp";
		target.setServiceEntryPoint(staticResponseURL);
	}

	public void requestCreateRoom(String Owner, String RoomName,
			final XmppIResponse response) {
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
			final XmppIResponse response) {
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
			final XmppIResponse response) {
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
