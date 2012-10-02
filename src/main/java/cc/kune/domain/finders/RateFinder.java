/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.domain.finders;

import cc.kune.domain.Content;
import cc.kune.domain.Rate;
import cc.kune.domain.User;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface RateFinder {

  @Finder(query = "SELECT AVG(r.value) FROM Rate r WHERE r.content = :content")
  public Double calculateRate(@Named("content") final Content content);

  @Finder(query = "SELECT count(*) FROM Rate r WHERE r.content = :content")
  public Long calculateRateNumberOfUsers(@Named("content") final Content content);

  @Finder(query = "SELECT r FROM Rate r WHERE r.rater = :user AND r.content = :content")
  public Rate find(@Named("user") final User user, @Named("content") final Content content);

}
