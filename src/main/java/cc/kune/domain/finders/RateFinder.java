/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.domain.finders;

import cc.kune.domain.Content;
import cc.kune.domain.Rate;
import cc.kune.domain.User;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

// TODO: Auto-generated Javadoc
/**
 * The Interface RateFinder.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface RateFinder {

  /**
   * Calculate rate.
   * 
   * @param content
   *          the content
   * @return the double
   */
  @Finder(query = "SELECT AVG(r.value) FROM Rate r WHERE r.content = :content")
  public Double calculateRate(@Named("content") final Content content);

  /**
   * Calculate rate number of users.
   * 
   * @param content
   *          the content
   * @return the long
   */
  @Finder(query = "SELECT count(*) FROM Rate r WHERE r.content = :content")
  public Long calculateRateNumberOfUsers(@Named("content") final Content content);

  /**
   * Find.
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   * @return the rate
   */
  @Finder(query = "SELECT r FROM Rate r WHERE r.rater = :user AND r.content = :content")
  public Rate find(@Named("user") final User user, @Named("content") final Content content);

}
