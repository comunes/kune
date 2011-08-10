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
package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;

public class IconsRegistryTest {

  private static final String CONTENT_TYPE_TEST = "somecontenttype";
  private static final String ICON = "someicon";
  private static final String JUSTANOTHERICON = "justanothericon";
  private static final String OTHERICON = "othericon";

  private IconsRegistry reg;

  @Before
  public void before() {
    reg = new IconsRegistry();
  }

  @Test
  public void testBasic() {
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, ICON);
    assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
  }

  @Test
  public void testBasicMimeType() {
    final BasicMimeTypeDTO mimeType = new BasicMimeTypeDTO("image/png");
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, mimeType, ICON);
    assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
    assertEquals(null, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
  }

  @Test
  public void testBasicMimeTypeWithDef() {
    final BasicMimeTypeDTO mimeType = new BasicMimeTypeDTO("image/png");
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, mimeType, ICON);
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, OTHERICON);
    assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
    assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
  }

  @Test
  public void testBasicMimeTypeWithDefType() {
    final BasicMimeTypeDTO mimeType = new BasicMimeTypeDTO("image/png");
    final BasicMimeTypeDTO genericMimeType = new BasicMimeTypeDTO("image");
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, mimeType, ICON);
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, genericMimeType, OTHERICON);
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, JUSTANOTHERICON);
    assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
    assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, genericMimeType));
    assertEquals(JUSTANOTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
  }

  @Test
  public void testBasicMimeTypeWithOnlyDefType() {
    final BasicMimeTypeDTO mimeType = new BasicMimeTypeDTO("image/png");
    final BasicMimeTypeDTO genericMimeType = new BasicMimeTypeDTO("image");
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, genericMimeType, OTHERICON);
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, JUSTANOTHERICON);
    assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
    assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, genericMimeType));
    assertEquals(JUSTANOTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
  }

  @Test
  public void testContentStatus() {
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, ContentStatus.inTheDustbin, ICON);
    assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, ContentStatus.inTheDustbin));
  }

  @Test
  public void testNoContentStatus() {
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, ContentStatus.inTheDustbin, ICON);
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, OTHERICON);
    assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, ContentStatus.editingInProgress));
  }

  @Test
  public void testNoResult() {
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, new BasicMimeTypeDTO("text", "plain"), ICON);
    assertEquals(null, reg.getContentTypeIcon(CONTENT_TYPE_TEST, new BasicMimeTypeDTO("text", "rtf")));
  }

  @Test
  public void testNullBasicMimeType() {
    final BasicMimeTypeDTO mimeType = null;
    reg.registerContentTypeIcon(CONTENT_TYPE_TEST, ICON);
    assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
  }
}
