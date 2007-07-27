package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface KuneService extends RemoteService {

    public class App {
        private static KuneServiceAsync ourInstance = null;

        public static synchronized KuneServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (KuneServiceAsync) GWT.create(KuneService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KuneService");
            }
            return ourInstance;
        }

        public static void setMock(KuneServiceAsync mocked) {
            ourInstance = mocked;
        }
    }
    
}
