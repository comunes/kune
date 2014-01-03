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
import java.io.IOException;

import cc.kune.core.server.manager.FileManager;

import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class FileManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class FileManagerDefault implements FileManager {

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.FileManager#createFileWithSequentialName(java
   * .lang.String, java.lang.String)
   */
  @Override
  public File createFileWithSequentialName(final String dir, final String fileName) throws IOException {
    String fileNameProposal = String.valueOf(fileName);
    File file = new File(dir, fileNameProposal);
    while (file.exists()) {
      fileNameProposal = FileUtils.getNextSequentialFileName(fileNameProposal, true);
      // @PMD:REVIEWED:AvoidInstantiatingObjectsInLoops: by vjrj on
      // 21/05/09 13:15
      file = new File(dir, fileNameProposal);
    }
    if (file.createNewFile()) {
      return file;
    } else {
      throw new IOException("Cannot create sequential file name");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.FileManager#exists(java.lang.String)
   */
  @Override
  public boolean exists(final String file) {
    return new File(file).exists();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.FileManager#mkdir(java.lang.String)
   */
  @Override
  public boolean mkdir(final String dir) {
    return new File(dir).mkdirs();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.FileManager#mv(java.lang.String,
   * java.lang.String)
   */
  @Override
  public boolean mv(final String oldPath, final String newPath) {
    final File oldFile = new File(oldPath);
    return oldFile.renameTo(new File(newPath));
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.FileManager#rm(java.lang.String,
   * java.lang.String)
   */
  @Override
  public boolean rm(final String dir, final String fileName) {
    final File file = new File(dir, fileName);
    return file.delete();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.FileManager#rmdir(java.lang.String)
   */
  @Override
  public boolean rmdir(final String dir) throws IOException {
    final File file = new File(dir);
    if (!file.isDirectory()) {
      throw new IOException("rmdir: " + dir + ": Not a directory");
    }
    if (file.listFiles().length != 0) {
      throw new IOException("rmdir: " + dir + ": Directory not empty");
    }
    return file.delete();
  }
}
