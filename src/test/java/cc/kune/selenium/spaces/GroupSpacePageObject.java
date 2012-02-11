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

import cc.kune.blogs.shared.BlogsConstants;
import cc.kune.chat.shared.ChatConstants;
import cc.kune.core.client.sitebar.search.EntitySearchPanel;
import cc.kune.core.client.sn.actions.AddEntityToThisGroupAction;
import cc.kune.core.client.sn.actions.AddNewBuddiesAction;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.docs.shared.DocsConstants;
import cc.kune.events.shared.EventsConstants;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPanel;
import cc.kune.lists.shared.ListsConstants;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;
import cc.kune.tasks.shared.TasksConstants;
import cc.kune.wiki.shared.WikiConstants;

public class GroupSpacePageObject extends PageObject {

  @FindBy(id = SeleniumConstants.GWTDEV + UserSNConfActions.ADD_BUDDIE_BTN)
  public WebElement addBuddieBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + AddNewBuddiesAction.ADD_NEW_BUDDIES_TEXTBOX)
  public WebElement addNewBuddieTextBox;
  @FindBy(id = SeleniumConstants.GWTDEV + AddEntityToThisGroupAction.ADD_NEW_MEMBER_TEXTBOX)
  public WebElement addNewMemberTextBox;
  @FindBy(id = ToolSelectorItemPanel.TOOL_ID_PREFIX + BlogsConstants.NAME)
  public WebElement blogTool;
  @FindBy(id = ToolSelectorItemPanel.TOOL_ID_PREFIX + ChatConstants.NAME)
  public WebElement chatTool;
  @FindBy(id = ToolSelectorItemPanel.TOOL_ID_PREFIX + DocsConstants.NAME)
  public WebElement docTool;
  @FindBy(id = ToolSelectorItemPanel.TOOL_ID_PREFIX + EventsConstants.NAME)
  public WebElement eventTool;
  @FindBy(xpath = "//td/img")
  public WebElement firstAvatarOfGroup;
  @FindBy(xpath = "//td[2]/div/div/table/tbody/tr/td")
  public WebElement firstFromSuggestionBox;
  @FindBy(id = ToolSelectorItemPanel.TOOL_ID_PREFIX + ListsConstants.NAME)
  public WebElement listTool;
  @FindBy(id = SeleniumConstants.GWTDEV + EntitySearchPanel.OK_ID)
  public WebElement searchEntitiesOk;
  @FindBy(xpath = "//div[3]/div/button")
  public WebElement socialNetOptions;
  @FindBy(id = ToolSelectorItemPanel.TOOL_ID_PREFIX + TasksConstants.NAME)
  public WebElement taskTool;
  @FindBy(id = ToolSelectorItemPanel.TOOL_ID_PREFIX + WikiConstants.NAME)
  public WebElement wikiTool;

}
