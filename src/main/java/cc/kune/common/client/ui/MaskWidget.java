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

public class MaskWidget extends PopupPanel implements MaskWidgetView {

  interface MaskWidgetUiBinder extends UiBinder<Widget, MaskWidget> {
  }
  private static MaskWidgetUiBinder uiBinder = GWT.create(MaskWidgetUiBinder.class);
  @UiField
  FlowPanel flow;
  @UiField
  Image icon;
  @UiField
  Label label;

  @UiField
  SimplePanel mainPanel;

  public MaskWidget() {
    super(false, false);
    add(uiBinder.createAndBindUi(this));
    setStyleName("k-mask");
  }

  @Override
  public boolean isShowing() {
    return super.isShowing();
  }

  @Override
  public void mask(final IsWidget widget) {
    mask(widget, "");
  }

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

  @Override
  public void unMask() {
    hide();
  }

}
