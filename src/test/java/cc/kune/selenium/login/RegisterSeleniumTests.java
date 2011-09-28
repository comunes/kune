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
package cc.kune.selenium.login;

import org.testng.annotations.Test;

import cc.kune.core.client.state.SiteTokens;
import cc.kune.selenium.KuneSeleniumTest;
import cc.kune.selenium.tools.SeleniumConstants;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;

public class RegisterSeleniumTests extends KuneSeleniumTest {

  @Test(dataProvider = "correctregister")
  public void basicRegister(final String shortName, final String longName, final String passwd,
      final String email) {
    // 15 chars, the limit, so we don't use shortName
    final String prefix = getTempString();
    showTitleSlide("User registration", "to get full access to this site tools/contents");
    login.createOne();
    register.fillRegisterForm(prefix, prefix + longName, passwd, prefix + email, false);
    login.assertIsConnectedAs(prefix);
    sleep(1000);
    entityHeader.waitForEntityTitle(prefix + longName);
    register.getWelcomeMsg().click();

    // user space
    showTitleSlide("User space (your Inbox)", "contents in which you participate");
    showTooltip(spaces.userBtn());
    spaces.userBtn().click();

    // chat
    showTitleSlide("Chat with your buddies", "compatible with gmail and similars", SiteTokens.WAVEINBOX);
    showTooltip(chat.icon());
    chat.show();
    sleep(3000);

    final XmppURI jid = XmppURI.jid(SeleniumConstants.USER_EMAIL);
    final String jids = jid.toString();
    showMsg("We need to add buddies (contacts) before chat with them");
    chat.openChat(jid);

    // chat.getPage(jids).click();
    chat.getTalkBox(jids).sendKeys("Helloo... ;)");
    chat.getSend(jids).click();
    chat.getTalkBox(jids).sendKeys("I'm just testing");
    chat.getSend(jids).click();

    login.logout();
  }

}
