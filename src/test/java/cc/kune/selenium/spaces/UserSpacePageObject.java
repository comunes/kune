/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.selenium.PageObject;

public class UserSpacePageObject extends PageObject {

  @FindBy(xpath = "//span[2]/span")
  private WebElement addParticipant;

  @FindBy(xpath = "//div[2]/div/div[2]/div[3]/div[4]")
  private WebElement cursive;

  @FindBy(xpath = "//div[3]/div/div/div[2]/div/div/div[3]")
  private WebElement firstReplyTitlebar;

  @FindBy(xpath = "//div[10]/div/div/div/div[3]/div/div[2]")
  private WebElement replyRootBlip;

  @FindBy(xpath = "//div[10]/div/div/div/div[3]/div/div/div/div/div/div[3]")
  private WebElement rootBlip;

  @FindBy(xpath = "//ul/div")
  private WebElement rootBlipText;
  @FindBy(xpath = "//div[3]/div/div/div/div/div/div/span")
  // this works but with click coords 5,5
  private WebElement rootEdit;

  public UserSpacePageObject() {
  }

  public WebElement getAddParcipant() {
    return addParticipant;
  }

  public WebElement getCursive() {
    return cursive;
  }

  public WebElement getFirstReplyTitlebar() {
    return firstReplyTitlebar;
  }

  public WebElement getFirstWave() {
    return findElement(By.xpath("//div[3]/div/div/span"));
  }

  public WebElement getNewWave() {
    return findElement(By.xpath("//div[2]/div[2]/div[4]"));
  }

  public WebElement getReplyRootBlip() {
    return replyRootBlip;
  }

  public WebElement getRootBlip() {
    return rootBlip;
  }

  public WebElement getRootBlipText() {
    return rootBlipText;
  }

  public WebElement getRootEdit() {
    return rootEdit;
  }

  public WebElement rootBlipText() {
    return rootBlipText;
  }

  // public void testCreation() {
  // selenium.setSpeed("200");
  // // selenium.while("index < 100");
  // selenium.click("//div[2]/div[2]/div[4]");
  // for (int second = 0;; second++) {
  // if (second >= 60) {
  // fail("timeout");
  // }
  // try {
  // if (selenium.isElementPresent("//ul/div")) {
  // break;
  // }
  // } catch (final Exception e) {
  // }
  // Thread.sleep(1000);
  // }
  //
  // selenium.click("//ul/div");
  // selenium.focus("//ul/div");
  // selenium.mouseDownAt("//ul/div", "5,5");
  // selenium.typeKeys("//ul/div", selenium.getEval("index"));
  // selenium.clickAt("//div[3]/div/div/div/div/div/div/span", "5,5");
  // selenium.clickAt("//div[10]/div/div/div/div[3]/div/div[2]", "5,5");
  // selenium.click("//div[3]/div/div/div[2]/div/div/div/span");
  // selenium.typeKeys("//div[2]/div/div[2]/div/div/ul/div",
  // selenium.getEval("index + 'áñç|«@#|ð€Ω]'"));
  // selenium.click("//div[@id='app']/div/div[2]/div/div[2]/div/div/div[10]/div/div[3]/div[2]/div/div");
  // selenium.click("//div[@id='app']/div/div[2]/div/div[2]/div/div/div[10]/div/div[3]/div/div/div");
  // for (int second = 0;; second++) {
  // if (second >= 60) {
  // fail("timeout");
  // }
  // try {
  // if (selenium.isTextPresent("@#|")) {
  // break;
  // }
  // } catch (final Exception e) {
  // }
  // Thread.sleep(1000);
  // }
  //
  // for (int second = 0;; second++) {
  // if (second >= 60) {
  // fail("timeout");
  // }
  // try {
  // if (selenium.isElementPresent("//div[3]/div/div/div[2]/div/div/div[3]")) {
  // break;
  // }
  // } catch (final Exception e) {
  // }
  // Thread.sleep(1000);
  // }
  //
  // selenium.clickAt("//div[3]/div/div/div[2]/div/div/div[3]", "1,1");
  // selenium.clickAt("//div[3]/div/div/div/div/div/div/span[2]", "5,5");
  // selenium.click("//div[2]/div/div/div/div[2]/div/div/ul/div");
  // selenium.focus("//div[2]/div/div/div/div[2]/div/div/ul/div");
  // selenium.mouseDownAt("//div[2]/div/div/div/div[2]/div/div/ul/div", "5,5");
  // selenium.typeKeys("//div[2]/div/div/div/div[2]/div/div/ul/div",
  // selenium.getEval("index + 'áñç|«@#|ð€Ω] la la la '"));
  // selenium.clickAt("//div[10]/div/div/div/div[3]/div/div[2]", "5,5");
  // selenium.clickAt("//div[3]/div/div[2]/div/div/ul/div", "5,5");
  // selenium.typeKeys("//div[3]/div/div[2]/div/div/ul/div",
  // selenium.getEval("index + 'áñç|«@#|ð€Ω] la la la la la'"));
  // // selenium.endWhile();
  // }

}
