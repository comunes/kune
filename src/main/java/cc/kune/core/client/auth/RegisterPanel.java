/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

public class RegisterPanel extends SignInAbstractPanel implements RegisterView {

  public static final String CANCEL_BUTTON_ID = "k-regp-cb";
  public static final String ERRMSG = "k-regp-errmsg";
  public static final String REGISTER_BUTTON_ID = "k-regp-rb";
  public static final String REGISTER_DIALOG = "k-regp-dialog";
  public static final String REGISTER_FORM = "k-regp-p";
  public static final String WELCOME_DIALOG = "k-regp-wdiag";
  public static final String WELCOME_OK_BUTTON = "k-regp-okbt";
  private final RegisterForm registerForm;

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

  @Override
  public String getEmail() {
    return registerForm.getEmail();
  }

  @Override
  public String getLongName() {
    return registerForm.getLongName();
  }

  @Override
  public String getRegisterPassword() {
    return registerForm.getRegisterPassword();
  }

  @Override
  public String getShortName() {
    return registerForm.getShortName();
  }

  @Override
  public boolean isRegisterFormValid() {
    return registerForm.isValid();
  }

  @Override
  public boolean isValid() {
    return registerForm.isValid();
  }

  @Override
  public void reset() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        registerForm.reset();
      }
    });
  }

  @Override
  public void setEmailFailed(final String msg) {
    registerForm.setEmailFailed(msg);
  }

  @Override
  public void setLongNameFailed(final String msg) {
    registerForm.setLongNameFailed(msg);
  }

  @Override
  public void setShortNameFailed(final String msg) {
    registerForm.setShortNameFailed(msg);
  }

  @Override
  public void show() {
    super.show();
    KuneUiUtils.focusOnField(registerForm.getLongNameField());
  }

  @Override
  public void validate() {
    registerForm.validate();
  }

}
