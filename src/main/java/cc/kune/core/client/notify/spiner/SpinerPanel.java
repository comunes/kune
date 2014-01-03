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
package cc.kune.core.client.notify.spiner;

import cc.kune.core.client.notify.spiner.SpinerPresenter.SpinerView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.UiHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class SpinerPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SpinerPanel extends PopupViewWithUiHandlers<UiHandlers> implements SpinerView {

  /**
   * The Interface SpinerPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface SpinerPanelUiBinder extends UiBinder<Widget, SpinerPanel> {
  }

  /** The ui binder. */
  private static SpinerPanelUiBinder uiBinder = GWT.create(SpinerPanelUiBinder.class);

  /** The img. */
  @UiField
  Image img;

  /** The label. */
  @UiField
  InlineLabel label;

  /** The panel. */
  @UiField
  HorizontalPanel panel;

  /** The popup. */
  private final PopupPanel popup;

  /** The widget. */
  Widget widget;

  /**
   * Instantiates a new spiner panel.
   * 
   * @param eventBus
   *          the event bus
   */
  @Inject
  public SpinerPanel(final EventBus eventBus) {
    super(eventBus);
    widget = uiBinder.createAndBindUi(this);
    popup = new PopupPanel(false, false);
    popup.add(widget);
    popup.setStyleName("k-spiner-popup");
    show("");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return popup;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.notify.spiner.SpinerPresenter.SpinerView#fade()
   */
  @Override
  public void fade() {
    popup.hide();
  }

  /**
   * Sets the center position and show.
   */
  protected void setCenterPositionAndShow() {
    popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      @Override
      public void setPosition(final int offsetWidth, final int offsetHeight) {
        final int x = (Window.getClientWidth() - (label != null ? label.getOffsetWidth() : 0)) / 2;
        final int y = 0;
        popup.setPopupPosition(x, y);
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.notify.spiner.SpinerPresenter.SpinerView#show(java.
   * lang.String)
   */
  @Override
  public void show(final String message) {
    if (message == null || message.isEmpty()) {
      label.setText("");
    } else {
      label.setText(message);
    }
    setCenterPositionAndShow();
  }

}
