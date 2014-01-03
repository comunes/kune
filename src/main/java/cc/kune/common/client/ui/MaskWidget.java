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
package cc.kune.common.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class MaskWidget.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MaskWidget extends PopupPanel implements MaskWidgetView {

  /**
   * The Interface MaskWidgetUiBinder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface MaskWidgetUiBinder extends UiBinder<Widget, MaskWidget> {
  }
  
  /** The ui binder. */
  private static MaskWidgetUiBinder uiBinder = GWT.create(MaskWidgetUiBinder.class);
  
  /** The flow. */
  @UiField
  FlowPanel flow;
  
  /** The icon. */
  @UiField
  Image icon;
  
  /** The label. */
  @UiField
  Label label;

  /** The main panel. */
  @UiField
  SimplePanel mainPanel;

  /**
   * Instantiates a new mask widget.
   */
  public MaskWidget() {
    super(false, false);
    add(uiBinder.createAndBindUi(this));
    setStyleName("k-mask");
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.PopupPanel#isShowing()
   */
  @Override
  public boolean isShowing() {
    return super.isShowing();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.MaskWidgetView#mask(com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public void mask(final IsWidget widget) {
    mask(widget, "");
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.MaskWidgetView#mask(com.google.gwt.user.client.ui.IsWidget, java.lang.String)
   */
  @Override
  public void mask(final IsWidget widget, final String message) {
    label.setText(message);
    setPopupPositionAndShow(new PositionCallback() {
      @Override
      public void setPosition(final int offsetWidth, final int offsetHeight) {
        final Widget asWidget = widget.asWidget();
        final int w = asWidget.getOffsetWidth();
        final int h = asWidget.getOffsetHeight();
        MaskWidget.this.setPopupPosition(asWidget.getAbsoluteLeft(), asWidget.getAbsoluteTop());
        getElement().getStyle().setWidth(w, Unit.PX);
        getElement().getStyle().setHeight(h, Unit.PX);
        flow.getElement().getStyle().setTop((h - flow.getOffsetHeight()) / 2d, Unit.PX);
        flow.getElement().getStyle().setLeft((w - flow.getOffsetWidth()) / 2d, Unit.PX);
      }
    });
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.MaskWidgetView#unMask()
   */
  @Override
  public void unMask() {
    hide();
  }

}
