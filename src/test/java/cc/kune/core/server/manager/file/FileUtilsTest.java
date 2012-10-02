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
 */
package cc.kune.core.server.manager.file;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.kune.core.server.manager.file.FileUtils;

public class FileUtilsTest {

  private static final String SIMPLE_FILE_NAME = "simple file name";
  private static final String EXT = ".someext";

  @Test
  public void dotFileRetSeq1() {
    assertEquals("." + SIMPLE_FILE_NAME + " 1",
        FileUtils.getNextSequentialFileName("." + SIMPLE_FILE_NAME, true));
  }

  @Test
  public void dotFileRetSeq1IgnoreExt() {
    assertEquals("." + SIMPLE_FILE_NAME + " 1",
        FileUtils.getNextSequentialFileName("." + SIMPLE_FILE_NAME, false));
  }

  @Test
  public void getExtensionBasic() {
    assertEquals("someext", FileUtils.getFileNameExtension("file.name.with.someext", false));
  }

  @Test
  public void getExtensionWithDot() {
    assertEquals("", FileUtils.getFileNameExtension("file name with no ext.", false));
  }

  @Test
  public void getExtensionWithDotBasic() {
    assertEquals(".someext", FileUtils.getFileNameExtension("file.name.with.someext", true));
  }

  @Test
  public void getExtensionWithDotWithFinalDot() {
    assertEquals("", FileUtils.getFileNameExtension("file name with no ext.", true));
  }

  @Test
  public void getExtensionWithDotWithInitialDot() {
    assertEquals("", FileUtils.getFileNameExtension(".file name with no ext", true));
  }

  @Test
  public void getExtensionWithInitialDot() {
    assertEquals("", FileUtils.getFileNameExtension(".file name with no ext", false));
  }

  @Test
  public void testFileNameWExtensionSimple() {
    assertEquals("test", FileUtils.getFileNameWithoutExtension("test.txt", "txt"));
  }

  @Test
  public void testFileNameWExtensionSimpleWithDot() {
    assertEquals("test", FileUtils.getFileNameWithoutExtension("test.txt", ".txt"));
  }

  @Test
  public void testFileNameWithoutExtension() {
    assertEquals("test", FileUtils.getFileNameWithoutExtension("test", ""));
  }

  @Test
  public void testFileNameWNoExtensionWithDot() {
    assertEquals("test.", FileUtils.getFileNameWithoutExtension("test.", ""));
  }

  @Test
  public void testGetFilenameExtNull() {
    assertEquals("", FileUtils.getFileNameExtension(null, true));
  }

  @Test
  public void testIfSeq101return102() {
    assertEquals(SIMPLE_FILE_NAME + " 102",
        FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + " 101"));
  }

  @Test
  public void testIfSeq1return2() {
    assertEquals(SIMPLE_FILE_NAME + " 2", FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + " 1"));
  }

  @Test
  public void testIfSeq1WithExtreturn2WithExt() {
    assertEquals(SIMPLE_FILE_NAME + " 2" + EXT,
        FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + " 1" + EXT, true));
  }

  @Test
  public void testNoSeqReturn1() {
    assertEquals(SIMPLE_FILE_NAME + " 1", FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME));
  }

  @Test
  public void testNoSeqWithExtNotTakedIntoAccountReturn1() {
    assertEquals(SIMPLE_FILE_NAME + EXT + " 1",
        FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + EXT, false));
  }

  @Test
  public void testNoSeqWithExtReturn1WithExt() {
    assertEquals(SIMPLE_FILE_NAME + " 1" + EXT,
        FileUtils.getNextSequentialFileName(SIMPLE_FILE_NAME + EXT, true));
  }

}
