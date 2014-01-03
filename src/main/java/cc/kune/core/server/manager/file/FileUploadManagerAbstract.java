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
package cc.kune.core.server.manager.file;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUploadManagerAbstract.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class FileUploadManagerAbstract extends HttpServlet {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(FileUploadManager.class);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -4227762495128652369L;

  /** The Constant UTF8. */
  protected static final String UTF8 = "UTF-8";

  /** The kune properties. */
  @Inject
  KuneProperties kuneProperties;

  /**
   * Before post start.
   */
  protected abstract void beforePostStart();

  /**
   * Before respond.
   * 
   * @param response
   *          the response
   * @param w
   *          the w
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected abstract void beforeRespond(final HttpServletResponse response, final Writer w)
      throws IOException;

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
   * @throws Exception
   *           the exception
   */
  abstract void createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId) throws Exception;

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
   * , javax.servlet.http.HttpServletResponse)
   */
  @Override
  @SuppressWarnings({ "rawtypes" })
  protected void doPost(final HttpServletRequest req, final HttpServletResponse response)
      throws ServletException, IOException {

    beforePostStart();

    final DiskFileItemFactory factory = new DiskFileItemFactory();
    // maximum size that will be stored in memory
    factory.setSizeThreshold(4096);
    // the location for saving data that is larger than getSizeThreshold()
    factory.setRepository(new File("/tmp"));

    if (!ServletFileUpload.isMultipartContent(req)) {
      LOG.warn("Not a multipart upload");
    }

    final ServletFileUpload upload = new ServletFileUpload(factory);
    // maximum size before a FileUploadException will be thrown
    upload.setSizeMax(kuneProperties.getLong(KuneProperties.UPLOAD_MAX_FILE_SIZE) * 1024 * 1024);

    try {
      final List fileItems = upload.parseRequest(req);
      String userHash = null;
      StateToken stateToken = null;
      String typeId = null;
      String fileName = null;
      FileItem file = null;
      for (final Iterator iterator = fileItems.iterator(); iterator.hasNext();) {
        final FileItem item = (FileItem) iterator.next();
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
          LOG.info("file: " + fileName + " fieldName: " + item.getFieldName() + " size: "
              + item.getSize() + " typeId: " + typeId);
          file = item;
        }
      }
      createUploadedFile(userHash, stateToken, fileName, file, typeId);
      onSuccess(response);
    } catch (final FileUploadException e) {
      onFileUploadException(response);
    } catch (final Exception e) {
      onOtherException(response, e);
    }
  }

  /**
   * Do response.
   * 
   * @param response
   *          the response
   * @param additionalResponse
   *          the additional response
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected void doResponse(final HttpServletResponse response, final String additionalResponse)
      throws IOException {
    doResponse(response, additionalResponse, HttpServletResponse.SC_OK);
  }

  /**
   * Do response.
   * 
   * @param response
   *          the response
   * @param additionalResponse
   *          the additional response
   * @param responseCode
   *          the response code
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected void doResponse(final HttpServletResponse response, final String additionalResponse,
      final int responseCode) throws IOException {
    final Writer w = new OutputStreamWriter(response.getOutputStream());
    if (additionalResponse != null) {
      w.write(additionalResponse);
    }
    w.close();
    response.setStatus(responseCode);
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

  /**
   * On file upload exception.
   * 
   * @param response
   *          the response
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected void onFileUploadException(final HttpServletResponse response) throws IOException {
    doResponse(response, null);
  }

  /**
   * On other exception.
   * 
   * @param response
   *          the response
   * @param e
   *          the e
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected void onOtherException(final HttpServletResponse response, final Exception e)
      throws IOException {
    LOG.info("Exception: " + e.getCause());
    e.printStackTrace();
    doResponse(response, null);
  }

  /**
   * On success.
   * 
   * @param response
   *          the response
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected void onSuccess(final HttpServletResponse response) throws IOException {
    doResponse(response, null);
  }

}
