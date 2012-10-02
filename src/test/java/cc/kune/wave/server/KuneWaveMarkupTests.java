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
package cc.kune.wave.server;

import static org.junit.Assert.*;

import org.junit.Test;
import org.waveprotocol.wave.model.document.util.XmlStringBuilder;

import cc.kune.core.server.manager.impl.ContentConstants;

import com.google.wave.api.Markup;

public class KuneWaveMarkupTests {

  private static final String FOO_MARKUP = "<b>Foo</b><br/>";

  @Test
  public void testMarkup() {
    final Markup markup = Markup.of(FOO_MARKUP);
    assertNotNull(markup.getMarkup());
    assertNotNull(markup.getText());
    assertNotNull(markup.getText().contains("\n"));
  }

  @Test
  public void testXmlStringBuilderOfMarkup() {
    XmlStringBuilder builder = XmlStringBuilder.createFromXmlString(FOO_MARKUP);
    assertTrue(builder.getLength() > 0);
    builder = XmlStringBuilder.createFromXmlString(ContentConstants.INITIAL_CONTENT);
    assertTrue(builder.getLength() > 0);
    builder = XmlStringBuilder.createFromXmlString(ContentConstants.WELCOME_WAVE_CONTENT);
    assertTrue(builder.getLength() > 0);
  }
}
