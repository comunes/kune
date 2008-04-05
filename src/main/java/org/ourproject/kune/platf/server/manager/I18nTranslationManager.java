package org.ourproject.kune.platf.server.manager;

import java.util.HashMap;
import java.util.List;

import org.ourproject.kune.platf.server.domain.I18nTranslation;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import com.google.gwt.user.client.rpc.SerializableException;

public interface I18nTranslationManager extends Manager<I18nTranslation, Long> {

    HashMap<String, String> getLexicon(String language);

    List<I18nTranslation> getUntranslatedLexicon(String language);

    List<I18nTranslation> getTranslatedLexicon(String language);

    String getTranslation(String language, String text);

    String getTranslation(String language, String text, String arg);

    String getTranslation(String language, String text, Integer arg);

    void setTranslation(String language, String text, String translation);

    String setTranslation(String id, String translation) throws SerializableException;

    SearchResult<I18nTranslation> getUntranslatedLexicon(String language, Integer firstResult, Integer maxResults);

    SearchResult<I18nTranslation> getTranslatedLexicon(String language, Integer firstResult, Integer maxResults);

}
