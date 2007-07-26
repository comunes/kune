package org.ourproject.kune.app.server.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Injector;

public class KuneServlet extends RemoteServiceServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        Injector injector = KuneServletContext.getInjector(config);
        injector.getInstance(KuneServletContext.getRemoteService(config));
    }

}
