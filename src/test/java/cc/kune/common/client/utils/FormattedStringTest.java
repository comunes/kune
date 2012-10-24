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
package cc.kune.common.client.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import cc.kune.common.shared.utils.AbstractFormattedString;
import cc.kune.core.server.utils.FormattedString;

public class FormattedStringTest {

  private void checkCopy(final AbstractFormattedString orig, final AbstractFormattedString copy) {
    copy.setTemplate("Other");
    assertNotSame(orig, copy);
    assertNotSame(orig.getTemplate(), copy.getTemplate());
  }

  @Test
  public void testCopy() {
    final FormattedString orig = new FormattedString(true, "Template", "arg1", "arg2", "arg3");
    final FormattedString copy = orig.copy();
    checkCopy(orig, copy);
    final ClientFormattedString origC = new ClientFormattedString(true, "Template", "arg1", "arg2",
        "arg3");
    final ClientFormattedString copyC = origC.copy();
    checkCopy(origC, copyC);
  }

  @Test
  public void testQuotes() {
    assertEquals("test 100%", FormattedString.build("%s 100%%", "test").getString());
    // assertEquals("test 100%", ClientFormattedString.build("%s 100%%",
    // "test").getString());
    assertNotNull(FormattedString.build("'%s''''\"%s\"", "test", "test2kl").getString());
    assertNotNull(ClientFormattedString.build("'%s''''\"%s\"", "test", "test2kl").getString());
  }
}
