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
 */
package cc.kune.core.server.rpc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import magick.MagickException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.util.stringencoder.java7.Base64;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.ui.UploadFile;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.GroupNotFoundException;
import cc.kune.core.client.rpcservices.UpDownService;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.manager.FileManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.file.FileUtils;
import cc.kune.core.server.manager.file.ImageUtilsDefault;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.utils.ChangedLogosRegistry;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Group;

import com.google.inject.Inject;

/**
 * The Class UpDownloadRPC.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UpDownRPC implements RPC, UpDownService {
  public static final Log LOG = LogFactory.getLog(UpDownRPC.class);
  private final ChangedLogosRegistry changedLogosRegistry;
  private final FileManager fileManager;
  private final GroupManager groupManager;
  private final I18nTranslationService i18n;
  private final KuneProperties kuneProperties;

  @Inject
  public UpDownRPC(final KuneProperties kuneProperties, final GroupManager groupManager,
      final FileManager fileManager, final I18nTranslationService i18n,
      final ChangedLogosRegistry changedLogosRegistry) {
    this.kuneProperties = kuneProperties;
    this.groupManager = groupManager;
    this.fileManager = fileManager;
    this.i18n = i18n;
    this.changedLogosRegistry = changedLogosRegistry;
  }

  private File createTempFile(final UploadFile uploadedfile) throws IOException {
    final File origFile = File.createTempFile("origFile", ".tmp");

    dumpUploadedFile(uploadedfile, origFile);
    return origFile;
  }

  private void dumpUploadedFile(final UploadFile uploadedfile, final File origFile) throws IOException {
    final String img64 = uploadedfile.getData();
    final String truncated = img64.substring(img64.indexOf(',') + 1);
    final FileOutputStream output = new FileOutputStream(origFile);
    output.write(Base64.decode(truncated));
    output.close();
  }

  private void failedUpload() {
    throw new DefaultException("Cannot create group logo thumb");
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @KuneTransactional
  public void uploadBackground(final String hash, final StateToken token, final UploadFile file) {
    try {
      final String mimeTypeS = file.getType();
      final BasicMimeType mimeType = new BasicMimeType(mimeTypeS);
      if (!mimeType.getType().equals("image")) {
        throw new DefaultException("Trying to set a non image (" + mimeTypeS + ") as group background");
      }
      final Group group = groupManager.findByShortName(token.getGroup());

      if (group == Group.NO_GROUP) {
        LOG.error("Group not found trying to set the background");
        throw new GroupNotFoundException();
      }

      final String relDir = FileUtils.groupToDir(group.getShortName());
      final String absDir = kuneProperties.get(KuneProperties.UPLOAD_LOCATION) + relDir;
      fileManager.mkdir(absDir);

      final String filenameUTF8 = new String(file.getName().getBytes(), "UTF-8");
      final File destFile = fileManager.createFileWithSequentialName(absDir, filenameUTF8);
      dumpUploadedFile(file, destFile);

      final String extension = FileUtils.getFileNameExtension(filenameUTF8, false);
      ImageUtilsDefault.generateThumbs(absDir, filenameUTF8, extension, false,
          Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_RESIZEWIDTH)),
          Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_THUMBSIZE)),
          Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_CROPSIZE)),
          Integer.parseInt(kuneProperties.get(KuneProperties.IMAGES_ICONSIZE)));

      LOG.info("Mimetype: " + mimeType);
      groupManager.setGroupBackgroundImage(group, filenameUTF8, mimeTypeS);
      changedLogosRegistry.add(group.getShortName());
    } catch (final Exception e) {
      throw new DefaultException(i18n.t("Error uploading background"));
    }
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @KuneTransactional
  public void uploadLogo(final String hash, final StateToken token, final UploadFile uploadedfile) {

    final String type = uploadedfile.getType();
    final BasicMimeType mimeType = new BasicMimeType(type);
    if (!mimeType.getType().equals("image")) {
      throw new DefaultException("Trying to set a non image (" + mimeType + ") as group logo");
    }
    final Group group = groupManager.findByShortName(token.getGroup());

    try {
      final File origFile = createTempFile(uploadedfile);

      final File tmpDestFile = File.createTempFile("logoDest", "");

      final boolean result = ImageUtilsDefault.scaleImageToMax(origFile.getAbsolutePath(),
          tmpDestFile.getAbsolutePath(), FileConstants.LOGO_DEF_MAX_SIZE);

      if (result) {
        group.setLogo(FileUtils.getBytesFromFile(tmpDestFile));
        group.setLogoMime(mimeType);
        origFile.delete();
        tmpDestFile.delete();
        changedLogosRegistry.add(group.getShortName());
      } else {
        origFile.delete();
        tmpDestFile.delete();
        failedUpload();
      }
    } catch (final IOException e) {
      failedUpload();
    } catch (final MagickException e) {
      failedUpload();
    }
  }
}
