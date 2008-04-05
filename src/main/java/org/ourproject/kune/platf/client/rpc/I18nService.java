package org.ourproject.kune.platf.client.rpc;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface I18nService extends RemoteService {

    I18nLanguageDTO getInitialLanguage(String localeParam);

    HashMap<String, String> getLexicon(String language);

    String getTranslation(String userHash, String language, String text) throws SerializableException;

    String setTranslation(String userHash, String id, String translation) throws SerializableException;

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
