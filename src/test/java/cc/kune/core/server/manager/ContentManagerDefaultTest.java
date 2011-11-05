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

import static cc.kune.docs.shared.DocsConstants.TYPE_DOCUMENT;
import static cc.kune.docs.shared.DocsConstants.TYPE_UPLOADEDFILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.client.errors.MoveOnSameContainerException;
import cc.kune.core.client.errors.NameInUseException;
import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.core.server.manager.impl.SearchResult;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Content;

public class ContentManagerDefaultTest extends PersistencePreLoadedDataTest {

  private static final String BODY = "body";
  private static final String MIMETYPE = "image";
  private static final String TITLE = "title";

  private Content createContent() {
    assertNotNull(container);
    final Content cnt = creationService.createContent(TITLE, BODY, user, container, TYPE_UPLOADEDFILE);
    persist(cnt);
    containerManager.persist(container);
    return cnt;
  }

  private void createContentWithMimeAndCheck(final String mimetype) {
    final Content cnt = creationService.createContent(TITLE, BODY, user, container, TYPE_UPLOADEDFILE);
    cnt.setMimeType(new BasicMimeType(mimetype));
    persist(cnt);
    final Content newCnt = contentManager.find(cnt.getId());
    assertEquals(mimetype, newCnt.getMimeType().toString());
  }

  @Ignore
  @Test
  public void testBasicBodySearch() {
    createContent();
    final SearchResult<Content> search = contentManager.search(BODY);
    contentManager.reIndex();
    assertEquals(1, search.getSize());
  }

  @Test
  public void testBasicMimePersist() {
    final String mimetype = "application/pdf";
    createContentWithMimeAndCheck(mimetype);
  }

  @Test
  public void testBasicMimePersistWithoutSubtype() {
    final String mimetype = "application";
    createContentWithMimeAndCheck(mimetype);
  }

  @Test
  public void testBasicMimeSearchWithQueriesAndFields() {
    createContentWithMimeAndCheck(MIMETYPE + "/png");
    contentManager.reIndex();
    final SearchResult<Content> search = contentManager.search(new String[] { MIMETYPE },
        new String[] { "mimeType.mimetype" }, 0, 10);
    assertEquals(1, search.getSize());
  }

  @Test
  public void testBasicMove() {
    final Content content = createContent();
    final Content newContent = contentManager.moveContent(content, otherContainer);
    assertEquals(newContent.getContainer(), otherContainer);
  }

  @Test(expected = NameInUseException.class)
  public void testBasicMoveWithExistingNameShouldFail() {
    final Content content = createContent();
    final Content sameNameCnt = creationService.createContent(TITLE, BODY, user, otherContainer,
        TYPE_UPLOADEDFILE);
    persist(sameNameCnt);
    contentManager.moveContent(content, otherContainer);
  }

  @Test
  public void testBasicSearchWithQueriesAndFields() {
    createContentWithMimeAndCheck(MIMETYPE);
    final SearchResult<Content> search = contentManager.search(new String[] { BODY },
        new String[] { "lastRevision.body" }, 0, 10);
    contentManager.reIndex();
    assertEquals(1, search.getSize());
  }

  @Test
  public void testBasicTitleSearch() {
    createContent();
    final SearchResult<Content> search = contentManager.search(TITLE);
    contentManager.reIndex();
    assertEquals(1, search.getSize());
  }

  @Test(expected = MoveOnSameContainerException.class)
  public void testMoveFolderToSame() {
    final Content content = createContent();
    contentManager.moveContent(content, content.getContainer());
  }

  @Ignore
  @Test
  public void testtMimeSearch() {
    createContentWithMimeAndCheck(MIMETYPE + "/png");
    contentManager.reIndex();
    final SearchResult<Content> search = contentManager.searchMime(BODY, 0, 10, "asb", MIMETYPE);
    assertEquals(1, search.getSize());
  }

  /**
   * This normally fails with mysql (not configured for utf-8), see the INSTALL
   * mysql section
   */
  @Test
  public void testUTF8Persist() {
    final Content cnt = creationService.createContent("汉语/漢語", "汉语/漢語", user, container, TYPE_DOCUMENT);
    final Content newCnt = contentManager.find(cnt.getId());
    assertEquals("汉语/漢語", newCnt.getTitle());
  }
}
