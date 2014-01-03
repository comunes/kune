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
package cc.kune.common.client.actions.ui.descrip;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
// @PMD:REVIEWED:AtLeastOneConstructor: by vjrj on 26/05/09 12:31
/**
 * The Class GuiActionDescCollection.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GuiActionDescCollection extends ArrayList<GuiActionDescrip> {

  /** The Constant EMPTY. */
  public static final GuiActionDescCollection EMPTY = new GuiActionDescCollection();
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 6759723760404227737L;

  /**
   * Adds the.
   *
   * @param descriptors the descriptors
   */
  public void add(final GuiActionDescrip... descriptors) {
    for (final GuiActionDescrip descriptor : descriptors) {
      if (!super.contains(descriptor)) {
        super.add(descriptor);
      }
    }
  }

}
