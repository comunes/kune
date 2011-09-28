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
package cc.kune.selenium.chat;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.FindBy;

import cc.kune.chat.client.ChatClient;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.tools.SeleniumConstants;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.hablar.core.client.Idify;

public class ChatPageObject extends PageObject {

  public static final String DEF_TESTING_USER = "some.testing.user@gmail.com";
  @FindBy(id = SeleniumConstants.GWTDEV + "OpenChatWidget-addToRoster" + SeleniumConstants.INPUT)
  private WebElement addToRoster;
  @FindBy(id = SeleniumConstants.GWTDEV + ChatClient.CHAT_CLIENT_ICON_ID)
  private WebElement chatIcon;
  @FindBy(id = SeleniumConstants.GWTDEV + "OpenChatWidget-jabberId")
  private WebElement jid;
  @FindBy(id = SeleniumConstants.GWTDEV + "HablarOpenChat-openAction")
  private WebElement openChat;
  @FindBy(id = SeleniumConstants.GWTDEV + "OpenChatWidget-open")
  private WebElement openChatBtn;

  // Duplicate in Hablar (we need a common PageObject)
  public WebElement getHeader(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String headerId = "gwt-debug-HeaderWidget-Chat-" + pageId;
    return findElement(new ByIdOrName(headerId));
  }

  public WebElement getList(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String id = "gwt-debug-ChatWidget-list-Chat-" + pageId;
    return findElement(new ByIdOrName(id));
  }

  public WebElement getPage(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String headerId = "gwt-debug-ChatWidget-Chat-" + pageId;
    return findElement(new ByIdOrName(headerId));
  }

  public WebElement getSend(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String id = "gwt-debug-ChatWidget-send-Chat-" + pageId;
    return findElement(new ByIdOrName(id));
  }

  public WebElement getTalkBox(final String uri) {
    final String pageId = Idify.uriId(uri);
    final String id = "gwt-debug-ChatWidget-talkBox-Chat-" + pageId;
    return findElement(new ByIdOrName(id));
  }

  public WebElement icon() {
    return chatIcon;
  }

  public void openChat(final XmppURI uri) {
    openChat.click();
    jid.sendKeys(uri.toString());
    addToRoster.click();
    openChatBtn.click();
  }

  public void show() {
    chatIcon.click();
  }

}
