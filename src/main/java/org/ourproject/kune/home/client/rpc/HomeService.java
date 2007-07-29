package org.ourproject.kune.home.client.rpc;

import org.ourproject.kune.platf.client.rpc.ContentProvider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface HomeService extends ContentProvider {

    public class App {
        private static HomeServiceAsync ourInstance = null;

        public static synchronized HomeServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (HomeServiceAsync) GWT.create(HomeService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "HomeService");
            }
            return ourInstance;
        }

        public static void setMock(HomeServiceAsync mocked) {
            ourInstance = mocked;
        }
    }
}
