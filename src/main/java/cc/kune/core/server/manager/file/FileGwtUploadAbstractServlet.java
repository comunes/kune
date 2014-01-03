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

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.servlet.RequestScoped;

// TODO: Auto-generated Javadoc
/**
 * The Class FileGwtUploadAbstractServlet.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RequestScoped
public abstract class FileGwtUploadAbstractServlet extends UploadAction {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(FileGwtUploadAbstractServlet.class);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant UTF8. */
  protected static final String UTF8 = "UTF-8";

  /** The kune properties. */
  protected final KuneProperties kuneProperties;

  /**
   * Instantiates a new file gwt upload abstract servlet.
   * 
   * @param kuneProperties
   *          the kune properties
   */
  public FileGwtUploadAbstractServlet(final KuneProperties kuneProperties) {
    this.kuneProperties = kuneProperties;
    this.maxSize = kuneProperties.getInteger(KuneProperties.UPLOAD_MAX_FILE_SIZE_IN_KS);
    this.uploadDelay = kuneProperties.getInteger(KuneProperties.UPLOAD_DELAY_FOR_TEST);
  }

  /**
   * Creates the uploaded file.
   * 
   * @param userHash
   *          the user hash
   * @param stateToken
   *          the state token
   * @param fileName
   *          the file name
   * @param file
   *          the file
   * @param typeId
   *          the type id
   * @return the string
   */
  protected abstract String createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId);

  /*
   * (non-Javadoc)
   * 
   * @see gwtupload.server.UploadAction#executeAction(javax.servlet.http.
   * HttpServletRequest, java.util.List)
   */
  @Override
  public String executeAction(final HttpServletRequest request, final List<FileItem> fileItems)
      throws UploadActionException {
    String userHash = null;
    StateToken stateToken = null;
    String typeId = null;
    String fileName = null;
    FileItem file = null;
    for (final Object element : fileItems) {
      final FileItem item = (FileItem) element;
      if (item.isFormField()) {
        final String name = item.getFieldName();
        final String value = item.getString();
        LOG.info("name: " + name + " value: " + value);
        if (name.equals(FileConstants.HASH)) {
          userHash = value;
        }
        if (name.equals(FileConstants.TOKEN)) {
          stateToken = new StateToken(value);
        }
        if (name.equals(FileConstants.TYPE_ID)) {
          typeId = value;
        }
      } else {
        fileName = item.getName();
        LOG.info("file: " + fileName + " fieldName: " + item.getFieldName() + " size: " + item.getSize()
            + " typeId: " + typeId);
        file = item;
      }
    }
    try {
      final String response = createUploadedFile(userHash, stateToken, fileName, file, typeId);

      // Remove files from session because we have a copy of them
      return response;
    } finally {
      // Without this the session restoration fails
      removeSessionFileItems(request);
      request.getSession().removeAttribute(ATTR_LAST_FILES);
    }

  }

  /**
   * Log file del.
   * 
   * @param delResult
   *          the del result
   */
  protected void logFileDel(final boolean delResult) {
    if (!delResult) {
      LOG.error("Cannot delete file");
    }
  }

}
