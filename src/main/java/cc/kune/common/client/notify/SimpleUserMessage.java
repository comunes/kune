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
package cc.kune.common.client.notify;

import org.cobogw.gwt.user.client.ui.RoundedPanel;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class SimpleUserMessage extends Composite {
  private static final int SHOWTIME = 3500;
  private final RoundedPanel rp;
  private final Label msg;
  private PopupPanel popupPalette;
  private final Timer timer;

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

  public void hide() {
    if (popupPalette != null) {
      popupPalette.hide();
    }
  }

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
