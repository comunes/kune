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
package cc.kune.core.client.ws.entheader;

import cc.kune.core.client.avatar.MediumAvatarDecorator;
import cc.kune.core.shared.FileConstants;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityTextLogo.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityTextLogo extends VerticalPanel {

  /** The Constant _100. */
  private static final String _100 = "100%";

  /** The Constant LOGO_IMAGE. */
  public static final String LOGO_IMAGE = "k-elogop-image";

  /** The Constant LOGO_LARGE_FONT_STYLE. */
  private static final String LOGO_LARGE_FONT_STYLE = "k-elogo-l-l";

  /** The Constant LOGO_MEDIUM_FONT_STYLE. */
  private static final String LOGO_MEDIUM_FONT_STYLE = "k-elogo-l-m";

  /** The Constant LOGO_NAME. */
  public static final String LOGO_NAME = "k-elogop-ln";

  /** The Constant LOGO_SMALL_FONT_STYLE. */
  private static final String LOGO_SMALL_FONT_STYLE = "k-elogo-l-s";

  /** The avatar decorator. */
  private final MediumAvatarDecorator avatarDecorator;

  /** The logo image. */
  private final Image logoImage;

  /** The logo label. */
  private final Label logoLabel;

  /**
   * Instantiates a new entity text logo.
   * 
   * @param avatarDecorator
   *          the avatar decorator
   */
  @Inject
  public EntityTextLogo(final MediumAvatarDecorator avatarDecorator) {
    // Initialize
    super();
    this.avatarDecorator = avatarDecorator;
    final HorizontalPanel generalHP = new HorizontalPanel();
    final VerticalPanel logoTextVP = new VerticalPanel();
    logoImage = new Image();
    setSize();
    logoLabel = new Label();
    avatarDecorator.setWidget(logoImage);
    final Label expandCell = new Label("");

    logoImage.ensureDebugId(LOGO_IMAGE);
    logoLabel.ensureDebugId(LOGO_NAME);

    // Layout
    add(generalHP);
    generalHP.add((Widget) avatarDecorator);
    generalHP.add(logoTextVP);
    logoTextVP.add(logoLabel);

    // Set properties

    expandCell.setStyleName("k-elogop-expand");
    generalHP.setWidth(_100);
    generalHP.setHeight(_100);
    generalHP.setCellWidth(logoTextVP, _100);
    generalHP.setCellHeight(logoTextVP, _100);
    logoTextVP.setWidth(_100);
    logoTextVP.setCellWidth(logoLabel, _100);
    super.setVerticalAlignment(ALIGN_MIDDLE);
    logoTextVP.setVerticalAlignment(ALIGN_MIDDLE);
    generalHP.setVerticalAlignment(ALIGN_MIDDLE);
    setStylePrimaryName("k-entitytextlogo");
    addStyleName("k-entitytextlogo-no-border");
    logoImage.setVisible(false);
    setLogoTextImpl("");
  }

  /**
   * Reset font size.
   */
  private void resetFontSize() {
    logoLabel.removeStyleName(LOGO_LARGE_FONT_STYLE);
    logoLabel.removeStyleName(LOGO_SMALL_FONT_STYLE);
    logoLabel.removeStyleName(LOGO_MEDIUM_FONT_STYLE);
  }

  /**
   * Sets the large font.
   */
  public void setLargeFont() {
    resetFontSize();
    logoLabel.addStyleName(LOGO_LARGE_FONT_STYLE);
  }

  /**
   * Sets the logo image.
   * 
   * @param imageProto
   *          the new logo image
   */
  public void setLogoImage(final AbstractImagePrototype imageProto) {
    imageProto.applyTo(logoImage);
  }

  /**
   * Sets the logo image.
   * 
   * @param url
   *          the new logo image
   */
  public void setLogoImage(final String url) {
    logoImage.setUrl(FileConstants.ASITE_PREFIX + "images/clear.gif");
    Image.prefetch(url);
    logoImage.setUrl(url);
    setSize();
  }

  /**
   * Sets the logo text.
   * 
   * @param text
   *          the new logo text
   */
  public void setLogoText(final String text) {
    setLogoTextImpl(text);
  }

  /**
   * Sets the logo text impl.
   * 
   * @param text
   *          the new logo text impl
   */
  private void setLogoTextImpl(final String text) {
    logoLabel.setText(text);
  }

  /**
   * Sets the logo visible.
   * 
   * @param visible
   *          the new logo visible
   */
  public void setLogoVisible(final boolean visible) {
    logoImage.setVisible(visible);
  }

  /**
   * Sets the medium font.
   */
  public void setMediumFont() {
    resetFontSize();
    logoLabel.addStyleName(LOGO_MEDIUM_FONT_STYLE);
  }

  /**
   * Sets the online status group.
   * 
   * @param group
   *          the new online status group
   */
  public void setOnlineStatusGroup(final String group) {
    avatarDecorator.setItem(group);
  }

  /**
   * Sets the online status visible.
   * 
   * @param visible
   *          the new online status visible
   */
  public void setOnlineStatusVisible(final boolean visible) {
    avatarDecorator.setDecoratorVisible(visible);
  }

  /**
   * Sets the size.
   */
  private void setSize() {
    logoImage.setSize(FileConstants.LOGO_DEF_SIZE + "px", FileConstants.LOGO_DEF_SIZE + "px");
  }

  /**
   * Sets the small font.
   */
  public void setSmallFont() {
    resetFontSize();
    logoLabel.addStyleName(LOGO_SMALL_FONT_STYLE);
  }
}
