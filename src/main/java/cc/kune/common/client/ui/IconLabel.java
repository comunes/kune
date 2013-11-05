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

import java.util.Iterator;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class IconLabel extends Composite implements HasWidgets, HasDirectionalText {

  interface IconTitleUiBinder extends UiBinder<Widget, IconLabel> {
  }

  private static final String DEF = "16px";

  private static IconTitleUiBinder uiBinder = GWT.create(IconTitleUiBinder.class);

  @UiField
  FlowPanel flow;
  @UiField
  Label iconFontLeft;
  @UiField
  Label iconFontRight;
  @UiField
  Image iconLeft;
  @UiField
  Image iconRight;
  @UiField
  Label label;
  @UiField
  FocusPanel self;

  public IconLabel() {
    this("");
  }

  public IconLabel(final ImageResource imgRes, final String text) {
    this(text);
    if (imgRes != null) {
      setLeftIconResourceImpl(imgRes);
    }
  }

  public IconLabel(final String text) {
    initWidget(uiBinder.createAndBindUi(this));
    setIconClear(iconLeft);
    setIconClear(iconRight);
    iconFontLeft.setVisible(false);
    iconFontRight.setVisible(false);
    setIconFloats();
    iconRight.setUrl(GWT.getModuleBaseURL() + "clear.cache.gif");
    label.setText(text);
    label.addStyleName("k-space-nowrap");
    label.setStylePrimaryName("k-iconlabel");
    setTextStyle(text);
    setStyleFromDirection(Direction.LTR);
  }

  @Override
  public void add(final Widget w) {
    flow.add(w);
  }

  public void addRightIconStyle(final String style) {
    iconRight.addStyleName(style);
  }

  public void addTextStyleName(final String style) {
    label.addStyleName(style);
  }

  @Override
  public void clear() {
    flow.clear();
  }

  private void commonStyle(final Image icon, final String imgCss) {
    setIconClear(icon);
    icon.setStyleName(imgCss);
    icon.addStyleName("oc-ico-pad");
  }

  private String getFloatFromDirection(final Direction direction) {
    return direction.equals(Direction.LTR) || direction.equals(Direction.DEFAULT) ? "k-fl" : "k-fr";
  }

  public HasClickHandlers getFocus() {
    return self;
  }

  @Override
  public String getText() {
    return label.getText();
  }

  @Override
  public Direction getTextDirection() {
    return label.getTextDirection();
  }

  @Override
  public Iterator<Widget> iterator() {
    return flow.iterator();
  }

  private Direction opossite(final Direction direction) {
    return direction.equals(Direction.LTR) || direction.equals(Direction.DEFAULT) ? Direction.RTL
        : Direction.LTR;
  }

  @Override
  public boolean remove(final Widget w) {
    return flow.remove(w);
  }

  private void setIconClear(final Image icon) {
    icon.setUrl(GWT.getModuleBaseURL() + "clear.cache.gif");
  }

  private void setIconFloats() {
    // setting floats again, because with setResource we lost them
    final Direction currentDirection = getTextDirection();
    setIconRTL(iconLeft, currentDirection);
    setIconRTL(iconRight, currentDirection);
    setIconRTL(iconFontLeft, currentDirection);
    setIconRTL(iconFontRight, currentDirection);
  }

  private void setIconRTL(final Widget widget, final Direction direction) {
    widget.removeStyleName(getFloatFromDirection(opossite(direction)));
    widget.addStyleName(getFloatFromDirection(direction));
  }

  public void setId(final String id) {
    self.ensureDebugId(id);
  }

  public void setLeftIcon(final String imgCss) {
    commonStyle(iconLeft, imgCss);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.addStyleName("k-tcell");
    iconLeft.setVisible(true);
  }

  public void setLeftIconBackground(final String backgroundColor) {
    DOM.setStyleAttribute(iconLeft.getElement(), "backgroundColor", backgroundColor);
  }

  public void setLeftIconFont(final KuneIcon icon) {
    iconFontLeft.setText(icon.getCharacter().toString());
    iconFontLeft.setVisible(true);
  }

  public void setLeftIconResource(final ImageResource res) {
    setLeftIconResourceImpl(res);
  }

  private void setLeftIconResourceImpl(final ImageResource res) {
    iconLeft.setResource(res);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.addStyleName("k-tcell");
    setIconFloats();
    iconLeft.setVisible(true);
  }

  public void setLeftIconUrl(final String url) {
    iconLeft.setUrl(url);
    iconLeft.setSize(DEF, DEF);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.addStyleName("k-tcell");
    setIconFloats();
    iconLeft.setVisible(true);
  }

  public void setRightIcon(final String imgCss) {
    commonStyle(iconRight, imgCss);
    iconRight.addStyleName("k-iconlabel-right");
    iconRight.addStyleName("k-tcell");
    setIconFloats();
    iconRight.setVisible(true);
  }

  public void setRightIconBackground(final String backgroundColor) {
    DOM.setStyleAttribute(iconRight.getElement(), "backgroundColor", backgroundColor);
  }

  public void setRightIconFont(final KuneIcon icon) {
    iconFontRight.setText(icon.getCharacter().toString());
    iconFontRight.setVisible(true);
  }

  public void setRightIconResource(final ImageResource res) {
    iconRight.setResource(res);
    iconRight.addStyleName("k-iconlabel-right");
    iconRight.addStyleName("k-tcell");
    setIconFloats();
    iconRight.setVisible(true);
  }

  public void setRightIconUrl(final String url) {
    iconRight.setUrl(url);
    iconRight.setSize(DEF, DEF);
    iconRight.addStyleName("k-iconlabel-right");
    iconRight.addStyleName("k-tcell");
    setIconFloats();
    iconRight.setVisible(true);
  }

  private void setStyleFromDirection(final Direction direction) {
    setStyleName(getFloatFromDirection(direction));
  }

  @Override
  public void setStyleName(final String style) {
    flow.setStyleName(style);
  }

  @Override
  public void setText(final String text) {
    label.setText(text, Direction.LTR);
    setTextStyle(text);
    setStyleFromDirection(Direction.LTR);
  }

  @Override
  public void setText(final String text, final Direction dir) {
    label.setText(text, dir);
    setTextStyle(text);
    setStyleFromDirection(Direction.LTR);
  }

  private void setTextStyle(final String text) {
    label.setStyleDependentName("notext", TextUtils.empty(text));
    label.setStyleDependentName("text", !TextUtils.empty(text));
  }

  public void setTooltip(final String text) {
    Tooltip.to(label, text);
  }

  public void setWordWrap(final boolean wordWrap) {
    label.setWordWrap(wordWrap);
  }
}
