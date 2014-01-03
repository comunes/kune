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
package cc.kune.selenium.login;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.selenium.KuneSeleniumTest;
import cc.kune.selenium.SeleniumConf;
import cc.kune.selenium.SeleniumUtils;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;

// TODO: Auto-generated Javadoc
/**
 * The Class ScreenCastsViaSeleniumTests.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ScreenCastsViaSeleniumTests extends KuneSeleniumTest {

  /**
   * Global screencast.
   * 
   * @param shortNameUntrans
   *          the short name untrans
   * @param longNameUntrans
   *          the long name untrans
   * @param passwd
   *          the passwd
   * @param emailUntrans
   *          the email untrans
   */
  @Test(dataProvider = "correctregister")
  public void globalScreencast(final String shortNameUntrans, final String longNameUntrans,
      final String passwd, final String emailUntrans) {
    final String shortName = t(shortNameUntrans);
    final String longName = t(longNameUntrans);
    final String email = t(emailUntrans);
    final String buddy = "luther";
    SeleniumUtils.fastSpeed(false);
    // 15 chars, the limit, so we don't use shortName
    final String sufix = getTempString();
    showTitleSlide(t("User registration"), t("to get full access to this site tools/contents"));
    login.createOne();
    register.fillRegisterForm(shortName + sufix, longName + sufix, passwd, sufix + email, false, true);
    sleep(1000);
    login.assertIsConnectedAs(sufix);
    entityHeader.waitForEntityTitle(longName + sufix);
    register.getWelcomeMsg().click();

    // home space
    showTitleSlide(t("Home space (your welcome page)"),
        t("Here you can see a summary of your activity in this site"));
    sleep(1000);
    site.homeSpaceBtn.click();
    sleep(2000);
    homeSpace.getSndStats().click();
    doScreenshot("home-stats");
    sleep(2000);
    homeSpace.getTrdStats().click();
    sleep(2000);

    // user space
    showTitleSlide(t("Your Inbox"), t("contents in which you participate"));
    sleep(1000);
    showTooltip(site.userSpaceBtn);
    site.userSpaceBtn.click();
    showMsg(t("You can see this like an advanced email system..."));
    userSpace.getFirstWave().click();
    sleep(2000);
    userSpace.getNewWave().click();

    userSpace.getAddParcipant().click();
    sleep(1000);
    answerOnNextPrompt(buddy);
    sleep(3000);

    showMsg(t("where you can compose personal messages..."));
    sleep(1000);
    showMsg(t("but also create contents to publish later"));
    sleep(1000);
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
    sleep(3000);
    doScreenshot("inbox");
    sleep(2000);

    // group space
    showTitleSlide(t("Your personal public space"), t("here you can have your blog, etc"));
    sleep(500);
    site.groupSpaceBtn.click();
    site.groupSpaceBtn.click();
    showMsg(t("Let's start adding some buddie to our social network"));
    sleep(1000);
    groupSpace.addBuddieBtn.click();
    groupSpace.addNewBuddieTextBox.click();
    groupSpace.addNewBuddieTextBox.sendKeys(buddy);
    groupSpace.addNewBuddieTextBox.sendKeys(Keys.ARROW_DOWN);
    doScreenshot("social-net");
    sleep(500);
    groupSpace.firstFromSuggestionBox.click();
    site.confirmationOk.click();
    sleep(500);
    groupSpace.searchEntitiesOk.click();
    // groupSpace.firstAvatarOfGroup().click();
    sleep(2000);

    // Chat space
    showTitleSlide(t("Chat with your buddies"), t("compatible with gmail and similars"),
        SiteTokens.WAVE_INBOX); //
    showTooltip(chat.icon());
    chat.show();
    sleep(2000);

    final XmppURI jid = XmppURI.jid(buddy + "@" + SeleniumConf.SITE.getDomain());
    final String jids = jid.toString();
    // chat.getRosterItem("", jids).click();

    chat.openChat(jid);

    chat.getPage(jids).click();
    chat.getTalkBox(jids).sendKeys(t("Helloo... ;)"));
    chat.getSend(jids).click();
    chat.getTalkBox(jids).sendKeys(t("I'm just testing"));
    chat.getSend(jids).click();

    showMsg(t("And you can chat event while going back/forward with your browser"));
    site.homeSpaceBtn.click();
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
    doScreenshot("chat");

    chat.getSend(jids).click();
    sleep(1000);
    // Doesn't works
    // chat.getTalkBox(jids).sendKeys(Keys.chord(Keys.ALT, "C")); //
    // chat.close();
    // workaround:
    chat.chatIcon.click();

    // group space

    final String shortname = "yseg";
    final String longname = t("Yellow Summarine Environmental Group ");
    final String description = t("The Yellow Summarine Environmental Group is an Argentine-Based environmental"
        + " direct action group. Currently we are focusing or activities in the environmental impact of mining.");
    final String tags = t("environmental, Argentina, action");

    groupCreation(shortname, longname, description, tags, GroupType.PROJECT, sufix);

    login.logout();

    showTitleSlide(t("Thank you"), t("and yes, feedback welcome"));

  }
}
