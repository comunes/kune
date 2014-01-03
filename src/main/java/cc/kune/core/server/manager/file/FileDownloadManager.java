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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.services.ImageSize;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Content;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * Some snippets from:
 * http://www.onjava.com/pub/a/onjava/excerpt/jebp_3/index1.html?page=1
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FileDownloadManager extends HttpServlet {

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

  /** The content manager. */
  @Inject
  ContentManager contentManager;

  /** The file utils. */
  @Inject
  FileUtils fileUtils;

  /** The kune properties. */
  @Inject
  KuneProperties kuneProperties;

  /**
   * Builds the response.
   * 
   * @param cnt
   *          the cnt
   * @param stateToken
   *          the state token
   * @param downloadS
   *          the download s
   * @param imageSizeS
   *          the image size s
   * @param resp
   *          the resp
   * @param fileUtils
   *          the file utils
   * @return the string
   * @throws FileNotFoundException
   *           the file not found exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  String buildResponse(final Content cnt, final StateToken stateToken, final String downloadS,
      final String imageSizeS, final HttpServletResponse resp, final FileUtils fileUtils)
      throws FileNotFoundException, IOException {
    final ImageSize imgsize = imageSizeS == null ? null : ImageSize.valueOf(imageSizeS);
    final boolean download = downloadS != null && downloadS.equals("true") ? true : false;
    final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION)
        + FileUtils.toDir(stateToken);
    String filename = cnt.getFilename();
    final String title = cnt.getTitle();
    String extension = FileUtils.getFileNameExtension(filename, true);
    BasicMimeType mimeType = cnt.getMimeType();

    final boolean isPdfAndNotDownload = mimeType != null && mimeType.isPdf() && !download;
    if (mimeType != null && (mimeType.isImage() || isPdfAndNotDownload)) {
      final String imgsizePrefix = imgsize == null ? "" : "." + imgsize;
      final String filenameWithoutExtension = FileUtils.getFileNameWithoutExtension(filename, extension);
      final String filenameResized = filenameWithoutExtension + imgsizePrefix
          + (isPdfAndNotDownload ? ".png" : extension);
      if (fileUtils.exist(absDir + filenameResized)) {
        filename = filenameResized;
      }
    }

    // We will send the pdf thumb not the real pdf
    if (isPdfAndNotDownload) {
      extension = ".png";
      mimeType = new BasicMimeType("image", "png");
    }

    final String absFilename = absDir + filename;

    final File file = new File(absFilename);

    resp.setContentLength((int) file.length());

    String contentType;
    if (mimeType == null || download) {
      contentType = APPLICATION_X_DOWNLOAD;
    } else {
      contentType = mimeType.toString();
    }
    resp.setContentType(contentType);
    LOG.info("Content type returned: " + contentType);

    resp.setHeader(RESP_HEADER_CONTEND_DISP, RESP_HEADER_ATTACHMENT_FILENAME + title + extension
        + RESP_HEADER_END);
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

    final String userHash = req.getParameter(FileConstants.HASH);
    final StateToken stateToken = new StateToken(req.getParameter(FileConstants.TOKEN));
    final String downloadS = req.getParameter(FileConstants.DOWNLOAD);
    final String imageSizeS = req.getParameter(FileConstants.IMGSIZE);

    try {
      final Content cnt = getContentForDownload(userHash, stateToken);
      final String absFilename = buildResponse(cnt, stateToken, downloadS, imageSizeS, resp, fileUtils);
      final OutputStream out = resp.getOutputStream();
      FileDownloadManagerUtils.returnFile(absFilename, out);
    } catch (final ContentNotFoundException e) {
      FileDownloadManagerUtils.returnNotFound404(resp);
      return;
    }
  }

  /**
   * Gets the content for download.
   * 
   * @param userHash
   *          the user hash
   * @param stateToken
   *          the state token
   * @return the content for download
   * @throws ContentNotFoundException
   *           the content not found exception
   */
  @Authenticated(mandatory = false)
  @Authorizated(accessRolRequired = AccessRol.Viewer, actionLevel = ActionLevel.content)
  private Content getContentForDownload(final String userHash, final StateToken stateToken)
      throws ContentNotFoundException {
    return contentManager.find(ContentUtils.parseId(stateToken.getDocument()));
  }
}
