/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Group;

import com.google.inject.Inject;

/**
 * Some snippets from:
 * http://www.onjava.com/pub/a/onjava/excerpt/jebp_3/index1.html?page=1
 * 
 */

public class EntityBackgroundDownloadManager extends HttpServlet {

  static final String APPLICATION_X_DOWNLOAD = "application/x-download";

  public static final Log LOG = LogFactory.getLog(FileDownloadManager.class);
  static final String RESP_HEADER_ATTACHMENT_FILENAME = "attachment; filename=\"";
  static final String RESP_HEADER_CONTEND_DISP = "Content-Disposition";

  static final String RESP_HEADER_END = "\"";

  private static final long serialVersionUID = -1160659289588014049L;

  @Inject
  FileUtils fileUtils;
  @Inject
  GroupManager groupManager;
  @Inject
  KuneProperties kuneProperties;

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

    resp.setContentLength((int) file.length());

    final String contentType = mimeType.toString();

    resp.setContentType(contentType);
    LOG.info("Content type returned: " + contentType);

    resp.setHeader(RESP_HEADER_CONTEND_DISP, RESP_HEADER_ATTACHMENT_FILENAME + filename
        + RESP_HEADER_END);
    return absFilename;
  }

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {

    final StateToken stateToken = new StateToken(req.getParameter(FileConstants.TOKEN));
    final String imageSizeS = req.getParameter(FileConstants.IMGSIZE);
    final ImageSize imgsize = imageSizeS == null ? null : ImageSize.valueOf(imageSizeS);

    try {
      final Group group = groupManager.findByShortName(stateToken.getGroup());

      final String absFilename = buildResponse(stateToken, group.getBackgroundImage(),
          group.getBackgroundMime(), imgsize, resp);
      final OutputStream out = resp.getOutputStream();
      FileDownloadManagerUtils.returnFile(absFilename, out);
    } catch (final ContentNotFoundException e) {
      FileDownloadManagerUtils.returnNotFound404(resp);
      return;
    }
  }
}
