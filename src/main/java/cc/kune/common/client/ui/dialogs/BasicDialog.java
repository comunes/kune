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
package cc.kune.common.client.ui.dialogs;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicDialog.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BasicDialog extends Composite implements BasicDialogView {

  /**
   * The Interface BasicDialogUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface BasicDialogUiBinder extends UiBinder<Widget, BasicDialog> {
  }

  /** The ui binder. */
  private static BasicDialogUiBinder uiBinder = GWT.create(BasicDialogUiBinder.class);

  /** The bottom panel. */
  @UiField
  VerticalPanel bottomPanel;

  /** The btn panel. */
  @UiField
  FlowPanel btnPanel;

  /** The close btn. */
  @UiField
  CloseDialogButton closeBtn;
  /** The first btn. */
  @UiField
  Button firstBtn;

  /** The main panel. */
  @UiField
  FlowPanel flow;

  /** The second btn. */
  @UiField
  Button secondBtn;

  /** The title. */
  @UiField
  IconLabel title;

  /** The vp. */
  @UiField
  FlowPanel vp;

  /**
   * Instantiates a new basic dialog.
   */
  public BasicDialog() {
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getBottomPanel()
   */
  @Override
  public ForIsWidget getBottomPanel() {
    return bottomPanel;
  }

  /**
   * Gets the btn panel.
   * 
   * @return the btn panel
   */
  public FlowPanel getBtnPanel() {
    return btnPanel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getCloseBtn()
   */
  @Override
  public HasClickHandlers getCloseBtn() {
    return closeBtn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getFirstBtn()
   */
  @Override
  public HasClickHandlers getFirstBtn() {
    return firstBtn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getInnerPanel()
   */
  @Override
  public ForIsWidget getInnerPanel() {
    return vp;
  }

  @Override
  public ForIsWidget getMainPanel() {
    return flow;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getSecondBtn()
   */
  @Override
  public HasClickHandlers getSecondBtn() {
    return secondBtn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getTitleText()
   */
  @Override
  public HasDirectionalText getTitleText() {
    return title;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setCloseBtnTooltip(java
   * .lang.String)
   */
  @Override
  public void setCloseBtnTooltip(final String tooltip) {
    Tooltip.to(closeBtn, tooltip);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setCloseBtnVisible(boolean
   * )
   */
  @Override
  public void setCloseBtnVisible(final boolean visible) {
    closeBtn.setVisible(visible);
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

  /**
   * Sets the first btn focus.
   */
  public void setFirstBtnFocus() {
    firstBtn.setFocus(true);
  }

  /**
   * Sets the first btn id.
   * 
   * @param id
   *          the new first btn id
   */
  public void setFirstBtnId(final String id) {
    firstBtn.ensureDebugId(id);
  }

  /**
   * Sets the first btn tab index.
   * 
   * @param index
   *          the new first btn tab index
   */
  public void setFirstBtnTabIndex(final int index) {
    firstBtn.setTabIndex(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setFirstBtnText(java.lang
   * .String)
   */
  @Override
  public void setFirstBtnText(final String text) {
    firstBtn.setText(text);
    firstBtn.setVisible(TextUtils.notEmpty(text));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setFirstBtnTitle(java.
   * lang.String)
   */
  @Override
  public void setFirstBtnTitle(final String title) {
    firstBtn.setTitle(title);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setFirstBtnVisible(boolean
   * )
   */
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

  /**
   * Sets the inner width.
   * 
   * @param width
   *          the new inner width
   */
  public void setInnerWidth(final String width) {
    vp.setWidth(width);
  }

  /**
   * Sets the second btn enabled.
   * 
   * @param enabled
   *          the new second btn enabled
   */
  public void setSecondBtnEnabled(final boolean enabled) {
    secondBtn.setEnabled(enabled);
  }

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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnText(java.
   * lang.String)
   */
  @Override
  public void setSecondBtnText(final String text) {
    secondBtn.setText(text);
    secondBtn.setVisible(TextUtils.notEmpty(text));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnTitle(java
   * .lang.String)
   */
  @Override
  public void setSecondBtnTitle(final String title) {
    secondBtn.setTitle(title);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnVisible(boolean
   * )
   */
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
    title.setLeftIconResource(img);
  }

  /**
   * Sets the title icon css
   * 
   * @param icon
   *          the new title icon css
   */
  public void setTitleIcon(final String icon) {
    if (TextUtils.notEmpty(icon)) {
      title.setLeftIcon(icon);
    }
  }

  public void setTitleIconUrl(final String url) {
    if (TextUtils.notEmpty(url)) {
      title.setLeftIconUrl(url);
    }
  }

  /**
   * Sets the title id.
   * 
   * @param id
   *          the new title id
   */
  public void setTitleId(final String id) {
    title.ensureDebugId(id);
  }

}
