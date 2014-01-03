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

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;

// TODO: Auto-generated Javadoc
/**
 * The Class NewMenusForTypeIdsRegistry.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewMenusForTypeIdsRegistry {

  /** The registry. */
  private final Map<String, MenuDescriptor> registry;

  /**
   * Instantiates a new new menus for type ids registry.
   */
  public NewMenusForTypeIdsRegistry() {
    registry = new HashMap<String, MenuDescriptor>();
  }

  /**
   * Gets the.
   * 
   * @param typeId
   *          the type id
   * @return the menu descriptor
   */
  public MenuDescriptor get(final String typeId) {
    return registry.get(typeId);
  }

  /**
   * Length.
   * 
   * @return the int
   */
  public int length() {
    return registry.size();
  }

  /**
   * Register.
   * 
   * @param contentTypeId
   *          the content type id
   * @param menu
   *          the menu
   */
  public void register(final String contentTypeId, final MenuDescriptor menu) {
    registry.put(contentTypeId, menu);
  }
}
