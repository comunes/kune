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

public class SitePageObject extends PageObject {

  @FindBy(id = SeleniumConstants.GWTDEV + UserConfirmPanel.CANCEL_ID)
  public WebElement confirmationCancel;
  @FindBy(id = SeleniumConstants.GWTDEV + UserConfirmPanel.OK_ID)
  public WebElement confirmationOk;
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.GROUP_SPACE_ID)
  public WebElement groupSpaceBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.HOME_SPACE_ID)
  public WebElement homeSpaceBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + MyGroupsMenu.MENU_ID)
  public WebElement myGroupMenu;
  @FindBy(id = SeleniumConstants.GWTDEV + MyGroupsMenu.NEW_GROUP_MENUITEM_ID)
  public WebElement myGroupMenuNewGroupItem;
  @FindBy(id = SeleniumConstants.GWTDEV + SitebarNewGroupLink.NEW_GROUP_BTN_ID)
  public WebElement newGroupBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.PUBLIC_SPACE_ID)
  public WebElement publicSpaceBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + SitebarSearchPanel.SITE_SEARCH_TEXTBOX)
  public WebElement searchTextBox;
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.USER_SPACE_ID)
  public WebElement userSpaceBtn;
}
