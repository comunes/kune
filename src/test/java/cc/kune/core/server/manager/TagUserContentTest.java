/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

public class TagUserContentTest extends PersistencePreLoadedDataTest {
  private static final String DUMMY_TAG = "dummy";
  @Inject
  TagUserContentFinder finder;
  @Inject
  TagUserContentManagerDefault manager;

  private Tag tag;
  @Inject
  TagManager tagManager;

  @Before
  public void before() {
    tag = new Tag(DUMMY_TAG);
    tagManager.persist(tag);
  }

  private void createSomeTagUserContent() {
    List<Tag> tags = manager.find(user, content);
    assertEquals(0, tags.size());
    createTagUserContent();
    tags = manager.find(user, content);
    assertEquals(1, tags.size());
  }

  private TagUserContent createTagUserContent() {
    final TagUserContent tagUC = new TagUserContent(tag, user, content);
    manager.persist(tagUC);
    return tagUC;
  }

  @Test
  public void getTagsAsString() {
    manager.setTags(user, content, DUMMY_TAG);
    final String tagS = manager.getTagsAsString(user, content);
    assertEquals(DUMMY_TAG, tagS);
  }

  @Test
  public void getTagsGrouped() {
    finder.getTagsGroups(user.getUserGroup());
  }

  @Test
  public void insertSomeUserContent() {
    createSomeTagUserContent();
  }

  @Test
  public void removeSomeUserContent() {
    createSomeTagUserContent();
    manager.remove(user, content);
    final List<Tag> tags = manager.find(user, content);
    assertEquals(0, tags.size());
  }

  @Test
  public void setTags() {
    List<Tag> tags = manager.find(user, content);
    assertEquals(0, tags.size());
    manager.setTags(user, content, DUMMY_TAG + " " + DUMMY_TAG);
    tags = manager.find(user, content);
    assertEquals(1, tags.size());
  }

  @Test
  public void setTagsRemoveBefore() {
    manager.setTags(user, content, "foo");
    manager.setTags(user, content, DUMMY_TAG);
    final List<Tag> tags = manager.find(user, content);
    assertEquals(1, tags.size());
    assertEquals(DUMMY_TAG, tags.get(0).getName());
  }

  @Test
  public void testInsertData() {
    final TagUserContent tagUC = createTagUserContent();
    assertNotNull(tagUC.getId());
  }
}
