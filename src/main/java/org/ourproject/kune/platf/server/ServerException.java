package org.ourproject.kune.platf.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerException extends RuntimeException {

    private static final long serialVersionUID = 2028618233098694418L;

    private static final Log LOG = LogFactory.getLog(ServerException.class);

    public ServerException() {
        super();
    }

    public ServerException(final String text) {
        super(text);
        LOG.error(text);
    }

    public ServerException(final String text, final Throwable cause) {
        super(text, cause);
        LOG.error(text, cause);
    }

    public ServerException(final Throwable cause) {
        super(cause);
        LOG.error("ServerManagerException", cause);
    }

}
