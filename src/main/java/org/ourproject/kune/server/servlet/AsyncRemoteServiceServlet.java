package org.ourproject.kune.server.servlet;
/* package org.mortbay.gwt; */

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.server.servlet.OpenRemoteServiceServlet;

public class AsyncRemoteServiceServlet extends OpenRemoteServiceServlet
{
    public static final String PAYLOAD= "com.google.gwt.payload";

    /* ------------------------------------------------------------ */
    /* (non-Javadoc)
     * @see com.google.gwt.user.server.rpc.OpenRemoteServiceServlet#readPayloadAsUtf8(javax.servlet.http.HttpServletRequest)
     */
    protected String readPayloadAsUtf8(HttpServletRequest request) throws IOException, ServletException
    {
        String payload=(String)request.getAttribute(PAYLOAD);
        if (payload==null)
        {
            payload=super.readPayloadAsUtf8(request);
            request.setAttribute(PAYLOAD,payload);
        }
        return payload;
    }

    /* ------------------------------------------------------------ */
    /* (non-Javadoc)
     * @see com.google.gwt.user.server.rpc.OpenRemoteServiceServlet#respondWithFailure(javax.servlet.http.HttpServletResponse, java.lang.Throwable)
     */
    protected void respondWithFailure(HttpServletResponse response, Throwable caught)
    {
        throwIfRetyRequest(caught);
        super.respondWithFailure(response,caught);
    }

    protected void handleException( String responsePayload, Throwable caught )
    {
        throwIfRetyRequest(caught);
        super.handleException( responsePayload, caught );
    }

    protected void throwIfRetyRequest( Throwable caught )
    {
        if (caught instanceof RuntimeException && "org.mortbay.jetty.RetryRequest".equals(caught.getClass().getName()))
        {
            throw (RuntimeException) caught;
        }
    }



}

