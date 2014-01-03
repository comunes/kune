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
package cc.kune.core.client.registry;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractContentRegistry.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractContentRegistry {

  /** The registry. */
  private final ArrayList<String> registry;

  /**
   * Instantiates a new abstract content registry.
   */
  public AbstractContentRegistry() {
    registry = new ArrayList<String>();
  }

  /**
   * As array.
   * 
   * @return the string[]
   */
  public String[] asArray() {
    return registry.toArray(new String[registry.size()]);
  }

  /**
   * Contains.
   * 
   * @param typeId
   *          the type id
   * @return true, if successful
   */
  public boolean contains(final String typeId) {
    return registry.contains(typeId);
  }

  /**
   * Register.
   * 
   * @param typeIds
   *          the type ids
   */
  public void register(final String... typeIds) {
    for (final String typeId : typeIds) {
      registry.add(typeId);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "registry: " + registry;
  }

  /**
   * Unregister.
   * 
   * @param typeIds
   *          the type ids
   */
  public void unregister(final String... typeIds) {
    for (final String typeId : typeIds) {
      registry.remove(typeId);
    }
  }
}
