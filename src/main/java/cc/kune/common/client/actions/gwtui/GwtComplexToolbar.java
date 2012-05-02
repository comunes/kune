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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.ui.FlowToolbar;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class GwtComplexToolbar extends Composite implements IsWidget {

  private enum FlowDir {
    left, right
  }
  private FlowDir currentFlow;
  private final FlowToolbar toolbar;

  public GwtComplexToolbar() {
    toolbar = new FlowToolbar();
    currentFlow = FlowDir.left;
    initWidget(toolbar);
  }

  protected void add(final AbstractGuiItem item) {
    item.addStyleName(getFlow());
    toolbar.add(item);
  }

  public void add(final UIObject uiObject) {
    uiObject.addStyleName(getFlow());
    toolbar.add((Widget) uiObject);
  }

  public Widget addFill() {
    currentFlow = FlowDir.right;
    return toolbar.addFill();
  }

  public Widget addSeparator() {
    return toolbar.addSeparator();
  }

  public Widget addSpacer() {
    return toolbar.addSpacer();
  }

  private String getFlow() {
    switch (currentFlow) {
    case left:
      return "oc-floatleft";
    case right:
    default:
      return "oc-floatright";
    }
  }

  protected void insert(final AbstractGuiItem item, final int position) {
    item.addStyleName(getFlow());
    toolbar.insert(item, position);
  }

  public void insert(final UIObject uiObject, final int position) {
    uiObject.addStyleName(getFlow());
    toolbar.insert((Widget) uiObject, position);
  }

  /**
   * Set the blank style
   */
  public void setCleanStyle() {
    toolbar.setBlankStyle();
  }

  /**
   * Set the normal grey style
   */
  public void setNormalStyle() {
    toolbar.setNormalStyle();
  }

  /**
   * Set the blank style
   */
  public void setTranspStyle() {
    toolbar.setTranspStyle();
  }

}
