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

import br.com.rpa.client._paperelements.PaperFab;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolSelectorItemPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolSelectorItemPanel implements ToolSelectorItemView {

  /** The Constant TOOL_ID_PREFIX. */
  public static final String TOOL_ID_PREFIX = "k_tool_item_";

  private final PaperFab btn;

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

    btn = PaperFab.wrap(TOOL_ID_PREFIX + shortName);

    setVisibleImpl(false);
    btn.setStylePrimaryName("k-tool-item");
    btn.addMouseOverHandler(new MouseOverHandler() {
      @Override
      public void onMouseOver(final MouseOverEvent event) {
        focus();
      }
    });
    btn.addMouseOutHandler(new MouseOutHandler() {
      @Override
      public void onMouseOut(final MouseOutEvent event) {
        unfocus();
      }
    });
  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.
   * ToolSelectorItemView#getFocus()
   */
  @Override
  public HandlerRegistration addClickHandler(final ClickHandler clickHandler) {
    return btn.addClickHandler(clickHandler);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.google.gwt.user.client.ui.Widget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return btn;
  }

  /**
   * Focus.
   */
  private void focus() {
    btn.addStyleDependentName("focus");
    btn.removeStyleDependentName("nofocus");
  }

  /**
   * Gets the focus panel.
   *
   * @return the focus panel
   */
  public Widget getFocusPanel() {
    return btn;
  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.
   * ToolSelectorItemView#getLabel()
   */
  @Override
  public HasText getLabel() {
    return btn;
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
    return btn;
  }

  @Override
  public IsWidget getWidget() {
    return btn;
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
      btn.addStyleName("active");
      // iconRight.setVisible(true);
    } else {
      btn.removeStyleName("active");
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
    Tooltip.to(btn, tooltip);
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
    btn.setVisible(visible);
  }

  /**
   * Unfocus.
   */
  private void unfocus() {
    btn.addStyleDependentName("nofocus");
    btn.removeStyleDependentName("focus");
  }

}
