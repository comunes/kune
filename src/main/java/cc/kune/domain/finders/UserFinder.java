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

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.User;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserFinder.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface UserFinder {

  /**
   * Count.
   * 
   * @return the long
   */
  @Finder(query = "SELECT count(*) FROM User u")
  public Long count();

  /**
   * Count by email.
   * 
   * @param email
   *          the email
   * @return the long
   */
  @Finder(query = "select count (*) from User where email = :email")
  public Long countByEmail(@Named("email") final String email);

  /**
   * Count by long name.
   * 
   * @param name
   *          the name
   * @return the long
   */
  @Finder(query = "select count (*) from User where name = :name")
  public Long countByLongName(@Named("name") final String name);

  /**
   * Count by short name.
   * 
   * @param shortName
   *          the short name
   * @return the long
   */
  @Finder(query = "select count (*) from User where shortName = :shortName")
  public Long countByShortName(@Named("shortName") final String shortName);

  /**
   * Find by email.
   * 
   * @param email
   *          the email
   * @return the user
   */
  @Finder(query = "from User where email = :email")
  public User findByEmail(@Named("email") final String email);

  /**
   * Find by hash.
   * 
   * @param emailConfirmHash
   *          the email confirm hash
   * @return the user
   */
  @Finder(query = "from User where emailConfirmHash = :emailConfirmHash")
  public User findByHash(@Named("emailConfirmHash") final String emailConfirmHash);

  /**
   * Find by id.
   * 
   * @param id
   *          the id
   * @return the user
   */
  @Finder(query = "from User where id = :id")
  public User findById(@Named("id") final Long id);

  /**
   * Find by long name.
   * 
   * @param name
   *          the name
   * @return the user
   */
  @Finder(query = "from User where name = :name")
  public User findByLongName(@Named("name") final String name);

  /**
   * Find by short name.
   * 
   * @param shortName
   *          the short name
   * @return the user
   */
  @Finder(query = "from User where shortName = :shortName")
  public User findByShortName(@Named("shortName") final String shortName);

  /**
   * Gets the all.
   * 
   * @return the all
   */
  @Finder(query = "from User", returnAs = ArrayList.class)
  public List<User> getAll();
}
