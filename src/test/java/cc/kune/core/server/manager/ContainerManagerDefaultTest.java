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
package cc.kune.core.server.manager;

import static cc.kune.docs.shared.DocsConstants.TYPE_FOLDER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.MoveOnSameContainerException;
import cc.kune.core.client.errors.NameInUseException;
import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.docs.shared.DocsConstants;
import cc.kune.domain.Container;

public class ContainerManagerDefaultTest extends PersistencePreLoadedDataTest {

  private Container rootFolder;

  @Before
  public void before() {
    rootFolder = containerManager.createRootFolder(user.getUserGroup(), DocsConstants.NAME,
        DocsConstants.ROOT_NAME, TYPE_FOLDER);
  }

  private Container createContainer(final Container parent) {
    return createContainer(parent, "Some title");
  }

  private Container createContainer(final Container parent, final String title) {
    return containerManager.createFolder(user.getUserGroup(), parent, title, english, TYPE_FOLDER);
  }

  @Test
  public void testCreateFolder() {
    final Container newFolder = createContainer(rootFolder);
    assertNotNull(newFolder.getParent());
    assertEquals(1, rootFolder.getChilds().size());
    assertEquals(0, newFolder.getChilds().size());
    assertEquals(1, rootFolder.getAbsolutePath().size());
    assertEquals(2, newFolder.getAbsolutePath().size());
  }

  @Test
  public void testMoveFolder() {
    final Container folderToMove = createContainer(rootFolder);
    final Container newParentFolder = createContainer(rootFolder);
    assertEquals(0, newParentFolder.getChilds().size());
    containerManager.moveContainer(folderToMove, newParentFolder);
    assertEquals(newParentFolder, folderToMove.getParent());
    assertEquals(1, newParentFolder.getChilds().size());
    assertEquals(1, rootFolder.getChilds().size());
    assertEquals(3, folderToMove.getAbsolutePath().size());
    assertEquals(rootFolder, folderToMove.getAbsolutePath().get(0));
    assertEquals(newParentFolder, folderToMove.getAbsolutePath().get(1));
    assertEquals(folderToMove, folderToMove.getAbsolutePath().get(2));
  }

  @Test(expected = NameInUseException.class)
  public void testMoveFolderNameExists() {
    final Container folderToMove = createContainer(rootFolder);
    final Container newParentFolder = createContainer(rootFolder);
    // Create a folder with the same name
    createContainer(newParentFolder);
    containerManager.moveContainer(folderToMove, newParentFolder);
  }

  @Test(expected = MoveOnSameContainerException.class)
  public void testMoveFolderToSame() {
    final Container folderToMove = createContainer(rootFolder);
    containerManager.moveContainer(folderToMove, rootFolder);
  }

  @Test
  public void testMoveFolderUp() {
    final Container parentFolder = createContainer(rootFolder);
    final Container folderToMove = createContainer(parentFolder, "Other title");
    assertEquals(0, folderToMove.getChilds().size());
    assertEquals(1, parentFolder.getChilds().size());
    containerManager.moveContainer(folderToMove, rootFolder);
    assertEquals(rootFolder, folderToMove.getParent());
    assertEquals(0, parentFolder.getChilds().size());
    assertEquals(2, rootFolder.getChilds().size());
    assertEquals(2, folderToMove.getAbsolutePath().size());
    assertEquals(rootFolder, folderToMove.getAbsolutePath().get(0));
    assertEquals(folderToMove, folderToMove.getAbsolutePath().get(1));
  }

  @Test
  public void testMoveMiddleFolder() {
    final Container parentFolder = createContainer(rootFolder);
    final Container middleFolder = createContainer(parentFolder, "Middle");
    final Container childFolder = createContainer(middleFolder, "Child");
    assertEquals(0, childFolder.getChilds().size());
    assertEquals(1, parentFolder.getChilds().size());
    assertEquals(1, middleFolder.getChilds().size());
    assertEquals(4, childFolder.getAbsolutePath().size());
    assertEquals(rootFolder, childFolder.getAbsolutePath().get(0));
    assertEquals(parentFolder, childFolder.getAbsolutePath().get(1));
    assertEquals(middleFolder, childFolder.getAbsolutePath().get(2));
    assertEquals(childFolder, childFolder.getAbsolutePath().get(3));
    containerManager.moveContainer(middleFolder, rootFolder);
    assertEquals(rootFolder, middleFolder.getParent());
    assertEquals(0, parentFolder.getChilds().size());
    assertEquals(2, rootFolder.getChilds().size());
    assertEquals(3, childFolder.getAbsolutePath().size());
    assertEquals(rootFolder, childFolder.getAbsolutePath().get(0));
    assertEquals(middleFolder, childFolder.getAbsolutePath().get(1));
    assertEquals(childFolder, childFolder.getAbsolutePath().get(2));
    assertEquals(1, rootFolder.getAbsolutePath().size());
    assertEquals(rootFolder, rootFolder.getAbsolutePath().get(0));
    assertEquals(2, parentFolder.getAbsolutePath().size());
    assertEquals(rootFolder, parentFolder.getAbsolutePath().get(0));
    assertEquals(parentFolder, parentFolder.getAbsolutePath().get(1));
  }

  @Test(expected = AccessViolationException.class)
  public void testMoveRootFolderFails() {
    containerManager.moveContainer(rootFolder, rootFolder);
  }
}
