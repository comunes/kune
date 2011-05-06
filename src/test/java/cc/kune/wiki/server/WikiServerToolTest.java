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
package cc.kune.wiki.server;

import static cc.kune.wiki.shared.WikiConstants.TYPE_FOLDER;
import static cc.kune.wiki.shared.WikiConstants.TYPE_ROOT;
import static cc.kune.wiki.shared.WikiConstants.TYPE_UPLOADEDFILE;
import static cc.kune.wiki.shared.WikiConstants.TYPE_WIKIPAGE;

import org.junit.Before;
import org.junit.Test;

import cc.kune.wiki.server.WikiServerTool;

public class WikiServerToolTest { // extends PersistenceTest {

  private WikiServerTool serverTool;

  @Before
  public void before() {
    serverTool = new WikiServerTool(null, null, null, null);
  }

  @Test
  public void testCreateContainerInCorrectContainer() {
    serverTool.checkContainerTypeId(TYPE_ROOT, TYPE_FOLDER);
    serverTool.checkContainerTypeId(TYPE_FOLDER, TYPE_FOLDER);
  }

  @Test
  public void testCreateContentInCorrectContainer() {
    serverTool.checkContentTypeId(TYPE_ROOT, TYPE_WIKIPAGE);
    serverTool.checkContentTypeId(TYPE_ROOT, TYPE_UPLOADEDFILE);
    serverTool.checkContentTypeId(TYPE_FOLDER, TYPE_WIKIPAGE);
    serverTool.checkContentTypeId(TYPE_FOLDER, TYPE_UPLOADEDFILE);
  }

}