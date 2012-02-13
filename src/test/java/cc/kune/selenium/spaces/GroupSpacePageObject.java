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
import cc.kune.chat.client.actions.OpenRoomBtn;
import cc.kune.chat.shared.ChatConstants;
import cc.kune.core.client.sitebar.search.EntitySearchPanel;
import cc.kune.core.client.sn.actions.AddEntityToThisGroupAction;
import cc.kune.core.client.sn.actions.AddNewBuddiesAction;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.docs.shared.DocsConstants;
import cc.kune.events.shared.EventsConstants;
import cc.kune.gspace.client.actions.GoParentContainerBtn;
import cc.kune.gspace.client.actions.NewContainerBtn;
import cc.kune.gspace.client.actions.NewContentBtn;
import cc.kune.gspace.client.actions.NewMenuProvider;
import cc.kune.gspace.client.options.GroupOptionsPanel;
import cc.kune.gspace.client.options.GroupOptionsPresenter;
import cc.kune.gspace.client.options.license.EntityOptDefLicensePanel;
import cc.kune.gspace.client.options.logo.EntityOptLogoPanel;
import cc.kune.gspace.client.options.style.EntityOptStylePanel;
import cc.kune.gspace.client.options.tools.EntityOptToolsPanel;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPanel;
import cc.kune.gspace.client.viewers.FolderViewerAsTablePanel;
import cc.kune.gspace.client.viewers.items.FolderItemWidget;
import cc.kune.lists.client.actions.NewListAction;
import cc.kune.lists.client.actions.NewListPostAction;
import cc.kune.lists.client.actions.SubscribeToListBtn;
import cc.kune.lists.shared.ListsConstants;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;
import cc.kune.selenium.SeleniumUtils;
import cc.kune.tasks.shared.TasksConstants;
import cc.kune.wiki.shared.WikiConstants;

public class GroupSpacePageObject extends PageObject {

  @FindBy(id = SeleniumConstants.GWTDEV + UserSNConfActions.ADD_BUDDIE_BTN)
  public WebElement addBuddieBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + AddNewBuddiesAction.ADD_NEW_BUDDIES_TEXTBOX)
  public WebElement addNewBuddieTextBox;
  @FindBy(id = SeleniumConstants.GWTDEV + AddEntityToThisGroupAction.ADD_NEW_MEMBER_TEXTBOX)
  public WebElement addNewMemberTextBox;
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX + BlogsConstants.NAME)
  public WebElement blogTool;
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX + ChatConstants.NAME)
  public WebElement chatTool;
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX + DocsConstants.NAME)
  public WebElement docTool;
  @FindBy(xpath = "//div[@id='gwt-debug-k-cnt-title-id']/div/span")
  public WebElement entityTitle;
  @FindBy(xpath = "//div[@id='gwt-debug-k-cnt-title-id']/div/input")
  public WebElement entityTitleTextarea;
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX + EventsConstants.NAME)
  public WebElement eventTool;
  @FindBy(xpath = "//td/img")
  public WebElement firstAvatarOfGroup;
  @FindBy(id = SeleniumConstants.GWTDEV + FolderViewerAsTablePanel.ITEM_ID + "1")
  public WebElement firstFolderItem;
  @FindBy(id = SeleniumConstants.GWTDEV + FolderViewerAsTablePanel.ITEM_ID + FolderItemWidget.MENU_ID)
  public WebElement firstFolderItemMenu;
  @FindBy(xpath = "//td[2]/div/div/table/tbody/tr/td")
  public WebElement firstFromSuggestionBox;
  @FindBy(id = SeleniumConstants.GWTDEV + GoParentContainerBtn.GO_PARENT_ID)
  public WebElement goParentBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + GroupOptionsPresenter.GROUP_OPTIONS_ICON)
  public WebElement groupOptions;
  @FindBy(id = SeleniumConstants.GWTDEV + GroupOptionsPanel.GROUP_OP_PANEL_ID_CLOSE)
  public WebElement groupOptionsClose;
  @FindBy(id = SeleniumConstants.GWTDEV + EntityOptDefLicensePanel.TAB_ID)
  public WebElement groupOptionsLicense;
  @FindBy(id = SeleniumConstants.GWTDEV + EntityOptLogoPanel.TAB_ID)
  public WebElement groupOptionsLogo;
  @FindBy(id = SeleniumConstants.GWTDEV + EntityOptStylePanel.TAB_ID)
  public WebElement groupOptionsStyle;
  @FindBy(id = SeleniumConstants.GWTDEV + EntityOptToolsPanel.TAB_ID)
  public WebElement groupOptionsTools;
  @FindBy(id = SeleniumConstants.GWTDEV + SubscribeToListBtn.ID)
  public WebElement listSubscribeBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX + ListsConstants.NAME)
  public WebElement listTool;
  @FindBy(id = SeleniumConstants.GWTDEV + NewContainerBtn.BTN_ID)
  public WebElement newContainerBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + NewContentBtn.BTN_ID)
  public WebElement newContentBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + NewListAction.CREATE_ID)
  public WebElement newListCreateBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + NewListPostAction.CREATE_ID)
  public WebElement newListPostCreateBtn;
  @FindBy(id = NewListPostAction.TEXTBOX_ID + SeleniumConstants.INPUT)
  public WebElement newListPostText;
  @FindBy(id = NewListAction.TEXTBOX_ID + SeleniumConstants.INPUT)
  public WebElement newListText;
  @FindBy(id = SeleniumConstants.GWTDEV + NewMenuProvider.MENU_ID)
  public WebElement newMenuBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + OpenRoomBtn.ID)
  public WebElement openRoomBtn;
  @FindBy(id = SeleniumConstants.GWTDEV + EntitySearchPanel.OK_ID)
  public WebElement searchEntitiesOk;
  @FindBy(xpath = "//div[3]/div/button")
  public WebElement socialNetOptions;
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX + TasksConstants.NAME)
  public WebElement taskTool;
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX + WikiConstants.NAME)
  public WebElement wikiTool;

  public void openFirtsContent() {
    SeleniumUtils.doubleClick(getWebDriver(), firstFolderItem);
  }

}
