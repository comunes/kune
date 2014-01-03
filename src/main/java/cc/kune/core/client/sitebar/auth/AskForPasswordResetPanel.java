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
package cc.kune.core.client.sitebar.auth;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.client.ui.dialogs.MessageToolbar;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.auth.SignInAbstractPanel;
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.errors.EmailNotFoundException;
import cc.kune.core.client.events.StackErrorEvent;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ui.DefaultForm;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class AskForPasswordResetPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class AskForPasswordResetPanel extends SignInAbstractPanel {

  /** The Constant ASK_PASSWD_RESET_DIALOG. */
  public static final String ASK_PASSWD_RESET_DIALOG = "k-ask-for-pwd-diag";

  /** The Constant CANCEL_BUTTON_ID. */
  public static final String CANCEL_BUTTON_ID = "k-ask-for-pwd-cancel";

  /** The Constant EMAIL_RESET_ID. */
  public static final String EMAIL_RESET_ID = "k-ask-for-pwd-email";

  /** The Constant ERRMSG. */
  public static final String ERRMSG = "k-ask-for-pwd-error";

  /** The Constant RESET_BUTTON_ID. */
  public static final String RESET_BUTTON_ID = "k-ask-for-pwd-reset";

  /** The reset email. */
  private final TextField<String> resetEmail;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new ask for password reset panel.
   * 
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   * @param mask
   *          the mask
   * @param images
   *          the images
   * @param eventbus
   *          the eventbus
   * @param userService
   *          the user service
   * @param stateManager
   *          the state manager
   */
  @Inject
  public AskForPasswordResetPanel(final I18nTranslationService i18n, final Session session,
      final MaskWidgetView mask, final NotifyLevelImages images, final EventBus eventbus,
      final Provider<UserServiceAsync> userService, final StateManager stateManager) {
    super(ASK_PASSWD_RESET_DIALOG, mask, i18n, i18n.t("Reset your password"), true, true, true, "",
        i18n.t("Reset your password"), RESET_BUTTON_ID, i18n.tWithNT("Cancel", "used in button"),
        CANCEL_BUTTON_ID, images, ERRMSG, 1);
    this.stateManager = stateManager;
    final DefaultForm form = new DefaultForm();
    form.setWidth(DEF_SIGN_IN_FORM_SIZE);
    final Label desc = new Label(
        i18n.t("Please enter your email address. You will receive a link to create a new password via email."));
    desc.setWidth("320px");
    resetEmail = UserFieldFactory.createUserEmail(EMAIL_RESET_ID);
    resetEmail.setFieldLabel(i18n.t("email"));
    resetEmail.setTabIndex(1);
    messageErrorBar = new MessageToolbar(images, errorLabelId);
    form.add(resetEmail);
    form.add(messageErrorBar);
    super.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (form.isValid()) {
          userService.get().askForPasswordReset(resetEmail.getValue(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(final Throwable caught) {
              if (caught instanceof EmailNotFoundException) {
                AskForPasswordResetPanel.this.setErrorMessage(
                    i18n.t("There is no registered user with that e-mail address"), NotifyLevel.error);
              } else {
                AskForPasswordResetPanel.this.setErrorMessage(
                    i18n.t("Other error trying to reset your password"), NotifyLevel.error);
              }
              StackErrorEvent.fire(eventbus, caught);
              AskForPasswordResetPanel.this.messageErrorBar.setVisible(true);
            }

            @Override
            public void onSuccess(final Void result) {
              NotifyUser.info(i18n.t("Check your email for the confirmation link"));
              hide();
            }
          });
        }
      }
    });
    super.getSecondBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
    super.getInnerPanel().add(desc);
    super.getInnerPanel().add(form.getFormPanel());

  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInAbstractPanel#hide()
   */
  @Override
  public void hide() {
    super.hide();
    super.messageErrorBar.hideErrorMessage();
    resetEmail.clear();
    stateManager.gotoHistoryToken(SiteTokens.HOME);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInAbstractPanel#show()
   */
  @Override
  public void show() {
    super.show();
  }
}
