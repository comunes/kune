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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.ToggleButton;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGwtButtonGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractGwtButtonGui extends AbstractChildGuiItem {

  /** The button. */
  private ButtonBase button;
  
  /** The enable tongle. */
  protected boolean enableTongle;
  
  /** The icon label. */
  private IconLabel iconLabel;
  
  /** The is child. */
  private boolean isChild;

  /**
   * Instantiates a new abstract gwt button gui.
   */
  public AbstractGwtButtonGui() {
    this(null, false);
  }

  /**
   * Instantiates a new abstract gwt button gui.
   *
   * @param enableTongle the enable tongle
   */
  public AbstractGwtButtonGui(final boolean enableTongle) {
    this(null, enableTongle);
  }

  /**
   * Instantiates a new abstract gwt button gui.
   *
   * @param buttonDescriptor the button descriptor
   */
  public AbstractGwtButtonGui(final ButtonDescriptor buttonDescriptor) {
    this(buttonDescriptor, false);
  }

  /**
   * Instantiates a new abstract gwt button gui.
   *
   * @param buttonDescriptor the button descriptor
   * @param enableTongle the enable tongle
   */
  public AbstractGwtButtonGui(final ButtonDescriptor buttonDescriptor, final boolean enableTongle) {
    super(buttonDescriptor);
    this.enableTongle = enableTongle;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractChildGuiItem#addStyle(java.lang.String)
   */
  @Override
  protected void addStyle(final String style) {
    button.addStyleName(style);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractChildGuiItem#create(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    iconLabel = new IconLabel("");
    if (enableTongle) {
      button = new ToggleButton();
    } else {
      button = new Button();
    }
    final String value = (String) descriptor.getValue(Action.STYLES);
    if (value == null) {
      // Default btn styles
      button.addStyleName("k-button");
      button.addStyleName("k-btn");
      button.addStyleName("k-5corners");
    } else {
      setStyles(value);
    }
    layout();
    // button.setEnableToggle(enableTongle);
    final String id = descriptor.getId();
    if (id != null) {
      button.ensureDebugId(id);
    }
    isChild = descriptor.isChild();
    if (isChild) {
      // If button is inside a toolbar don't init...
      if (descriptor.isChild()) {
        child = button;
      }
    } else {
      initWidget(button);
    }
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        descriptor.fire(new ActionEvent(button, getTargetObjectOfAction(descriptor),
            Event.as(event.getNativeEvent())));
      }
    });
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  /**
   * Layout.
   */
  private void layout() {
    button.setHTML(iconLabel.getElement().getInnerHTML());
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    // Log.info("Set button" + descriptor.getValue(Action.NAME) + " enabled " +
    // enabled
    // + " ----------------------------------");
    button.setEnabled(enabled);
    button.getElement().getStyle().setOpacity(enabled ? 1d : 0.6d);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(cc.kune.common.shared.res.KuneIcon)
   */
  @Override
  public void setIcon(final KuneIcon icon) {
    iconLabel.setLeftIconFont(icon);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java.lang.String)
   */
  @Override
  public void setIconBackground(final String backgroundColor) {
    iconLabel.setLeftIconBackground(backgroundColor);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconResource(com.google.gwt.resources.client.ImageResource)
   */
  @Override
  public void setIconResource(final ImageResource icon) {
    iconLabel.setLeftIconResource(icon);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang.String)
   */
  @Override
  protected void setIconStyle(final String style) {
    iconLabel.setRightIcon(style);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconUrl(java.lang.String)
   */
  @Override
  public void setIconUrl(final String url) {
    iconLabel.setLeftIconUrl(url);
    layout();
  }

  /**
   * Sets the pressed.
   *
   * @param pressed the new pressed
   */
  public void setPressed(final boolean pressed) {
    final ToggleButton toggleButton = (ToggleButton) button;

    if (toggleButton.isDown() != pressed) {
      toggleButton.setDown(pressed);
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    iconLabel.setText(text, descriptor.getDirection());
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setToolTipText(java.lang.String)
   */
  @Override
  public void setToolTipText(final String tooltipText) {
    Tooltip.to(button, tooltipText);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    button.setVisible(visible);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() {
    return !descriptor.isChild();
  }

}
