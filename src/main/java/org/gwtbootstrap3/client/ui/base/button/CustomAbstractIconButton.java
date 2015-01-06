package org.gwtbootstrap3.client.ui.base.button;

/*
 * #%L
 * GwtBootstrap3
 * %%
 * Copyright (C) 2013 GwtBootstrap3
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.gwtbootstrap3.client.ui.base.HasIcon;
import org.gwtbootstrap3.client.ui.base.HasIconPosition;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconFlip;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconRotate;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.user.client.ui.HasText;

/**
 * Base class for buttons that can contain an icon.
 *
 * @author Sven Jacobs
 * @see org.gwtbootstrap3.client.ui.Icon
 */
public abstract class CustomAbstractIconButton extends AbstractButton implements HasText, HasIcon,
    HasIconPosition {

  CustomIconTextMixin<CustomAbstractIconButton> iconTextMixin = new CustomIconTextMixin<CustomAbstractIconButton>(
      this);

  protected CustomAbstractIconButton() {
  }

  protected CustomAbstractIconButton(final ButtonType type) {
    super(type);
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

  @Override
  public void setText(final String text) {
    iconTextMixin.setText(text);
  }
}
