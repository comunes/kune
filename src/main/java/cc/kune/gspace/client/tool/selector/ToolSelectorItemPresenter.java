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

// TODO: Auto-generated Javadoc
/**
 * The Class ToolSelectorItemPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolSelectorItemPresenter implements ToolSelectorItem {

  /**
   * The Interface ToolSelectorItemView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ToolSelectorItemView extends IsWidget {

    /**
     * Gets the focus.
     * 
     * @return the focus
     */
    HasClickHandlers getFocus();

    /**
     * Gets the label.
     * 
     * @return the label
     */
    HasText getLabel();

    /**
     * Gets the target.
     * 
     * @return the target
     */
    Object getTarget();

    /**
     * Sets the selected.
     * 
     * @param selected
     *          the new selected
     */
    void setSelected(boolean selected);

    /**
     * Sets the tooltip.
     * 
     * @param tooltip
     *          the new tooltip
     */
    void setTooltip(String tooltip);

    /**
     * Sets the visible.
     * 
     * @param visible
     *          the new visible
     */
    void setVisible(boolean visible);
  }

  /** The history. */
  private final HistoryWrapper history;

  /** The long name. */
  private final String longName;

  /** The short name. */
  private final String shortName;

  /** The token. */
  private StateToken token;

  /** The tool selector. */
  private final ToolSelector toolSelector;

  /** The tooltip. */
  private final String tooltip;

  /** The view. */
  private ToolSelectorItemView view;

  /** The visible fo rol. */
  private final AccessRolDTO visibleFoRol;

  /**
   * Instantiates a new tool selector item presenter.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param tooltip
   *          the tooltip
   * @param visibleForRol
   *          the visible for rol
   * @param toolSelector
   *          the tool selector
   * @param history
   *          the history
   */
  public ToolSelectorItemPresenter(final String shortName, final String longName, final String tooltip,
      final AccessRolDTO visibleForRol, final ToolSelector toolSelector, final HistoryWrapper history) {
    this.shortName = shortName;
    this.longName = longName;
    this.tooltip = tooltip;
    this.visibleFoRol = visibleForRol;
    this.toolSelector = toolSelector;
    this.history = history;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.selector.ToolSelectorItem#getToolShortName()
   */
  @Override
  public String getToolShortName() {
    return shortName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.selector.ToolSelectorItem#getView()
   */
  @Override
  public ToolSelectorItemView getView() {
    return view;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.selector.ToolSelectorItem#getVisibleForRol()
   */
  @Override
  public AccessRolDTO getVisibleForRol() {
    return visibleFoRol;
  }

  /**
   * Inits the.
   * 
   * @param view
   *          the view
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.selector.ToolSelectorItem#setGroupShortName(
   * java.lang.String)
   */
  @Override
  public void setGroupShortName(final String groupShortName) {
    token = new StateToken(groupShortName, getToolShortName(), null, null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.selector.ToolSelectorItem#setSelected(boolean)
   */
  @Override
  public void setSelected(final boolean selected) {
    view.setSelected(selected);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.selector.ToolSelectorItem#setToken(cc.kune.core
   * .shared.domain.utils.StateToken)
   */
  @Override
  public void setToken(final StateToken token) {
    this.token = token;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.selector.ToolSelectorItem#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    view.setVisible(visible);
  }
}
