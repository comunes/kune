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
package cc.kune.core.server.manager.file;

import java.io.File;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;

import cc.kune.core.server.UserSession;
import cc.kune.core.server.access.AccessService;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.manager.FileManager;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.utils.StringW;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class FileUploadManager extends FileJsonUploadManagerAbstract {

  private static final long serialVersionUID = -7209922761735338754L;
  @Inject
  AccessService accessService;
  @Inject
  ContentManager contentManager;
  @Inject
  FileManager fileManager;
  @Inject
  I18nTranslationService i18n;
  @Inject
  UserSession userSession;

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

  @Override
  protected void createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId) throws Exception {
    createUploadedFileWrapped(userHash, stateToken, fileName, file, typeId);
  }

  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, actionLevel = ActionLevel.container, mustCheckMembership = false)
  @Transactional
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
      final Content content = contentManager.createContent(
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

  private void generateThumbs(final String absDir, final String filename, final String extension,
      final boolean isPdf) {
    ImageUtilsDefault.generateThumbs(absDir, filename, extension, isPdf,
        Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_RESIZEWIDTH)),
        Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_THUMBSIZE)),
        Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_CROPSIZE)),
        Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_ICONSIZE)));
  }
}
