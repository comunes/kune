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
 \*/
package cc.kune.common.client.ui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractFlowToolbar extends FlowPanel implements AbstractToolbar, HasWidgets {

  public AbstractFlowToolbar() {
    super();
  }

  @Override
  public Widget addFill() {
    final Label emptyLabel = new Label("");
    emptyLabel.addStyleName("oc-floatright");
    // emptyLabel.setWidth("100%");
    add(emptyLabel);
    return emptyLabel;
  }

  @Override
  public Widget addSeparator() {
    final Label emptyLabel = new Label("");
    emptyLabel.setStyleName("ytb-sep-FIXMEEE");
    emptyLabel.addStyleName("oc-tb-sep");
    emptyLabel.addStyleName("oc-floatleft");
    add(emptyLabel);
    return emptyLabel;
  }

  @Override
  public Widget addSpacer() {
    final Label emptyLabel = new Label("");
    emptyLabel.setStyleName("oc-tb-spacer");
    emptyLabel.addStyleName("oc-floatleft");
    add(emptyLabel);
    return emptyLabel;
  }

  @Override
  public void removeAll() {
    clear();
  }

  private void setBasicStyle() {
    setStyleName("x-toolbar-FIXME");
    addStyleName("x-panel-FIXME");
  }

  /**
   * Set the blank style
   */
  @Override
  public void setBlankStyle() {
    setBasicStyle();
    addStyleName("oc-blank-toolbar");
  }

  /**
   * Set the normal grey style
   */
  @Override
  public void setNormalStyle() {
    setBasicStyle();
    addStyleName("oc-tb-bottom-line");
  }

  /**
   * Set the transparent style
   */
  public void setTranspStyle() {
    setBasicStyle();
    addStyleName("oc-transp");
  }

}