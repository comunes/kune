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
package cc.kune.gspace.client.viewers.items;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class FolderItemWidget.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FolderItemWidget extends Composite implements HasText {

  /**
   * The Interface FolderItemWidgetUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface FolderItemWidgetUiBinder extends UiBinder<Widget, FolderItemWidget> {
  }

  /** The Constant MENU_ID. */
  public static final String MENU_ID = "-menu";

  /** The ui binder. */
  private static FolderItemWidgetUiBinder uiBinder = GWT.create(FolderItemWidgetUiBinder.class);

  /** The flow. */
  @UiField
  FlowPanel flow;

  /** The focus panel. */
  @UiField
  FocusPanel focusPanel;

  /** The icon. */
  @UiField
  Label icon;

  /** The menu. */
  @UiField
  SimplePanel menu;

  /** The modified. */
  @UiField
  InlineLabel modified;

  /** The title. */
  @UiField
  InlineLabel title;

  /** The token. */
  private final StateToken token;

  /**
   * Instantiates a new folder item widget.
   * 
   * @param icon
   *          the icon
   * @param title
   *          the title
   * @param token
   *          the token
   * @param id
   *          the id
   */
  public FolderItemWidget(final KuneIcon icon, final String title, final StateToken token,
      final String id) {
    this.token = token;
    initWidget(uiBinder.createAndBindUi(this));
    this.title.setText(title);
    this.icon.setText(icon.getCharacter().toString());
    this.ensureDebugId(id);
    menu.ensureDebugId(id + MENU_ID);
  }

  /**
   * Clear focus styles.
   */
  private void clearFocusStyles() {
    focusPanel.removeStyleDependentName("nofocused");
    focusPanel.removeStyleDependentName("focused");
  }

  /**
   * Gets the flow.
   * 
   * @return the flow
   */
  public FlowPanel getFlow() {
    return flow;
  }

  /**
   * Gets the icon.
   * 
   * @return the icon
   */
  public Widget getIcon() {
    return icon;
  }

  /**
   * Gets the row click.
   * 
   * @return the row click
   */
  public HasClickHandlers getRowClick() {
    return focusPanel;
  }

  /**
   * Gets the row double click.
   * 
   * @return the row double click
   */
  public HasDoubleClickHandlers getRowDoubleClick() {
    return focusPanel;
  }

  /**
   * Gets the row focus.
   * 
   * @return the row focus
   */
  public HasAllFocusHandlers getRowFocus() {
    return focusPanel;
  }

  /**
   * Gets the row mouse.
   * 
   * @return the row mouse
   */
  public HasAllMouseHandlers getRowMouse() {
    return focusPanel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasText#getText()
   */
  @Override
  public String getText() {
    return title.getText();
  }

  /**
   * Gets the title widget.
   * 
   * @return the title widget
   */
  public Widget getTitleWidget() {
    return title;
  }

  /**
   * Gets the token.
   * 
   * @return the token
   */
  public StateToken getToken() {
    return token;
  }

  /**
   * On blur.
   * 
   * @param event
   *          the event
   */
  @UiHandler("focusPanel")
  public void onBlur(final BlurEvent event) {
    clearFocusStyles();
    focusPanel.addStyleDependentName("nofocused");
  }

  /**
   * On focus.
   * 
   * @param event
   *          the event
   */
  @UiHandler("focusPanel")
  public void onFocus(final FocusEvent event) {
    clearFocusStyles();
    focusPanel.addStyleDependentName("focused");
  }

  /**
   * On out.
   * 
   * @param event
   *          the event
   */
  @UiHandler("focusPanel")
  public void onOut(final MouseOutEvent event) {
    clearFocusStyles();
    focusPanel.addStyleDependentName("nofocused");
  }

  /**
   * On over.
   * 
   * @param event
   *          the event
   */
  @UiHandler("focusPanel")
  public void onOver(final MouseOverEvent event) {
    clearFocusStyles();
    focusPanel.addStyleDependentName("focused");
  }

  /**
   * Sets the menu.
   * 
   * @param toolbar
   *          the new menu
   */
  public void setMenu(final ActionSimplePanel toolbar) {
    menu.add(toolbar);
  }

  /**
   * Sets the menu visible.
   * 
   * @param visible
   *          the new menu visible
   */
  public void setMenuVisible(final boolean visible) {
    menu.getElement().getStyle().setOpacity(visible ? 1d : 0.2d);
  }

  /**
   * Sets the modified text.
   * 
   * @param text
   *          the new modified text
   */
  public void setModifiedText(final String text) {
    modified.setText(text);
  }

  /**
   * Sets the select.
   * 
   * @param selected
   *          the new select
   */
  public void setSelect(final boolean selected) {
    clearFocusStyles();
    focusPanel.removeStyleDependentName(selected ? "noselected" : "selected");
    focusPanel.addStyleDependentName(selected ? "selected" : "noselected");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    title.setText(text);
  }

}
