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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.selenium.PageObject;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeSpacePageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HomeSpacePageObject extends PageObject {

  /** The fst stats. */
  @FindBy(xpath = "//div[@id='k-home-group-stats']/div/div[2]/div/div/div/img")
  private WebElement fstStats;

  /** The snd stats. */
  @FindBy(xpath = "//div[@id='k-home-group-stats']/div/div[2]/div/div[2]/div/img")
  private WebElement sndStats;

  /** The trd stats. */
  @FindBy(xpath = "//div[@id='k-home-group-stats']/div/div[2]/div/div[3]/div/img")
  private WebElement trdStats;

  /**
   * Instantiates a new home space page object.
   */
  public HomeSpacePageObject() {
  }

  /**
   * Gets the fst stats.
   * 
   * @return the fst stats
   */
  public WebElement getFstStats() {
    return fstStats;
  }

  /**
   * Gets the snd stats.
   * 
   * @return the snd stats
   */
  public WebElement getSndStats() {
    return sndStats;
  }

  /**
   * Gets the trd stats.
   * 
   * @return the trd stats
   */
  public WebElement getTrdStats() {
    return trdStats;
  }

}
