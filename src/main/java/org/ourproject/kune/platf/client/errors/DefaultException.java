package org.ourproject.kune.platf.client.errors;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.StatusCodeException;

public class DefaultException extends StatusCodeException implements IsSerializable {
    private static final long serialVersionUID = 1L;

    public DefaultException() {
        this(0, "");
    }

    public DefaultException(final int statusCode, final String message) {
        super(statusCode, message);
    }

    public DefaultException(final String message) {
        this(0, message);
    }
}
