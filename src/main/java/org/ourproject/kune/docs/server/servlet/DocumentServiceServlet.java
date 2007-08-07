package org.ourproject.kune.docs.server.servlet;

import org.ourproject.kune.docs.server.services.DocumentServiceDefault;
import org.ourproject.kune.platf.server.servlet.GuiceRemoteServiceServlet;

import com.google.gwt.user.client.rpc.RemoteService;

public class DocumentServiceServlet extends GuiceRemoteServiceServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public Class<? extends RemoteService> getServiceTypeName() {
	return DocumentServiceDefault.class;
    }

}
