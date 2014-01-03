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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtilsTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FileUtilsTest {

  /** The Constant SIMPLE_FILE_NAME. */
  private static final String SIMPLE_FILE_NAME = "simple file name";

  /** The Constant EXT. */
  private static final String EXT = ".someext";

  /**
   * Dot file ret seq1.
   */
  @Test
  public void dotFileRetSeq1() {
    assertEquals("." + SIMPLE_FILE_NAME + " 1",
        FileUtils.getNextSequentialFileName("." + SIMPLE_FILE_NAME, true));
  }

  /**
   * Dot file ret seq1 ignore ext.
   */
  @Test
  public void dotFileRetSeq1IgnoreExt() {
    assertEquals("." + SIMPLE_FILE_NAME + " 1",
        FileUtils.getNextSequentialFileName("." + SIMPLE_FILE_NAME, false));
  }

  /**
   * Gets the extension basic.
   * 
   * @return the extension basic
   */
  @Test
  public void getExtensionBasic() {
    assertEquals("someext", FileUtils.getFileNameExtension("file.name.with.someext", false));
  }

  /**
   * Gets the extension with dot.
   * 
   * @return the extension with dot
   */
  @Test
  public void getExtensionWithDot() {
    assertEquals("", FileUtils.getFileNameExtension("file name with no ext.", false));
  }

  /**
   * Gets the extension with dot basic.
   * 
   * @return the extension with dot basic
   */
  @Test
  public void getExtensionWithDotBasic() {
    assertEquals(".someext", FileUtils.getFileNameExtension("file.name.with.someext", true));
  }

  /**
   * Gets the extension with dot with final dot.
   * 
   * @return the extension with dot with final dot
   */
  @Test
  public void getExtensionWithDotWithFinalDot() {
    assertEquals("", FileUtils.getFileNameExtension("file name with no ext.", true));
  }

  /**
   * Gets the extension with dot with initial dot.
   * 
   * @return the extension with dot with initial dot
   */
  @Test
  public void getExtensionWithDotWithInitialDot() {
    assertEquals("", FileUtils.getFileNameExtension(".file name with no ext", true));
  }

  /**
   * Gets the extension with initial dot.
   * 
   * @return the extension with initial dot
   */
  @Test
  public void getExtensionWithInitialDot() {
    assertEquals("", FileUtils.getFileNameExtension(".file name with no ext", false));
  }

  /**
   * Test file name w extension simple.
   */
  @Test
  public void testFileNameWExtensionSimple() {
    assertEquals("test", FileUtils.getFileNameWithoutExtension("test.txt", "txt"));
  }

  /**
   * Test file name w extension simple with dot.
   */
  @Test
  public void testFileNameWExtensionSimpleWithDot() {
    assertEquals("test", FileUtils.getFileNameWithoutExtension("test.txt", ".txt"));
  }

  /**
   * Test file name without extension.
   */
  @Test
  public void testFileNameWithoutExtension() {
    assertEquals("test", FileUtils.getFileNameWithoutExtension("test", ""));
  }

  /**
   * Test file name w no extension with dot.
   */
  @Test
  public void testFileNameWNoExtensionWithDot() {
    assertEquals("test.", FileUtils.getFileNameWithoutExtension("test.", ""));
  }

  /**
   * Test get filename ext null.
   */
  @Test
  public void testGetFilenameExtNull() {
    assertEquals("", FileUtils.getFileNameExtension(null, true));
  }

  /**
   * Test if seq101return102.
   */
  @Test
  public void testIfSeq101return102() {
    assertEquals(SIMPLE_FILE_NAME + " 102",
        FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + " 101"));
  }

  /**
   * Test if seq1return2.
   */
  @Test
  public void testIfSeq1return2() {
    assertEquals(SIMPLE_FILE_NAME + " 2", FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + " 1"));
  }

  /**
   * Test if seq1 with extreturn2 with ext.
   */
  @Test
  public void testIfSeq1WithExtreturn2WithExt() {
    assertEquals(SIMPLE_FILE_NAME + " 2" + EXT,
        FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + " 1" + EXT, true));
  }

  /**
   * Test no seq return1.
   */
  @Test
  public void testNoSeqReturn1() {
    assertEquals(SIMPLE_FILE_NAME + " 1", FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME));
  }

  /**
   * Test no seq with ext not taked into account return1.
   */
  @Test
  public void testNoSeqWithExtNotTakedIntoAccountReturn1() {
    assertEquals(SIMPLE_FILE_NAME + EXT + " 1",
        FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + EXT, false));
  }

  /**
   * Test no seq with ext return1 with ext.
   */
  @Test
  public void testNoSeqWithExtReturn1WithExt() {
    assertEquals(SIMPLE_FILE_NAME + " 1" + EXT,
        FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + EXT, true));
  }

}
