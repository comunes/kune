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
package cc.kune.core.server.manager;

import java.io.File;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Interface FileManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface FileManager {

  /**
   * Create a new file adding a numeric sequence if the file already exists.
   * 
   * @param dir
   *          the directory (use File.separator for dir delimiters)
   * @param fileName
   *          (the new file name)
   * @return if 'file.txt' exists it creates 'file 1.txt' and if 'file 1.txt'
   *         exists it creates 'file 2.txt' and so on
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  File createFileWithSequentialName(String dir, String fileName) throws IOException;

  /**
   * Exists.
   * 
   * @param file
   *          path
   * @return returns true if exists
   */
  boolean exists(String file);

  /**
   * Mkdir.
   * 
   * @param dir
   *          the directory (use File.separator for dir delimiters)
   * @return true if and only if the directory was created, along with all
   *         necessary parent directories; false otherwise
   */
  boolean mkdir(String dir);

  /**
   * Mv.
   * 
   * @param oldFile
   *          the old file
   * @param newFile
   *          the new file
   * @return true, if successful
   */
  boolean mv(String oldFile, String newFile);

  /**
   * Rm.
   * 
   * @param dir
   *          the directory (use File.separator for dir delimiters)
   * @param file
   *          the file to remove
   * @return true, if successful
   */
  boolean rm(String dir, String file);

  /**
   * Rmdir.
   * 
   * @param dir
   *          the directory (use File.separator for dir delimiters)
   * @return true, if successful
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  boolean rmdir(String dir) throws IOException;

}
