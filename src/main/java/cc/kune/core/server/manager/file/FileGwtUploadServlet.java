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
package cc.kune.core.server.manager.file;

import javax.inject.Inject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.utils.StateToken;

// TODO: Auto-generated Javadoc
/**
 * The Class FileGwtUploadServlet.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FileGwtUploadServlet extends FileGwtUploadAbstractServlet {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(FileGwtUploadServlet.class);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new file gwt upload servlet.
   * 
   * @param kuneProperties
   *          the kune properties
   */
  @Inject
  public FileGwtUploadServlet(final KuneProperties kuneProperties) {
    super(kuneProperties);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.file.FileGwtUploadAbstractServlet#
   * createUploadedFile(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, java.lang.String,
   * org.apache.commons.fileupload.FileItem, java.lang.String)
   */
  @Override
  protected String createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId) {
    LOG.info("ok");
    return "";
  }

}
