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

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.blogs.shared.BlogsToolConstants;
import cc.kune.chat.client.actions.OpenRoomBtn;
import cc.kune.chat.shared.ChatToolConstants;
import cc.kune.core.client.sitebar.search.EntitySearchPanel;
import cc.kune.core.client.sn.actions.AddEntityToThisGroupAction;
import cc.kune.core.client.sn.actions.AddNewBuddiesAction;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.docs.shared.DocsToolConstants;
import cc.kune.events.shared.EventsToolConstants;
import cc.kune.gspace.client.actions.GoParentContainerBtn;
import cc.kune.gspace.client.actions.NewContainerBtn;
import cc.kune.gspace.client.actions.NewContentBtn;
import cc.kune.gspace.client.actions.NewMenuProvider;
import cc.kune.gspace.client.actions.TutorialBtn;
import cc.kune.gspace.client.options.GroupOptionsPanel;
import cc.kune.gspace.client.options.GroupOptionsPresenter;
import cc.kune.gspace.client.options.license.EntityOptDefLicensePanel;
import cc.kune.gspace.client.options.logo.EntityOptLogoPanel;
import cc.kune.gspace.client.options.style.EntityOptStylePanel;
import cc.kune.gspace.client.options.tools.EntityOptToolsPanel;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPanel;
import cc.kune.gspace.client.viewers.FolderViewerAsTablePanel;
import cc.kune.gspace.client.viewers.TutorialViewer;
import cc.kune.gspace.client.viewers.items.FolderItemWidget;
import cc.kune.lists.client.actions.NewListAction;
import cc.kune.lists.client.actions.NewListPostAction;
import cc.kune.lists.client.actions.SubscribeToListBtn;
import cc.kune.lists.shared.ListsToolConstants;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;
import cc.kune.selenium.SeleniumUtils;
import cc.kune.tasks.shared.TasksToolConstants;
import cc.kune.wiki.shared.WikiToolConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupSpacePageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupSpacePageObject extends PageObject {

  /** The add buddie btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + UserSNConfActions.ADD_BUDDIE_BTN)
  public WebElement addBuddieBtn;

  /** The add new buddie text box. */
  @FindBy(id = SeleniumConstants.GWTDEV + AddNewBuddiesAction.ADD_NEW_BUDDIES_TEXTBOX)
  public WebElement addNewBuddieTextBox;

  /** The add new member text box. */
  @FindBy(id = SeleniumConstants.GWTDEV + AddEntityToThisGroupAction.ADD_NEW_MEMBER_TEXTBOX)
  public WebElement addNewMemberTextBox;

  /** The blog tool. */
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX
      + BlogsToolConstants.TOOL_NAME)
  public WebElement blogTool;

  /** The chat tool. */
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX
      + ChatToolConstants.TOOL_NAME)
  public WebElement chatTool;

  /** The doc tool. */
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX
      + DocsToolConstants.TOOL_NAME)
  public WebElement docTool;

  /** The entity title. */
  @FindBy(xpath = "//div[@id='gwt-debug-k-cnt-title-id']/div/span")
  public WebElement entityTitle;

  /** The entity title textarea. */
  @FindBy(xpath = "//div[@id='gwt-debug-k-cnt-title-id']/div/input")
  public WebElement entityTitleTextarea;

  /** The event tool. */
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX
      + EventsToolConstants.TOOL_NAME)
  public WebElement eventTool;

  /** The first avatar of group. */
  @FindBy(xpath = "//td/img")
  public WebElement firstAvatarOfGroup;

  /** The first folder item. */
  @FindBy(id = SeleniumConstants.GWTDEV + FolderViewerAsTablePanel.ITEM_ID + "1")
  public WebElement firstFolderItem;

  /** The first folder item menu. */
  @FindBy(id = SeleniumConstants.GWTDEV + FolderViewerAsTablePanel.ITEM_ID + FolderItemWidget.MENU_ID)
  public WebElement firstFolderItemMenu;

  /** The first from suggestion box. */
  @FindBy(xpath = "//td[2]/div/div/table/tbody/tr/td")
  public WebElement firstFromSuggestionBox;

  /** The go parent btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + GoParentContainerBtn.GO_PARENT_ID)
  public WebElement goParentBtn;

  /** The group options. */
  @FindBy(id = SeleniumConstants.GWTDEV + GroupOptionsPresenter.GROUP_OPTIONS_ICON)
  public WebElement groupOptions;

  /** The group options close. */
  @FindBy(id = SeleniumConstants.GWTDEV + GroupOptionsPanel.GROUP_OP_PANEL_ID_CLOSE)
  public WebElement groupOptionsClose;

  /** The group options license. */
  @FindBy(id = SeleniumConstants.GWTDEV + EntityOptDefLicensePanel.TAB_ID)
  public WebElement groupOptionsLicense;

  /** The group options logo. */
  @FindBy(id = SeleniumConstants.GWTDEV + EntityOptLogoPanel.TAB_ID)
  public WebElement groupOptionsLogo;

  /** The group options style. */
  @FindBy(id = SeleniumConstants.GWTDEV + EntityOptStylePanel.TAB_ID)
  public WebElement groupOptionsStyle;

  /** The group options tools. */
  @FindBy(id = SeleniumConstants.GWTDEV + EntityOptToolsPanel.TAB_ID)
  public WebElement groupOptionsTools;

  /** The list subscribe btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + SubscribeToListBtn.ID)
  public WebElement listSubscribeBtn;

  /** The list tool. */
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX
      + ListsToolConstants.TOOL_NAME)
  public WebElement listTool;

  /** The new container btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + NewContainerBtn.BTN_ID)
  public WebElement newContainerBtn;

  /** The new content btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + NewContentBtn.BTN_ID)
  public WebElement newContentBtn;

  /** The new list create btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + NewListAction.CREATE_ID)
  public WebElement newListCreateBtn;

  /** The new list post create btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + NewListPostAction.CREATE_ID)
  public WebElement newListPostCreateBtn;

  /** The new list post text. */
  @FindBy(id = NewListPostAction.TEXTBOX_ID + SeleniumConstants.INPUT)
  public WebElement newListPostText;

  /** The new list text. */
  @FindBy(id = NewListAction.TEXTBOX_ID + SeleniumConstants.INPUT)
  public WebElement newListText;

  /** The new menu btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + NewMenuProvider.MENU_ID)
  public WebElement newMenuBtn;

  /** The open room btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + OpenRoomBtn.ID)
  public WebElement openRoomBtn;

  /** The search entities ok. */
  @FindBy(id = SeleniumConstants.GWTDEV + EntitySearchPanel.OK_ID)
  public WebElement searchEntitiesOk;

  /** The show tutorial btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + TutorialBtn.INFO_CONTAINER_ID)
  public WebElement showTutorialBtn;

  /** The social net options. */
  @FindBy(xpath = "//div[3]/div/button")
  public WebElement socialNetOptions;

  /** The task tool. */
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX
      + TasksToolConstants.TOOL_NAME)
  public WebElement taskTool;

  /** The tutorial close btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + TutorialViewer.CLOSE_BTN_ID)
  public WebElement tutorialCloseBtn;

  /** The tutorial frame. */
  @FindBy(id = SeleniumConstants.GWTDEV + TutorialViewer.IFRAME_ID)
  public WebElement tutorialFrame;

  /** The wiki tool. */
  @FindBy(id = SeleniumConstants.GWTDEV + ToolSelectorItemPanel.TOOL_ID_PREFIX
      + WikiToolConstants.TOOL_NAME)
  public WebElement wikiTool;

  /**
   * Open firts content.
   */
  public void openFirtsContent() {
    SeleniumUtils.doubleClick(getWebDriver(), firstFolderItem);
  }

  /**
   * Show tutorial.
   * 
   * @param slidesSegs
   *          the slides segs
   */
  public void showTutorial(final int... slidesSegs) {
    showTutorialBtn.click();
    final Point showLocation = showTutorialBtn.getLocation();
    SeleniumUtils.showCursor(getWebDriver(), showLocation.getX() - 50, showLocation.getY() + 50);
    for (int segs : slidesSegs) {
      if (segs < 0) {
        SeleniumUtils.doScreenshot(getWebDriver(), "tutorials");
        segs = segs * -1;
      }
      sleep(segs * 1000);
      SeleniumUtils.jsExec(getWebDriver()).executeScript(
          "document.getElementById('" + SeleniumConstants.GWTDEV + TutorialViewer.IFRAME_ID
              + "').contentWindow.sozi.player.moveToNext();");
    }
    getWebDriver().switchTo().defaultContent();
    sleep(500);
    tutorialCloseBtn.click();
  }

}
