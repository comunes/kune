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
package cc.kune.gspace.client.tool.selector;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolSelectorItemPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolSelectorItemPanel extends Composite implements ToolSelectorItemView {

  /**
   * The Interface ToolSelectorItemPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface ToolSelectorItemPanelUiBinder extends UiBinder<Widget, ToolSelectorItemPanel> {
  }

  /** The Constant TOOL_ID_PREFIX. */
  public static final String TOOL_ID_PREFIX = "k-tool-item-";

  /** The ui binder. */
  private static ToolSelectorItemPanelUiBinder uiBinder = GWT.create(ToolSelectorItemPanelUiBinder.class);

  /** The arrow. */
  @UiField
  HTMLPanel arrow;

  /** The flow. */
  @UiField
  FlowPanel flow;

  /** The icon left. */
  @UiField
  Label iconLeft;

  /** The icon right. */
  @UiField
  Image iconRight;

  /** The label. */
  @UiField
  InlineLabel label;

  /** The self. */
  @UiField
  FocusPanel self;

  /** The short name. */
  private final String shortName;

  /**
   * Instantiates a new tool selector item panel.
   * 
   * @param shortName
   *          the short name
   * @param icon
   *          the icon
   */
  public ToolSelectorItemPanel(final String shortName, final KuneIcon icon) {
    this.shortName = shortName;
    initWidget(uiBinder.createAndBindUi(this));
    ensureDebugId(TOOL_ID_PREFIX + shortName);
    setVisibleImpl(false);
    iconLeft.setText(icon.getCharacter().toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.Widget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return this;
  }

  /**
   * Focus.
   */
  private void focus() {
    self.addStyleDependentName("focus");
    self.removeStyleDependentName("nofocus");
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.
   * ToolSelectorItemView#getFocus()
   */
  @Override
  public HasClickHandlers getFocus() {
    return self;
  }

  /**
   * Gets the focus panel.
   * 
   * @return the focus panel
   */
  public Widget getFocusPanel() {
    return flow;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.
   * ToolSelectorItemView#getLabel()
   */
  @Override
  public HasText getLabel() {
    return label;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return shortName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.
   * ToolSelectorItemView#getTarget()
   */
  @Override
  public Object getTarget() {
    return this;
  }

  /**
   * On self mouse out.
   * 
   * @param event
   *          the event
   */
  @UiHandler("self")
  void onSelfMouseOut(final MouseOutEvent event) {
    unfocus();
  }

  /**
   * On self mouse over.
   * 
   * @param event
   *          the event
   */
  @UiHandler("self")
  void onSelfMouseOver(final MouseOverEvent event) {
    focus();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.
   * ToolSelectorItemView#setSelected(boolean)
   */
  @Override
  public void setSelected(final boolean selected) {
    if (selected) {
      self.addStyleDependentName("selected");
      self.removeStyleDependentName("notselected");
      arrow.setVisible(true);
      // iconRight.setVisible(true);
    } else {
      self.addStyleDependentName("notselected");
      self.removeStyleDependentName("selected");
      iconRight.setVisible(false);
      arrow.setVisible(false);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.
   * ToolSelectorItemView#setTooltip(java.lang.String)
   */
  @Override
  public void setTooltip(final String tooltip) {
    Tooltip.to(this, tooltip);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    setVisibleImpl(visible);
  }

  /**
   * Sets the visible impl.
   * 
   * @param visible
   *          the new visible impl
   */
  private void setVisibleImpl(final boolean visible) {
    self.setVisible(visible);
  }

  /**
   * Unfocus.
   */
  private void unfocus() {
    self.addStyleDependentName("nofocus");
    self.removeStyleDependentName("focus");
  }

}
