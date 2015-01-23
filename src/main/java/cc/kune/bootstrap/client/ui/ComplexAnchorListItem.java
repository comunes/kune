package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.base.button.CustomIconTextMixin;
import org.gwtbootstrap3.client.ui.constants.IconFlip;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconRotate;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;

import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.resources.client.ImageResource;

public class ComplexAnchorListItem extends AnchorListItem {
  CustomIconTextMixin<Anchor> iconTextMixin;

  public ComplexAnchorListItem() {
    iconTextMixin = new CustomIconTextMixin<Anchor>(anchor);
  }

  public ComplexAnchorListItem(final String text) {
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

  public void setShortcut(final String shortcutHtml) {
    iconTextMixin.setShortcut(shortcutHtml);
  }

  @Override
  public void setText(final String text) {
    iconTextMixin.setText(text);
  }
}
