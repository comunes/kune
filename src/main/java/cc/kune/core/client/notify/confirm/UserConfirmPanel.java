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
package cc.kune.core.client.notify.confirm;

import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class UserConfirmPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserConfirmPanel extends ViewImpl implements UserConfirmView {

  /** The Constant CANCEL_ID. */
  public static final String CANCEL_ID = "k-conf-dial";

  /** The Constant DIALOG_ID. */
  public static final String DIALOG_ID = "k-conf-dial-nok";

  /** The Constant OK_ID. */
  public static final String OK_ID = "k-conf-dial-ok";

  /** The accept handler. */
  private HandlerRegistration acceptHandler;

  /** The ask label. */
  private final HTML askLabel;

  /** The cancel handler. */
  private HandlerRegistration cancelHandler;

  /** The dialog. */
  private final BasicTopDialog dialog;

  /**
   * Instantiates a new user confirm panel.
   * 
   * @param i18n
   *          the i18n
   */
  @Inject
  public UserConfirmPanel(final I18nTranslationService i18n) {
    dialog = new BasicTopDialog.Builder(DIALOG_ID, false, true, i18n.getDirection()).autoscroll(false).firstButtonId(
        OK_ID).sndButtonId(CANCEL_ID).tabIndexStart(1).build();
    askLabel = new HTML();
    askLabel.addStyleName("k-userconfirm-label");
    dialog.getInnerPanel().add(askLabel);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmView
   * #confirmAsk(cc.kune.common.client.notify.ConfirmAskEvent)
   */
  @Override
  public void confirmAsk(final ConfirmAskEvent ask) {
    dialog.setFirstBtnText(ask.getAcceptBtnMsg());
    final String cancelBtnMsg = ask.getCancelBtnMsg();
    dialog.setFirstBtnTitle(ask.getAcceptBtnTooltip());
    dialog.setSecondBtnText(cancelBtnMsg);
    dialog.setSecondBtnTitle(ask.getCancelBtnTooltip());
    dialog.getTitleText().setText(ask.getTitle());
    dialog.setFirstBtnTabIndex(1);
    dialog.setSecondBtnTabIndex(2);
    askLabel.setHTML(ask.getMessage());
    dialog.showCentered();
    resetHandlers();
    acceptHandler = dialog.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        dialog.hide();
        ask.getCallback().onSuccess();
      }
    });
    cancelHandler = dialog.getSecondBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        dialog.hide();
        ask.getCallback().onCancel();
      }
    });
    dialog.showCentered();
    dialog.setFirstBtnFocus();
    final ImageResource icon = ask.getIcon();
    if (icon != null) {
      dialog.setTitleIcon(icon);
    }
  }

  /**
   * Reset handlers.
   */
  private void resetHandlers() {
    if (acceptHandler != null) {
      acceptHandler.removeHandler();
    }
    if (cancelHandler != null) {
      cancelHandler.removeHandler();
    }
  }

}
