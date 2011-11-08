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
package cc.kune.selenium.userspace;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.selenium.PageObject;

public class UserSpacePageObject extends PageObject {

  @FindBy(xpath = "//div[3]/div/div/span[2]")
  private WebElement firstWave;
  @FindBy(xpath = "//div[2]/div[2]/div[4]")
  private WebElement newWave;
  @FindBy(xpath = "")
  private WebElement wavePanel;

  public UserSpacePageObject() {
  }

  public WebElement getFirstWave() {
    return findElement(By.xpath("//div[3]/div[4]/div"));
  }

  public WebElement getNewWave() {
    return findElement(By.xpath("//div[2]/div[2]/div[4]"));
  }

  public WebElement getWavePanel() {
    return wavePanel;
  }
}
