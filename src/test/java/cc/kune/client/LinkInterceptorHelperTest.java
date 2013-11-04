/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkInterceptorHelperTest {

  @Test
  public void testBasicHash() {
    assertEquals("hash", LinkInterceptorHelper.getHash("http://kune.example.com/#hash"));
    assertEquals("hash", LinkInterceptorHelper.getHash("http://kune.example.com/#!hash"));
    assertEquals("hash", LinkInterceptorHelper.getHash("http://kune.example.com/?var=true#!hash"));
    assertEquals("hash",
        LinkInterceptorHelper.getHash("http://kune.example.com:8080/?var1=true&var2=false#!hash"));

  }

  @Test
  public void testIsLocal() {
    assertTrue(LinkInterceptorHelper.isLocal("http://kune.example.com/#hash", "http://kune.example.com/"));
    assertTrue(LinkInterceptorHelper.isLocal("http://kune.example.com/#!hash",
        "http://kune.example.com/"));
    assertFalse(LinkInterceptorHelper.isLocal("http://kune.example.com/#!hash",
        "http://other.example.com/"));
    assertFalse(LinkInterceptorHelper.isLocal("http://kune.example.com/#!hash", "kune.example.com/"));
    assertFalse(LinkInterceptorHelper.isLocal("http://kune.example.com/#!hash",
        "https://kune.example.com/"));
  }

}
