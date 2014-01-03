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
package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGuiBinding.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractGuiBinding implements GuiBinding {

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.GuiBinding#create(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) { // NOPMD
                                                                     // by
                                                                     // vjrj
                                                                     // on
                                                                     // 18/01/11
                                                                     // 0:47
    return null;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.GuiBinding#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() { // NOPMD by vjrj on 18/01/11 0:47
    return true;
  }

}
