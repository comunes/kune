
package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class I18nCountryManagerDefault extends DefaultManager<I18nCountry, Long> implements I18nCountryManager {

    private final I18nCountry finder;

    @Inject
    public I18nCountryManagerDefault(final Provider<EntityManager> provider, final I18nCountry finder) {
        super(provider, I18nCountry.class);
        this.finder = finder;
    }

    public List<I18nCountry> getAll() {
        return finder.getAll();
    }

    public I18nCountry findByCode(final String country) {
        return finder.findByCode(country);
    }
}
