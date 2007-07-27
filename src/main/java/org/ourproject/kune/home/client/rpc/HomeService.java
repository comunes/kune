package org.ourproject.kune.home.client.rpc;

import org.ourproject.kune.platf.client.rpc.ServiceHelper;

public interface HomeService {

    public static class App extends ServiceHelper {
        public static HomeServiceAsync getInstance() {
            return (HomeServiceAsync) getInstance("HomeService");
        }
    }

}
