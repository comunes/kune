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
package cc.kune.selenium.spaces;

import org.testng.annotations.Test;

import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.selenium.KuneSeleniumTest;
import cc.kune.selenium.SeleniumConstants;
import cc.kune.selenium.SeleniumUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupSeleniumTests.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupSeleniumTests extends KuneSeleniumTest {

  /**
   * New group tests.
   * 
   * @param shortname
   *          the shortname
   * @param longname
   *          the longname
   * @param description
   *          the description
   * @param tags
   *          the tags
   * @param groupType
   *          the group type
   */
  @Test(dataProvider = "newGroups")
  public void newGroupTests(final String shortname, final String longname, final String description,
      final String tags, final GroupType groupType) {
    SeleniumUtils.fastSpeed(false);
    login.assertIsDisconnected();

    login.signIn(SeleniumConstants.USER_SHORNAME, SeleniumConstants.USER_PASSWD);
    login.assertIsConnectedAs(SeleniumConstants.USER_SHORNAME);

    final String sufix = getTempString();

    gotoToken(SiteTokens.NEW_GROUP);

    groupCreation(shortname, longname, description, tags, groupType, sufix);

    logout();

    login.assertIsDisconnected();
  }

  /**
   * Test tutorial.
   */
  @Test
  public void testTutorial() {
    SeleniumUtils.fastSpeed(false);
    login.assertIsDisconnected();

    login.signIn(SeleniumConstants.USER_SHORNAME, SeleniumConstants.USER_PASSWD);
    login.assertIsConnectedAs(SeleniumConstants.USER_SHORNAME);

    site.groupSpaceBtn.click();

    groupSpace.docTool.click();
    groupSpace.showTutorial(4, 5, 4, 4, 2, 5, 3, 6, 7, 6, 4, 4, 5, 5, 3, 3, 4, 6, 4, 3);

    logout();
    login.assertIsDisconnected();
  }

}
