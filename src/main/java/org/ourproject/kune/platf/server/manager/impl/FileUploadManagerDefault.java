package org.ourproject.kune.platf.server.manager.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.cafesip.gwtcomp.server.FileUploadAction;
import org.cafesip.gwtcomp.server.FormResponse;

public class FileUploadManagerDefault implements FileUploadAction {

    public FormResponse onSubmit(final HttpServlet servlet, final HttpServletRequest request) {
        return new FormResponse(200, "", false);
    }

    public void setFileList(final HashMap files) {        
    }

}
