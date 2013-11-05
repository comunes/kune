/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.client.ui;

import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.shared.domain.utils.StateToken;

import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.user.client.ui.Widget;

public class BasicDragableThumb extends BasicThumb implements HasDragHandle {

  private final StateToken token;

  public BasicDragableThumb(final Object imageRef, final int imgSize, final String text,
      final int textMaxLenght, final boolean crop, final StateToken token) {
    super(imageRef, imgSize, text, textMaxLenght, crop);
    this.token = token;
  }

  @Override
  public Widget getDragHandle() {
    return getImage();
  }

  public StateToken getToken() {
    return token;
  }

}
