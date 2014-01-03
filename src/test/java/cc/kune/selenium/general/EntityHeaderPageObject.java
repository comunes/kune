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
package cc.kune.selenium.general;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.core.client.ws.entheader.EntityTextLogo;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityHeaderPageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityHeaderPageObject extends PageObject {

  /** The logo image. */
  @FindBy(id = SeleniumConstants.GWTDEV + EntityTextLogo.LOGO_IMAGE)
  protected WebElement logoImage;

  /** The logo name. */
  @FindBy(id = SeleniumConstants.GWTDEV + EntityTextLogo.LOGO_NAME)
  protected WebElement logoName;

  /**
   * Instantiates a new entity header page object.
   */
  public EntityHeaderPageObject() {
  }

  /**
   * Wait for entity title.
   * 
   * @param text
   *          the text
   */
  public void waitForEntityTitle(final String text) {
    waitFor(logoName, text);
  }
}
