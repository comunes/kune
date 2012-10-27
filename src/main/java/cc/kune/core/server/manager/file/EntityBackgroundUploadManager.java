/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.manager.FileManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Group;

import com.google.inject.Inject;

public class EntityBackgroundUploadManager extends FileGwtUploadAbstractServlet {

  public static final Log LOG = LogFactory.getLog(FileGwtUploadServlet.class);
  private static final long serialVersionUID = 1L;
  private final FileManager fileManager;
  private final GroupManager groupManager;
  private final I18nTranslationService i18n;

  @Inject
  public EntityBackgroundUploadManager(final KuneProperties kuneProperties,
      final GroupManager groupManager, final I18nTranslationService i18n, final FileManager fileManager) {
    super(kuneProperties);
    this.groupManager = groupManager;
    this.i18n = i18n;
    this.fileManager = fileManager;
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @KuneTransactional
  protected String createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem origFile, final String typeId) {
    try {
      final String mimeTypeS = origFile.getContentType();
      final BasicMimeType mimeType = new BasicMimeType(mimeTypeS);
      if (!mimeType.getType().equals("image")) {
        return "Trying to set a non image (" + mimeTypeS + ") as group background";
      }
      final Group group = groupManager.findByShortName(stateToken.getGroup());

      if (group == Group.NO_GROUP) {
        LOG.error("Group not found trying to set the background");
        return i18n.t("Error uploading file");
      }

      final String relDir = FileUtils.groupToDir(group.getShortName());
      final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION) + relDir;
      fileManager.mkdir(absDir);

      File destFile = null;
      final String filenameUTF8 = new String(origFile.getName().getBytes(), UTF8);
      destFile = fileManager.createFileWithSequentialName(absDir, filenameUTF8);
      origFile.write(destFile);

      final String extension = FileUtils.getFileNameExtension(filenameUTF8, false);
      ImageUtilsDefault.generateThumbs(absDir, filenameUTF8, extension, false,
          Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_RESIZEWIDTH)),
          Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_THUMBSIZE)),
          Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_CROPSIZE)),
          Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_ICONSIZE)));

      LOG.info("Mimetype: " + mimeType);
      groupManager.setGroupBackgroundImage(group, filenameUTF8, mimeTypeS);

    } catch (final Exception e) {
      return i18n.t("Error uploading background");
    }
    return i18n.t("Background changed");
  }
}
