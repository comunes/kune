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
package cc.kune.domain.finders;

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.I18nCountry;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface I18nCountryFinder {

  @Finder(query = "FROM I18nCountry WHERE code = :country")
  public I18nCountry findByCode(@Named("country") final String country);

  @Finder(query = "FROM I18nCountry ORDER BY english_name", returnAs = ArrayList.class)
  public List<I18nCountry> getAll();

}
