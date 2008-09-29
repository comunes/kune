package org.ourproject.kune.platf.server.manager.impl;

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

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.access.AccessService;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentUtils;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.FileManager;
import org.ourproject.kune.platf.server.properties.KuneProperties;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@RequestScoped
public class FileUploadManager extends HttpServlet {

    public static final Log log = LogFactory.getLog(FileUploadManager.class);

    private static final String UTF8 = "UTF-8";

    private static final long serialVersionUID = 1L;

    @Inject
    KuneProperties kuneProperties;
    @Inject
    UserSession userSession;
    @Inject
    ContentManager contentManager;
    @Inject
    AccessService accessService;
    @Inject
    FileManager fileManager;
    @Inject
    I18nTranslationService i18n;

    @Override
    @SuppressWarnings( { "unchecked", "deprecation" })
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
	    IOException {

	JSONObject jsonResponse = createSuccessResponse();

	final DiskFileItemFactory factory = new DiskFileItemFactory();
	// maximum size that will be stored in memory
	factory.setSizeThreshold(4096);
	// the location for saving data that is larger than getSizeThreshold()
	factory.setRepository(new File("/tmp"));

	if (!ServletFileUpload.isMultipartContent(req)) {
	    log.warn("Not a multipart upload");
	}

	final ServletFileUpload upload = new ServletFileUpload(factory);
	// maximum size before a FileUploadException will be thrown
	upload.setSizeMax(new Integer(kuneProperties.get(KuneProperties.UPLOAD_MAX_FILE_SIZE)) * 1024 * 1024);

	try {
	    final List fileItems = upload.parseRequest(req);
	    String userHash = null;
	    StateToken stateToken = null;
	    String fileName = null;
	    FileItem file = null;
	    for (final Iterator iterator = fileItems.iterator(); iterator.hasNext();) {
		final FileItem item = (FileItem) iterator.next();
		if (item.isFormField()) {
		    final String name = item.getFieldName();
		    final String value = item.getString();
		    log.info("name: " + name + " value: " + value);
		    if (name.equals(FileUploader.USERHASH)) {
			userHash = value;
		    }
		    if (name.equals(FileUploader.CURRENT_STATE_TOKEN)) {
			stateToken = new StateToken(value);
		    }
		} else {
		    fileName = item.getName();
		    log.info("file: " + fileName + " fieldName: " + item.getFieldName() + " size: " + item.getSize());
		    file = item;
		}
	    }
	    createUploadedFile(userHash, stateToken, fileName, file);
	} catch (final FileUploadException e) {
	    jsonResponse = createJsonResponse(false, i18n.t("Error: File too large"));
	} catch (final Exception e) {
	    jsonResponse = createJsonResponse(false, i18n.t("Error uploading file"));
	    log.info("Exception: " + e.getCause());
	    e.printStackTrace();
	}

	final Writer w = new OutputStreamWriter(resp.getOutputStream());
	w.write(jsonResponse.toString());
	w.close();
	resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, actionLevel = ActionLevel.container)
    @Transactional(type = TransactionType.READ_WRITE)
    Content createUploadedFile(final String userHash, final StateToken stateToken, final String fileName,
	    final FileItem fileUploadItem) throws Exception {
	final String relDir = FileUtils.toDir(stateToken);
	final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION) + relDir;
	fileManager.mkdir(absDir);

	File file = null;
	try {
	    final String filenameUTF8 = new String(fileName.getBytes(), UTF8);
	    file = fileManager.createFileWithSequentialName(absDir, filenameUTF8);
	    fileUploadItem.write(file);

	    final String mimetype = fileUploadItem.getContentType();
	    final String extension = FileUtils.getFileNameExtension(filenameUTF8, false);

	    // Persist
	    final User user = userSession.getUser();
	    final Container container = accessService.accessToContainer(ContentUtils.parseId(stateToken.getFolder()),
		    user, AccessRol.Editor);
	    final String preview = "Preview of this file (in development)";
	    final Content content = contentManager.createContent(FileUtils.getFileNameWithoutExtension(file.getName(),
		    extension), preview, user, container);
	    content.setTypeId(DocumentServerTool.TYPE_UPLOADEDFILE);
	    content.setMimeType(new BasicMimeType(mimetype));
	    content.setFilename(filenameUTF8);
	    return content;
	} catch (final Exception e) {
	    if (file != null && file.exists()) {
		file.delete();
	    }
	    throw e;
	}
    }

    private JSONObject createJsonResponse(final boolean success, final String message) {
	/**
	 * 
	 * http://max-bazhenov.com/dev/upload-dialog-2.0/index.php
	 * 
	 * Server side handler.
	 * 
	 * The files in the queue are posted one at a time, the file field name
	 * is 'file'. The handler should return json encoded object with
	 * following properties: { success: true|false, // required error:
	 * 'Error or success message' // optional, also can be named 'message' }
	 */

	JSONObject response = null;
	try {
	    response = new JSONObject();
	    response.put("success", success);
	    response.put("error", message);
	    response.put("code", "232");
	} catch (final Exception e) {
	    log.error("Error building response");
	}
	return response;
    }

    private JSONObject createSuccessResponse() {
	return createJsonResponse(true, i18n.t("Success uploading"));
    }
}
