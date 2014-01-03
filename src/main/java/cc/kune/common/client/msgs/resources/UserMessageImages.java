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
package cc.kune.common.client.msgs.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserMessageImages.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface UserMessageImages extends ClientBundle {

  /** The Constant INST. */
  public static final UserMessageImages INST = GWT.create(UserMessageImages.class);

  /**
   * Error.
   *
   * @return the image resource
   */
  ImageResource error();

  /**
   * Info.
   *
   * @return the image resource
   */
  ImageResource info();

  /**
   * Warning.
   *
   * @return the image resource
   */
  ImageResource warning();

  /**
   * Important.
   *
   * @return the image resource
   */
  ImageResource important();

  /**
   * Severe.
   *
   * @return the image resource
   */
  ImageResource severe();

  /**
   * Removes the.
   *
   * @return the image resource
   */
  ImageResource remove();

  /**
   * Removes the grey.
   *
   * @return the image resource
   */
  @Source("remove-grey.png")
  ImageResource removeGrey();

  /**
   * Removes the over.
   *
   * @return the image resource
   */
  @Source("remove-over.png")
  ImageResource removeOver();
}
