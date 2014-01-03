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
package cc.kune.common.client.notify;

import org.cobogw.gwt.user.client.ui.RoundedPanel;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleUserMessage.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SimpleUserMessage extends Composite {
  
  /** The Constant SHOWTIME. */
  private static final int SHOWTIME = 3500;
  
  /** The rp. */
  private final RoundedPanel rp;
  
  /** The msg. */
  private final Label msg;
  
  /** The popup palette. */
  private PopupPanel popupPalette;
  
  /** The timer. */
  private final Timer timer;

  /**
   * Instantiates a new simple user message.
   */
  public SimpleUserMessage() {
    msg = new Label();
    msg.addStyleName("oc-user-msg");
    rp = new RoundedPanel(msg, RoundedPanel.ALL, 2);
    rp.setBorderColor("#FFCC00");
    timer = new Timer() {
      @Override
      public void run() {
        hide();
      }
    };

  }

  /**
   * Hide.
   */
  public void hide() {
    if (popupPalette != null) {
      popupPalette.hide();
    }
  }

  /**
   * Show.
   *
   * @param message the message
   */
  public void show(final String message) {
    msg.setText(message);
    popupPalette = new PopupPanel(true, false);
    popupPalette.setWidget(rp);
    popupPalette.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      public void setPosition(final int offsetWidth, final int offsetHeight) {
        popupPalette.setPopupPosition((Window.getClientWidth() - msg.getOffsetWidth()) / 2,
            Window.getClientHeight() / 3);
      }
    });
    popupPalette.setStyleName("oc-user-msg-popup");
    popupPalette.setAnimationEnabled(true);
    timer.schedule(SHOWTIME);
  }
}
