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
package cc.kune.selenium.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.calclab.hablar.login.client.LoginMessages;
import com.calclab.hablar.search.client.SearchMessages;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nHelperTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nHelperTest {

  /**
   * Test one plural.
   */
  @Test
  public void testOnePlural() {
    assertEquals("Results for «test1»: One user found.",
        I18nHelper.get(SearchMessages.class, "searchResultsFor", "test1", 1));
  }

  /**
   * Test simple arg.
   */
  @Test
  public void testSimpleArg() {
    assertEquals("Connected as test1", I18nHelper.get(LoginMessages.class, "connectedAs", "test1"));
  }

  /**
   * Test simple several args.
   */
  @Test
  public void testSimpleSeveralArgs() {
    assertEquals("Results for «test1»: 2 users found.",
        I18nHelper.get(SearchMessages.class, "searchResultsFor", "test1", 2));
  }

}
