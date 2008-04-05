
package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class I18nLanguageManagerDefault extends DefaultManager<I18nLanguage, Long> implements I18nLanguageManager {

    private final I18nLanguage finder;

    @Inject
    public I18nLanguageManagerDefault(final Provider<EntityManager> provider, final I18nLanguage finder) {
        super(provider, I18nLanguage.class);
        this.finder = finder;
    }

    public I18nLanguage findByCode(final String language) {
        return finder.findByCode(language);
    }

    public List<I18nLanguage> getAll() {
        return finder.getAll();
    }
}
