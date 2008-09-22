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
import org.ourproject.kune.platf.server.properties.KuneProperties;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class FileUploadManagerRevisited extends HttpServlet {

    public static final Log log = LogFactory.getLog(FileUploadManagerRevisited.class);

    private static final long serialVersionUID = 1L;

    @Inject
    KuneProperties kuneProperties;

    @Override
    @SuppressWarnings( { "unchecked", "deprecation" })
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
	    IOException {

	// i18n
	String jsonResponse = createJsonResponse(true, "OK");

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
	    for (final Iterator iterator = fileItems.iterator(); iterator.hasNext();) {
		final FileItem item = (FileItem) iterator.next();
		if (item.isFormField()) {
		    final String name = item.getFieldName();
		    final String value = item.getString();
		    log.info("name: " + name + " value: " + value);
		} else {
		    final String fileName = item.getName();
		    log.info("file: " + fileName + " fieldName: " + item.getFieldName() + " size: " + item.getSize());
		    item.write(new File(kuneProperties.get(KuneProperties.UPLOAD_LOCATION), fileName));
		}
	    }
	} catch (final FileUploadException e) {
	    jsonResponse = createJsonResponse(false, "Error: File too large");
	} catch (final Exception e) {
	    jsonResponse = createJsonResponse(false, "Error uploading file");
	    e.printStackTrace();
	}

	final Writer w = new OutputStreamWriter(resp.getOutputStream());
	w.write(jsonResponse);
	w.close();
	resp.setStatus(HttpServletResponse.SC_OK);
    }

    private String createJsonResponse(final boolean success, final String message) {
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
	return response.toString();
    }
}
