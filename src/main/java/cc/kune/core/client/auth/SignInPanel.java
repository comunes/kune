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

import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.client.ui.dialogs.MessageToolbar;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.auth.SignInPresenter.SignInView;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.KuneUiUtils;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SignInPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SignInPanel extends SignInAbstractPanel implements SignInView {

  /** The Constant CANCEL_BUTTON_ID. */
  public static final String CANCEL_BUTTON_ID = "k-signinp-cb";

  /** The Constant CREATE_ONE. */
  public static final String CREATE_ONE = "k-signinp-create";

  /** The Constant ERROR_MSG. */
  public static final String ERROR_MSG = "k-sigp-errmsg";

  /** The Constant FORGOT_PASSWD. */
  public static final String FORGOT_PASSWD = "k-signinp-forgot";

  /** The Constant SIGN_IN_BUTTON_ID. */
  public static final String SIGN_IN_BUTTON_ID = "k-signinp-sib";

  /** The Constant SIGNIN_DIALOG. */
  public static final String SIGNIN_DIALOG = "k-signinp-dialog";

  /** The forgot password label. */
  private Label forgotPasswordLabel;

  /** The register label. */
  private Label registerLabel;

  /** The sign in form. */
  private final SignInForm signInForm;

  /**
   * Instantiates a new sign in panel.
   * 
   * @param i18n
   *          the i18n
   * @param mask
   *          the mask
   * @param images
   *          the images
   */
  @Inject
  public SignInPanel(final I18nTranslationService i18n, final MaskWidgetView mask,
      final NotifyLevelImages images) {
    super(SIGNIN_DIALOG, mask, i18n, i18n.t(CoreMessages.SIGN_IN_TITLE), true, true, true, "",
        i18n.t(CoreMessages.SIGN_IN_TITLE), SIGN_IN_BUTTON_ID, i18n.t("Cancel"), CANCEL_BUTTON_ID,
        images, ERROR_MSG, 102);

    signInForm = new SignInForm(i18n);
    signInForm.setWidth(DEF_SIGN_IN_FORM_SIZE);
    signInForm.add(createRegisterAndForgotPasswd());
    messageErrorBar = new MessageToolbar(images, errorLabelId);
    signInForm.add(messageErrorBar);
    super.getInnerPanel().add(signInForm.getFormPanel());
  }

  /**
   * Creates the link.
   * 
   * @param label
   *          the label
   * @param id
   *          the id
   */
  private void createLink(final Label label, final String id) {
    label.ensureDebugId(id);
    label.addStyleName("k-link");
    label.addStyleName("k-cursor");
  }

  /**
   * Creates the register and forgot passwd.
   * 
   * @return the vertical panel
   */
  private VerticalPanel createRegisterAndForgotPasswd() {
    final VerticalPanel noAccRegisterPanel = new VerticalPanel();
    final HorizontalPanel hpanel = new HorizontalPanel();
    final Label dontHaveAccount = new Label(i18n.t("Don't have an account?"));
    registerLabel = new Label(i18n.t("Create one."));
    createLink(registerLabel, CREATE_ONE);
    registerLabel.addStyleName("kune-Margin-Medium-l");
    forgotPasswordLabel = new Label(i18n.t("Lost your password?"));
    createLink(forgotPasswordLabel, FORGOT_PASSWD);
    hpanel.add(dontHaveAccount);
    hpanel.add(registerLabel);
    noAccRegisterPanel.add(hpanel);
    noAccRegisterPanel.add(forgotPasswordLabel);
    return noAccRegisterPanel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInPresenter.SignInView#focusOnNickname()
   */
  @Override
  public void focusOnNickname() {
    signInForm.focusLogin();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInPresenter.SignInView#focusOnPassword()
   */
  @Override
  public void focusOnPassword() {
    signInForm.focusOnPassword();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.SignInPresenter.SignInView#getAccountRegister()
   */
  @Override
  public HasClickHandlers getAccountRegister() {
    return registerLabel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInPresenter.SignInView#getForgotPasswd()
   */
  @Override
  public HasClickHandlers getForgotPasswd() {
    return forgotPasswordLabel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInPresenter.SignInView#getLoginPassword()
   */
  @Override
  public String getLoginPassword() {
    return signInForm.getLoginPassword();
  }

  /**
   * Gets the nickname.
   * 
   * @return the nickname
   */
  private Field<String> getNickname() {
    return signInForm.getNickOrEmailField();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInPresenter.SignInView#getNickOrEmail()
   */
  @Override
  public String getNickOrEmail() {
    return signInForm.getNickOrEmail();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.SignInPresenter.SignInView#isSignInFormValid()
   */
  @Override
  public boolean isSignInFormValid() {
    return signInForm.isValid();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInAbstractView#reset()
   */
  @Override
  public void reset() {
    signInForm.reset();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.SignInPresenter.SignInView#setLoginPassword(java
   * .lang.String)
   */
  @Override
  public void setLoginPassword(final String password) {
    signInForm.setLoginPassword(password);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.SignInPresenter.SignInView#setNickOrEmail(java
   * .lang.String)
   */
  @Override
  public void setNickOrEmail(final String nickOrEmail) {
    signInForm.setNickOrEmail(nickOrEmail);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.SignInPresenter.SignInView#setOnPasswordReturn
   * (cc.kune.common.client.utils.OnAcceptCallback)
   */
  @Override
  public void setOnPasswordReturn(final OnAcceptCallback onAcceptCallback) {
    signInForm.setOnPasswordReturn(onAcceptCallback);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInAbstractPanel#show()
   */
  @Override
  public void show() {
    super.show();
    KuneUiUtils.focusOnField(getNickname());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInPresenter.SignInView#validate()
   */
  @Override
  public void validate() {
    signInForm.validate();
  }

}
