package org.ourproject.kune.platf.server.manager;

import java.util.List;

import org.ourproject.kune.platf.server.domain.I18nLanguage;

public interface I18nLanguageManager extends Manager<I18nLanguage, Long> {

    I18nLanguage findByCode(String language);

    List<I18nLanguage> getAll();

}
