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

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import cc.kune.core.client.state.SiteTokens;
import cc.kune.selenium.KuneSeleniumTest;
import cc.kune.selenium.SeleniumConf;
import cc.kune.selenium.SeleniumConstants;
import cc.kune.selenium.SeleniumUtils;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;

public class RegisterSeleniumTests extends KuneSeleniumTest {

  @Test(dataProvider = "correctregister")
  public void basicRegister(final String shortNameUntrans, final String longNameUntrans,
      final String passwd, final String emailUntrans) {
    final String shortName = t(shortNameUntrans);
    final String longName = t(longNameUntrans);
    final String email = t(emailUntrans);
    SeleniumUtils.fastSpeed(false);
    // 15 chars, the limit, so we don't use shortName
    final String prefix = getTempString();
    showTitleSlide(t("User registration"), t("to get full access to this site tools/contents"));
    login.createOne();
    register.fillRegisterForm(shortName + prefix, longName + prefix, passwd, prefix + email, false, true);
    doScreenshot("register");
    sleep(1000);
    login.assertIsConnectedAs(prefix);
    entityHeader.waitForEntityTitle(longName + prefix);
    register.getWelcomeMsg().click();

    // home space
    showTitleSlide(t("Home space (your welcome page)"),
        t("Here you can see a summary of your activity in this site"));
    site.homeBtn().click();
    sleep(2000);
    homeSpace.getSndStats().click();
    sleep(2000);
    homeSpace.getTrdStats().click();
    sleep(2000);

    // user space
    showTitleSlide(t("User space (your Inbox)"), t("contents in which you participate"));
    showTooltip(site.userBtn());
    site.userBtn().click();
    showMsg(t("You can see this like an advanced email system..."));
    userSpace.getFirstWave().click();
    sleep(2000);
    userSpace.getNewWave().click();
    showMsg(t("where you can compose personal messages..."));
    showMsg(t("but also create contents to publish later"));
    userSpace.rootBlipText().sendKeys(t("Congratulations for your report\n\n"));
    sleep(1000);
    userSpace.getCursive().click();
    userSpace.rootBlipText().sendKeys(
        t("Hi there, Just to say that I like") + t("a lot your last report\n\n"));
    sleep(1000);
    userSpace.getCursive().click();
    userSpace.rootBlipText().sendKeys(t("Best\n\nJane"));
    sleep(3000);
    userSpace.getRootEdit().click();
    userSpace.getAddParcipant().click();
    sleep(1000);
    answerOnNextPrompt("admin");
    sleep(5000);

    // group space
    showTitleSlide(t("Your personal public space"), t("here you can have your blog, etc"));
    sleep(500);
    site.groupBtn().click();
    site.groupBtn().click();
    showMsg(t("Let's start adding some buddie to our social network"));
    sleep(1000);
    groupSpace.addBuddieBtn().click();
    groupSpace.addNewBuddieTextBox.click();
    groupSpace.addNewBuddieTextBox.sendKeys("admin");
    groupSpace.addNewBuddieTextBox.sendKeys(Keys.ARROW_DOWN);
    sleep(500);
    groupSpace.firstFromSuggestionBox.click();
    site.confirmationOk.click();
    sleep(500);
    groupSpace.searchEntitiesOk().click();
    // groupSpace.firstAvatarOfGroup().click();
    sleep(2000);

    showTitleSlide(t("Chat with your buddies"), t("compatible with gmail and similars"),
        SiteTokens.WAVEINBOX);
    // showTooltip(chat.icon());
    chat.show();
    sleep(2000);

    final XmppURI jid = XmppURI.jid(SeleniumConstants.USER_SHORNAME + "@"
        + SeleniumConf.SITE.getDomain());
    final String jids = jid.toString();
    chat.getRosterItem("", jids).click();

    // chat.openChat(jid);

    // chat.getPage(jids).click();
    chat.getTalkBox(jids).sendKeys(t("Helloo... ;)"));
    chat.getSend(jids).click();
    chat.getTalkBox(jids).sendKeys(t("I'm just testing"));
    chat.getSend(jids).click();

    showMsg(t("And you can chat event while going back/forward with your browser"));
    site.homeBtn().click();
    sleep(2000);
    chat.getTalkBox(jids).sendKeys(t("la la la"));
    chat.getSend(jids).click();
    showMsg(t("Browser history back"));
    browserBack();
    sleep(2000);
    chat.getTalkBox(jids).sendKeys(t("I can continue chat smoothly ;)"));
    chat.getSend(jids).click();
    sleep(2000);
    showMsg(t("Browser history forward"));
    browserForward();
    sleep(1000);
    showMsg(t("And more options for your contacts"));
    chat.getItemMenu("", jids).click();
    chat.getTalkBox(jids).sendKeys(t("goodbye!"));
    chat.getSend(jids).click();
    sleep(1000);
    chat.getTalkBox(jids).sendKeys(Keys.chord(Keys.ALT, "C"));
    // chat.close();

    login.logout();

    showTitleSlide(t("Thank you"), t("and yes, feedback welcome"));

  }
}
