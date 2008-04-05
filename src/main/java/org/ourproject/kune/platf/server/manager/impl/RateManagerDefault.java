
package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.RateManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class RateManagerDefault extends DefaultManager<Rate, Long> implements RateManager {

    private final Rate finder;

    @Inject
    public RateManagerDefault(final Provider<EntityManager> provider, final Rate finder) {
        super(provider, Rate.class);
        this.finder = finder;
    }

    public Rate find(final User user, final Content content) {
        try {
            return finder.find(user, content);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Double getRateAvg(final Content content) {
        return finder.calculateRate(content);
    }

    public Long getRateByUsers(final Content content) {
        return finder.calculateRateNumberOfUsers(content);
    }

}
