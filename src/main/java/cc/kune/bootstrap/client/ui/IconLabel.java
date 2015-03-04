/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.base.button.CustomIconTextMixin;
import org.gwtbootstrap3.client.ui.constants.IconFlip;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconRotate;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;

import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.resources.client.ImageResource;

public class IconLabel extends Anchor {
  CustomIconTextMixin<Anchor> iconTextMixin;

  public IconLabel() {
    addStyleName("k-bs-iconlabel");
    iconTextMixin = new CustomIconTextMixin<Anchor>(this);
  }

  public IconLabel(final String text) {
    this();
    iconTextMixin.setText(text);
  }

  @Override
  public IconType getIcon() {
    return iconTextMixin.getIcon();
  }

  @Override
  public IconFlip getIconFlip() {
    return iconTextMixin.getIconFlip();
  }

  @Override
  public IconPosition getIconPosition() {
    return iconTextMixin.getIconPosition();
  }

  @Override
  public IconRotate getIconRotate() {
    return iconTextMixin.getIconRotate();
  }

  @Override
  public IconSize getIconSize() {
    return iconTextMixin.getIconSize();
  }

  @Override
  public String getText() {
    return iconTextMixin.getText();
  }

  @Override
  public boolean isIconBordered() {
    return iconTextMixin.isIconBordered();
  }

  @Override
  public boolean isIconLight() {
    return iconTextMixin.isIconLight();
  }

  @Override
  public boolean isIconMuted() {
    return iconTextMixin.isIconMuted();
  }

  @Override
  public boolean isIconSpin() {
    return iconTextMixin.isIconSpin();
  }

  @Override
  public void setIcon(final IconType iconType) {
    iconTextMixin.setIcon(iconType);
  }

  public void setIcon(final KuneIcon icon) {
    iconTextMixin.setIcon(icon);
  }

  public void setIconBackColor(final String backgroundColor) {
    iconTextMixin.setIconBackColor(backgroundColor);
  }

  @Override
  public void setIconBordered(final boolean iconBordered) {
    iconTextMixin.setIconBordered(iconBordered);
  }

  @Override
  public void setIconFlip(final IconFlip iconFlip) {
    iconTextMixin.setIconFlip(iconFlip);
  }

  @Override
  public void setIconLight(final boolean iconLight) {
    iconTextMixin.setIconLight(iconLight);
  }

  @Override
  public void setIconMuted(final boolean iconMuted) {
    iconTextMixin.setIconMuted(iconMuted);
  }

  @Override
  public void setIconPosition(final IconPosition iconPosition) {
    iconTextMixin.setIconPosition(iconPosition);
  }

  public void setIconResource(final ImageResource icon) {
    iconTextMixin.setIconResource(icon);
  }

  public void setIconRightResource(final ImageResource icon) {
    iconTextMixin.setIconRightResource(icon);
  }

  @Override
  public void setIconRotate(final IconRotate iconRotate) {
    iconTextMixin.setIconRotate(iconRotate);
  }

  @Override
  public void setIconSize(final IconSize iconSize) {
    iconTextMixin.setIconSize(iconSize);
  }

  @Override
  public void setIconSpin(final boolean iconSpin) {
    iconTextMixin.setIconSpin(iconSpin);
  }

  public void setIconStyle(final String style) {
    iconTextMixin.setIconStyle(style);
  }

  public void setIconUrl(final String url) {
    iconTextMixin.setIconUrl(url);
  }

  @Override
  public void setId(final String id) {
    ensureDebugId(id);
  }

  @Override
  public void setText(final String text) {
    iconTextMixin.setText(text);
  }
}
