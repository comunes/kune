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
package cc.kune.core.client.i18n;

import static org.junit.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ConstantsTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ConstantsTest {

  /** The english. */
  private ResourceBundle english;

  /** The spanish. */
  private ResourceBundle spanish;

  /**
   * Basic english found.
   */
  @Test
  public void basicEnglishFound() {
    assertEquals("Are you Sure?", english.getString(I18nUtils.convertMethodName("Are you Sure?")));
  }

  /**
   * Starting with numbers should add underscore.
   */
  @Test
  public void startingWithNumbersShouldAddUnderscore() {
    assertEquals("_1day", I18nUtils.convertMethodName("1day"));
  }

  /**
   * Basic english found with note for translators.
   */
  @Test
  public void basicEnglishFoundWithNoteForTranslators() {
    assertEquals("Next", english.getString(I18nUtils.convertMethodName("Next", "used in button")));
  }

  /**
   * Basic english not found.
   */
  @Test(expected = MissingResourceException.class)
  public void basicEnglishNotFound() {
    english.getString(I18nUtils.convertMethodName("Other thing"));
  }

  /**
   * Basic found.
   */
  @Test
  public void basicFound() {
    assertEquals("¡Por favor confirma!",
        spanish.getString(I18nUtils.convertMethodName("Please confirm!")));
  }

  /**
   * Basic not found.
   */
  @Test(expected = MissingResourceException.class)
  public void basicNotFound() {
    assertTrue(english.containsKey(I18nUtils.convertMethodName("Something not translated")));
    assertFalse(spanish.containsKey(I18nUtils.convertMethodName("Something not translated")));
    assertEquals("Something not translated",
        spanish.getString(I18nUtils.convertMethodName("Something not translated")));
  }

  /**
   * Before.
   */
  @Before
  public void before() {
    english = ResourceBundle.getBundle("TestConstants", Locale.ENGLISH);
    spanish = ResourceBundle.getBundle("TestConstants", new Locale("es"));
    Locale.setDefault(Locale.ENGLISH);
  }

  /**
   * Multiple lines.
   */
  @Test
  public void multipleLines() {
    assertEquals("some multiple lines",
        english.getString(I18nUtils.convertMethodName("some multiple lines")));
  }

  /**
   * Multiple lines with carriage return.
   */
  @Test
  public void multipleLinesWithCarriageReturn() {
    assertEquals("some\nmultiple\nlines\nwith\ncarriage",
        english.getString(I18nUtils.convertMethodName("some\nmultiple\nlines\nwith\ncarriage")));
  }
}
