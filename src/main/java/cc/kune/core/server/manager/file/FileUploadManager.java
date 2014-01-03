/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server.manager.file;

import java.io.File;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.access.AccessService;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.FileManager;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.utils.StringW;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUploadManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RequestScoped
public class FileUploadManager extends FileJsonUploadManagerAbstract {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -7209922761735338754L;

  /** The access service. */
  @Inject
  AccessService accessService;

  /** The creation service. */
  @Inject
  CreationService creationService;

  /** The file manager. */
  @Inject
  FileManager fileManager;

  /** The i18n. */
  @Inject
  I18nTranslationService i18n;

  /** The user session. */
  @Inject
  UserSessionManager userSession;

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.file.FileUploadManagerAbstract#beforePostStart
   * ()
   */
  @Override
  protected void beforePostStart() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.file.FileJsonUploadManagerAbstract#
   * createJsonResponse(boolean, java.lang.String)
   */
  @Override
  protected JSONObject createJsonResponse(final boolean success, final String message) {
    /**
     * 
     * http://max-bazhenov.com/dev/upload-dialog-2.0/index.php
     * 
     * Server side handler.
     * 
     * The files in the queue are posted one at a time, the file field name is
     * 'file'. The handler should return json encoded object with following
     * properties: { success: true|false, // required error: 'Error or success
     * message' // optional, also can be named 'message' }
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.file.FileUploadManagerAbstract#createUploadedFile
   * (java.lang.String, cc.kune.core.shared.domain.utils.StateToken,
   * java.lang.String, org.apache.commons.fileupload.FileItem, java.lang.String)
   */
  @Override
  protected void createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId) throws Exception {
    createUploadedFileWrapped(userHash, stateToken, fileName, file, typeId);
  }

  /**
   * Creates the uploaded file wrapped.
   * 
   * @param userHash
   *          the user hash
   * @param stateToken
   *          the state token
   * @param fileName
   *          the file name
   * @param fileUploadItem
   *          the file upload item
   * @param typeId
   *          the type id
   * @return the content
   * @throws Exception
   *           the exception
   */
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, actionLevel = ActionLevel.container, mustCheckMembership = false)
  @KuneTransactional
  Content createUploadedFileWrapped(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem fileUploadItem, final String typeId) throws Exception {
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
      final Container container = accessService.accessToContainer(
          ContentUtils.parseId(stateToken.getFolder()), user, AccessRol.Editor);
      final Content content = creationService.createContent(
          FileUtils.getFileNameWithoutExtension(file.getName(), extension), preview, user, container,
          typeId);
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

  /**
   * Generate thumbs.
   * 
   * @param absDir
   *          the abs dir
   * @param filename
   *          the filename
   * @param extension
   *          the extension
   * @param isPdf
   *          the is pdf
   */
  private void generateThumbs(final String absDir, final String filename, final String extension,
      final boolean isPdf) {
    ImageUtilsDefault.generateThumbs(absDir, filename, extension, isPdf,
        Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_RESIZEWIDTH)),
        Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_THUMBSIZE)),
        Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_CROPSIZE)),
        Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_ICONSIZE)));
  }
}
