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
package cc.kune.gspace.client.options.license;

import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.gspace.client.options.EntityOptionsTabView;

import com.google.gwt.event.dom.client.HasClickHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Interface EntityOptDefLicenseView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface EntityOptDefLicenseView extends EntityOptionsTabView {

  /**
   * Gets the change.
   * 
   * @return the change
   */
  HasClickHandlers getChange();

  /**
   * Gets the license image.
   * 
   * @return the license image
   */
  HasClickHandlers getLicenseImage();

  /**
   * Open window.
   * 
   * @param url
   *          the url
   */
  void openWindow(String url);

  /**
   * Sets the license.
   * 
   * @param defaultLicense
   *          the new license
   */
  void setLicense(LicenseDTO defaultLicense);

}
