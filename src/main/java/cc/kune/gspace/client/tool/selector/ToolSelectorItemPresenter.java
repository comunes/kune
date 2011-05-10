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
package cc.kune.gspace.client.tool.selector;

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public class ToolSelectorItemPresenter implements ToolSelectorItem {
  public interface ToolSelectorItemView extends IsWidget {

    HasClickHandlers getFocus();

    HasText getLabel();

    // void setTheme(WsTheme oldTheme, WsTheme newTheme);

    void setSelected(boolean selected);

    void setVisible(boolean visible);
  }

  private final String longName;
  // private final WsThemeManager wsThemePresenter;
  private final String shortName;
  private StateToken token;
  private final ToolSelector toolSelector;
  private ToolSelectorItemView view;

  public ToolSelectorItemPresenter(final String shortName, final String longName,
      final ToolSelector toolSelector) {
    this.shortName = shortName;
    this.longName = longName;
    this.toolSelector = toolSelector;
    // this.wsThemePresenter = wsThemePresenter;
  }

  @Override
  public String getShortName() {
    return shortName;
  }

  @Override
  public ToolSelectorItemView getView() {
    return view;
  }

  public void init(final ToolSelectorItemView view) {
    this.view = view;
    toolSelector.addTool(this);
    view.getFocus().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        History.newItem(token.toString());
      }
    });
    view.getLabel().setText(longName);
    // wsThemePresenter.addOnThemeChanged(new Listener2<WsTheme, WsTheme>()
    // {
    // public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
    // setTheme(oldTheme, newTheme);
    // }
    // });
  }

  @Override
  public void setGroupShortName(final String groupShortName) {
    token = new StateToken(groupShortName, getShortName(), null, null);
  }

  @Override
  public void setSelected(final boolean selected) {
    view.setSelected(selected);
  }

  // private void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
  // view.setTheme(oldTheme, newTheme);
  // }

  @Override
  public void setVisible(final boolean visible) {
    view.setVisible(visible);
  }
}
