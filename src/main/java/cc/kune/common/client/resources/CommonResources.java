/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.common.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Interface CommonResources.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface CommonResources extends ClientBundle {

  /**
   * The Interface CommonStyle.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface CommonStyle extends CssResource {

  }

  /** The Constant INSTANCE. */
  public static final CommonResources INSTANCE = GWT.create(CommonResources.class);

  /**
   * Alert.
   *
   * @return the image resource
   */
  @Source("alert.png")
  ImageResource alert();

  /**
   * Chain close black.
   *
   * @return the image resource
   */
  @Source("icons/chain-close-black.png")
  ImageResource chainCloseBlack();

  /**
   * Chain closed grey.
   *
   * @return the image resource
   */
  @Source("icons/chain-closed-grey.png")
  ImageResource chainClosedGrey();

  /**
   * Chain close white.
   *
   * @return the image resource
   */
  @Source("icons/chain-close-white.png")
  ImageResource chainCloseWhite();

  /**
   * Chain open black.
   *
   * @return the image resource
   */
  @Source("icons/chain-open-black.png")
  ImageResource chainOpenBlack();

  /**
   * Chain open grey.
   *
   * @return the image resource
   */
  @Source("icons/chain-open-grey.png")
  ImageResource chainOpenGrey();

  /**
   * Chain open white.
   *
   * @return the image resource
   */
  @Source("icons/chain-open-white.png")
  ImageResource chainOpenWhite();

  /**
   * Checked.
   *
   * @return the image resource
   */
  @Source("checked.png")
  ImageResource checked();

  /**
   * Clear.
   *
   * @return the image resource
   */
  @Source("clear.png")
  ImageResource clear();

  /**
   * Clear16.
   *
   * @return the image resource
   */
  @Source("clear16.png")
  ImageResource clear16();

  /**
   * Common style.
   *
   * @return the common style
   */
  @Source("common-resources.css")
  CommonStyle commonStyle();

  /**
   * Edits the black.
   *
   * @return the image resource
   */
  @Source("icons/edit-black.png")
  ImageResource editBlack();

  /**
   * Edits the gray dark.
   *
   * @return the image resource
   */
  @Source("icons/edit-gray_dark.png")
  ImageResource editGrayDark();

  /**
   * Edits the white.
   *
   * @return the image resource
   */
  @Source("icons/edit-white.png")
  ImageResource editWhite();

  /**
   * Error.
   *
   * @return the image resource
   */
  @Source("error.png")
  ImageResource error();

  /**
   * Important.
   *
   * @return the image resource
   */
  @Source("important.png")
  ImageResource important();

  /**
   * Important32.
   *
   * @return the image resource
   */
  @Source("important-32.png")
  ImageResource important32();

  /**
   * Info.
   *
   * @return the image resource
   */
  @Source("info.png")
  ImageResource info();

  /**
   * Kune anim.
   *
   * @return the image resource
   */
  @Source("kune-anim.gif")
  ImageResource kuneAnim();

  /**
   * Kune anim g.
   *
   * @return the image resource
   */
  @Source("kune-anim-g.gif")
  ImageResource kuneAnimG();

  /**
   * Kune close.
   *
   * @return the image resource
   */
  @Source("kune-close.png")
  ImageResource kuneClose();

  /**
   * Kune close grey.
   *
   * @return the image resource
   */
  @Source("kune-close-grey.png")
  ImageResource kuneCloseGrey();

  /**
   * Kune close greylight.
   *
   * @return the image resource
   */
  @Source("kune-close-greylight.png")
  ImageResource kuneCloseGreylight();

  /**
   * Kune font resource.
   *
   * @return the data resource
   */
  @Source("fonts/kune/iconsfont.ttf")
  DataResource kuneFontResource();

  /**
   * Location black.
   *
   * @return the image resource
   */
  @Source("icons/location-black.png")
  ImageResource locationBlack();

  /**
   * Location grey.
   *
   * @return the image resource
   */
  @Source("icons/location-grey.png")
  ImageResource locationGrey();

  /**
   * Location white.
   *
   * @return the image resource
   */
  @Source("icons/location-white.png")
  ImageResource locationWhite();

  /**
   * Picture black.
   *
   * @return the image resource
   */
  @Source("icons/picture-black.png")
  ImageResource pictureBlack();

  /**
   * Picture grey.
   *
   * @return the image resource
   */
  @Source("icons/picture-grey.png")
  ImageResource pictureGrey();

  /**
   * Picture white.
   *
   * @return the image resource
   */
  @Source("icons/picture-white.png")
  ImageResource pictureWhite();

  /**
   * Pref black.
   *
   * @return the image resource
   */
  @Source("icons/pref-black.png")
  ImageResource prefBlack();

  /**
   * Pref grey.
   *
   * @return the image resource
   */
  @Source("icons/pref-grey.png")
  ImageResource prefGrey();

  /**
   * Pref white.
   *
   * @return the image resource
   */
  @Source("icons/pref-white.png")
  ImageResource prefWhite();

  /**
   * Radio checked.
   *
   * @return the image resource
   */
  @Source("radiochecked.png")
  ImageResource radioChecked();

  /**
   * Radio un checked.
   *
   * @return the image resource
   */
  @Source("radiounchecked.png")
  ImageResource radioUnChecked();

  /**
   * Red cross.
   *
   * @return the image resource
   */
  @Source("cross.png")
  ImageResource redCross();

  /**
   * Trash black.
   *
   * @return the image resource
   */
  @Source("icons/trash-black.png")
  ImageResource trashBlack();

  /**
   * Trash white.
   *
   * @return the image resource
   */
  @Source("icons/trash-white.png")
  ImageResource trashWhite();

  /**
   * Un checked.
   *
   * @return the image resource
   */
  @Source("unchecked.png")
  ImageResource unChecked();

  /**
   * World16.
   *
   * @return the image resource
   */
  @Source("icons/world-16.png")
  ImageResource world16();
}
