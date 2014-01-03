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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.core.server.manager.impl.TagUserContentManagerDefault;
import cc.kune.domain.Tag;
import cc.kune.domain.TagUserContent;
import cc.kune.domain.finders.TagUserContentFinder;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class TagUserContentTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TagUserContentTest extends PersistencePreLoadedDataTest {

  /** The Constant DUMMY_TAG. */
  private static final String DUMMY_TAG = "dummy";

  /** The finder. */
  @Inject
  TagUserContentFinder finder;

  /** The manager. */
  @Inject
  TagUserContentManagerDefault manager;

  /** The tag. */
  private Tag tag;

  /** The tag manager. */
  @Inject
  TagManager tagManager;

  /**
   * Before.
   */
  @Before
  public void before() {
    tag = new Tag(DUMMY_TAG);
    tagManager.persist(tag);
  }

  /**
   * Creates the some tag user content.
   */
  private void createSomeTagUserContent() {
    List<Tag> tags = manager.find(user, content);
    assertEquals(0, tags.size());
    createTagUserContent();
    tags = manager.find(user, content);
    assertEquals(1, tags.size());
  }

  /**
   * Creates the tag user content.
   * 
   * @return the tag user content
   */
  private TagUserContent createTagUserContent() {
    final TagUserContent tagUC = new TagUserContent(tag, user, content);
    manager.persist(tagUC);
    return tagUC;
  }

  /**
   * Gets the tags as string.
   * 
   * @return the tags as string
   */
  @Test
  public void getTagsAsString() {
    manager.setTags(user, content, DUMMY_TAG);
    final String tagS = manager.getTagsAsString(user, content);
    assertEquals(DUMMY_TAG, tagS);
  }

  /**
   * Gets the tags grouped.
   * 
   * @return the tags grouped
   */
  @Test
  public void getTagsGrouped() {
    finder.getTagsGroups(user.getUserGroup());
  }

  /**
   * Insert some user content.
   */
  @Test
  public void insertSomeUserContent() {
    createSomeTagUserContent();
  }

  /**
   * Removes the some user content.
   */
  @Test
  public void removeSomeUserContent() {
    createSomeTagUserContent();
    manager.remove(user, content);
    final List<Tag> tags = manager.find(user, content);
    assertEquals(0, tags.size());
  }

  /**
   * Sets the tags.
   */
  @Test
  public void setTags() {
    List<Tag> tags = manager.find(user, content);
    assertEquals(0, tags.size());
    manager.setTags(user, content, DUMMY_TAG + " " + DUMMY_TAG);
    tags = manager.find(user, content);
    assertEquals(1, tags.size());
  }

  /**
   * Sets the tags remove before.
   */
  @Test
  public void setTagsRemoveBefore() {
    manager.setTags(user, content, "foo");
    manager.setTags(user, content, DUMMY_TAG);
    final List<Tag> tags = manager.find(user, content);
    assertEquals(1, tags.size());
    assertEquals(DUMMY_TAG, tags.get(0).getName());
  }

  /**
   * Test insert data.
   */
  @Test
  public void testInsertData() {
    final TagUserContent tagUC = createTagUserContent();
    assertNotNull(tagUC.getId());
  }
}
