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
package cc.kune.core.server.manager;

import static cc.kune.docs.shared.DocsToolConstants.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.client.errors.MoveOnSameContainerException;
import cc.kune.core.client.errors.NameInUseException;
import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Content;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentManagerDefaultTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentManagerDefaultTest extends PersistencePreLoadedDataTest {

  /** The Constant BODY. */
  private static final String BODY = "body";

  /** The Constant MIMETYPE. */
  private static final String MIMETYPE = "image";

  /** The Constant TITLE. */
  private static final String TITLE = "title";

  /**
   * Creates the content.
   * 
   * @return the content
   */
  private Content createContent() {
    assertNotNull(container);
    final Content cnt = creationService.createContent(TITLE, BODY, user, container, TYPE_UPLOADEDFILE);
    persist(cnt);
    containerManager.persist(container);
    return cnt;
  }

  /**
   * Creates the content with mime and check.
   * 
   * @param mimetype
   *          the mimetype
   */
  private void createContentWithMimeAndCheck(final String mimetype) {
    final Content cnt = creationService.createContent(TITLE, BODY, user, container, TYPE_UPLOADEDFILE);
    cnt.setMimeType(new BasicMimeType(mimetype));
    persist(cnt);
    final Content newCnt = contentManager.find(cnt.getId());
    assertEquals(mimetype, newCnt.getMimeType().toString());
  }

  /**
   * Test basic body search.
   */
  @Ignore
  @Test
  public void testBasicBodySearch() {
    createContent();
    final SearchResult<Content> search = contentManager.search(BODY);
    contentManager.reIndex();
    assertEquals(1, search.getSize());
  }

  /**
   * Test basic mime persist.
   */
  @Test
  public void testBasicMimePersist() {
    final String mimetype = "application/pdf";
    createContentWithMimeAndCheck(mimetype);
  }

  /**
   * Test basic mime persist without subtype.
   */
  @Test
  public void testBasicMimePersistWithoutSubtype() {
    final String mimetype = "application";
    createContentWithMimeAndCheck(mimetype);
  }

  /**
   * Test basic mime search with queries and fields.
   */
  @Test
  public void testBasicMimeSearchWithQueriesAndFields() {
    createContentWithMimeAndCheck(MIMETYPE + "/png");
    closeTransaction();
    contentManager.reIndex();
    final SearchResult<Content> search = contentManager.search(new String[] { MIMETYPE },
        new String[] { "mimeType.mimetype" }, 0, 10);
    assertEquals(1, search.getSize());
  }

  /**
   * Test basic move.
   */
  @Test
  public void testBasicMove() {
    final Content content = createContent();
    final Content newContent = contentManager.moveContent(content, otherContainer);
    assertEquals(newContent.getContainer(), otherContainer);
  }

  /**
   * Test basic move with existing name should fail.
   */
  @Test(expected = NameInUseException.class)
  public void testBasicMoveWithExistingNameShouldFail() {
    final Content content = createContent();
    final Content sameNameCnt = creationService.createContent(TITLE, BODY, user, otherContainer,
        TYPE_UPLOADEDFILE);
    persist(sameNameCnt);
    contentManager.moveContent(content, otherContainer);
  }

  /**
   * Test basic search with queries and fields.
   */
  @Ignore
  @Test
  public void testBasicSearchWithQueriesAndFields() {
    createContentWithMimeAndCheck(MIMETYPE);
    final SearchResult<Content> search = contentManager.search(new String[] { BODY },
        new String[] { "lastRevision.body" }, 0, 10);
    closeTransaction();
    contentManager.reIndex();
    assertEquals(1, search.getSize());
  }

  /**
   * Test basic title search.
   */
  @Test
  public void testBasicTitleSearch() {
    createContent();
    closeTransaction();
    final SearchResult<Content> search = contentManager.search(TITLE);
    contentManager.reIndex();
    assertEquals(1, search.getSize());
  }

  /**
   * Test move folder to same.
   */
  @Test(expected = MoveOnSameContainerException.class)
  public void testMoveFolderToSame() {
    final Content content = createContent();
    contentManager.moveContent(content, content.getContainer());
  }

  /**
   * Testt mime search.
   */
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
   * mysql section.
   */
  @Test
  public void testUTF8Persist() {
    final Content cnt = creationService.createContent("汉语/漢語", "汉语/漢語", user, container, TYPE_DOCUMENT);
    final Content newCnt = contentManager.find(cnt.getId());
    assertEquals("汉语/漢語", newCnt.getTitle());
  }
}
