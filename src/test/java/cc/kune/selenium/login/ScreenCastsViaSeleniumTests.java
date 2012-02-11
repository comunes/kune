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

import cc.kune.selenium.KuneSeleniumTest;
import cc.kune.selenium.SeleniumUtils;

public class ScreenCastsViaSeleniumTests extends KuneSeleniumTest {

  @Test(dataProvider = "correctregister")
  public void globalScreencast(final String shortNameUntrans, final String longNameUntrans,
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
    sleep(1000);
    login.assertIsConnectedAs(prefix);
    entityHeader.waitForEntityTitle(longName + prefix);
    register.getWelcomeMsg().click();

    // home space
    showTitleSlide(t("Home space (your welcome page)"),
        t("Here you can see a summary of your activity in this site"));
    site.homeSpaceBtn.click();
    sleep(2000);
    homeSpace.getSndStats().click();
    doScreenshot("home-stats");
    sleep(2000);
    homeSpace.getTrdStats().click();
    sleep(2000);

    // user space
    showTitleSlide(t("User space (your Inbox)"), t("contents in which you participate"));
    showTooltip(site.userSpaceBtn);
    site.userSpaceBtn.click();
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
    sleep(2000);
    doScreenshot("inbox");
    sleep(3000);

    // group space
    showTitleSlide(t("Your personal public space"), t("here you can have your blog, etc"));
    sleep(500);
    site.groupSpaceBtn.click();
    site.groupSpaceBtn.click();
    showMsg(t("Let's start adding some buddie to our social network"));
    sleep(1000);
    groupSpace.addBuddieBtn.click();
    groupSpace.addNewBuddieTextBox.click();
    groupSpace.addNewBuddieTextBox.sendKeys("admin");
    groupSpace.addNewBuddieTextBox.sendKeys(Keys.ARROW_DOWN);
    sleep(500);
    groupSpace.firstFromSuggestionBox.click();
    doScreenshot("social-net");
    site.confirmationOk.click();
    sleep(500);
    groupSpace.searchEntitiesOk.click();
    // groupSpace.firstAvatarOfGroup().click();
    sleep(2000);

    /*
     * showTitleSlide(t("Chat with your buddies"),
     * t("compatible with gmail and similars"), SiteTokens.WAVE_INBOX); //
     * showTooltip(chat.icon()); chat.show(); sleep(2000);
     * 
     * final XmppURI jid = XmppURI.jid(SeleniumConstants.USER_SHORNAME + "@" +
     * SeleniumConf.SITE.getDomain()); final String jids = jid.toString();
     * chat.getRosterItem("", jids).click();
     * 
     * chat.openChat(jid);
     * 
     * chat.getPage(jids).click();
     * chat.getTalkBox(jids).sendKeys(t("Helloo... ;)"));
     * chat.getSend(jids).click();
     * chat.getTalkBox(jids).sendKeys(t("I'm just testing"));
     * chat.getSend(jids).click(); doScreenshot("chat");
     * 
     * showMsg(t("And you can chat event while going back/forward with your browser"
     * )); site.homeSpaceBtn.click(); sleep(2000);
     * chat.getTalkBox(jids).sendKeys(t("la la la"));
     * chat.getSend(jids).click(); showMsg(t("Browser history back"));
     * browserBack(); sleep(2000);
     * chat.getTalkBox(jids).sendKeys(t("I can continue chat smoothly ;)"));
     * chat.getSend(jids).click(); sleep(2000);
     * showMsg(t("Browser history forward")); browserForward(); sleep(1000);
     * showMsg(t("And more options for your contacts")); chat.getItemMenu("",
     * jids).click(); chat.getTalkBox(jids).sendKeys(t("goodbye!"));
     * chat.getSend(jids).click(); sleep(1000);
     * chat.getTalkBox(jids).sendKeys(Keys.chord(Keys.ALT, "C")); //
     * chat.close();
     */

    // group space
    showTitleSlide(t("Group space (collaboration space)"),
        t("Here you can create groups and collaborate within them"));
    site.groupSpaceBtn.click();
    site.groupSpaceBtn.click();
    showMsg(t("Let's create a new group"));
    site.newGroupBtn.click();
    newGroup.shortName.sendKeys("yseg" + prefix);
    newGroup.longName.sendKeys(t("Yellow Summarine Environmental Group ") + prefix);
    newGroup.publicDescription.sendKeys(t("The Yellow Summarine Environmental Group is an Argentine-Based environmental"
        + " direct action group. Currently we are focusing or activities in the environmental impact of mining."));
    newGroup.tags.sendKeys(t("environmental, Argentina, action"));
    newGroup.projectType.click();
    showTooltip(newGroup.projectType);
    doScreenshot("newgroup");
    newGroup.registerBtn.click();
    sleep(2000);
    showTitleSlide(t("Group space (collaboration space) III "),
        t("Let's see the diferent tools you have available"));
    groupSpace.blogTool.click();
    sleep(2000);
    groupSpace.wikiTool.click();
    sleep(2000);
    groupSpace.listTool.click();
    sleep(2000);
    groupSpace.eventTool.click();

    showTitleSlide(t("Group space (collaboration space) II"),
        t("but also you can have your personal space (with blogs, etc)"));

    login.logout();

    showTitleSlide(t("Thank you"), t("and yes, feedback welcome"));

  }
}
