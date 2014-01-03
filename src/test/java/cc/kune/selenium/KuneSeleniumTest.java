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
package cc.kune.selenium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cc.kune.core.shared.dto.GroupType;

// TODO: Auto-generated Javadoc
/**
 * Shared behaviour in selenium tests.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class KuneSeleniumTest extends KuneSeleniumDefaults {

  /**
   * Gets the temp string.
   * 
   * @return the temp string
   */
  protected String getTempString() {
    final DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
    final String value = dateFormat.format(new Date());
    return value;
  }

  /**
   * Group creation.
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
   * @param sufix
   *          the sufix
   */
  public void groupCreation(final String shortname, final String longname, final String description,
      final String tags, final GroupType groupType, final String sufix) {

    site.groupSpaceBtn.click();
    showTitleSlide(t("Group space (collaboration space)"),
        t("Here you can create groups and collaborate within them"));
    // showMsg(t("Let's create a new group"));
    // sleep(<1000);
    if (site.newGroupBtn.isDisplayed()) {
      site.newGroupBtn.click();
    } else {
      // Several groups already
      // For now we use #newgroup in the future MyGroup constant
    }

    newGroup.create(sufix, shortname, longname, description, tags, groupType);

    entityHeader.waitForEntityTitle(longname);
    sleep(1000);

    showTitleSlide(t("Group space (collaboration space) II "), t("Let's see the group preferences"));

    groupSpace.groupOptions.click();
    sleep(1000);
    groupSpace.groupOptionsTools.click();
    sleep(1000);
    doScreenshot("groupOptions");
    groupSpace.groupOptionsLicense.click();
    sleep(2000);
    groupSpace.groupOptionsClose.click();

    groupSpace.socialNetOptions.click();
    doScreenshot("socialNetOptions");
    sleep(3000);

    showTitleSlide(t("Group space (collaboration space) III "),
        t("Let's see the different tools you have available"));

    // docs
    showTooltip(groupSpace.firstFolderItem);
    sleep(1500);
    groupSpace.openFirtsContent();

    groupSpace.entityTitle.click();
    sendKeys(groupSpace.entityTitleTextarea, t("About us\n"));
    sleep(2000);

    groupSpace.goParentBtn.click();
    groupSpace.newContainerBtn.click();
    groupSpace.entityTitle.click();
    groupSpace.entityTitleTextarea.sendKeys(t("Archive\n"));
    sleep(1000);
    groupSpace.goParentBtn.click();
    groupSpace.newMenuBtn.click();
    sleep(2000);
    // FIXME move content?
    groupSpace.showTutorial(3, 4, 5, 4, 4, 2, -5, 3, 6, 7, 6, 4, 4, 5, 5, 3, 3, 4, 6, 4, 3);

    // // blogs
    // groupSpace.blogTool.click();
    // showTooltip(groupSpace.blogTool);
    // sleep(3000);
    // groupSpace.showTutorial(3, 4, 3, 3, 6, 5, 5, 3, 3);
    //
    // // chats
    // groupSpace.chatTool.click();
    // showTooltip(groupSpace.chatTool);
    // sleep(3000);
    // groupSpace.showTutorial(3, 6, 6, 4, 7, 3, 3);

    // lists
    groupSpace.listTool.click();
    showTooltip(groupSpace.listTool);
    sleep(2000);
    groupSpace.showTutorial(3, 4, 5, 4, 5, 6, 5, 4, 5, 7, 3, 3);
    groupSpace.newContainerBtn.click();
    sleep(1000);
    groupSpace.newListText.sendKeys(t("News"));
    sleep(1000);
    groupSpace.newListCreateBtn.click();
    sleep(1000);
    groupSpace.listSubscribeBtn.click();
    sleep(2000);
    doScreenshot("newlist");
    groupSpace.newContentBtn.click();
    sleep(1000);
    groupSpace.newListPostText.sendKeys(t("Welcome to this list"));
    sleep(1000);
    groupSpace.newListPostCreateBtn.click();
    sleep(3000);
    groupSpace.goParentBtn.click();
    // sleep(2000);

    site.userSpaceBtn.click();
    showMsg(t("All new contents are shown also in your Inbox"));
    sleep(3000);
    userSpace.getFirstWave().click();
    sleep(3000);
    site.groupSpaceBtn.click();

    // events
    groupSpace.eventTool.click();
    showTooltip(groupSpace.eventTool);
    sleep(2000);
    doScreenshot("calendar");
    groupSpace.showTutorial(3, 5, 6, 7, 3, 5, 3, 3, 3);

    // // tasks
    // groupSpace.taskTool.click();
    // showTooltip(groupSpace.taskTool);
    // sleep(2000);
    // groupSpace.showTutorial(3, 3, 3, 3, 3, 3, 3, 4, 3, 4, 3, 3);
    //
    // // wiki
    // groupSpace.wikiTool.click();
    // showTooltip(groupSpace.wikiTool);
    // sleep(2000);
    // groupSpace.showTutorial(3, 5, 3, 3, 3);

    // // barters
    // groupSpace.showTutorial(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3);
  }

  /**
   * Login.
   */
  protected void login() {
    login("admin", "easyeasy");
  }

  /**
   * Login.
   * 
   * @param user
   *          the user
   * @param password
   *          the password
   */
  protected void login(final String user, final String password) {
    login.signIn(user, password);
    login.assertIsConnectedAs(user);
  }

  /**
   * Logout.
   */
  protected void logout() {
    login.logout();
  }

}
