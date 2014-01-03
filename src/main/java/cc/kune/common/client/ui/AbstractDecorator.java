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

import cc.kune.common.client.tooltip.Tooltip;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDecorator.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AbstractDecorator extends Composite {

  /**
   * The Interface Binder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface Binder extends UiBinder<Widget, AbstractDecorator> {
  }
  
  /** The Constant BINDER. */
  private static final Binder BINDER = GWT.create(Binder.class);
  
  /** The decoration image. */
  @UiField
  Image decorationImage;
  
  /** The left. */
  private int left = 0;

  /** The main panel. */
  @UiField
  FlowPanel mainPanel;
  
  /** The offset. */
  private int offset = 0;
  
  /** The tooltip. */
  private Tooltip tooltip;
  
  /** The top. */
  private int top = 0;
  
  /** The widget container. */
  @UiField
  SimplePanel widgetContainer;

  /**
   * Instantiates a new abstract decorator.
   */
  public AbstractDecorator() {
    initWidget(BINDER.createAndBindUi(this));
    decorationImage.setVisible(false);
  }

  /**
   * Clear image.
   */
  public void clearImage() {
    decorationImage.setVisible(false);
  }

  /**
   * Sets the decorator visible.
   *
   * @param visible the new decorator visible
   */
  public void setDecoratorVisible(final boolean visible) {
    decorationImage.setVisible(visible);
  }

  /**
   * Sets the image.
   *
   * @param img the new image
   */
  public void setImage(final ImageResource img) {
    decorationImage.setResource(img);
    decorationImage.setVisible(true);
    setPosition();
  }

  /**
   * Sets the image position.
   *
   * @param top the top
   * @param left the left
   * @param offset the offset
   */
  public void setImagePosition(final int top, final int left, final int offset) {
    this.top = top;
    this.left = left;
    this.offset = offset;
    setPosition();
  }

  /**
   * Sets the image tooltip.
   *
   * @param text the new image tooltip
   */
  public void setImageTooltip(final String text) {
    if (tooltip == null) {
      tooltip = Tooltip.to(decorationImage, text);
      decorationImage.addStyleName("k-pointer");
    } else {
      tooltip.setText(text);
    }
  }

  /**
   * Sets the position.
   */
  private void setPosition() {
    final Element elem = decorationImage.getElement();
    elem.getStyle().setPropertyPx("left", left);
    elem.getStyle().setPropertyPx("top", top);
    elem.getStyle().setPosition(Position.RELATIVE);
    elem.getStyle().setFloat(Float.LEFT);
    elem.getStyle().setMarginRight(offset, Unit.PX);
  }

  /**
   * Sets the widget.
   *
   * @param widget the new widget
   */
  public void setWidget(final IsWidget widget) {
    widgetContainer.clear();
    widgetContainer.add(widget);
  }

}
