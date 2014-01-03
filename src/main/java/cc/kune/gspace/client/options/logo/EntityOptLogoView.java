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
package cc.kune.gspace.client.options.logo;

import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.gspace.client.options.EntityOptionsTabView;
import cc.kune.gspace.client.options.EntityOptionsUploaderView;

// TODO: Auto-generated Javadoc
/**
 * The Interface EntityOptLogoView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface EntityOptLogoView extends EntityOptionsTabView, EntityOptionsUploaderView {

  /**
   * Gets the on submit.
   * 
   * @return the on submit
   */
  OnAcceptCallback getOnSubmit();

  /**
   * Reset.
   */
  void reset();

  /**
   * Sets the normal groups labels.
   */
  void setNormalGroupsLabels();

  /**
   * Sets the personal groups labels.
   */
  void setPersonalGroupsLabels();

  /**
   * Sets the upload params.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   */
  void setUploadParams(String userHash, String token);

}
