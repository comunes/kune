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

package cc.kune.common.shared.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TextUtilsTest {

  private static final String DOMAIN_REGEXP = "^http([s]|)://localhost/.*";

  @Test
  public void testDeAccent() {
    assertTrue(TextUtils.deAccent("áéíóúàèìòùâêîôûäëïöüÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÄËÏÖÜñÑçÇ").equals(
        "aeiouaeiouaeiouaeiouAEIOUAEIOUAEIOUAEIOUnNcC"));
  }

  @Test
  public void testEmail() {
    assertTrue("kk@ex.com".matches(TextUtils.EMAIL_REGEXP));
    assertTrue("kk@local.net".matches(TextUtils.EMAIL_REGEXP));
  }

  @Test
  public void testEmailList() {
    assertTrue("kk@ex.com,kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertTrue("kk@ex.com, kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertTrue("kk@ex.com,,,, kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertTrue("kk@ex.com,   kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertTrue("kk@ex.com,   kk@ex2.com, kk@ex3.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertFalse("kk@ex.com   ;kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertFalse("kk@ex.com;kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    // with Carriage Return, etc:
    assertTrue("kk@ex.com, \t  \n  \f  \r kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
  }

  @Test
  public void testRemoveHttp() {
    assertEquals("localhost", TextUtils.removeHttp("http://localhost/"));
    assertEquals("localhost", TextUtils.removeHttp("https://localhost/"));
    assertEquals("localhost", TextUtils.removeHttp("http://localhost"));
    assertEquals("localhost", TextUtils.removeHttp("https://localhost"));
    assertEquals("localhost:8080", TextUtils.removeHttp("http://localhost:8080/"));
    assertEquals("localhost:8080", TextUtils.removeHttp("https://localhost:8080/"));
  }

  @Test
  public void testUrlDomain() {
    assertTrue("http://localhost/kk".matches(DOMAIN_REGEXP));
    assertTrue("https://localhost/kk".matches(DOMAIN_REGEXP));
    assertFalse("http://localhost:9898/kk".matches(DOMAIN_REGEXP));
    assertFalse("http://localhost".matches(DOMAIN_REGEXP));
    assertFalse("ftp://localhost/kk".matches(DOMAIN_REGEXP));
  }
}
