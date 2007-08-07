package org.ourproject.kune.app.server.servlet;

import javax.servlet.ServletContext;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DelegatedRemoteServlet extends RemoteServiceServlet {
    private static final long serialVersionUID = 1L;
    private transient RemoteService service;
    private ServletContext servletContext;

    public void setService(final RemoteService service) {
	this.service = service;
    }

    @Override
    public String processCall(final String payload) throws SerializationException {
	try {
	    RPCRequest rpcRequest = RPC.decodeRequest(payload, service.getClass());
	    return RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(), rpcRequest.getParameters());
	} catch (IncompatibleRemoteServiceException ex) {
	    return RPC.encodeResponseForFailure(null, ex);
	}
    }

    @Override
    protected void doUnexpectedFailure(final Throwable e) {
	e.printStackTrace();
	super.doUnexpectedFailure(e);
    }

    public void setServletContext(final ServletContext servletContext) {
	this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
	return servletContext;
    }
}