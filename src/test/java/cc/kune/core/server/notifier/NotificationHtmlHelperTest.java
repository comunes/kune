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
package cc.kune.core.server.notifier;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.properties.KunePropertiesDefault;
import cc.kune.core.server.utils.AbsoluteFileDownloadUtils;

public class NotificationHtmlHelperTest {
  private static final String GROUP_NAME = "groupShortName";
  private static final String MESSAGE = "some message";

  NotificationHtmlHelper helper;

  @Test
  public void basicTest() {
    assertNotNull(helper.groupNotification(GROUP_NAME, false, MESSAGE).getString());
    assertNotNull(helper.groupNotification(GROUP_NAME, true, MESSAGE).getString());
  }

  @Before
  public void before() {
    helper = new NotificationHtmlHelper(new AbsoluteFileDownloadUtils(new KuneBasicProperties(
        new KunePropertiesDefault("kune.properties"))));
  }

}
