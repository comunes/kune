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
package cc.kune.gspace.client.armor.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Interface GSpaceArmorResources.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface GSpaceArmorResources extends ClientBundle {

  /**
   * The Interface Style.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface Style extends CssResource {

    /**
     * Doc editor.
     * 
     * @return the string
     */
    String docEditor();

    /**
     * Doc editor container.
     * 
     * @return the string
     */
    String docEditorContainer();

    /**
     * Doc editor margin.
     * 
     * @return the string
     */
    String docEditorMargin();

    /**
     * Doc footer.
     * 
     * @return the string
     */
    String docFooter();

    /**
     * Doc header.
     * 
     * @return the string
     */
    String docHeader();

    /**
     * Doc header arrow.
     * 
     * @return the string
     */
    String docHeaderArrow();

    /**
     * Doc subheader.
     * 
     * @return the string
     */
    String docSubheader();

    /**
     * Doc subheader arrow.
     * 
     * @return the string
     */
    String docSubheaderArrow();

    /**
     * Doc subheader left.
     * 
     * @return the string
     */
    String docSubheaderLeft();

    /**
     * Entity central container.
     * 
     * @return the string
     */
    String entityCentralContainer();

    /**
     * Entity footer.
     * 
     * @return the string
     */
    String entityFooter();

    /**
     * Entity header.
     * 
     * @return the string
     */
    String entityHeader();

    /**
     * Entity tools.
     * 
     * @return the string
     */
    String entityTools();

    /**
     * Entity tools center.
     * 
     * @return the string
     */
    String entityToolsCenter();

    /**
     * Entity tools north.
     * 
     * @return the string
     */
    String entityToolsNorth();

    /**
     * Entity tools south.
     * 
     * @return the string
     */
    String entityToolsSouth();

    /**
     * Float left.
     * 
     * @return the string
     */
    String floatLeft();

    /**
     * Float right.
     * 
     * @return the string
     */
    String floatRight();

    /**
     * Main panel.
     * 
     * @return the string
     */
    String mainPanel();

    /**
     * Site bar.
     * 
     * @return the string
     */
    String siteBar();
  }

  /**
   * Def theme doc arrow down.
   * 
   * @return the image resource
   */
  @Source("def-theme-doc-arrow-down.png")
  ImageResource defThemeDocArrowDown();

  /**
   * Def theme tools arrow left.
   * 
   * @return the image resource
   */
  @Source("def-theme-tools-arrow-left.png")
  ImageResource defThemeToolsArrowLeft();

  /**
   * Group space disabled.
   * 
   * @return the image resource
   */
  ImageResource groupSpaceDisabled();

  /**
   * Group space enabled.
   * 
   * @return the image resource
   */
  ImageResource groupSpaceEnabled();

  /**
   * Home space disabled.
   * 
   * @return the image resource
   */
  ImageResource homeSpaceDisabled();

  /**
   * Home space enabled.
   * 
   * @return the image resource
   */
  ImageResource homeSpaceEnabled();

  /**
   * Public space disabled.
   * 
   * @return the image resource
   */
  ImageResource publicSpaceDisabled();

  /**
   * Public space enabled.
   * 
   * @return the image resource
   */
  ImageResource publicSpaceEnabled();

  /**
   * Style.
   * 
   * @return the style
   */
  @Source({ "gSpaceArmor.css", "gSpaceArmorTheme.css" })
  Style style();

  /**
   * User space disabled.
   * 
   * @return the image resource
   */
  ImageResource userSpaceDisabled();

  /**
   * User space enabled.
   * 
   * @return the image resource
   */
  ImageResource userSpaceEnabled();
}
