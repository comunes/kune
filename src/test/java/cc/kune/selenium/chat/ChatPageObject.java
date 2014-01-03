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
package cc.kune.selenium.chat;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.FindBy;

import cc.kune.chat.client.ChatClient;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.hablar.core.client.Idify;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatPageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ChatPageObject extends PageObject {

  /** The Constant DEF_TESTING_USER. */
  public static final String DEF_TESTING_USER = "some.testing.user@gmail.com";

  /** The add to roster. */
  @FindBy(id = SeleniumConstants.GWTDEV + "OpenChatWidget-addToRoster" + SeleniumConstants.INPUT)
  private WebElement addToRoster;

  /** The chat icon. */
  @FindBy(id = SeleniumConstants.GWTDEV + ChatClient.CHAT_CLIENT_ICON_ID)
  public WebElement chatIcon;

  /** The close chat. */
  @FindBy(xpath = "//div[14]/div/div/div/div/div/table/tbody/tr/td[2]/div")
  private WebElement closeChat;

  /** The first contact. */
  @FindBy(id = "//div[3]/div/div[3]/div/div/div/div[2]/div/div/div/div/div/div")
  public WebElement firstContact;

  /** The jid. */
  @FindBy(id = SeleniumConstants.GWTDEV + "OpenChatWidget-jabberId")
  private WebElement jid;

  /** The open chat. */
  @FindBy(id = SeleniumConstants.GWTDEV + "HablarOpenChat-openAction")
  private WebElement openChat;

  /** The open chat btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + "OpenChatWidget-open")
  private WebElement openChatBtn;

  /**
   * Close.
   */
  public void close() {
    closeChat.click();
  }

  // Duplicate in Hablar (we need a common PageObject)
  /**
   * Gets the header.
   * 
   * @param uri
   *          the uri
   * @return the header
   */
  public WebElement getHeader(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String headerId = "gwt-debug-HeaderWidget-Chat-" + pageId;
    return findElement(new ByIdOrName(headerId));
  }

  /**
   * Gets the item menu.
   * 
   * @param groupId
   *          the group id
   * @param jid
   *          the jid
   * @return the item menu
   */
  public WebElement getItemMenu(final String groupId, final String jid) {
    final String id = Idify.id("RosterItemWidget", groupId, Idify.uriId(jid), "roster-menu");
    return findElement(new ByIdOrName("gwt-debug-" + id));
  }

  /**
   * Gets the list.
   * 
   * @param uri
   *          the uri
   * @return the list
   */
  public WebElement getList(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String id = "gwt-debug-ChatWidget-list-Chat-" + pageId;
    return findElement(new ByIdOrName(id));
  }

  /**
   * Gets the page.
   * 
   * @param uri
   *          the uri
   * @return the page
   */
  public WebElement getPage(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String headerId = "gwt-debug-ChatWidget-Chat-" + pageId;
    return findElement(new ByIdOrName(headerId));
  }

  /**
   * Gets the roster item.
   * 
   * @param groupId
   *          the group id
   * @param jid
   *          the jid
   * @return the roster item
   */
  public WebElement getRosterItem(final String groupId, final String jid) {
    final String id = Idify.id("RosterItemWidget", groupId, Idify.uriId(jid));
    return findElement(new ByIdOrName("gwt-debug-" + id));
  }

  /**
   * Gets the send.
   * 
   * @param uri
   *          the uri
   * @return the send
   */
  public WebElement getSend(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String id = "gwt-debug-ChatWidget-send-Chat-" + pageId;
    return findElement(new ByIdOrName(id));
  }

  /**
   * Gets the talk box.
   * 
   * @param uri
   *          the uri
   * @return the talk box
   */
  public WebElement getTalkBox(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String id = "gwt-debug-ChatWidget-talkBox-Chat-" + pageId;
    return findElement(new ByIdOrName(id));
  }

  /**
   * Icon.
   * 
   * @return the web element
   */
  public WebElement icon() {
    return chatIcon;
  }

  /**
   * Open chat.
   * 
   * @param uri
   *          the uri
   */
  public void openChat(final XmppURI uri) {
    // hightlight(openChat);
    openChat.click();
    jid.sendKeys(uri.toString());
    // hightlight(addToRoster);
    addToRoster.click();
    // hightlight(openChatBtn);
    openChatBtn.click();
  }

  /**
   * Show.
   */
  public void show() {
    chatIcon.click();
  }

}
