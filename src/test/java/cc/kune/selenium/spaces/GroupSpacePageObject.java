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

import cc.kune.selenium.PageObject;

public class GroupSpacePageObject extends PageObject {

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
  @FindBy(xpath = "//div[3]/div/button")
  private WebElement socialNetOptions;
  @FindBy(xpath = "//div[2]/div/div[7]/div/span")
  private WebElement taskTool;
  @FindBy(xpath = "//div[2]/div/div[7]/div/span")
  private WebElement wikiTool;

  public WebElement getBlogTool() {
    return blogTool;
  }

  public WebElement getChatTool() {
    return chatTool;
  }

  public WebElement getDocTool() {
    return docTool;
  }

  public WebElement getEventTool() {
    return eventTool;
  }

  public WebElement getFirstAvatarOfGroup() {
    return firstAvatarOfGroup;
  }

  public WebElement getListTool() {
    return listTool;
  }

  public WebElement getSocialNetOptions() {
    return socialNetOptions;
  }

  public WebElement getTaskTool() {
    return taskTool;
  }

  public WebElement getWikiTool() {
    return wikiTool;
  }

}
