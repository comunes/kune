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
package cc.kune.selenium.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.calclab.hablar.login.client.LoginMessages;
import com.calclab.hablar.search.client.SearchMessages;

public class I18nHelperTest {

  @Test
  public void testOnePlural() {
    assertEquals("Results for «test1»: One user found.",
        I18nHelper.get(SearchMessages.class, "searchResultsFor", "test1", 1));
  }

  @Test
  public void testSimpleArg() {
    assertEquals("Connected as test1", I18nHelper.get(LoginMessages.class, "connectedAs", "test1"));
  }

  @Test
  public void testSimpleSeveralArgs() {
    assertEquals("Results for «test1»: 2 users found.",
        I18nHelper.get(SearchMessages.class, "searchResultsFor", "test1", 2));
  }

}
