
package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.InitDataDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SiteService extends RemoteService {

    InitDataDTO getInitData(String userHash) throws SerializableException;

    public class App {
        private static SiteServiceAsync ourInstance = null;

        public static synchronized SiteServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (SiteServiceAsync) GWT.create(SiteService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "SiteService");
            }
            return ourInstance;
        }

        public static void setMock(final SiteServiceAsync mocked) {
            ourInstance = mocked;
        }
    }

}
