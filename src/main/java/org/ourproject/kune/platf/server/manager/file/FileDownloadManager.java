package org.ourproject.kune.platf.server.manager.file;

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

        if (mimeType.getType().equals("image")) {
            String imgsizePrefix = imgsize == null ? "" : "." + imgsize;
            String filenameWithoutExtension = FileUtils.getFileNameWithoutExtension(filename, extension);
            String filenameResized = filenameWithoutExtension + imgsizePrefix + extension;
            if (new File(absDir + filenameResized).exists()) {
                // thumb can fail
                filename = filenameResized;
            }
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
        } else if (mimeType.getType().equals("image")) {
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
