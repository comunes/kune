package org.ourproject.kune.platf.client.rpc;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface I18nService extends RemoteService {

    /**
     * @gwt.typeArgs <java.lang.String,java.lang.String>
     */
    HashMap getLexicon(String language);

    String getTranslation(String userHash, String language, String text);

    public class App {
        private static I18nServiceAsync ourInstance = null;

        public static synchronized I18nServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (I18nServiceAsync) GWT.create(I18nService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "I18nService");
            }
            return ourInstance;
        }

    }

}
