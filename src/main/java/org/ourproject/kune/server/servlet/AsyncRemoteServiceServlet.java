package org.ourproject.kune.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.mortbay.jetty.RetryRequest;

public class AsyncRemoteServiceServlet extends OpenRemoteServiceServlet {

    public static final String PAYLOAD= "com.google.gwt.payload";

    /* ------------------------------------------------------------ */
    /* (non-Javadoc)
     * @see com.google.gwt.user.server.rpc.OpenRemoteServiceServlet#readPayloadAsUtf8(javax.servlet.http.HttpServletRequest)
     */
    protected String readPayloadAsUtf8(HttpServletRequest request)
        throws IOException, ServletException {
        String payload = (String) request.getAttribute(PAYLOAD);
        if (payload == null) {
            payload = super.readPayloadAsUtf8(request);
            request.setAttribute(PAYLOAD,payload);
        }
        return payload;
    }

    protected void doUnexpectedFailure(Throwable e) {
        if (e.toString().matches(".*org.mortbay.jetty.RetryRequest.*")) {
            // TODO: A better way to match that?
            e = (RuntimeException) new RetryRequest();
        }
        throwIfRetryRequest(e);
        super.doUnexpectedFailure(e);
    }

    protected void throwIfRetryRequest( Throwable caught ) {
        if (caught instanceof RuntimeException && "org.mortbay.jetty.RetryRequest".equals(caught.getClass().getName())) {
            throw (RuntimeException) caught;
        }
    }
}