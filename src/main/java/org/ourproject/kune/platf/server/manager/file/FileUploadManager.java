/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

import magick.MagickException;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.ourproject.kune.platf.client.ui.download.ImageSize;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessService;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentUtils;
import org.ourproject.kune.platf.server.manager.FileManager;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.utils.StringW;

import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@RequestScoped
public class FileUploadManager extends FileJsonUploadManagerAbstract {

    private static final long serialVersionUID = -7209922761735338754L;
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
    protected void beforePostStart() {
    }

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
            LOG.error("Error building response");
        }
        return response;
    }

    @Override
    protected void createUploadedFile(final String userHash, final StateToken stateToken, final String fileName,
            final FileItem file, final String typeId) throws Exception {
        createUploadedFileWrapped(userHash, stateToken, fileName, file, typeId);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, actionLevel = ActionLevel.container, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    Content createUploadedFileWrapped(final String userHash, final StateToken stateToken, final String fileName,
            final FileItem fileUploadItem, final String typeId) throws Exception {
        final String relDir = FileUtils.toDir(stateToken);
        final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION) + relDir;
        fileManager.mkdir(absDir);

        File file = null;
        try {
            final String filenameUTF8 = new String(fileName.getBytes(), UTF8);
            file = fileManager.createFileWithSequentialName(absDir, filenameUTF8);
            fileUploadItem.write(file);

            final String mimetype = fileUploadItem.getContentType();
            final BasicMimeType basicMimeType = new BasicMimeType(mimetype);
            LOG.info("Mimetype: " + basicMimeType);
            final String extension = FileUtils.getFileNameExtension(file.getName(), false);

            String preview = "";

            if (basicMimeType.isImage()) {
                generateThumbs(absDir, file.getName(), extension, false);
            } else if (basicMimeType.isPdf()) {
                generateThumbs(absDir, file.getName(), "png", true);
            } else if (basicMimeType.isText()) {
                final String textPreview = new String(FileUtils.getBytesFromFile(file));
                preview = "<pre>" + StringW.wordWrap(textPreview) + "</pre>";
            }

            // Persist
            final User user = userSession.getUser();
            final Container container = accessService.accessToContainer(ContentUtils.parseId(stateToken.getFolder()),
                    user, AccessRol.Editor);
            final Content content = contentManager.createContent(FileUtils.getFileNameWithoutExtension(file.getName(),
                    extension), preview, user, container, typeId);
            content.setMimeType(basicMimeType);
            content.setFilename(file.getName());
            return content;
        } catch (final Exception e) {
            if (file != null && file.exists()) {
                logFileDel(file.delete());
            }
            throw e;
        }
    }

    private void generateThumbs(final String absDir, final String filename, final String extension, final boolean isPdf) {
        try {
            final String fileOrig = absDir + filename;
            final String withoutExtension = FileUtils.getFileNameWithoutExtension(filename, extension);

            final String resizeName = absDir + withoutExtension + "." + ImageSize.sized + "." + extension;
            final String thumbName = absDir + withoutExtension + "." + ImageSize.thumb + "." + extension;
            final String iconName = absDir + withoutExtension + "." + ImageSize.ico + "." + extension;
            final String previewName = absDir + withoutExtension + "." + extension;

            final int resizeWidth = Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_RESIZEWIDTH));
            final int thumbSize = Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_THUMBSIZE));
            final int cropSize = Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_CROPSIZE));
            final int iconSize = Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_ICONSIZE));

            ImageUtilsDefault.scaleImageToMax(fileOrig, resizeName, resizeWidth);
            ImageUtilsDefault.createThumb(fileOrig, thumbName, thumbSize, cropSize);
            ImageUtilsDefault.createThumb(fileOrig, iconName, iconSize);
            if (isPdf) {
                ImageUtilsDefault.createThumbFromPdf(fileOrig, previewName);
            }
        } catch (final NumberFormatException e) {
            LOG.error("Image sizes in kune.properties are not integers", e);
        } catch (final MagickException e) {
            LOG.info("Problem generating image thumb for " + filename, e);
        } catch (final FileNotFoundException e) {
            LOG.info("Original image not found generating image thumb for " + filename, e);
        }
    }
}
