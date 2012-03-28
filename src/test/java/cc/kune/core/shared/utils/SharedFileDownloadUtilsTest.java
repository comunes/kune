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
package cc.kune.core.shared.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SharedFileDownloadUtilsTest {

  private static final String GROUP = "groupname";
  private SharedFileDownloadUtils noPrefixUtils;
  private SharedFileDownloadUtils prefixUtils;

  @Before
  public void before() {
    prefixUtils = new SharedFileDownloadUtils("http://example.org");
    noPrefixUtils = new SharedFileDownloadUtils("");
  }

  @Test
  public void testGetLogoHtml() {
    assertTrue(prefixUtils.getLogoAvatarHtml(GROUP, false, false, 50, 5).contains(
        "'http://example.org/others/defgroup.gif"));
    assertTrue(noPrefixUtils.getLogoAvatarHtml(GROUP, false, true, 50, 5),
        prefixUtils.getLogoAvatarHtml(GROUP, false, true, 50, 5).contains("/others/unknown.jpg"));
    assertTrue(
        prefixUtils.getLogoAvatarHtml(GROUP, true, false, 50, 5),
        prefixUtils.getLogoAvatarHtml(GROUP, true, false, 50, 5).contains(
            "'http://example.org/ws/servlets/EntityLogoDownloadManager?token=groupname"));
    assertTrue(
        noPrefixUtils.getLogoAvatarHtml(GROUP, true, true, 50, 5),
        noPrefixUtils.getLogoAvatarHtml(GROUP, true, true, 50, 5).contains(
            "/ws/servlets/EntityLogoDownloadManager?token=groupname"));
    assertTrue(!noPrefixUtils.getLogoAvatarHtml(GROUP, true, true, 50, 5).contains("http"));
  }

  @Test
  public void testUserAvatar() {
    assertEquals("http://example.org/ws/servlets/UserLogoDownloadManager?username=groupname",
        prefixUtils.getUserAvatar(GROUP));
    assertEquals("/ws/servlets/UserLogoDownloadManager?username=groupname",
        noPrefixUtils.getUserAvatar(GROUP));
  }

}
