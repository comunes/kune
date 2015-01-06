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

import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.base.HasIcon;
import org.gwtbootstrap3.client.ui.base.HasIconPosition;
import org.gwtbootstrap3.client.ui.constants.IconFlip;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconRotate;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Text;

import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;

/**
 * Custom mixin for Widgets that have text and some optional icons.
 *
 * @author Sven Jacobs
 * @author Vicente J. Ruiz Jurado (adaptation for kune)
 */
public class CustomIconTextMixin<T extends ComplexWidget & HasText & HasIcon & HasIconPosition>
    implements HasText, HasIcon, HasIconPosition {
  private static final String DEF = "16px";
  private Icon icon;
  private boolean iconBordered = false;
  private IconFlip iconFlip = IconFlip.NONE;
  private Image iconImage;
  private Text iconLabel;
  private boolean iconLight = false;
  private boolean iconMuted = false;
  private IconPosition iconPosition = IconPosition.LEFT;
  private Image iconRightImage;
  private IconRotate iconRotate = IconRotate.NONE;
  private IconSize iconSize = IconSize.NONE;
  private boolean iconSpin = false;
  private IconType iconType;
  private final Text separator = new Text(" ");
  private final Text separator2 = new Text(" ");
  private final Text text = new Text();
  private final T widget;

  public CustomIconTextMixin(final T widget) {
    this.widget = widget;
  }

  public void addTextWidgetToParent() {
    widget.add(text);
  }

  @Override
  public IconType getIcon() {
    return icon == null ? null : icon.getType();
  }

  @Override
  public IconFlip getIconFlip() {
    return iconFlip;
  }

  @Override
  public IconPosition getIconPosition() {
    return iconPosition;
  }

  @Override
  public IconRotate getIconRotate() {
    return iconRotate;
  }

  @Override
  public IconSize getIconSize() {
    return iconSize;
  }

  @Override
  public String getText() {
    return text.getText();
  }

  @Override
  public boolean isIconBordered() {
    return iconBordered;
  }

  @Override
  public boolean isIconLight() {
    return iconLight;
  }

  @Override
  public boolean isIconMuted() {
    return iconMuted;
  }

  @Override
  public boolean isIconSpin() {
    return iconSpin;
  }

  private void render() {
    // We defer to make sure the elements are available to manipulate their
    // positions
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      private int addOtherIcons(final boolean hasIconImage, final boolean hasIconRightImage,
          final boolean hasIconLabel, int position) {
        if (hasIconImage) {
          widget.insert(iconImage, position++);
        }
        if (hasIconRightImage) {
          widget.insert(iconRightImage, position++);
        }
        if (hasIconLabel) {
          widget.insert(iconLabel, position++);
        }
        return position;
      }

      private int addOtherSeparator(final boolean hasIconImage, final boolean hasIconRightImage,
          final boolean hasIconLabel, int position) {
        if (hasIconImage || hasIconRightImage || hasIconLabel) {
          widget.insert(separator2, position++);
        }
        return position;
      }

      @Override
      public void execute() {
        if (text.isAttached()) {
          text.removeFromParent();
        }
        if (separator.isAttached()) {
          separator.removeFromParent();
        }
        if (separator2.isAttached()) {
          separator2.removeFromParent();
        }
        if (icon != null) {
          icon.removeFromParent();
        }

        final boolean hasIconImage = iconImage != null;
        final boolean hasIconRightImage = iconRightImage != null;
        final boolean hasIconLabel = iconLabel != null;

        if (hasIconImage) {
          iconImage.removeFromParent();
        }
        if (hasIconRightImage) {
          iconRightImage.removeFromParent();
        }
        if (hasIconLabel) {
          iconLabel.removeFromParent();
        }

        icon = new Icon();
        icon.setType(iconType);
        icon.setSize(iconSize);
        icon.setFlip(iconFlip);
        icon.setRotate(iconRotate);
        icon.setMuted(iconMuted);
        icon.setSpin(iconSpin);
        icon.setBorder(iconBordered);
        icon.setLight(iconLight);

        // Since we are dealing with Icon/Text, we can insert them at the right
        // position
        // Helps on widgets like ButtonDropDown, where it has a caret added
        int position = 0;

        if (iconPosition == IconPosition.LEFT) {
          widget.insert(icon, position++);
          widget.insert(separator, position++);
          position = addOtherIcons(hasIconImage, hasIconRightImage, hasIconLabel, position);
          position = addOtherSeparator(hasIconImage, hasIconRightImage, hasIconLabel, position);
        }

        if (text.getText() != null && text.getText().length() > 0) {
          widget.insert(text, position);
        }

        if (iconPosition == IconPosition.RIGHT) {
          widget.insert(separator, position++);
          widget.insert(icon, position);
          position = addOtherSeparator(hasIconImage, hasIconRightImage, hasIconLabel, position);
          position = addOtherIcons(hasIconImage, hasIconRightImage, hasIconLabel, position);
        }
      }

    });
  }

  @Override
  public void setIcon(final IconType iconType) {
    this.iconType = iconType;
    render();
  }

  public void setIcon(final KuneIcon icon) {
    iconLabel = new Text(icon.getCharacter().toString());
    render();
  }

  public void setIconBackColor(final String backgroundColor) {
    iconLabel = new Text(" ");
    iconLabel.getElement().getStyle().setBackgroundColor(backgroundColor);
    render();
  }

  @Override
  public void setIconBordered(final boolean iconBordered) {
    this.iconBordered = iconBordered;
    render();
  }

  @Override
  public void setIconFlip(final IconFlip iconFlip) {
    this.iconFlip = iconFlip;
    render();
  }

  @Override
  public void setIconLight(final boolean iconLight) {
    this.iconLight = iconLight;
    render();
  }

  @Override
  public void setIconMuted(final boolean iconMuted) {
    this.iconMuted = iconMuted;
    render();
  }

  @Override
  public void setIconPosition(final IconPosition iconPosition) {
    this.iconPosition = iconPosition;
    render();
  }

  public void setIconResource(final ImageResource icon) {
    iconImage = new Image(icon);
    iconImage.setSize(DEF, DEF);
    render();
  }

  public void setIconRightResource(final ImageResource icon) {
    iconRightImage = new Image(icon);
    iconRightImage.setSize(DEF, DEF);
    render();
  }

  @Override
  public void setIconRotate(final IconRotate iconRotate) {
    this.iconRotate = iconRotate;
    render();
  }

  @Override
  public void setIconSize(final IconSize iconSize) {
    this.iconSize = iconSize;
    render();
  }

  @Override
  public void setIconSpin(final boolean iconSpin) {
    this.iconSpin = iconSpin;
  }

  public void setIconStyle(final String style) {
    iconLabel = new Text(" ");
    iconLabel.addStyleName(style);
    render();
  }

  public void setIconUrl(final String url) {
    iconImage = new Image(url);
    iconImage.setSize(DEF, DEF);
    render();
  }

  @Override
  public void setText(final String text) {
    this.text.setText(text);
  }
}
