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

import magick.MagickException;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.download.ImageSize;
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
public class FileUploadManager extends FileJsonUploadManagerAbstract {

    private static final long serialVersionUID = 1L;

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
    protected JSONObject createJsonResponse(final boolean success, final String message) {
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

    @Override
    protected void createUploadedFile(String userHash, StateToken stateToken, String fileName, FileItem file,
            String typeId) throws Exception {
        createUploadedFileWrapped(userHash, stateToken, fileName, file, typeId);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, actionLevel = ActionLevel.container)
    @Transactional(type = TransactionType.READ_WRITE)
    Content createUploadedFileWrapped(final String userHash, final StateToken stateToken, final String fileName,
            final FileItem fileUploadItem, String typeId) throws Exception {
        final String relDir = FileUtils.toDir(stateToken);
        final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION) + relDir;
        fileManager.mkdir(absDir);

        File file = null;
        try {
            final String filenameUTF8 = new String(fileName.getBytes(), UTF8);
            file = fileManager.createFileWithSequentialName(absDir, filenameUTF8);
            fileUploadItem.write(file);

            final String mimetype = fileUploadItem.getContentType();
            BasicMimeType basicMimeType = new BasicMimeType(mimetype);
            log.info("Mimetype: " + basicMimeType);
            final String extension = FileUtils.getFileNameExtension(file.getName(), false);

            if (basicMimeType.getType().equals("image")) {
                generateThumbs(absDir, file.getName(), extension);
            }

            // Persist
            final User user = userSession.getUser();
            final Container container = accessService.accessToContainer(ContentUtils.parseId(stateToken.getFolder()),
                    user, AccessRol.Editor);
            final String preview = "Preview of this file (in development)";
            final Content content = contentManager.createContent(FileUtils.getFileNameWithoutExtension(file.getName(),
                    extension), preview, user, container, typeId);
            content.setMimeType(basicMimeType);
            content.setFilename(file.getName());
            return content;
        } catch (final Exception e) {
            if (file != null && file.exists()) {
                file.delete();
            }
            throw e;
        }
    }

    private void generateThumbs(String absDir, String filename, String extension) {
        try {
            String fileOrig = absDir + filename;
            String withoutExtension = FileUtils.getFileNameWithoutExtension(filename, extension);

            String resizeName = absDir + withoutExtension + "." + ImageSize.sized + "." + extension;
            String thumbName = absDir + withoutExtension + "." + ImageSize.thumb + "." + extension;
            String iconName = absDir + withoutExtension + "." + ImageSize.ico + "." + extension;

            int resizeWidth = Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_RESIZEWIDTH));
            int thumbSize = Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_THUMBSIZE));
            int cropSize = Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_CROPSIZE));
            int iconSize = Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_ICONSIZE));

            ImageUtilsDefault.scaleImageToMax(fileOrig, resizeName, resizeWidth);
            ImageUtilsDefault.createThumb(fileOrig, thumbName, thumbSize, cropSize);
            ImageUtilsDefault.createThumb(fileOrig, iconName, iconSize);

        } catch (NumberFormatException e) {
            log.error("Image sizes in kune.properties are not integers");
            e.printStackTrace();
        } catch (MagickException e) {
            log.info("Problem generating image thumb for " + filename);
        } catch (FileNotFoundException e) {
            log.info("Original image not found generating image thumb for " + filename);
        }
    }
}
