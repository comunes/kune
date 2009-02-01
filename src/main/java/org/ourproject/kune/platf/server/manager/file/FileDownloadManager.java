/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.manager.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.ui.download.FileParams;
import org.ourproject.kune.platf.client.ui.download.ImageSize;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentUtils;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.properties.KuneProperties;

import com.google.inject.Inject;

/**
 * Some snippets from:
 * http://www.onjava.com/pub/a/onjava/excerpt/jebp_3/index1.html?page=1
 * 
 */
public class FileDownloadManager extends HttpServlet {

    static final String RESP_HEADER_ATTACHMENT_FILENAME = "attachment; filename=\"";
    static final String RESP_HEADER_CONTEND_DISP = "Content-Disposition";
    static final String RESP_HEADER_END = "\"";

    static final String APPLICATION_X_DOWNLOAD = "application/x-download";

    private static final long serialVersionUID = 1L;

    @Inject
    ContentManager contentManager;
    @Inject
    KuneProperties kuneProperties;
    @Inject
    FileUtils fileUtils;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
            IOException {

        final String userHash = req.getParameter(FileParams.HASH);
        final StateToken stateToken = new StateToken(req.getParameter(FileParams.TOKEN));
        final String downloadS = req.getParameter(FileParams.DOWNLOAD);
        String imageSizeS = req.getParameter(FileParams.IMGSIZE);

        try {
            Content cnt = getContentForDownload(userHash, stateToken);
            String absFilename = buildResponse(cnt, stateToken, downloadS, imageSizeS, resp, fileUtils);
            final OutputStream out = resp.getOutputStream();
            FileDownloadManagerUtils.returnFile(absFilename, out);
        } catch (ContentNotFoundException e) {
            FileDownloadManagerUtils.returnNotFound(resp);
            return;
        }
    }

    String buildResponse(final Content cnt, final StateToken stateToken, final String downloadS, String imageSizeS,
            final HttpServletResponse resp, FileUtils fileUtils) throws FileNotFoundException, IOException {
        final ImageSize imgsize = imageSizeS == null ? null : ImageSize.valueOf(imageSizeS);
        final boolean download = downloadS != null && downloadS.equals("true") ? true : false;
        final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION) + FileUtils.toDir(stateToken);
        String filename = cnt.getFilename();
        String title = cnt.getTitle();
        String extension = FileUtils.getFileNameExtension(filename, true);
        BasicMimeType mimeType = cnt.getMimeType();

        boolean isPdfAndNotDownload = mimeType != null && mimeType.isPdf() && !download;
        if (mimeType != null && (mimeType.isImage() || isPdfAndNotDownload)) {
            String imgsizePrefix = imgsize == null ? "" : "." + imgsize;
            String filenameWithoutExtension = FileUtils.getFileNameWithoutExtension(filename, extension);
            String filenameResized = filenameWithoutExtension + imgsizePrefix
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
        if (mimeType == null || download) {
            resp.setContentType(APPLICATION_X_DOWNLOAD);
        } else if (mimeType.isImage()) {
            resp.setContentType(mimeType.toString());
        } else {
            resp.setContentType(APPLICATION_X_DOWNLOAD);
        }
        resp.setHeader(RESP_HEADER_CONTEND_DISP, RESP_HEADER_ATTACHMENT_FILENAME + title + extension + RESP_HEADER_END);
        return absFilename;
    }

    @Authenticated(mandatory = false)
    @Authorizated(accessRolRequired = AccessRol.Viewer, actionLevel = ActionLevel.content)
    private Content getContentForDownload(final String userHash, final StateToken stateToken)
            throws ContentNotFoundException {
        return contentManager.find(ContentUtils.parseId(stateToken.getDocument()));
    }
}
