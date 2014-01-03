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

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class PopupTopPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PopupTopPanel extends AbstractAtBorderPopupPanel {
  
  /**
   * Instantiates a new popup top panel.
   */
  public PopupTopPanel() {
    this(false, false);
  }

  /**
   * Instantiates a new popup top panel.
   *
   * @param autohide the autohide
   */
  public PopupTopPanel(final boolean autohide) {
    this(autohide, false);
  }

  /**
   * Instantiates a new popup top panel.
   *
   * @param autohide the autohide
   * @param modal the modal
   */
  public PopupTopPanel(final boolean autohide, final boolean modal) {
    super(autohide, modal);
    defaultStyleImpl();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractAtBorderPopupPanel#defaultStyle()
   */
  @Override
  public void defaultStyle() {
    defaultStyleImpl();
  }

  /**
   * Default style impl.
   */
  private void defaultStyleImpl() {
    setStyleName("k-popup-top-centered");
    super.defaultStyle();
    addStyleName("k-bottom-10corners");
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractAtBorderPopupPanel#setCenterPositionImpl()
   */
  @Override
  protected void setCenterPositionImpl() {
    setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      @Override
      public void setPosition(final int offsetWidth, final int offsetHeight) {
        final int x = (Window.getClientWidth() - (getWidget() != null ? getWidget().getOffsetWidth() : 0)) / 2;
        final int y = 0;
        PopupTopPanel.this.setPopupPosition(x, y);
      }
    });
  }

}
