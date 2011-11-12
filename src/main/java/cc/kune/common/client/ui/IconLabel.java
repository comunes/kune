/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
    label.setText(text);
    label.addStyleName("k-space-nowrap");
    label.addStyleName("k-iconlabel-text");
    iconLeft.setVisible(false);
    iconRight.setVisible(false);
  }

  @Override
  public void add(final Widget w) {
    flow.add(w);
  }

  public void addRightIconStyle(final String style) {
    iconRight.addStyleName(style);
  }

  @Override
  public void addStyleName(final String style) {
    flow.addStyleName(style);
  }

  public void addTextStyleName(final String style) {
    label.addStyleName(style);
  }

  @Override
  public void clear() {
    flow.clear();
  }

  private void commonStyle(final Image icon, final String imgCss) {
    icon.setUrl(GWT.getModuleBaseURL() + "images/clear.gif");
    icon.setStyleName(imgCss);
    icon.addStyleName("oc-ico-pad");
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

  @Override
  public boolean remove(final Widget w) {
    return flow.remove(w);
  }

  // @Deprecated
  // private void setDirection(final Direction dir) {
  // setIconRTL(iconLeft, dir);
  // setIconRTL(iconRight, dir);
  // label.addStyleName(dir.equals(Direction.LTR) ? "k-fr" : "k-fl");
  // }
  //
  @Deprecated
  private void setIconRTL(final Widget widget, final Direction direction) {
    widget.addStyleName(direction.equals(Direction.LTR) ? "k-fl" : "k-fr");
  }

  public void setId(final String id) {
    self.ensureDebugId(id);
  }

  public void setLeftIcon(final String imgCss) {
    commonStyle(iconLeft, imgCss);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.setVisible(true);
  }

  public void setLeftIconBackground(final String backgroundColor) {
    DOM.setStyleAttribute(iconLeft.getElement(), "backgroundColor", backgroundColor);
  }

  public void setLeftIconResource(final ImageResource res) {
    setLeftIconResourceImpl(res);
  }

  private void setLeftIconResourceImpl(final ImageResource res) {
    iconLeft.setResource(res);
    iconLeft.addStyleName("k-iconlabel-left");
    // setting floats again, because with setResource we lost them
    setIconRTL(iconLeft, getTextDirection());
    iconLeft.setVisible(true);
  }

  public void setLeftIconUrl(final String url) {
    iconLeft.setUrl(url);
    iconLeft.setSize(DEF, DEF);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.setVisible(true);
  }

  public void setRightIcon(final String imgCss) {
    commonStyle(iconRight, imgCss);
    iconRight.addStyleName("k-iconlabel-right");
    iconRight.setVisible(true);
  }

  public void setRightIconBackground(final String backgroundColor) {
    DOM.setStyleAttribute(iconRight.getElement(), "backgroundColor", backgroundColor);
  }

  public void setRightIconResource(final ImageResource res) {
    iconRight.setResource(res);
    iconRight.addStyleName("k-iconlabel-right");
    // setting floats again, because with setResource we lost them
    setIconRTL(iconRight, getTextDirection());
    iconRight.setVisible(true);
  }

  public void setRightIconUrl(final String url) {
    iconRight.setUrl(url);
    iconRight.setSize(DEF, DEF);
    iconRight.addStyleName("k-iconlabel-right");
    iconRight.setVisible(true);
  }

  @Override
  public void setStyleName(final String style) {
    flow.setStyleName(style);
  }

  @Override
  public void setText(final String text) {
    // setDirection(Direction.LTR);
    label.setText(text, Direction.LTR);
  }

  @Override
  public void setText(final String text, final Direction dir) {
    // setDirection(dir);
    label.setText(text, dir);
  }

  public void setTooltip(final String text) {
    Tooltip.to(label, text);
  }

  public void setWordWrap(final boolean wordWrap) {
    label.setWordWrap(wordWrap);
  }
}
