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
package cc.kune.common.client.ui;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractAtBorderPopupPanel extends PopupPanel {

  private boolean showCentered = true;
  protected UIObject showNearObject;

  public AbstractAtBorderPopupPanel() {
    super(false, false);
  }

  public AbstractAtBorderPopupPanel(final boolean autohide) {
    this(autohide, false);
  }

  public AbstractAtBorderPopupPanel(final boolean autohide, final boolean modal) {
    super(autohide, modal);
    setGlassEnabled(modal);
    init();
  }

  public void defaultStyle() {
    addStyleName("k-opacity90");
    addStyleName("k-box-10shadow");
  }

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

  public void setCenterPosition() {
    setCenterPositionImpl();
  }

  protected abstract void setCenterPositionImpl();

  public void showCentered() {
    showCentered = true;
    setCenterPositionImpl();
  }

  public void showNear(final UIObject object) {
    this.showNearObject = object;
    showCentered = false;
    showRelativeImpl();
  }

  private void showRelativeImpl() {
    showRelativeTo(showNearObject);
  }
}
