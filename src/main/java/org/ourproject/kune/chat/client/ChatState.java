package org.ourproject.kune.chat.client;

import com.calclab.gwtjsjac.client.XmppUserSettings;

public class ChatState {
    public XmppUserSettings user;
    public final String httpBase;
    public final String domain;
    public final String roomHost;

    public ChatState(final String httpBase, final String domain, final String roomHost) {
	this.httpBase = httpBase;
	this.domain = domain;
	this.roomHost = roomHost;
	user = null;
    }
}
