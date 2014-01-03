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

package cc.kune.gspace.client.options;

import gwtupload.client.IUploader.OnCancelUploaderHandler;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;

import com.google.gwt.event.shared.HandlerRegistration;

// TODO: Auto-generated Javadoc
/**
 * The Interface EntityOptionsUploaderView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface EntityOptionsUploaderView {

  /**
   * Adds the on cancel upload handler.
   * 
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration addOnCancelUploadHandler(final OnCancelUploaderHandler handler);

  /**
   * Adds the on change upload handler.
   * 
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration addOnChangeUploadHandler(final OnChangeUploaderHandler handler);

  /**
   * Adds the on finish upload handler.
   * 
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration addOnFinishUploadHandler(final OnFinishUploaderHandler handler);

  /**
   * Adds the on start upload handler.
   * 
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration addOnStartUploadHandler(final OnStartUploaderHandler handler);
}
