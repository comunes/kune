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
package cc.kune.gspace.client.options;

import cc.kune.common.client.ProvidersCollection;
import cc.kune.gspace.client.options.general.UserOptGeneral;
import cc.kune.gspace.client.options.general.UserOptPass;
import cc.kune.gspace.client.options.license.UserOptDefLicense;
import cc.kune.gspace.client.options.logo.UserOptLogo;
import cc.kune.gspace.client.options.style.UserOptStyle;
import cc.kune.gspace.client.options.tools.UserOptTools;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class UserOptionsCollection.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@SuppressWarnings("serial")
@Singleton
public class UserOptionsCollection extends ProvidersCollection {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new user options collection.
   * 
   * @param ug
   *          the ug
   * @param up
   *          the up
   * @param utc
   *          the utc
   * @param ul
   *          the ul
   * @param ups
   *          the ups
   * @param udl
   *          the udl
   */
  @Inject
  public UserOptionsCollection(final Provider<UserOptGeneral> ug, final Provider<UserOptPass> up,
      final Provider<UserOptTools> utc, final Provider<UserOptLogo> ul,
      final Provider<UserOptStyle> ups, final Provider<UserOptDefLicense> udl) {
    add(ug);
    add(up);
    add(ul);
    add(utc);
    add(ups);
    add(udl);
  }
}
