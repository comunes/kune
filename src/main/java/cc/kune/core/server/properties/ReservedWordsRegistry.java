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
package cc.kune.core.server.properties;

import java.util.ArrayList;
import java.util.List;

import cc.kune.core.client.errors.DefaultException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class ReservedWordsRegistry.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ReservedWordsRegistry extends ArrayList<String> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7455756500618858360L;

  /**
   * From list.
   * 
   * @param kuneProperties
   *          the kune properties
   * @return the list
   */
  public static List<String> fromList(final KuneProperties kuneProperties) {
    return kuneProperties.getList(KuneProperties.RESERVED_WORDS);
  }

  /**
   * Instantiates a new reserved words registry.
   * 
   * @param kuneProperties
   *          the kune properties
   */
  @Inject
  public ReservedWordsRegistry(final KuneProperties kuneProperties) {
    super(fromList(kuneProperties));
  }

  /**
   * Check.
   * 
   * @param names
   *          the names
   */
  public void check(final String... names) {
    for (final String name : names) {
      if (this.contains(name) || this.contains(name.toLowerCase())) {
        throw new DefaultException("This name is a reserved word and cannot be used");
      }
    }
  }
}
