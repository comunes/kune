/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */package org.ourproject.kune.platf.server.manager.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
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
public class FileDownloadManager extends FileDownloadManagerAbstract {

    private static final long serialVersionUID = 1L;

    @Inject
    ContentManager contentManager;
    @Inject
    KuneProperties kuneProperties;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
            IOException {

        final String userHash = req.getParameter(FileParams.HASH);
        final StateToken stateToken = new StateToken(req.getParameter(FileParams.TOKEN));
        final String downloadS = req.getParameter(FileParams.DOWNLOAD);
        String imageSizeS = req.getParameter(FileParams.IMGSIZE);
        final ImageSize imgsize = imageSizeS == null ? null : ImageSize.valueOf(imageSizeS);
        final boolean download = downloadS != null && downloadS.equals("true") ? true : false;

        final Content cnt = getContentForDownload(userHash, stateToken);

        final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION) + FileUtils.toDir(stateToken);
        String filename = cnt.getFilename();
        String extension = FileUtils.getFileNameExtension(filename, true);
        BasicMimeType mimeType = cnt.getMimeType();

        boolean isPdfAndNotDownload = mimeType.isPdf() && !download;
        if (mimeType.isImage() || isPdfAndNotDownload) {
            String imgsizePrefix = imgsize == null ? "" : "." + imgsize;
            String filenameWithoutExtension = FileUtils.getFileNameWithoutExtension(filename, extension);
            String filenameResized = filenameWithoutExtension + imgsizePrefix
                    + (isPdfAndNotDownload ? ".png" : extension);
            if (new File(absDir + filenameResized).exists()) {
                // thumb can fail
                filename = filenameResized;
            }
        }

        // We will send the pdf thumb not the real pdf
        if (isPdfAndNotDownload) {
            extension = ".png";
            mimeType = new BasicMimeType("image", "png");
        }

        final String absFilename = absDir + filename;

        doBuildResp(resp, absFilename, cnt.getTitle(), mimeType, extension, download);
    }

    private void doBuildResp(final HttpServletResponse resp, final String filename, final String otherName,
            final BasicMimeType mimeType, final String extension, final boolean download) throws FileNotFoundException,
            IOException {
        final File file = new File(filename);

        resp.setContentLength((int) file.length());
        if (mimeType == null || download) {
            resp.setContentType("application/x-download");
        } else if (mimeType.isImage()) {
            resp.setContentType(mimeType.toString());
        } else {
            resp.setContentType("application/x-download");
        }
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + otherName + extension + "\"");

        final OutputStream out = resp.getOutputStream();
        returnFile(filename, out);
    }

    @Authenticated(mandatory = false)
    @Authorizated(accessRolRequired = AccessRol.Viewer, actionLevel = ActionLevel.content)
    private Content getContentForDownload(final String userHash, final StateToken stateToken) throws ServletException {
        try {
            return contentManager.find(ContentUtils.parseId(stateToken.getDocument()));
        } catch (final ContentNotFoundException e) {
            // what response to send in this case ?
            throw new ServletException();
        }

    }
}
