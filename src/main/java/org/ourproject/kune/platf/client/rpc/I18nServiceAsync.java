
package org.ourproject.kune.platf.client.rpc;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface I18nServiceAsync {

    void getLexicon(String language, AsyncCallback<HashMap<String, String>> callback);

    void getTranslation(String userHash, String language, String text, AsyncCallback<String> callback);

    void setTranslation(String userHash, String id, String translation, AsyncCallback<String> asyncCallback);

    void getInitialLanguage(String localeParam, AsyncCallback<I18nLanguageDTO> callback);

}
