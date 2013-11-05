/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.User;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface UserFinder {

  @Finder(query = "SELECT count(*) FROM User u")
  public Long count();

  @Finder(query = "select count (*) from User where email = :email")
  public Long countByEmail(@Named("email") final String email);

  @Finder(query = "select count (*) from User where name = :name")
  public Long countByLongName(@Named("name") final String name);

  @Finder(query = "select count (*) from User where shortName = :shortName")
  public Long countByShortName(@Named("shortName") final String shortName);

  @Finder(query = "from User where email = :email")
  public User findByEmail(@Named("email") final String email);

  @Finder(query = "from User where emailConfirmHash = :emailConfirmHash")
  public User findByHash(@Named("emailConfirmHash") final String emailConfirmHash);

  @Finder(query = "from User where id = :id")
  public User findById(@Named("id") final Long id);

  @Finder(query = "from User where name = :name")
  public User findByLongName(@Named("name") final String name);

  @Finder(query = "from User where shortName = :shortName")
  public User findByShortName(@Named("shortName") final String shortName);

  @Finder(query = "from User", returnAs = ArrayList.class)
  public List<User> getAll();
}
