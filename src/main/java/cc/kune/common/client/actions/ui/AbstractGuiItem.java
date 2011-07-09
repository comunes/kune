/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.bind.GuiBinding;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.resources.IconConstants;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractGuiItem extends Composite implements GuiBinding {

  protected GuiActionDescrip descriptor;

  public AbstractGuiItem() {
    super();
  }

  public AbstractGuiItem(final GuiActionDescrip descriptor) {
    super();
    this.descriptor = descriptor;
  }

  protected void addStyle(final String style) {
    if (super.isOrWasAttached()) {
      super.addStyleName(style);
    }
  }

  protected void clearStyles() {
    if (super.isOrWasAttached()) {
      super.setStyleName("k-none");
    }
  }

  private void configure() {
    configureProperties();
    final PropertyChangeListener changeListener = createActionPropertyChangeListener();
    descriptor.getAction().addPropertyChangeListener(changeListener);
    descriptor.addPropertyChangeListener(changeListener);
  }

  /**
   * Sets the item properties from the stored values
   */
  public void configureItemFromProperties() {
    configure();
  }

  private void configureProperties() {
    setText((String) (descriptor.getValue(Action.NAME)));
    setToolTipText((String) (descriptor.getValue(Action.SHORT_DESCRIPTION)));
    setIcon(descriptor.getValue(Action.SMALL_ICON));
    setEnabled((Boolean) descriptor.getValue(AbstractAction.ENABLED));
    setVisible((Boolean) descriptor.getValue(GuiActionDescrip.VISIBLE));
    setStyles((String) descriptor.getValue(Action.STYLES));
  }

  @Override
  public abstract AbstractGuiItem create(final GuiActionDescrip descriptor);

  private PropertyChangeListener createActionPropertyChangeListener() {
    return new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        final Object newValue = event.getNewValue();
        if (event.getPropertyName().equals(Action.ENABLED)) {
          setEnabled((Boolean) newValue);
        } else if (event.getPropertyName().equals(Action.NAME)) {
          setText((String) newValue);
        } else if (event.getPropertyName().equals(Action.SMALL_ICON)) {
          setIcon(newValue);
        } else if (event.getPropertyName().equals(Action.SHORT_DESCRIPTION)) {
          setToolTipText((String) newValue);
        } else if (event.getPropertyName().equals(GuiActionDescrip.VISIBLE)) {
          setVisible((Boolean) newValue);
        } else if (event.getPropertyName().equals(GuiActionDescrip.TOOGLE_TOOLTIP_VISIBLE)) {
          toogleTooltipVisible();
        } else if (event.getPropertyName().equals(Action.STYLES)) {
          setStyles((String) newValue);
        }
      }
    };
  }

  protected Object getTargetObjectOfAction(final GuiActionDescrip descriptor) {
    // If the action is associated with a item (like a Group, a
    // group shortname, a username, etc) we pass this item to
    // the action, if not we only pass the menuitem
    return descriptor.hasTarget() ? descriptor.getTarget()
        : descriptor.isChild() ? descriptor.getParent().getTarget() : ActionEvent.NO_TARGET;
  }

  protected abstract void setEnabled(boolean enabled);

  private void setEnabled(final Boolean enabled) {
    setEnabled(enabled == null ? true : enabled);
  }

  private void setIcon(final Object icon) {
    if (icon instanceof ImageResource) {
      setIconResource((ImageResource) icon);
    } else if (icon instanceof String) {
      final String iconS = (String) icon;
      if (iconS.startsWith("http")) {
        setIconUrl(iconS);
      } else {
        if (iconS.startsWith("#")) {
          setIconBackground(iconS);
        } else {
          setIconStyle(iconS);
        }
      }
    } else if (icon != null) {
      throw new NotImplementedException();
    }
  }

  protected abstract void setIconBackground(String backgroundColor);

  public void setIconResource(final ImageResource icon) {
    setIconStyle((IconConstants.CSS_SUFFIX + icon.getName()));
  }

  protected abstract void setIconStyle(String style);

  public abstract void setIconUrl(String url);

  private void setStyles(final String styles) {
    if (styles != null) {
      clearStyles();
      for (final String style : TextUtils.splitTags(styles)) {
        addStyle(style);
      }
    }
  }

  protected abstract void setText(String text);

  protected void toogleTooltipVisible() {
    // do nothing by default
    return;
  }

  protected abstract void setToolTipText(String text);

  private void setVisible(final Boolean visible) {
    // if you have problems with setVisible check if the GuiItem calls
    // configureItemFromProperties on creation
    setVisible(visible == null ? true : visible);
  }

  @Override
  public boolean shouldBeAdded() { // NOPMD by vjrj on 18/01/11 0:48
    return true;
  }
}
