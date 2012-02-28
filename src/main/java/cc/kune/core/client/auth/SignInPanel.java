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

public class SignInPanel extends SignInAbstractPanel implements SignInView {

  public static final String CANCEL_BUTTON_ID = "k-signinp-cb";
  public static final String CREATE_ONE = "k-signinp-create";
  public static final String ERROR_MSG = "k-sigp-errmsg";
  public static final String FORGOT_PASSWD = "k-signinp-forgot";
  public static final String SIGN_IN_BUTTON_ID = "k-signinp-sib";
  public static final String SIGNIN_DIALOG = "k-signinp-dialog";
  private Label forgotPasswordLabel;
  private Label registerLabel;
  private final SignInForm signInForm;

  @Inject
  public SignInPanel(final I18nTranslationService i18n, final MaskWidgetView mask,
      final NotifyLevelImages images) {
    super(SIGNIN_DIALOG, mask, i18n, i18n.t(CoreMessages.SIGN_IN_TITLE), true, true, true, "",
        i18n.t(CoreMessages.SIGN_IN_TITLE), SIGN_IN_BUTTON_ID, i18n.t("Cancel"), CANCEL_BUTTON_ID,
        images, ERROR_MSG, 102);

    signInForm = new SignInForm(i18n);
    signInForm.setWidth(330);
    signInForm.add(createRegisterAndForgotPasswd());
    messageErrorBar = new MessageToolbar(images, errorLabelId);
    signInForm.add(messageErrorBar);
    super.getInnerPanel().add(signInForm.getFormPanel());
  }

  private void createLink(final Label label, final String id) {
    label.ensureDebugId(id);
    label.addStyleName("k-link");
    label.addStyleName("k-cursor");
  }

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

  @Override
  public void focusOnNickname() {
    signInForm.focusLogin();
  }

  @Override
  public void focusOnPassword() {
    signInForm.focusOnPassword();
  }

  @Override
  public HasClickHandlers getAccountRegister() {
    return registerLabel;
  }

  @Override
  public HasClickHandlers getForgotPasswd() {
    return forgotPasswordLabel;
  }

  @Override
  public String getLoginPassword() {
    return signInForm.getLoginPassword();
  }

  private Field<String> getNickname() {
    return signInForm.getNickOrEmailField();
  }

  @Override
  public String getNickOrEmail() {
    return signInForm.getNickOrEmail();
  }

  @Override
  public boolean isSignInFormValid() {
    return signInForm.isValid();
  }

  @Override
  public void reset() {
    signInForm.reset();
  }

  @Override
  public void setLoginPassword(final String password) {
    signInForm.setLoginPassword(password);
  }

  @Override
  public void setNickOrEmail(final String nickOrEmail) {
    signInForm.setNickOrEmail(nickOrEmail);
  }

  @Override
  public void setOnPasswordReturn(final OnAcceptCallback onAcceptCallback) {
    signInForm.setOnPasswordReturn(onAcceptCallback);
  }

  @Override
  public void show() {
    super.show();
    KuneUiUtils.focusOnField(getNickname());
  }

  @Override
  public void validate() {
    signInForm.validate();
  }

}
