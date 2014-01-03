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

import cc.kune.common.client.errors.UIException;
import cc.kune.core.client.groups.newgroup.NewGroupPanel;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;
import cc.kune.selenium.SeleniumUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class NewGroupPageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewGroupPageObject extends PageObject {

  /** The closed type. */
  @FindBy(id = NewGroupPanel.CLOSED_GROUP_TYPE_ID)
  public WebElement closedType;

  /** The community type. */
  @FindBy(id = NewGroupPanel.COMM_GROUP_TYPE_ID)
  public WebElement communityType;

  /** The long name. */
  @FindBy(id = NewGroupPanel.LONGNAME_FIELD + SeleniumConstants.INPUT)
  public WebElement longName;

  /** The org type. */
  @FindBy(id = NewGroupPanel.ORG_GROUP_TYPE_ID)
  public WebElement orgType;

  /** The project type. */
  @FindBy(id = NewGroupPanel.PROJ_GROUP_TYPE_ID)
  public WebElement projectType;

  /** The public description. */
  @FindBy(id = NewGroupPanel.PUBLICDESC_FIELD + SeleniumConstants.INPUT)
  public WebElement publicDescription;

  /** The register btn. */
  @FindBy(id = SeleniumConstants.GWTDEV + NewGroupPanel.REGISTER_BUTTON)
  public WebElement registerBtn;

  /** The short name. */
  @FindBy(id = NewGroupPanel.SHORTNAME_FIELD + SeleniumConstants.INPUT)
  public WebElement shortName;

  /** The tags. */
  @FindBy(id = NewGroupPanel.TAGS_FIELD + SeleniumConstants.INPUT)
  public WebElement tags;

  /**
   * Creates the.
   * 
   * @param sufix
   *          the sufix
   * @param shortname
   *          the shortname
   * @param longname
   *          the longname
   * @param description
   *          the description
   * @param tagsS
   *          the tags s
   * @param groupType
   *          the group type
   */
  public void create(final String sufix, final String shortname, final String longname,
      final String description, final String tagsS, final GroupType groupType) {
    shortName.sendKeys(shortname + sufix);
    longName.sendKeys(longname + " " + sufix);
    publicDescription.sendKeys(description);
    tags.sendKeys(tagsS);

    switch (groupType) {
    case PROJECT:
      projectType.click();
      showTooltip(projectType);
      break;
    case ORGANIZATION:
      orgType.click();
      showTooltip(orgType);
      break;
    case CLOSED:
      closedType.click();
      showTooltip(closedType);
      break;
    case COMMUNITY:
      closedType.click();
      showTooltip(communityType);
      break;
    default:
      throw new UIException("New group types?");
    }
    SeleniumUtils.doScreenshot(getWebDriver(), "newgroup");
    registerBtn.click();
  }
}
