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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.services.ImageSize;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Group;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * Some snippets from:
 * http://www.onjava.com/pub/a/onjava/excerpt/jebp_3/index1.html?page=1
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */

public class EntityBackgroundDownloadManager extends HttpServlet {

  /** The Constant APPLICATION_X_DOWNLOAD. */
  static final String APPLICATION_X_DOWNLOAD = "application/x-download";

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(FileDownloadManager.class);

  /** The Constant RESP_HEADER_ATTACHMENT_FILENAME. */
  static final String RESP_HEADER_ATTACHMENT_FILENAME = "attachment; filename=\"";

  /** The Constant RESP_HEADER_CONTEND_DISP. */
  static final String RESP_HEADER_CONTEND_DISP = "Content-Disposition";

  /** The Constant RESP_HEADER_END. */
  static final String RESP_HEADER_END = "\"";

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -1160659289588014049L;

  /** The file utils. */
  @Inject
  FileUtils fileUtils;

  /** The group manager. */
  @Inject
  GroupManager groupManager;

  /** The kune properties. */
  @Inject
  KuneProperties kuneProperties;

  /** The last modified. */
  private long lastModified = 0l;

  /**
   * Builds the response.
   * 
   * @param statetoken
   *          the statetoken
   * @param filename
   *          the filename
   * @param mimeType
   *          the mime type
   * @param imgsize
   *          the imgsize
   * @param resp
   *          the resp
   * @return the string
   * @throws FileNotFoundException
   *           the file not found exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  String buildResponse(final StateToken statetoken, final String filename, final String mimeType,
      final ImageSize imgsize, final HttpServletResponse resp) throws FileNotFoundException, IOException {
    final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION)
        + FileUtils.groupToDir(statetoken.getGroup());
    String absFilename = absDir + filename;

    if (imgsize != null) {
      final String imgsizePrefix = "." + imgsize;
      final String extension = FileUtils.getFileNameExtension(filename, true);
      final String filenameWithoutExtension = FileUtils.getFileNameWithoutExtension(filename, extension);
      final String filenameResized = filenameWithoutExtension + imgsizePrefix + extension;
      if (fileUtils.exist(absDir + filenameResized)) {
        absFilename = absDir + filenameResized;
      }
    }

    final File file = new File(absFilename);
    lastModified = file.lastModified();

    resp.setContentLength((int) file.length());

    if (mimeType != null) {
      final String contentType = mimeType.toString();
      resp.setContentType(contentType);
      LOG.info("Content type returned: " + contentType);
    }

    resp.setHeader(RESP_HEADER_CONTEND_DISP, RESP_HEADER_ATTACHMENT_FILENAME + filename
        + RESP_HEADER_END);
    CacheUtils.setCache1Day(resp);
    return absFilename;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {

    final StateToken stateToken = new StateToken(req.getParameter(FileConstants.TOKEN));
    final String imageSizeS = req.getParameter(FileConstants.IMGSIZE);
    final ImageSize imgsize = imageSizeS == null ? null : ImageSize.valueOf(imageSizeS);

    try {
      final Group group = groupManager.findByShortName(stateToken.getGroup());
      if (!group.hasBackground()) {
        FileDownloadManagerUtils.returnNotFound404(resp);
        return;
      }
      final String absFilename = buildResponse(stateToken, group.getBackgroundImage(),
          group.getBackgroundMime(), imgsize, resp);
      final OutputStream out = resp.getOutputStream();
      FileDownloadManagerUtils.returnFile(absFilename, out);
    } catch (final ContentNotFoundException e) {
      FileDownloadManagerUtils.returnNotFound404(resp);
    } catch (final NoResultException e2) {
      FileDownloadManagerUtils.returnNotFound404(resp);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.http.HttpServlet#getLastModified(javax.servlet.http.
   * HttpServletRequest)
   */
  @Override
  protected long getLastModified(final HttpServletRequest req) {
    // http://oreilly.com/catalog/jservlet/chapter/ch03.html#14260
    // (...)to play it safe, getLastModified() should always round down to the
    // nearest thousand milliseconds.
    return lastModified / 1000 * 1000;
  }
}
