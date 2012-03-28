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
package cc.kune.core.server.utils;

import org.junit.Test;

import cc.kune.core.client.errors.NameNotPermittedException;

public class FilenameUtilsTest {

  @Test(expected = NameNotPermittedException.class)
  public void testNoDot() {
    FilenameUtils.checkBasicFilename(".");
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoDoubleDot() {
    FilenameUtils.checkBasicFilename("..");
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoEmpty() {
    FilenameUtils.checkBasicFilename("");
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoNull() {
    FilenameUtils.checkBasicFilename(null);
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoReturn() {
    FilenameUtils.checkBasicFilename("\n");
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoSpace() {
    FilenameUtils.checkBasicFilename(" ");
  }
}
