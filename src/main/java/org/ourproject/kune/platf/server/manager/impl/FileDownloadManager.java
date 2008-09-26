package org.ourproject.kune.platf.server.manager.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
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

    private static final long serialVersionUID = 1L;

    @Inject
    ContentManager contentManager;
    @Inject
    KuneProperties kuneProperties;

    public void returnFile(final String filename, final OutputStream out) throws FileNotFoundException, IOException {
	InputStream in = null;
	try {
	    in = new BufferedInputStream(new FileInputStream(filename));
	    final byte[] buf = new byte[4 * 1024]; // 4K buffer
	    int bytesRead;
	    while ((bytesRead = in.read(buf)) != -1) {
		out.write(buf, 0, bytesRead);
	    }
	} finally {
	    if (in != null) {
		in.close();
	    }
	}
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
	    IOException {

	final String userHash = req.getParameter("hash");
	final StateToken stateToken = new StateToken(req.getParameter("token"));
	final Content cnt = getContentForDownload(userHash, stateToken);

	final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION) + FileUtils.toDir(stateToken);
	final String filename = new String(cnt.getLastRevision().getBody());
	final String absFilename = absDir + filename;

	doBuildResp(resp, absFilename, cnt.getTitle(), cnt.getMimeType(), FileUtils
		.getFileNameExtension(filename, true));
    }

    private void doBuildResp(final HttpServletResponse resp, final String filename, final String otherName,
	    final BasicMimeType mimeType, final String extension) throws FileNotFoundException, IOException {
	final File file = new File(filename);

	resp.setContentLength((int) file.length());
	if (mimeType == null) {
	    resp.setContentType("application/x-download");
	} else if (mimeType.getType().equals("image") || mimeType.toString().equals("text/plain")) {
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
