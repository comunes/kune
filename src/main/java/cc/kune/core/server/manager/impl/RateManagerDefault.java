/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.manager.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import cc.kune.core.server.manager.RateManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.domain.Content;
import cc.kune.domain.Rate;
import cc.kune.domain.User;
import cc.kune.domain.finders.RateFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class RateManagerDefault extends DefaultManager<Rate, Long> implements RateManager {

  private final RateFinder finder;

  @Inject
  public RateManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final RateFinder finder) {
    super(provider, Rate.class);
    this.finder = finder;
  }

  @Override
  public Rate find(final User user, final Content content) {
    try {
      return finder.find(user, content);
    } catch (final NoResultException e) {
      return null;
    }
  }

  @Override
  public Double getRateAvg(final Content content) {
    return finder.calculateRate(content);
  }

  @Override
  public Long getRateByUsers(final Content content) {
    return finder.calculateRateNumberOfUsers(content);
  }

}
