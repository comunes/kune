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
package cc.kune.common.client.actions;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class BeforeActionCollection.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BeforeActionCollection extends ArrayList<BeforeActionListener> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -1508664709628420137L;

  /**
   * Check before action listeners.
   * 
   * @return true, if all listener returns true
   */
  public boolean checkBeforeAction() {
    for (BeforeActionListener listener : this) {
      if (!listener.beforeAction()) {
        return false;
      }
    }
    return true;
  }

}
