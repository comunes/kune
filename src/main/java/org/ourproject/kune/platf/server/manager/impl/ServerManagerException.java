package org.ourproject.kune.platf.server.manager.impl;

import org.ourproject.kune.platf.server.ServerException;

public class ServerManagerException extends ServerException {

    private static final long serialVersionUID = -8407996943857184946L;

    public ServerManagerException() {
        super();
    }

    public ServerManagerException(final String text) {
        super(text);
    }

    public ServerManagerException(final String text, final Throwable cause) {
        super(text, cause);
    }

    public ServerManagerException(final Throwable cause) {
        super(cause);
    }

}
