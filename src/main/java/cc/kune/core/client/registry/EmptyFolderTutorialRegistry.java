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
 \*/
package cc.kune.core.client.registry;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class EmptyFolderTutorialRegistry.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmptyFolderTutorialRegistry {

  /** The registry. */
  private final List<String> registry;

  /**
   * Instantiates a new empty folder tutorial registry.
   */
  public EmptyFolderTutorialRegistry() {
    registry = new ArrayList<String>();
  }

  /**
   * Checks for tutorial.
   * 
   * @param typeId
   *          the type id
   * @return true, if successful
   */
  public boolean hasTutorial(final String typeId) {
    return registry.contains(typeId);
  }

  /**
   * Register.
   * 
   * @param contentTypeId
   *          the content type id
   */
  public void register(final String contentTypeId) {
    registry.add(contentTypeId);
  }
}
