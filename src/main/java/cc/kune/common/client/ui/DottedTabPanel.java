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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class DottedTabPanel extends Composite {

  interface DottedTabPanelUiBinder extends UiBinder<Widget, DottedTabPanel> {
  }

  private static DottedTabPanelUiBinder uiBinder = GWT.create(DottedTabPanelUiBinder.class);

  @UiField
  TabLayoutPanel tabPanel;

  public DottedTabPanel(final String width, final String height) {
    initWidget(uiBinder.createAndBindUi(this));
    tabPanel.setSize(width, height);
  }

  public void addTab(final IsWidget view) {
    tabPanel.add(view, new DottedTab());
  }

  public int getWidgetIndex(final IsWidget view) {
    return tabPanel.getWidgetIndex(view.asWidget());
  }

  public void insertTab(final IsWidget view, final int beforeIndex) {
    tabPanel.insert(view.asWidget(), new DottedTab(), beforeIndex);
  }

  public void removeTab(final IsWidget view) {
    tabPanel.remove(view.asWidget());
  }

  public void selectTab(final int index) {
    tabPanel.selectTab(index);
  }
}
