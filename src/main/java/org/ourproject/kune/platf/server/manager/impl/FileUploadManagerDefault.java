package org.ourproject.kune.platf.server.manager.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cafesip.gwtcomp.server.FileUploadAction;
import org.cafesip.gwtcomp.server.FormResponse;

public class FileUploadManagerDefault implements FileUploadAction {

    public static final Log log = LogFactory.getLog(FileUploadManagerDefault.class);
    private HashMap<String, File> files;

    @SuppressWarnings("unchecked")
    public FormResponse onSubmit(final HttpServlet servlet, final HttpServletRequest request) {
	for (final Map.Entry<String, File> entry : files.entrySet()) {
	    log.info("file uploaded: " + entry.getKey());
	}

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
	return new FormResponse(HttpServletResponse.SC_OK, "{'success':true,'message':'OK'}", false);
    }

    public void setFileList(final HashMap<String, File> files) {
	log.info("Setting files");
	this.files = files;
    }

}
