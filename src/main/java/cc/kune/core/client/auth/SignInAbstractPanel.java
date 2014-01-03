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
package cc.kune.core.client.auth;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder;
import cc.kune.common.client.ui.dialogs.MessageToolbar;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SignInAbstractPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class SignInAbstractPanel extends ViewImpl {

  /** The Constant DEF_SIGN_IN_FORM_SIZE. */
  public static final int DEF_SIGN_IN_FORM_SIZE = 340;

  /** The dialog. */
  private final BasicTopDialog dialog;

  /** The error label id. */
  protected final String errorLabelId;

  /** The i18n. */
  protected final I18nTranslationService i18n;

  /** The images. */
  protected final NotifyLevelImages images;

  /** The mask. */
  private final MaskWidgetView mask;

  /** The message error bar. */
  protected MessageToolbar messageErrorBar;

  /**
   * Instantiates a new sign in abstract panel.
   * 
   * @param dialogId
   *          the dialog id
   * @param mask
   *          the mask
   * @param i18n
   *          the i18n
   * @param title
   *          the title
   * @param autohide
   *          the autohide
   * @param modal
   *          the modal
   * @param autoscroll
   *          the autoscroll
   * @param icon
   *          the icon
   * @param firstButtonTitle
   *          the first button title
   * @param firstButtonId
   *          the first button id
   * @param cancelButtonTitle
   *          the cancel button title
   * @param cancelButtonId
   *          the cancel button id
   * @param images
   *          the images
   * @param errorLabelId
   *          the error label id
   * @param tabIndexStart
   *          the tab index start
   */
  public SignInAbstractPanel(final String dialogId, final MaskWidgetView mask,
      final I18nTranslationService i18n, final String title, final boolean autohide,
      final boolean modal, final boolean autoscroll, final String icon, final String firstButtonTitle,
      final String firstButtonId, final String cancelButtonTitle, final String cancelButtonId,
      final NotifyLevelImages images, final String errorLabelId, final int tabIndexStart) {

    final Builder builder = new BasicTopDialog.Builder(dialogId, autohide, modal, i18n.getDirection()).autoscroll(
        autoscroll).title(title);
    builder.icon(icon);
    builder.firstButtonTitle(firstButtonTitle).firstButtonId(firstButtonId);
    builder.sndButtonTitle(cancelButtonTitle).sndButtonId(cancelButtonId);
    builder.tabIndexStart(tabIndexStart);
    dialog = builder.build();
    this.i18n = i18n;
    this.images = images;
    this.errorLabelId = errorLabelId;
    this.mask = mask;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return dialog;
  }

  /**
   * Gets the close.
   * 
   * @return the close
   */
  public HasCloseHandlers<PopupPanel> getClose() {
    return dialog.getClose();
  }

  /**
   * Gets the first btn.
   * 
   * @return the first btn
   */
  public HasClickHandlers getFirstBtn() {
    return dialog.getFirstBtn();
  }

  /**
   * Gets the inner panel.
   * 
   * @return the inner panel
   */
  public ForIsWidget getInnerPanel() {
    return dialog.getInnerPanel();
  }

  /**
   * Gets the second btn.
   * 
   * @return the second btn
   */
  public HasClickHandlers getSecondBtn() {
    return dialog.getSecondBtn();
  }

  /**
   * Hide.
   */
  public void hide() {
    if (dialog.isVisible()) {
      dialog.hide();
    }
  }

  /**
   * Hide messages.
   */
  public void hideMessages() {
    messageErrorBar.hideErrorMessage();
  }

  /**
   * Mask.
   * 
   * @param message
   *          the message
   */
  public void mask(final String message) {
    mask.mask(dialog);
  }

  /**
   * Mask processing.
   */
  public void maskProcessing() {
    mask.mask(dialog, i18n.t("Processing"));
  }

  /**
   * Sets the error message.
   * 
   * @param message
   *          the message
   * @param level
   *          the level
   */
  public void setErrorMessage(final String message, final NotifyLevel level) {
    messageErrorBar.setErrorMessage(message, level);
  }

  public void setHeaderLogo(final String url) {
    final Image logo = new Image(url);
    logo.addStyleName("kune-Margin-7-trbl");
    dialog.getMainPanel().insert(logo, 0);
  }

  public void setTitleIcon(final String url) {
    dialog.setTitleIconUrl(url);
  }

  /**
   * Show.
   */
  public void show() {
    dialog.showCentered();
  }

  /**
   * Un mask.
   */
  public void unMask() {
    mask.unMask();
  }
}
