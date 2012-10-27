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

public class EntityTextLogo extends VerticalPanel {

  private static final String _100 = "100%";
  public static final String LOGO_IMAGE = "k-elogop-image";

  private static final String LOGO_LARGE_FONT_STYLE = "k-elogo-l-l";
  private static final String LOGO_MEDIUM_FONT_STYLE = "k-elogo-l-m";
  public static final String LOGO_NAME = "k-elogop-ln";
  private static final String LOGO_SMALL_FONT_STYLE = "k-elogo-l-s";
  private final MediumAvatarDecorator avatarDecorator;
  private final Image logoImage;
  private final Label logoLabel;

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

  private void resetFontSize() {
    logoLabel.removeStyleName(LOGO_LARGE_FONT_STYLE);
    logoLabel.removeStyleName(LOGO_SMALL_FONT_STYLE);
    logoLabel.removeStyleName(LOGO_MEDIUM_FONT_STYLE);
  }

  public void setLargeFont() {
    resetFontSize();
    logoLabel.addStyleName(LOGO_LARGE_FONT_STYLE);
  }

  public void setLogoImage(final AbstractImagePrototype imageProto) {
    imageProto.applyTo(logoImage);
  }

  public void setLogoImage(final String url) {
    logoImage.setUrl(FileConstants.ASITE_PREFIX + "images/clear.gif");
    Image.prefetch(url);
    logoImage.setUrl(url);
    setSize();
  }

  public void setLogoText(final String text) {
    setLogoTextImpl(text);
  }

  private void setLogoTextImpl(final String text) {
    logoLabel.setText(text);
  }

  public void setLogoVisible(final boolean visible) {
    logoImage.setVisible(visible);
  }

  public void setMediumFont() {
    resetFontSize();
    logoLabel.addStyleName(LOGO_MEDIUM_FONT_STYLE);
  }

  public void setOnlineStatusGroup(final String group) {
    avatarDecorator.setItem(group);
  }

  public void setOnlineStatusVisible(final boolean visible) {
    avatarDecorator.setDecoratorVisible(visible);
  }

  private void setSize() {
    logoImage.setSize(FileConstants.LOGO_DEF_SIZE + "px", FileConstants.LOGO_DEF_SIZE + "px");
  }

  public void setSmallFont() {
    resetFontSize();
    logoLabel.addStyleName(LOGO_SMALL_FONT_STYLE);
  }
}
