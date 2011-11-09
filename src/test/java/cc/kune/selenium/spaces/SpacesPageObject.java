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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.tools.SeleniumConstants;

public class SpacesPageObject extends PageObject {

  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.GROUP_SPACE_ID)
  protected WebElement groupSpaceBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.HOME_SPACE_ID)
  protected WebElement homeSpaceBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.PUBLIC_SPACE_ID)
  protected WebElement publicSpaceBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + SpaceSelectorView.USER_SPACE_ID)
  protected WebElement userSpaceBtn;

  public SpacesPageObject() {
  }

  public WebElement groupBtn() {
    return groupSpaceBtn;
  }

  public WebElement homeBtn() {
    return homeSpaceBtn;
  }

  public WebElement publicBtn() {
    return publicSpaceBtn;
  }

  public WebElement userBtn() {
    return userSpaceBtn;
  }

}
