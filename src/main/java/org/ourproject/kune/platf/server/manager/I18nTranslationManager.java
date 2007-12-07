package org.ourproject.kune.platf.server.manager;

import java.util.HashMap;

import org.ourproject.kune.platf.server.domain.I18nTranslation;

public interface I18nTranslationManager extends Manager<I18nTranslation, Long> {

    HashMap<String, String> getLexicon(String language);

    String getTranslation(String language, String text);

    String getTranslation(String language, String text, String arg);

    String getTranslation(String language, String text, Integer arg);

    void setTranslation(String language, String text, String translation);

}
