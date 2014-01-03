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
package cc.kune.common.client.ui;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractAtBorderPopupPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractAtBorderPopupPanel extends PopupPanel {

  /** The show centered. */
  private boolean showCentered = true;
  
  /** The show near object. */
  protected UIObject showNearObject;

  /**
   * Instantiates a new abstract at border popup panel.
   */
  public AbstractAtBorderPopupPanel() {
    this(false, false);
  }

  /**
   * Instantiates a new abstract at border popup panel.
   *
   * @param autohide the autohide
   */
  public AbstractAtBorderPopupPanel(final boolean autohide) {
    this(autohide, false);
  }

  /**
   * Instantiates a new abstract at border popup panel.
   *
   * @param autohide the autohide
   * @param modal the modal
   */
  public AbstractAtBorderPopupPanel(final boolean autohide, final boolean modal) {
    super(autohide, modal);
    setGlassEnabled(modal);
    init();
  }

  /**
   * Default style.
   */
  public void defaultStyle() {
    addStyleName("k-opacity90");
    addStyleName("k-box-10shadow");
  }

  /**
   * Inits the.
   */
  private void init() {
    Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(final ResizeEvent event) {
        if (isShowing()) {
          if (showCentered) {
            setCenterPositionImpl();
          } else {
            showRelativeImpl();
          }
        }
      }
    });
  }

  /**
   * Sets the center position.
   */
  public void setCenterPosition() {
    setCenterPositionImpl();
  }

  /**
   * Sets the center position impl.
   */
  protected abstract void setCenterPositionImpl();

  /**
   * Show centered.
   */
  public void showCentered() {
    showCentered = true;
    setCenterPositionImpl();
  }

  /**
   * Show near.
   *
   * @param object the object
   */
  public void showNear(final UIObject object) {
    this.showNearObject = object;
    showCentered = false;
    showRelativeImpl();
  }

  /**
   * Show relative impl.
   */
  private void showRelativeImpl() {
    showRelativeTo(showNearObject);
  }
}
