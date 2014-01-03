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

// TODO: Auto-generated Javadoc
/**
 * The Class IconLabel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class IconLabel extends Composite implements HasWidgets, HasDirectionalText {

  /**
   * The Interface IconTitleUiBinder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface IconTitleUiBinder extends UiBinder<Widget, IconLabel> {
  }

  /** The Constant DEF. */
  private static final String DEF = "16px";

  /** The ui binder. */
  private static IconTitleUiBinder uiBinder = GWT.create(IconTitleUiBinder.class);

  /** The flow. */
  @UiField
  FlowPanel flow;
  
  /** The icon font left. */
  @UiField
  Label iconFontLeft;
  
  /** The icon font right. */
  @UiField
  Label iconFontRight;
  
  /** The icon left. */
  @UiField
  Image iconLeft;
  
  /** The icon right. */
  @UiField
  Image iconRight;
  
  /** The label. */
  @UiField
  Label label;
  
  /** The self. */
  @UiField
  FocusPanel self;

  /**
   * Instantiates a new icon label.
   */
  public IconLabel() {
    this("");
  }

  /**
   * Instantiates a new icon label.
   *
   * @param imgRes the img res
   * @param text the text
   */
  public IconLabel(final ImageResource imgRes, final String text) {
    this(text);
    if (imgRes != null) {
      setLeftIconResourceImpl(imgRes);
    }
  }

  /**
   * Instantiates a new icon label.
   *
   * @param text the text
   */
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

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public void add(final Widget w) {
    flow.add(w);
  }

  /**
   * Adds the right icon style.
   *
   * @param style the style
   */
  public void addRightIconStyle(final String style) {
    iconRight.addStyleName(style);
  }

  /**
   * Adds the text style name.
   *
   * @param style the style
   */
  public void addTextStyleName(final String style) {
    label.addStyleName(style);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasWidgets#clear()
   */
  @Override
  public void clear() {
    flow.clear();
  }

  /**
   * Common style.
   *
   * @param icon the icon
   * @param imgCss the img css
   */
  private void commonStyle(final Image icon, final String imgCss) {
    setIconClear(icon);
    icon.setStyleName(imgCss);
    icon.addStyleName("oc-ico-pad");
  }

  /**
   * Gets the float from direction.
   *
   * @param direction the direction
   * @return the float from direction
   */
  private String getFloatFromDirection(final Direction direction) {
    return direction.equals(Direction.LTR) || direction.equals(Direction.DEFAULT) ? "k-fl" : "k-fr";
  }

  /**
   * Gets the focus.
   *
   * @return the focus
   */
  public HasClickHandlers getFocus() {
    return self;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasText#getText()
   */
  @Override
  public String getText() {
    return label.getText();
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasDirectionalText#getTextDirection()
   */
  @Override
  public Direction getTextDirection() {
    return label.getTextDirection();
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
   */
  @Override
  public Iterator<Widget> iterator() {
    return flow.iterator();
  }

  /**
   * Opossite.
   *
   * @param direction the direction
   * @return the direction
   */
  private Direction opossite(final Direction direction) {
    return direction.equals(Direction.LTR) || direction.equals(Direction.DEFAULT) ? Direction.RTL
        : Direction.LTR;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public boolean remove(final Widget w) {
    return flow.remove(w);
  }

  /**
   * Sets the icon clear.
   *
   * @param icon the new icon clear
   */
  private void setIconClear(final Image icon) {
    icon.setUrl(GWT.getModuleBaseURL() + "clear.cache.gif");
  }

  /**
   * Sets the icon floats.
   */
  private void setIconFloats() {
    // setting floats again, because with setResource we lost them
    final Direction currentDirection = getTextDirection();
    setIconRTL(iconLeft, currentDirection);
    setIconRTL(iconRight, currentDirection);
    setIconRTL(iconFontLeft, currentDirection);
    setIconRTL(iconFontRight, currentDirection);
  }

  /**
   * Sets the icon rtl.
   *
   * @param widget the widget
   * @param direction the direction
   */
  private void setIconRTL(final Widget widget, final Direction direction) {
    widget.removeStyleName(getFloatFromDirection(opossite(direction)));
    widget.addStyleName(getFloatFromDirection(direction));
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final String id) {
    self.ensureDebugId(id);
  }

  /**
   * Sets the left icon.
   *
   * @param imgCss the new left icon
   */
  public void setLeftIcon(final String imgCss) {
    commonStyle(iconLeft, imgCss);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.addStyleName("k-tcell");
    iconLeft.setVisible(true);
  }

  /**
   * Sets the left icon background.
   *
   * @param backgroundColor the new left icon background
   */
  public void setLeftIconBackground(final String backgroundColor) {
    DOM.setStyleAttribute(iconLeft.getElement(), "backgroundColor", backgroundColor);
  }

  /**
   * Sets the left icon font.
   *
   * @param icon the new left icon font
   */
  public void setLeftIconFont(final KuneIcon icon) {
    iconFontLeft.setText(icon.getCharacter().toString());
    iconFontLeft.setVisible(true);
  }

  /**
   * Sets the left icon resource.
   *
   * @param res the new left icon resource
   */
  public void setLeftIconResource(final ImageResource res) {
    setLeftIconResourceImpl(res);
  }

  /**
   * Sets the left icon resource impl.
   *
   * @param res the new left icon resource impl
   */
  private void setLeftIconResourceImpl(final ImageResource res) {
    iconLeft.setResource(res);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.addStyleName("k-tcell");
    setIconFloats();
    iconLeft.setVisible(true);
  }

  /**
   * Sets the left icon url.
   *
   * @param url the new left icon url
   */
  public void setLeftIconUrl(final String url) {
    iconLeft.setUrl(url);
    iconLeft.setSize(DEF, DEF);
    iconLeft.addStyleName("k-iconlabel-left");
    iconLeft.addStyleName("k-tcell");
    setIconFloats();
    iconLeft.setVisible(true);
  }

  /**
   * Sets the right icon.
   *
   * @param imgCss the new right icon
   */
  public void setRightIcon(final String imgCss) {
    commonStyle(iconRight, imgCss);
    iconRight.addStyleName("k-iconlabel-right");
    iconRight.addStyleName("k-tcell");
    setIconFloats();
    iconRight.setVisible(true);
  }

  /**
   * Sets the right icon background.
   *
   * @param backgroundColor the new right icon background
   */
  public void setRightIconBackground(final String backgroundColor) {
    DOM.setStyleAttribute(iconRight.getElement(), "backgroundColor", backgroundColor);
  }

  /**
   * Sets the right icon font.
   *
   * @param icon the new right icon font
   */
  public void setRightIconFont(final KuneIcon icon) {
    iconFontRight.setText(icon.getCharacter().toString());
    iconFontRight.setVisible(true);
  }

  /**
   * Sets the right icon resource.
   *
   * @param res the new right icon resource
   */
  public void setRightIconResource(final ImageResource res) {
    iconRight.setResource(res);
    iconRight.addStyleName("k-iconlabel-right");
    iconRight.addStyleName("k-tcell");
    setIconFloats();
    iconRight.setVisible(true);
  }

  /**
   * Sets the right icon url.
   *
   * @param url the new right icon url
   */
  public void setRightIconUrl(final String url) {
    iconRight.setUrl(url);
    iconRight.setSize(DEF, DEF);
    iconRight.addStyleName("k-iconlabel-right");
    iconRight.addStyleName("k-tcell");
    setIconFloats();
    iconRight.setVisible(true);
  }

  /**
   * Sets the style from direction.
   *
   * @param direction the new style from direction
   */
  private void setStyleFromDirection(final Direction direction) {
    setStyleName(getFloatFromDirection(direction));
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setStyleName(java.lang.String)
   */
  @Override
  public void setStyleName(final String style) {
    flow.setStyleName(style);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    label.setText(text, Direction.LTR);
    setTextStyle(text);
    setStyleFromDirection(Direction.LTR);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasDirectionalText#setText(java.lang.String, com.google.gwt.i18n.client.HasDirection.Direction)
   */
  @Override
  public void setText(final String text, final Direction dir) {
    label.setText(text, dir);
    setTextStyle(text);
    setStyleFromDirection(Direction.LTR);
  }

  /**
   * Sets the text style.
   *
   * @param text the new text style
   */
  private void setTextStyle(final String text) {
    label.setStyleDependentName("notext", TextUtils.empty(text));
    label.setStyleDependentName("text", !TextUtils.empty(text));
  }

  /**
   * Sets the tooltip.
   *
   * @param text the new tooltip
   */
  public void setTooltip(final String text) {
    Tooltip.to(label, text);
  }

  /**
   * Sets the word wrap.
   *
   * @param wordWrap the new word wrap
   */
  public void setWordWrap(final boolean wordWrap) {
    label.setWordWrap(wordWrap);
  }
}
