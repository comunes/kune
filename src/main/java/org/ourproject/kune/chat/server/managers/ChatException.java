package org.ourproject.kune.chat.server.managers;

import org.jivesoftware.smack.XMPPException;

public class ChatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ChatException(final XMPPException cause) {
	super(cause);
    }

    public ChatException() {
	super("");
    }
}
