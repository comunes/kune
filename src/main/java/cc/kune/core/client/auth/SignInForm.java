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
 \*/
package cc.kune.core.client.auth;

import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.ui.DefaultForm;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;

public class SignInForm extends DefaultForm {
  public static final String PASSWORD_FIELD_ID = "kune-sif-psf";
  public static final String USER_FIELD_ID = "kune-sif-nkf";

  private final TextField<String> loginNickOrEmailField;
  private final TextField<String> loginPassField;
  private OnAcceptCallback onAcceptCallback;

  public SignInForm(final I18nTranslationService i18n) {
    super.addStyleName("kune-Margin-Large-trbl");

    loginNickOrEmailField = new TextField<String>();
    loginNickOrEmailField.setFieldLabel(i18n.t("Username"));
    loginNickOrEmailField.setName(USER_FIELD_ID);
    loginNickOrEmailField.setWidth(DEF_SMALL_FIELD_WIDTH);
    loginNickOrEmailField.setAllowBlank(false);
    loginNickOrEmailField.setValidationDelay(1000);
    loginNickOrEmailField.setId(USER_FIELD_ID);
    loginNickOrEmailField.setTabIndex(100);
    super.add(loginNickOrEmailField);

    loginPassField = new TextField<String>();
    loginPassField.setFieldLabel(i18n.t("Password"));
    loginPassField.setName(PASSWORD_FIELD_ID);
    loginPassField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
    loginPassField.setPassword(true);
    loginPassField.setAllowBlank(false);
    loginPassField.setValidationDelay(1000);
    loginPassField.setId(PASSWORD_FIELD_ID);
    loginPassField.setTabIndex(101);
    loginPassField.addListener(Events.OnKeyPress, new Listener<FieldEvent>() {
      @Override
      public void handleEvent(final FieldEvent fe) {
        if (fe.getEvent().getKeyCode() == 13) {
          onAcceptCallback.onSuccess();
        }
      }
    });
    super.add(loginPassField);
  }

  public void focusLogin() {
    loginNickOrEmailField.focus();
  }

  public void focusOnPassword() {
    loginPassField.focus();
  }

  public String getLoginPassword() {
    return loginPassField.getValue();
  }

  public String getNickOrEmail() {
    return loginNickOrEmailField.getValue();
  }

  public Field<String> getNickOrEmailField() {
    return loginNickOrEmailField;
  }

  public void setLoginPassword(final String password) {
    loginPassField.setValue(password);
  }

  public void setNickOrEmail(final String nickOrEmail) {
    loginNickOrEmailField.setValue(nickOrEmail);
  }

  public void setOnPasswordReturn(final OnAcceptCallback onAcceptCallback) {
    this.onAcceptCallback = onAcceptCallback;
  }
}
