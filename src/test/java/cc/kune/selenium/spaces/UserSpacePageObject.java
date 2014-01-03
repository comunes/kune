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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.selenium.PageObject;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSpacePageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserSpacePageObject extends PageObject {

  /** The add participant. */
  @FindBy(xpath = "//span[2]/span")
  private WebElement addParticipant;

  /** The cursive. */
  @FindBy(xpath = "//div[2]/div/div[2]/div[3]/div[4]")
  private WebElement cursive;

  /** The first reply titlebar. */
  @FindBy(xpath = "//div[3]/div/div/div[2]/div/div/div[3]")
  private WebElement firstReplyTitlebar;

  /** The reply root blip. */
  @FindBy(xpath = "//div[10]/div/div/div/div[3]/div/div[2]")
  private WebElement replyRootBlip;

  /** The root blip. */
  @FindBy(xpath = "//div[10]/div/div/div/div[3]/div/div/div/div/div/div[3]")
  private WebElement rootBlip;

  /** The root blip text. */
  @FindBy(xpath = "//ul/div")
  private WebElement rootBlipText;

  /** The root edit. */
  @FindBy(xpath = "//div[3]/div/div/div/div/div/div/span")
  // this works but with click coords 5,5
  private WebElement rootEdit;

  /**
   * Instantiates a new user space page object.
   */
  public UserSpacePageObject() {
  }

  /**
   * Gets the adds the parcipant.
   * 
   * @return the adds the parcipant
   */
  public WebElement getAddParcipant() {
    return addParticipant;
  }

  /**
   * Gets the cursive.
   * 
   * @return the cursive
   */
  public WebElement getCursive() {
    return cursive;
  }

  /**
   * Gets the first reply titlebar.
   * 
   * @return the first reply titlebar
   */
  public WebElement getFirstReplyTitlebar() {
    return firstReplyTitlebar;
  }

  /**
   * Gets the first wave.
   * 
   * @return the first wave
   */
  public WebElement getFirstWave() {
    return findElement(By.xpath("//div[3]/div/div/span"));
  }

  /**
   * Gets the new wave.
   * 
   * @return the new wave
   */
  public WebElement getNewWave() {
    return findElement(By.xpath("//div[2]/div[2]/div[4]"));
  }

  /**
   * Gets the reply root blip.
   * 
   * @return the reply root blip
   */
  public WebElement getReplyRootBlip() {
    return replyRootBlip;
  }

  /**
   * Gets the root blip.
   * 
   * @return the root blip
   */
  public WebElement getRootBlip() {
    return rootBlip;
  }

  /**
   * Gets the root blip text.
   * 
   * @return the root blip text
   */
  public WebElement getRootBlipText() {
    return rootBlipText;
  }

  /**
   * Gets the root edit.
   * 
   * @return the root edit
   */
  public WebElement getRootEdit() {
    return rootEdit;
  }

  /**
   * Root blip text.
   * 
   * @return the web element
   */
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
