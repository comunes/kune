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
import java.io.FileNotFoundException;
import java.io.IOException;

import magick.MagickException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Group;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityLogoUploadManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityLogoUploadManager extends FileGwtUploadAbstractServlet {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(FileGwtUploadServlet.class);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The group manager. */
  private final GroupManager groupManager;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /**
   * Instantiates a new entity logo upload manager.
   * 
   * @param kuneProperties
   *          the kune properties
   * @param groupManager
   *          the group manager
   * @param i18n
   *          the i18n
   */
  @Inject
  public EntityLogoUploadManager(final KuneProperties kuneProperties, final GroupManager groupManager,
      final I18nTranslationService i18n) {
    super(kuneProperties);
    this.groupManager = groupManager;
    this.i18n = i18n;
  }

  /**
   * Creates the uploaded file.
   * 
   * @param stateToken
   *          the state token
   * @param mimeTypeS
   *          the mime type s
   * @param origFile
   *          the orig file
   * @throws Exception
   *           the exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @throws MagickException
   *           the magick exception
   * @throws FileNotFoundException
   *           the file not found exception
   */
  protected void createUploadedFile(final StateToken stateToken, final String mimeTypeS,
      final File origFile) throws Exception, IOException, MagickException, FileNotFoundException {
    final BasicMimeType mimeType = new BasicMimeType(mimeTypeS);
    if (!mimeType.getType().equals("image")) {
      throw new Exception("Trying to set a non image (" + mimeTypeS + ") as group logo");
    }
    final Group group = groupManager.findByShortName(stateToken.getGroup());

    if (group == Group.NO_GROUP) {
      throw new Exception("Group not found trying to set the logo");
    }

    final File tmpDestFile = File.createTempFile("logoDest", "");

    final boolean result = ImageUtilsDefault.scaleImageToMax(origFile.getAbsolutePath(),
        tmpDestFile.getAbsolutePath(), FileConstants.LOGO_DEF_MAX_SIZE);

    if (result) {
      group.setLogo(FileUtils.getBytesFromFile(tmpDestFile));
      group.setLogoMime(mimeType);
      logFileDel(tmpDestFile.delete());
    } else {
      logFileDel(tmpDestFile.delete());
      throw new Exception("Cannot create group logo thumb");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.file.FileGwtUploadAbstractServlet#
   * createUploadedFile(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, java.lang.String,
   * org.apache.commons.fileupload.FileItem, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @KuneTransactional
  protected String createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId) {
    final String mimeTypeS = file.getContentType();
    try {
      final File tmpOrigFile = File.createTempFile("logoOrig", "");
      file.write(tmpOrigFile);
      createUploadedFile(stateToken, mimeTypeS, tmpOrigFile);
      logFileDel(tmpOrigFile.delete());
    } catch (final Exception e) {
      return i18n.t("Error uploading file");
    }
    return i18n.t("Success uploading");
  }

}
