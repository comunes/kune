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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class GwtLabelGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GwtLabelGui extends AbstractGuiItem {
  
  /** The label. */
  private Label label;

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#addStyle(java.lang.String)
   */
  @Override
  protected void addStyle(final String style) {
    label.addStyleName(style);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#clearStyles()
   */
  @Override
  protected void clearStyles() {
    label.setStyleName("k-none");
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#create(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    label = new Label("");
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    final String id = descriptor.getId();
    if (id != null) {
      label.ensureDebugId(id);
    }
    initWidget(label);
    label.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        final AbstractAction action = descriptor.getAction();
        if (action != null) {
          action.actionPerformed(new ActionEvent(label, getTargetObjectOfAction(descriptor),
              Event.as(event.getNativeEvent())));
        }
      }
    });
    configureItemFromProperties();
    return this;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    super.setVisible(enabled);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(cc.kune.common.shared.res.KuneIcon)
   */
  @Override
  public void setIcon(final KuneIcon icon) {
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java.lang.String)
   */
  @Override
  protected void setIconBackground(final String backgroundColor) {
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang.String)
   */
  @Override
  protected void setIconStyle(final String style) {
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconUrl(java.lang.String)
   */
  @Override
  public void setIconUrl(final String url) {
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    label.setText(text, descriptor.getDirection());
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public void setToolTipText(final String tooltipText) {
    Tooltip.to(label, tooltipText);
  }

  @Override
  public void setVisible(final boolean visible) {
    super.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() {
    return true;
  }

}
