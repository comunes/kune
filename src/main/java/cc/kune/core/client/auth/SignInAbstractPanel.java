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
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public abstract class SignInAbstractPanel extends ViewImpl {
  public static final int DEF_SIGN_IN_FORM_SIZE = 340;
  private final BasicTopDialog dialog;
  protected final String errorLabelId;
  protected final I18nTranslationService i18n;
  protected final NotifyLevelImages images;
  private final MaskWidgetView mask;
  protected MessageToolbar messageErrorBar;

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

  @Override
  public Widget asWidget() {
    return dialog;
  }

  public HasCloseHandlers<PopupPanel> getClose() {
    return dialog.getClose();
  }

  public HasClickHandlers getFirstBtn() {
    return dialog.getFirstBtn();
  }

  public ForIsWidget getInnerPanel() {
    return dialog.getInnerPanel();
  }

  public HasClickHandlers getSecondBtn() {
    return dialog.getSecondBtn();
  }

  public void hide() {
    if (dialog.isVisible()) {
      dialog.hide();
    }
  }

  public void hideMessages() {
    messageErrorBar.hideErrorMessage();
  }

  public void mask(final String message) {
    mask.mask(dialog);
  }

  public void maskProcessing() {
    mask.mask(dialog, i18n.t("Processing"));
  }

  public void setErrorMessage(final String message, final NotifyLevel level) {
    messageErrorBar.setErrorMessage(message, level);
  }

  public void show() {
    dialog.showCentered();
  }

  public void unMask() {
    mask.unMask();
  }
}
