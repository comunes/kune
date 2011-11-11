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

import cc.kune.core.client.sitebar.search.EntitySearchPanel;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;

public class GroupSpacePageObject extends PageObject {

  @FindBy(id = SeleniumConstants.GWTDEV + UserSNConfActions.ADD_BUDDIE_BTN)
  private WebElement addBuddieBtn;
  @FindBy(xpath = "//div[2]/div/div[3]/div/span")
  private WebElement blogTool;
  @FindBy(xpath = "//div[2]/div/div[4]/div/span")
  private WebElement chatTool;
  @FindBy(xpath = "//div[3]/div/div[2]/div/div/div/span")
  private WebElement docTool;
  @FindBy(xpath = "//div[2]/div/div[6]/div/span")
  private WebElement eventTool;
  @FindBy(xpath = "//td/img")
  private WebElement firstAvatarOfGroup;
  @FindBy(xpath = "//div[2]/div/div[5]/div/span")
  private WebElement listTool;
  @FindBy(id = SeleniumConstants.GWTDEV + EntitySearchPanel.OK_ID)
  private WebElement searchEntitiesOk;
  @FindBy(id = SeleniumConstants.GWTDEV + EntitySearchPanel.ENTITY_SEARCH_TEXTBOX)
  private WebElement searchEntitiesTextBox;
  @FindBy(xpath = "//div[3]/div/button")
  private WebElement socialNetOptions;
  @FindBy(xpath = "//div[2]/div/div[7]/div/span")
  private WebElement taskTool;
  @FindBy(xpath = "//div[2]/div/div[7]/div/span")
  private WebElement wikiTool;

  public WebElement addBuddieBtn() {
    return addBuddieBtn;
  }

  public WebElement blogTool() {
    return blogTool;
  }

  public WebElement chatTool() {
    return chatTool;
  }

  public WebElement docTool() {
    return docTool;
  }

  public WebElement eventTool() {
    return eventTool;
  }

  public WebElement firstAvatarOfGroup() {
    return firstAvatarOfGroup;
  }

  public WebElement listTool() {
    return listTool;
  }

  public WebElement searchEntitiesOk() {
    return searchEntitiesOk;
  }

  public WebElement searchEntitiesTextBox() {
    return searchEntitiesTextBox;
  }

  public WebElement socialNetOptions() {
    return socialNetOptions;
  }

  public WebElement taskTool() {
    return taskTool;
  }

  public WebElement wikiTool() {
    return wikiTool;
  }

}
