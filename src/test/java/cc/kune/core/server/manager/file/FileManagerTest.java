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
package cc.kune.core.server.manager.file;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.manager.FileManager;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class FileManagerTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FileManagerTest {

  /** The file manager. */
  @Inject
  FileManager fileManager;

  /** The temp dir. */
  private String tempDir;

  /**
   * Inject.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Before
  public void inject() throws IOException {
    new IntegrationTestHelper(false, this);
    // TestHelper.inject(this);
    tempDir = File.createTempFile("temp", "txt").getParent();
  }

  /**
   * Seq.
   * 
   * @param file
   *          the file
   * @return the string
   */
  private String seq(final String file) {
    return FileUtils.getNextSequentialFileName(file, true);
  }

  /**
   * Test3 file creation with seq.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void test3FileCreationWithSeq() throws IOException {
    final File tempFile = File.createTempFile("some file name", ".txt");
    final File newFile1 = fileManager.createFileWithSequentialName(tempDir, tempFile.getName());
    final File newFile2 = fileManager.createFileWithSequentialName(tempDir, tempFile.getName());
    final File newFile3 = fileManager.createFileWithSequentialName(tempDir, tempFile.getName());
    assertEquals(tempDir + File.separator + seq(seq(seq(tempFile.getName()))),
        newFile3.getAbsolutePath());
    newFile1.delete();
    newFile2.delete();
    newFile3.delete();
  }

  /**
   * Test dir creation.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void testDirCreation() throws IOException {
    final String newTestDir = tempDir + File.separator + "test1" + File.separator + "test2"
        + File.separator;
    assertTrue(fileManager.mkdir(newTestDir));
    final File dir = new File(newTestDir);
    assertTrue(dir.exists());
    fileManager.rmdir(newTestDir);
    assertFalse(dir.exists());
  }

  /**
   * Test file creation with seq.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Test
  public void testFileCreationWithSeq() throws IOException {
    final File tempFile = File.createTempFile("some file name", ".txt");
    final File newFile = fileManager.createFileWithSequentialName(tempDir, tempFile.getName());
    assertEquals(tempDir + File.separator + seq(tempFile.getName()), newFile.getAbsolutePath());
    newFile.delete();
  }
}
