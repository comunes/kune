package org.ourproject.kune.app.server.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class KuneServletContext {
    private static final String KEY_IOC = Injector.class.getName();
    private static final String REMOTE_SERVICE = "remote.service";

    public static Injector getInjector(ServletConfig config) throws ServletException {
        ServletContext ctx = config.getServletContext();
        Injector injector = (Injector) ctx.getAttribute(KEY_IOC);
        if (injector == null) {
            injector = Guice.createInjector(createModule(config));
            ctx.setAttribute(KEY_IOC, injector);
        }
        return injector;
    }

    public static Class<? extends RemoteService> getRemoteService(ServletConfig config) throws ServletException {
        String serviceClassName = config.getInitParameter(REMOTE_SERVICE);
        if (serviceClassName == null) {
            throw new ServletException(REMOTE_SERVICE + " not defined for this servlet!");
        }
        try {
            return (Class<? extends RemoteService>) Class.forName(serviceClassName);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    
    private static Module createModule(ServletConfig config) throws ServletException {
        String routerModuleClassName = config.getServletContext().getInitParameter(Module.class.getName());
        if (routerModuleClassName == null) {
            throw new ServletException(Module.class.getName() + " not defined in web.xml!");
        }
        try {
            Class<?> type = Class.forName(routerModuleClassName);
            return (Module) type.newInstance();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }



}
