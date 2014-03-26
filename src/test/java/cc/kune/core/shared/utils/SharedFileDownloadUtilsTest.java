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
package cc.kune.core.shared.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.shared.FileConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class SharedFileDownloadUtilsTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SharedFileDownloadUtilsTest {

  /** The Constant GROUP. */
  private static final String GROUP = "groupname";

  /** The prefix utils. */
  private SharedFileDownloadUtils[] prefixUtils;

  /** The util no prefix. */
  private SharedFileDownloadUtils utilNoPrefix;

  /** The util prefix. */
  private SharedFileDownloadUtils utilPrefix;

  /** The util prefix with slash. */
  private SharedFileDownloadUtils utilPrefixWithSlash;

  /**
   * Before.
   */
  @Before
  public void before() {
    utilPrefix = new SharedFileDownloadUtils("http://example.org");
    utilPrefixWithSlash = new SharedFileDownloadUtils("http://example.org/");
    utilNoPrefix = new SharedFileDownloadUtils("");
    prefixUtils = new SharedFileDownloadUtils[] { utilPrefix, utilPrefixWithSlash };
  }

  /**
   * Test get logo html.
   */
  @Test
  public void testGetLogoHtml() {
    for (final SharedFileDownloadUtils util : prefixUtils) {
      assertTrue(util.getLogoAvatarHtml(GROUP, false, false, 50, 5).contains(
          "'http://example.org/others/defgroup.gif"));
      assertTrue(
          util.getLogoAvatarHtml(GROUP, false, false, 50, 5),
          util.getLogoAvatarHtml(GROUP, false, false, 50, 5).contains(
              "http://example.org/others/defgroup.gif"));
      assertTrue(
          util.getLogoAvatarHtml(GROUP, false, true, 50, 5),
          util.getLogoAvatarHtml(GROUP, false, true, 50, 5).contains(
              "/EntityLogoDownloadManager?token=groupname&onlyusers=false"));
      assertTrue(
          utilNoPrefix.getLogoAvatarHtml(GROUP, false, true, 50, 5),
          util.getLogoAvatarHtml(GROUP, false, true, 50, 5).contains(
              "/EntityLogoDownloadManager?token=groupname&onlyusers=false"));
      assertTrue(
          util.getLogoAvatarHtml(GROUP, true, false, 50, 5),
          util.getLogoAvatarHtml(GROUP, true, false, 50, 5).contains(
              "'http://example.org/ws/servlets/EntityLogoDownloadManager?token=groupname&onlyusers=false"));
    }

    assertTrue(
        utilNoPrefix.getLogoAvatarHtml(GROUP, true, true, 50, 5),
        utilNoPrefix.getLogoAvatarHtml(GROUP, true, true, 50, 5).contains(
            "/ws/servlets/EntityLogoDownloadManager?token=groupname"));
    assertTrue(!utilNoPrefix.getLogoAvatarHtml(GROUP, true, true, 50, 5).contains("http"));
  }

  /**
   * Test user avatar.
   */
  @Test
  public void testUserAvatar() {
    assertEquals("http://example.org" + FileConstants.LOGODOWNLOADSERVLET
        + "?token=groupname&onlyusers=true", utilPrefix.getUserAvatar(GROUP));
    assertEquals("http://example.org" + FileConstants.LOGODOWNLOADSERVLET
        + "?token=groupname&onlyusers=true", utilPrefixWithSlash.getUserAvatar(GROUP));
    assertEquals(FileConstants.LOGODOWNLOADSERVLET + "?token=groupname&onlyusers=true",
        utilNoPrefix.getUserAvatar(GROUP));
  }
}
