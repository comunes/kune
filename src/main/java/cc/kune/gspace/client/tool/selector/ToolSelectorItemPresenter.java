/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
 \*/
package cc.kune.gspace.client.tool.selector;

import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public class ToolSelectorItemPresenter implements ToolSelectorItem {
  public interface ToolSelectorItemView extends IsWidget {

    HasClickHandlers getFocus();

    HasText getLabel();

    Object getTarget();

    void setSelected(boolean selected);

    void setTooltip(String tooltip);

    void setVisible(boolean visible);
  }

  private final String longName;
  private final String shortName;
  private StateToken token;
  private final ToolSelector toolSelector;
  private final String tooltip;
  private ToolSelectorItemView view;
  private final AccessRolDTO visibleFoRol;
  private final HistoryWrapper history;

  public ToolSelectorItemPresenter(final String shortName, final String longName, final String tooltip,
      final AccessRolDTO visibleForRol, final ToolSelector toolSelector, HistoryWrapper history) {
    this.shortName = shortName;
    this.longName = longName;
    this.tooltip = tooltip;
    this.visibleFoRol = visibleForRol;
    this.toolSelector = toolSelector;
    this.history = history;
  }

  @Override
  public String getToolShortName() {
    return shortName;
  }

  @Override
  public ToolSelectorItemView getView() {
    return view;
  }

  @Override
  public AccessRolDTO getVisibleForRol() {
    return visibleFoRol;
  }

  public void init(final ToolSelectorItemView view) {
    this.view = view;
    toolSelector.addTool(this);
    view.getFocus().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        history.newItem(token);
      }
    });
    view.getLabel().setText(longName);
    view.setTooltip(tooltip);
  }

  @Override
  public void setGroupShortName(final String groupShortName) {
    token = new StateToken(groupShortName, getToolShortName(), null, null);
  }

  @Override
  public void setSelected(final boolean selected) {
    view.setSelected(selected);
  }

  @Override
  public void setToken(final StateToken token) {
    this.token = token;
  }

  @Override
  public void setVisible(final boolean visible) {
    view.setVisible(visible);
  }
}
