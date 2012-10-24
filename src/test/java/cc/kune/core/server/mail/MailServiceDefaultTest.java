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
package cc.kune.core.server.mail;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.properties.KunePropertiesDefault;
import cc.kune.core.server.utils.FormattedString;

public class MailServiceDefaultTest {

  private MailServiceDefault service;

  @Test(expected = NullPointerException.class)
  public void basicException() {
    assertEquals(null, FormattedString.build(null).getString());
  }

  @Test(expected = NullPointerException.class)
  public void basicExceptionWithArgs() {
    assertEquals(null, FormattedString.build(null).getString());
    assertEquals(null, FormattedString.build(null, "arg").getString());
  }

  @Test
  public void basicFormat() {
    assertEquals("basic", FormattedString.build("basic").getString());
    assertEquals("basic arg", FormattedString.build("basic %s", "arg").getString());
    assertEquals("basic %s", FormattedString.build("basic %s", null).getString());
  }

  @Before
  public void before() {
    service = new MailServiceDefault(new KunePropertiesDefault("kune.properties"));
  }

  @Test
  public void simpleTest() {
    service.sendPlain(FormattedString.build("Some test subject"), FormattedString.build("Some body"),
        "vjrj@localhost");
  }

  @Test
  public void utf8Test() {
    service.sendPlain(FormattedString.build("áéíóú"), FormattedString.build("Some body áéíóú"),
        "vjrj@localhost");
    service.sendHtml(FormattedString.build("áéíóú"), FormattedString.build("Some body áéíóú"),
        "vjrj@localhost");
  }
}
