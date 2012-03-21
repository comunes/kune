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
import com.extjs.gxt.ui.client.widget.ComponentHelper;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class SignInForm extends DefaultForm {
  private static final String LOGIN_ID = "loginrender";
  public static final String PASSWORD_FIELD_ID = "kune-sif-psf";
  public static final String USER_FIELD_ID = "kune-sif-nkf";

  private final TextField<String> loginNickOrEmailField;
  private final TextField<String> loginPassField;
  private OnAcceptCallback onAcceptCallback;

  /**
   * Remember user/pass implementation <a href=
   * "http://stackoverflow.com/questions/1245174/is-it-possible-to-implement-cross-browser-username-password-autocomplete-in-gxt"
   * >based in this</a> and <a href=
   * "http://www.sencha.com/forum/showthread.php?72027-Auto-complete-login-form"
   * >this</a>.
   */
  public SignInForm(final I18nTranslationService i18n) {
    super.addStyleName("kune-Margin-Large-trbl");
    loginNickOrEmailField = new TextField<String>() {
      @Override
      protected void onRender(final Element target, final int index) {
        if (el() == null) {
          setElement(Document.get().getElementById("usernamerender"));
        }
        super.onRender(target, index);
      }
    };
    loginNickOrEmailField.setFieldLabel(i18n.t("Username"));
    loginNickOrEmailField.setName(USER_FIELD_ID);
    loginNickOrEmailField.setWidth(DEF_SMALL_FIELD_WIDTH);
    loginNickOrEmailField.setAllowBlank(false);
    loginNickOrEmailField.setValidationDelay(3000);
    loginNickOrEmailField.setId(USER_FIELD_ID);
    loginNickOrEmailField.setTabIndex(100);
    loginNickOrEmailField.render(RootPanel.get(LOGIN_ID).getElement());
    ComponentHelper.doAttach(loginNickOrEmailField);
    super.add(loginNickOrEmailField);

    loginPassField = new TextField<String>() {
      @Override
      protected void onRender(final Element target, final int index) {
        if (el() == null) {
          final String elementId = "passwordrender";
          setElement(Document.get().getElementById(elementId));
        }
        super.onRender(target, index);
      }
    };
    loginPassField.setFieldLabel(i18n.t("Password"));
    loginPassField.setName(PASSWORD_FIELD_ID);
    loginPassField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
    loginPassField.setPassword(true);
    loginPassField.setAllowBlank(false);
    loginPassField.setValidationDelay(3000);
    loginPassField.setId(PASSWORD_FIELD_ID);
    loginPassField.setTabIndex(101);
    loginPassField.render(RootPanel.get(LOGIN_ID).getElement());
    ComponentHelper.doAttach(loginPassField);

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
