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

package cc.kune.common.client.ui.dialogs;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class BSBasicDialog extends Composite implements BasicDialogView {

  interface BSBasicDialogUiBinder extends UiBinder<Widget, BSBasicDialog> {
  }

  private static BSBasicDialogUiBinder uiBinder = GWT.create(BSBasicDialogUiBinder.class);

  @UiField
  FlowPanel bottomPanel;
  @UiField
  Button firstBtn;
  @UiField
  Modal modal;
  @UiField
  Button secondBtn;
  @UiField
  FlowPanel vp;

  public BSBasicDialog() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  /**
   * Ensure debug id first btn.
   *
   * @param id
   *          the id
   */
  public void ensureDebugIdFirstBtn(final String id) {
    if (id != null && id.length() > 0) {
      firstBtn.ensureDebugId(id);
    }
  }

  /**
   * Ensure debug id second btn.
   *
   * @param id
   *          the id
   */
  public void ensureDebugIdSecondBtn(final String id) {
    if (id != null && id.length() > 0) {
      secondBtn.ensureDebugId(id);
    }
  }

  @Override
  public ForIsWidget getBottomPanel() {
    return bottomPanel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getFirstBtn()
   */

  @Override
  public HasClickHandlers getCloseBtn() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getInnerPanel()
   */

  @Override
  public HasClickHandlers getFirstBtn() {
    return firstBtn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setCloseBtnTooltip(java
   * .lang.String)
   */

  @Override
  public ForIsWidget getInnerPanel() {
    return vp;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setCloseBtnVisible(boolean
   * )
   */

  @Override
  public ForIsWidget getMainPanel() {
    return vp;
  }

  @Override
  public HasClickHandlers getSecondBtn() {
    return secondBtn;
  }

  @Override
  public HasDirectionalText getTitleText() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setCloseBtnTooltip(final String tooltip) {
    Tooltip.to(secondBtn, tooltip);
  }

  @Override
  public void setCloseBtnVisible(final boolean visible) {
    secondBtn.setVisible(visible);
  }

  /**
   * Sets the first btn enabled.
   *
   * @param enabled
   *          the new first btn enabled
   */
  public void setFirstBtnEnabled(final boolean enabled) {
    firstBtn.setEnabled(enabled);
  }

  public void setFirstBtnFocus() {
    firstBtn.setFocus(true);
  }

  public void setFirstBtnId(final String id) {
    firstBtn.ensureDebugId(id);
  }

  public void setFirstBtnTabIndex(final int index) {
    firstBtn.setTabIndex(index);
  }

  @Override
  public void setFirstBtnText(final String text) {
    firstBtn.setText(text);
    firstBtn.setVisible(TextUtils.notEmpty(text));
  }

  @Override
  public void setFirstBtnTitle(final String title) {
    firstBtn.setTitle(title);
  }

  @Override
  public void setFirstBtnVisible(final boolean visible) {
    firstBtn.setVisible(visible);
  }

  /**
   * Sets the inner height.
   *
   * @param height
   *          the new inner height
   */
  public void setInnerHeight(final String height) {
    vp.setHeight(height);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnText(java.
   * lang.String)
   */

  /**
   * Sets the inner width.
   *
   * @param width
   *          the new inner width
   */
  public void setInnerWidth(final String width) {
    vp.setWidth(width);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnTitle(java
   * .lang.String)
   */

  /**
   * Sets the second btn enabled.
   *
   * @param enabled
   *          the new second btn enabled
   */
  public void setSecondBtnEnabled(final boolean enabled) {
    secondBtn.setEnabled(enabled);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnVisible(boolean
   * )
   */

  /**
   * Sets the second btn id.
   *
   * @param id
   *          the new second btn id
   */
  public void setSecondBtnId(final String id) {
    secondBtn.ensureDebugId(id);
  }

  /**
   * Sets the second btn tab index.
   *
   * @param index
   *          the new second btn tab index
   */
  public void setSecondBtnTabIndex(final int index) {
    secondBtn.setTabIndex(index);
  }

  @Override
  public void setSecondBtnText(final String text) {
    secondBtn.setText(text);
    secondBtn.setVisible(TextUtils.notEmpty(text));
  }

  @Override
  public void setSecondBtnTitle(final String title) {
    secondBtn.setTitle(title);
  }

  @Override
  public void setSecondBtnVisible(final boolean visible) {
    secondBtn.setVisible(visible);
  }

  /**
   * Sets the title icon.
   *
   * @param img
   *          the new title icon
   */
  public void setTitleIcon(final ImageResource img) {
    // title.setLeftIconResource(img);
  }

  /**
   * Sets the title icon css
   *
   * @param icon
   *          the new title icon css
   */
  public void setTitleIcon(final String icon) {
    if (TextUtils.notEmpty(icon)) {
      // title.setLeftIcon(icon);
    }
  }

  public void setTitleIconUrl(final String url) {
    if (TextUtils.notEmpty(url)) {
      // title.setLeftIconUrl(url);
    }
  }

  /**
   * Sets the title id.
   *
   * @param id
   *          the new title id
   */
  public void setTitleId(final String id) {
    // title.ensureDebugId(id);
  }

  public void setTitleText(final String title) {
    modal.setTitle(title);
  }

  public void show() {
    if (!modal.isAttached()) {
      RootPanel.get().add(this);
    }
    modal.show();
  }

}
