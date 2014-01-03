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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cc.kune.core.shared.domain.utils.StateToken;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FileUtils {

  /** The Constant SLASH. */
  public static final String SLASH = File.separator;

  /**
   * Snippet from: http://www.java-tips.org/java-se-tips/java.io/reading-a-file
   * -into-a-byte-array.html
   * 
   * @param file
   *          the file
   * @return the bytes from file
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  public static byte[] getBytesFromFile(final File file) throws IOException {
    final InputStream iStream = new FileInputStream(file);

    // Get the size of the file
    final long length = file.length();

    if (length > Integer.MAX_VALUE) {
      // File is too large
    }

    // Create the byte array to hold the data
    final byte[] bytes = new byte[(int) length];

    // Read in the bytes
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length && (numRead = iStream.read(bytes, offset, bytes.length - offset)) >= 0) {
      offset += numRead;
    }

    // Ensure all the bytes have been read in
    if (offset < bytes.length) {
      throw new IOException("Could not completely read file " + file.getName());
    }

    // Close the input stream and return bytes
    iStream.close();
    return bytes;
  }

  /**
   * For filename extension info see:
   * http://en.wikipedia.org/wiki/File_name_extension
   * 
   * @param filename
   *          the filename
   * @param withDot
   *          the with dot
   * @return the file name extension
   */
  public static String getFileNameExtension(final String filename, final boolean withDot) {
    // also we can use FilenameUtils
    if (filename == null) {
      return "";
    }
    final int dotIndex = filename.lastIndexOf('.');
    if (dotIndex == -1 || dotIndex == 0) {
      return "";
    } else {
      if (withDot) {
        final String ext = filename.substring(dotIndex);
        return ext.length() == 1 ? "" : ext;
      } else {
        return filename.substring(dotIndex + 1);
      }
    }
  }

  /**
   * Gets the file name without extension.
   * 
   * @param fileName
   *          the file name
   * @param extension
   *          the extension
   * @return the file name without extension
   */
  public static String getFileNameWithoutExtension(final String fileName, final String extension) {
    final int extlength = extension.length();
    if (extlength > 0) {
      final boolean withDot = extension.charAt(0) == '.';
      return fileName.substring(0, fileName.length() - extlength - (withDot ? 0 : 1));
    } else {
      return fileName;
    }
  }

  /**
   * Gets the next sequential file name.
   * 
   * @param fileName
   *          the file name
   * @return the next sequential file name
   */
  public static String getNextSequentialFileName(final String fileName) {
    final int lastSpace = fileName.lastIndexOf(" ");
    if (lastSpace != -1) {
      final String suffix = fileName.substring(lastSpace + 1);
      try {
        final Integer i = Integer.valueOf(suffix);
        return fileName.substring(0, lastSpace + 1) + (i + 1);
      } catch (final NumberFormatException e) {
      }
    }
    return fileName + " 1";
  }

  /**
   * Gets the next sequential file name.
   * 
   * @param fileName
   *          the file name
   * @param preserveExtension
   *          the preserve extension
   * @return the next sequential file name
   */
  public static String getNextSequentialFileName(final String fileName, final boolean preserveExtension) {
    if (!preserveExtension) {
      return getNextSequentialFileName(fileName);
    }
    final int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex == -1 || dotIndex == 0) {
      return getNextSequentialFileName(fileName);
    } else {
      final String ext = fileName.substring(dotIndex);
      return getNextSequentialFileName(fileName.substring(0, dotIndex)) + ext;
    }
  }

  /**
   * Group to dir.
   * 
   * @param groupName
   *          the group name
   * @return the string
   */
  public static String groupToDir(final String groupName) {
    return SLASH + groupName + SLASH;
  }

  /**
   * To dir.
   * 
   * @param stateToken
   *          the state token
   * @return the string
   */
  public static String toDir(final StateToken stateToken) {
    return SLASH + stateToken.getGroup() + SLASH + stateToken.getTool() + SLASH + stateToken.getFolder()
        + SLASH;
  }

  /**
   * Exist.
   * 
   * @param file
   *          the file
   * @return true, if successful
   */
  public boolean exist(final String file) {
    return new File(file).exists();
  }
}
