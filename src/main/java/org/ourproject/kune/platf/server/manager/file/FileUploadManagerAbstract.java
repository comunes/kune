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
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.ui.download.FileParams;
import org.ourproject.kune.platf.server.properties.KuneProperties;

import com.google.inject.Inject;

public abstract class FileUploadManagerAbstract extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final Log log = LogFactory.getLog(FileUploadManager.class);

    protected static final String UTF8 = "UTF-8";

    @Inject
    KuneProperties kuneProperties;

    protected void beforePostStart() {
    }

    protected void beforeRespond(final HttpServletResponse response, final Writer w) throws IOException {
    }

    protected void createUploadedFile(String userHash, StateToken stateToken, String fileName, FileItem file,
            String typeId) throws Exception {
    }

    @Override
    @SuppressWarnings( { "unchecked", "deprecation" })
    protected void doPost(final HttpServletRequest req, final HttpServletResponse response) throws ServletException,
            IOException {

        beforePostStart();

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
            String typeId = null;
            String fileName = null;
            FileItem file = null;
            for (final Iterator iterator = fileItems.iterator(); iterator.hasNext();) {
                final FileItem item = (FileItem) iterator.next();
                if (item.isFormField()) {
                    final String name = item.getFieldName();
                    final String value = item.getString();
                    log.info("name: " + name + " value: " + value);
                    if (name.equals(FileParams.HASH)) {
                        userHash = value;
                    }
                    if (name.equals(FileParams.TOKEN)) {
                        stateToken = new StateToken(value);
                    }
                    if (name.equals(FileParams.TYPE_ID)) {
                        typeId = value;
                    }
                } else {
                    fileName = item.getName();
                    log.info("file: " + fileName + " fieldName: " + item.getFieldName() + " size: " + item.getSize()
                            + " typeId: " + typeId);
                    file = item;
                }
            }
            createUploadedFile(userHash, stateToken, fileName, file, typeId);
            onSuccess(response);
        } catch (final FileUploadException e) {
            onFileUploadException(response);
        } catch (final Exception e) {
            onOtherException(response, e);
        }
    }

    protected void doResponse(final HttpServletResponse response, String additionalResponse) throws IOException {
        doResponse(response, additionalResponse, HttpServletResponse.SC_OK);
    }

    protected void doResponse(final HttpServletResponse response, String additionalResponse, int responseCode)
            throws IOException {
        final Writer w = new OutputStreamWriter(response.getOutputStream());
        if (additionalResponse != null) {
            w.write(additionalResponse);
        }
        w.close();
        response.setStatus(responseCode);
    }

    protected void onFileUploadException(final HttpServletResponse response) throws IOException {
        doResponse(response, null);
    }

    protected void onOtherException(HttpServletResponse response, final Exception e) throws IOException {
        log.info("Exception: " + e.getCause());
        e.printStackTrace();
        doResponse(response, null);
    }

    protected void onSuccess(HttpServletResponse response) throws IOException {
        doResponse(response, null);
    }

}
