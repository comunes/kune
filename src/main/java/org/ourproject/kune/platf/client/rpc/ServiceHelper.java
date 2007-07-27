package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.docs.client.rpc.DocumentService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ServiceHelper {
    private static Object instance = null;

    protected static synchronized Object getInstance(String name) {
        if (instance == null) {
            instance = GWT.create(DocumentService.class);
            ((ServiceDefTarget) instance).setServiceEntryPoint(GWT.getModuleBaseURL() + name);
        }
        return instance;
    }
    
    public static void setMock(Object mock) {
        instance = mock;
    }
}
