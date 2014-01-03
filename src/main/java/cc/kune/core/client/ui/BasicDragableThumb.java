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
package cc.kune.core.client.ui;

import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.shared.domain.utils.StateToken;

import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicDragableThumb.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BasicDragableThumb extends BasicThumb implements HasDragHandle {

  /** The token. */
  private final StateToken token;

  /**
   * Instantiates a new basic dragable thumb.
   * 
   * @param imageRef
   *          the image ref
   * @param imgSize
   *          the img size
   * @param text
   *          the text
   * @param textMaxLenght
   *          the text max lenght
   * @param crop
   *          the crop
   * @param token
   *          the token
   */
  public BasicDragableThumb(final Object imageRef, final int imgSize, final String text,
      final int textMaxLenght, final boolean crop, final StateToken token) {
    super(imageRef, imgSize, text, textMaxLenght, crop);
    this.token = token;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.allen_sauer.gwt.dnd.client.HasDragHandle#getDragHandle()
   */
  @Override
  public Widget getDragHandle() {
    return getImage();
  }

  /**
   * Gets the token.
   * 
   * @return the token
   */
  public StateToken getToken() {
    return token;
  }

}
