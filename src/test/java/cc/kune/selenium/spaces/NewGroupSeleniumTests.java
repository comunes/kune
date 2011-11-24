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
package cc.kune.selenium.spaces;

import org.testng.annotations.Test;

import cc.kune.common.client.errors.UIException;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.selenium.KuneSeleniumTest;
import cc.kune.selenium.SeleniumConstants;
import cc.kune.selenium.SeleniumUtils;

public class NewGroupSeleniumTests extends KuneSeleniumTest {

  @Test(dataProvider = "newGroups")
  public void basicSignIn(final String shortname, final String longname, final GroupType groupType) {
    SeleniumUtils.fastSpeed(true);
    login.assertIsDisconnected();

    login.signIn(SeleniumConstants.USER_SHORNAME, SeleniumConstants.USER_PASSWD);
    login.assertIsConnectedAs(SeleniumConstants.USER_SHORNAME);
    site.groupSpaceBtn.click();
    site.newGroupBtn.click();

    final String prefix = getTempString();

    newGroup.shortName.sendKeys(shortname + prefix);
    final String longNameTranslated = t(longname) + " " + prefix;
    newGroup.longName.sendKeys(longNameTranslated);
    newGroup.publicDescription.sendKeys(t("This is only a test group"));
    newGroup.tags.sendKeys("tag1 tag2");
    switch (groupType) {
    case PROJECT:
      newGroup.projectType.click();
      break;
    case ORGANIZATION:
      newGroup.orgType.click();
      break;
    case CLOSED:
      newGroup.closedType.click();
      break;
    case COMMUNITY:
      newGroup.closedType.click();
      break;
    default:
      throw new UIException("New group types?");
    }
    newGroup.registerBtn.click();

    entityHeader.waitForEntityTitle(longNameTranslated);

    login.assertIsDisconnected();
  }

}
