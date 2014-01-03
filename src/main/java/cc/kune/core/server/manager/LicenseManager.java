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
package cc.kune.core.server.manager;

import java.util.List;

import cc.kune.domain.License;
import cc.kune.domain.finders.LicenseFinder;

// TODO: Auto-generated Javadoc
/**
 * The Interface LicenseManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface LicenseManager {

  /**
   * Find by short name.
   * 
   * @param licenseDef
   *          the license def
   * @return the license
   */
  License findByShortName(String licenseDef);

  /**
   * Gets the all.
   * 
   * @return the all
   */
  List<License> getAll();

  /**
   * Gets the cc.
   * 
   * @return the cc
   */
  List<License> getCC();

  /**
   * Gets the def license.
   * 
   * @return the def license
   */
  License getDefLicense();

  /**
   * Gets the not cc.
   * 
   * @return the not cc
   */
  List<License> getNotCC();

  /**
   * Persist.
   * 
   * @param license
   *          the license
   * @return the license
   */
  License persist(final License license);

  /**
   * Sets the license finder.
   * 
   * @param licenseFinder
   *          the new license finder
   */
  void setLicenseFinder(final LicenseFinder licenseFinder);

}
