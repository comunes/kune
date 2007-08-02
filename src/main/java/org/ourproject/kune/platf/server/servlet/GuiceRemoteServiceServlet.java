package org.ourproject.kune.platf.server.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Injector;

public class GuiceRemoteServiceServlet extends RemoteServiceServlet {
    private static final Log log = LogFactory.getLog(GuiceRemoteServiceServlet.class);
    private static final long serialVersionUID = 1L;
    private transient Injector injector;
    private transient RemoteService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        injector = KuneServletContext.getInjector(config);
        Class<? extends RemoteService> remoteServiceType = KuneServletContext.getRemoteService(config);
        service = injector.getInstance(remoteServiceType);
        log.debug("KuneService init complete: " + service.getClass().getName());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public String processCall(String payload) throws SerializationException {
        try {
            RPCRequest rpcRequest = RPC.decodeRequest(payload, service.getClass());
            return RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(),
                rpcRequest.getParameters());
          } catch (IncompatibleRemoteServiceException ex) {
            return RPC.encodeResponseForFailure(null, ex);
          }
    }

}
