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
      iconLeft.setResource(imgRes);
    }
  }

  public IconLabel(final String text) {
    initWidget(uiBinder.createAndBindUi(this));
    label.setText(text);
    label.addStyleName("k-space-nowrap");
    label.addStyleName("k-iconlabel-text");
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

  public void setLabelText(final String text) {
    label.setText(text);
  }

  public void setLeftIcon(final String imgCss) {
    iconLeft.setUrl("images/clear.gif");
    iconLeft.setStyleName(imgCss);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.addStyleName("oc-ico-pad");
  }

  public void setLeftIconResource(final ImageResource res) {
    iconLeft.setResource(res);
    iconLeft.addStyleName("k-iconlabel-left");
  }

  public void setLeftIconUrl(final String url) {
    iconLeft.setUrl(url);
    iconLeft.setSize(DEF, DEF);
    iconLeft.addStyleName("k-iconlabel-left");
  }

  public void setRightIcon(final String imgCss) {
    iconRight.setUrl("images/clear.gif");
    iconRight.setStyleName(imgCss);
    iconRight.addStyleName("oc-ico-pad");
    iconRight.addStyleName("k-iconlabel-right");
  }

  public void setRightIconResource(final ImageResource res) {
    iconRight.setResource(res);
    iconRight.addStyleName("k-iconlabel-right");
  }

  public void setRightIconUrl(final String url) {
    iconRight.setUrl(url);
    iconRight.setSize(DEF, DEF);
    iconRight.addStyleName("k-iconlabel-right");
  }

  @Override
  public void setStyleName(final String style) {
    flow.setStyleName(style);
  }

  @Override
  public void setText(final String text) {
    label.setText(text);
  }

  @Override
  public void setText(final String text, final Direction dir) {
    label.setText(text, dir);
  }

  public void setTooltip(final String text) {
    Tooltip.to(label, text);
  }

  public void setWordWrap(final boolean wordWrap) {
    label.setWordWrap(wordWrap);
  }
}
