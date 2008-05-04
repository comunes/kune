package org.ourproject.kune.platf.client.errors;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DefaultException extends Exception implements IsSerializable {
    private static final long serialVersionUID = 1L;

    public DefaultException() {
    }

    public DefaultException(final String arg0) {
	super(arg0);
    }
}
