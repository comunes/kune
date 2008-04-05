/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
