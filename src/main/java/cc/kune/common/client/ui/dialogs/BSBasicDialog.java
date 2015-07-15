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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AttachDetachException;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.utils.TextUtils;

public class BSBasicDialog extends Composite implements BasicDialogView, HasCloseHandlers<BSBasicDialog> {

  interface BSBasicDialogUiBinder extends UiBinder<Widget, BSBasicDialog> {
  }

  private static BSBasicDialogUiBinder uiBinder = GWT.create(BSBasicDialogUiBinder.class);

  @UiField
  FlowPanel bottomPanel;
  @UiField
  FlowPanel btnPanel;
  @UiField
  Button firstBtn;
  @UiField
  Modal modal;
  @UiField
  CustomModalHeader modalHeader;
  @UiField
  Button secondBtn;

  private HasDirectionalText title;

  @UiField
  FlowPanel vp;

  public BSBasicDialog() {
    initWidget(uiBinder.createAndBindUi(this));
    this.fireEvent(null);

    title = new HasDirectionalText() {

      @Override
      public String getText() {
        return modal.getTitle();
      }

      @Override
      public Direction getTextDirection() {
        // TODO
        return Direction.LTR;
      }

      @Override
      public void setText(final String text) {
        modal.setTitle(text);
      }

      @Override
      public void setText(final String text, final Direction dir) {
        // TODO
        modal.setTitle(text);
      }
    };

  }

  @Override
  public HandlerRegistration addCloseHandler(final CloseHandler<BSBasicDialog> handler) {
   return modalHeader.addHandler(handler, CloseEvent.getType());
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

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.ui.dialogs.BasicDialogView#getInnerPanel()
   */

  @Override
  public FlowPanel getBtnPanel() {
    return btnPanel;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setCloseBtnTooltip(java
   * .lang.String)
   */

  @Override
  public HasClickHandlers getFirstBtn() {
    return firstBtn;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setCloseBtnVisible(boolean
   * )
   */

  @Override
  public ForIsWidget getInnerPanel() {
    return vp;
  }

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
    return title;
  }

  public void hide() {
    modal.hide();
  }

  @Override
  public void setCloseBtnTooltip(final String tooltip) {
    Tooltip.to(modalHeader.getCloseButton(), tooltip);
  }

  @Override
  public void setCloseBtnVisible(final boolean visible) {
    modalHeader.setClosable(visible);
  }

  public void setDialogHeight(final String height) {
    modal.setHeight(height + "px");
    if (height.contains("px")) {
      modal.getElement().getStyle().setHeight(Double.parseDouble(height.replace("px", "") + 20), Unit.PX);
    } else if (height.contains("%")) {
      modal.getElement().getStyle().setHeight(Double.parseDouble(height.replace("%", "")), Unit.PCT);
    }
  }

  public void setDialogWidth(final String width) {
    modal.setWidth(width);
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

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnText(java.
   * lang.String)
   */

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

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnTitle(java
   * .lang.String)
   */

  /**
   * Sets the inner height.
   *
   * @param height
   *          the new inner height
   */
  public void setInnerHeight(final String height) {
    Log.info("set height: " + height);
    vp.setHeight(height);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.ui.dialogs.BasicDialogView#setSecondBtnVisible(boolean
   * )
   */

  /**
   * Sets the inner width.
   *
   * @param width
   *          the new inner width
   */
  public void setInnerWidth(final String width) {
    Log.info("set width: " + width);
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
    modalHeader.setIconResource(img);
  }

  /**
   * Sets the title icon css
   *
   * @param icon
   *          the new title icon css
   */
  public void setTitleIcon(final String icon) {
    if (TextUtils.notEmpty(icon)) {
      modalHeader.setIconStyle(icon);
    }
  }

  public void setTitleIconUrl(final String url) {
    if (TextUtils.notEmpty(url)) {
      modalHeader.setIconUrl(url);
    }
  }

  /**
   * Sets the title id.
   *
   * @param id
   *          the new title id
   */
  public void setTitleId(final String id) {
    modalHeader.ensureDebugId(id);
  }

  public void setTitleText(final String title) {
    modal.setTitle(title);
  }

  public void show() {
    try {
      if (!this.isAttached()) {
        RootPanel.get().add(this);
      }
      modal.show();
    } catch (AttachDetachException e) {
      // This happens mainly in FF
      Log.debug("Attach modal fails");
    }
  }
}
