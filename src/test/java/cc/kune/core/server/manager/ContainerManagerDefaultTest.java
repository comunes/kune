package cc.kune.core.server.manager;

import static cc.kune.docs.shared.DocsConstants.TYPE_FOLDER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.AccessViolationException;
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
    return containerManager.createFolder(user.getUserGroup(), parent, "Some title", english, TYPE_FOLDER);
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

  @Test(expected = AccessViolationException.class)
  public void testMoveRootFolderFails() {
    containerManager.moveContainer(rootFolder, rootFolder);
  }
}
