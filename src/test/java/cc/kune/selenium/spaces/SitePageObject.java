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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.core.client.notify.confirm.UserConfirmPanel;
import cc.kune.core.client.sitebar.MyGroupsMenu;
import cc.kune.core.client.sitebar.SitebarNewGroupLink;
import cc.kune.core.client.sitebar.search.SitebarSearchPanel;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class SitePageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitePageObject extends PageObject {

  /** The confirmation cancel. */
  @FindBy(id = SeleniumConstants.GWTDEV + UserConfirmPanel.CANCEL_ID)
  public WebElement confirmationCancel;

  /** The confirmation ok. */
  @FindBy(id = SeleniumConstants.GWTDEV + UserConfirmPanel.OK_ID)
  public WebElement confirmationOk;

  /** The group space btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.GROUP_SPACE_ID)
  public WebElement groupSpaceBtn;

  /** The home space btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.HOME_SPACE_ID)
  public WebElement homeSpaceBtn;

  /** The my group menu. */
  @FindBy(id = SeleniumConstants.GWTDEV + MyGroupsMenu.MENU_ID)
  public WebElement myGroupMenu;

  /** The my group menu new group item. */
  @FindBy(id = SeleniumConstants.GWTDEV + MyGroupsMenu.NEW_GROUP_MENUITEM_ID)
  public WebElement myGroupMenuNewGroupItem;

  /** The new group btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + SitebarNewGroupLink.NEW_GROUP_BTN_ID)
  public WebElement newGroupBtn;

  /** The public space btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.PUBLIC_SPACE_ID)
  public WebElement publicSpaceBtn;

  /** The search text box. */
  @FindBy(id = SeleniumConstants.GWTDEV + SitebarSearchPanel.SITE_SEARCH_TEXTBOX)
  public WebElement searchTextBox;

  /** The user space btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.USER_SPACE_ID)
  public WebElement userSpaceBtn;
}
