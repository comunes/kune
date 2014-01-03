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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.auth.RegisterPresenter.RegisterView;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.KuneUiUtils;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RegisterPanel extends SignInAbstractPanel implements RegisterView {

  /** The Constant CANCEL_BUTTON_ID. */
  public static final String CANCEL_BUTTON_ID = "k-regp-cb";

  /** The Constant ERRMSG. */
  public static final String ERRMSG = "k-regp-errmsg";

  /** The Constant REGISTER_BUTTON_ID. */
  public static final String REGISTER_BUTTON_ID = "k-regp-rb";

  /** The Constant REGISTER_DIALOG. */
  public static final String REGISTER_DIALOG = "k-regp-dialog";

  /** The Constant REGISTER_FORM. */
  public static final String REGISTER_FORM = "k-regp-p";

  /** The Constant WELCOME_DIALOG. */
  public static final String WELCOME_DIALOG = "k-regp-wdiag";

  /** The Constant WELCOME_OK_BUTTON. */
  public static final String WELCOME_OK_BUTTON = "k-regp-okbt";

  /** The register form. */
  private final RegisterForm registerForm;

  /**
   * Instantiates a new register panel.
   * 
   * @param i18n
   *          the i18n
   * @param mask
   *          the mask
   * @param images
   *          the images
   */
  @Inject
  public RegisterPanel(final I18nTranslationService i18n, final MaskWidgetView mask,
      final NotifyLevelImages images) {
    super(REGISTER_DIALOG, mask, i18n, i18n.t(CoreMessages.REGISTER_TITLE), true, true, true, "",
        i18n.t(CoreMessages.REGISTER_TITLE), REGISTER_BUTTON_ID,
        i18n.tWithNT("Cancel", "used in button"), CANCEL_BUTTON_ID, images, ERRMSG, 4);
    registerForm = new RegisterForm(i18n);
    registerForm.setWidth(DEF_SIGN_IN_FORM_SIZE);
    registerForm.getFormPanel().setId(REGISTER_FORM);
    messageErrorBar = new MessageToolbar(images, errorLabelId);
    registerForm.add(messageErrorBar);
    super.getInnerPanel().add(registerForm.getFormPanel());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.RegisterPresenter.RegisterView#getEmail()
   */
  @Override
  public String getEmail() {
    return registerForm.getEmail();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.RegisterPresenter.RegisterView#getLongName()
   */
  @Override
  public String getLongName() {
    return registerForm.getLongName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.RegisterPresenter.RegisterView#getRegisterPassword
   * ()
   */
  @Override
  public String getRegisterPassword() {
    return registerForm.getRegisterPassword();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.RegisterPresenter.RegisterView#getShortName()
   */
  @Override
  public String getShortName() {
    return registerForm.getShortName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.RegisterPresenter.RegisterView#isRegisterFormValid
   * ()
   */
  @Override
  public boolean isRegisterFormValid() {
    return registerForm.isValid();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.RegisterPresenter.RegisterView#isValid()
   */
  @Override
  public boolean isValid() {
    return registerForm.isValid();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInAbstractView#reset()
   */
  @Override
  public void reset() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        registerForm.reset();
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.RegisterPresenter.RegisterView#setEmailFailed(
   * java.lang.String)
   */
  @Override
  public void setEmailFailed(final String msg) {
    registerForm.setEmailFailed(msg);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.RegisterPresenter.RegisterView#setLongNameFailed
   * (java.lang.String)
   */
  @Override
  public void setLongNameFailed(final String msg) {
    registerForm.setLongNameFailed(msg);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.RegisterPresenter.RegisterView#setShortNameFailed
   * (java.lang.String)
   */
  @Override
  public void setShortNameFailed(final String msg) {
    registerForm.setShortNameFailed(msg);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.SignInAbstractPanel#show()
   */
  @Override
  public void show() {
    super.show();
    KuneUiUtils.focusOnField(registerForm.getLongNameField());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.RegisterPresenter.RegisterView#validate()
   */
  @Override
  public void validate() {
    registerForm.validate();
  }

}
