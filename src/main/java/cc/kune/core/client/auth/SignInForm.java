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

// TODO: Auto-generated Javadoc
/**
 * The Class SignInForm.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SignInForm extends DefaultForm {

  /** The Constant LOGIN_ID. */
  private static final String LOGIN_ID = "loginrender";

  /** The Constant PASSWORD_FIELD_ID. */
  public static final String PASSWORD_FIELD_ID = "kune-sif-psf";

  /** The Constant USER_FIELD_ID. */
  public static final String USER_FIELD_ID = "kune-sif-nkf";

  /** The login nick or email field. */
  private final TextField<String> loginNickOrEmailField;

  /** The login pass field. */
  private final TextField<String> loginPassField;

  /** The on accept callback. */
  private OnAcceptCallback onAcceptCallback;

  /**
   * Remember user/pass implementation <a href=
   * "http://stackoverflow.com/questions/1245174/is-it-possible-to-implement-cross-browser-username-password-autocomplete-in-gxt"
   * >based in this</a> and <a href=
   * "http://www.sencha.com/forum/showthread.php?72027-Auto-complete-login-form"
   * >this</a>.
   * 
   * @param i18n
   *          the i18n
   */
  public SignInForm(final I18nTranslationService i18n) {
    final Listener<FieldEvent> enterListener = new Listener<FieldEvent>() {
      @Override
      public void handleEvent(final FieldEvent fe) {
        if (fe.getEvent().getKeyCode() == 13) {
          onAcceptCallback.onSuccess();
        }
      }
    };

    super.addStyleName("kune-Margin-Large-trbl");
    loginNickOrEmailField = new TextField<String>() {
      @Override
      protected void onRender(final Element target, final int index) {
        if (el() == null) {
          setElement(Document.get().getElementById("usernamerender"));
        }
        super.onRender(target, index);
      }

      @Override
      protected void setAriaState(final String stateName, final String stateValue) {
      }
    };
    loginNickOrEmailField.setFieldLabel(i18n.t("Username or email"));
    loginNickOrEmailField.setName(USER_FIELD_ID);
    loginNickOrEmailField.setWidth(DEF_SMALL_FIELD_WIDTH);
    loginNickOrEmailField.setAllowBlank(false);
    loginNickOrEmailField.setValidationDelay(3000);
    loginNickOrEmailField.setId(USER_FIELD_ID);
    loginNickOrEmailField.setTabIndex(100);
    loginNickOrEmailField.addStyleName("k-lower");
    loginNickOrEmailField.render(RootPanel.get(LOGIN_ID).getElement());
    ComponentHelper.doAttach(loginNickOrEmailField);
    super.add(loginNickOrEmailField);
    loginNickOrEmailField.addListener(Events.OnKeyPress, enterListener);

    loginPassField = new TextField<String>() {
      @Override
      protected void onRender(final Element target, final int index) {
        if (el() == null) {
          final String elementId = "passwordrender";
          setElement(Document.get().getElementById(elementId));
        }
        super.onRender(target, index);
      }

      @Override
      protected void setAriaState(final String stateName, final String stateValue) {
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
    loginPassField.addListener(Events.OnKeyPress, enterListener);
    super.add(loginPassField);
  }

  /**
   * Focus login.
   */
  public void focusLogin() {
    loginNickOrEmailField.focus();
  }

  /**
   * Focus on password.
   */
  public void focusOnPassword() {
    loginPassField.focus();
  }

  /**
   * Gets the login password.
   * 
   * @return the login password
   */
  public String getLoginPassword() {
    return loginPassField.getValue();
  }

  /**
   * Gets the nick or email.
   * 
   * @return the nick or email
   */
  public String getNickOrEmail() {
    return loginNickOrEmailField.getValue();
  }

  /**
   * Gets the nick or email field.
   * 
   * @return the nick or email field
   */
  public Field<String> getNickOrEmailField() {
    return loginNickOrEmailField;
  }

  /**
   * Sets the login password.
   * 
   * @param password
   *          the new login password
   */
  public void setLoginPassword(final String password) {
    loginPassField.setValue(password);
  }

  /**
   * Sets the nick or email.
   * 
   * @param nickOrEmail
   *          the new nick or email
   */
  public void setNickOrEmail(final String nickOrEmail) {
    loginNickOrEmailField.setValue(nickOrEmail);
  }

  /**
   * Sets the on password return.
   * 
   * @param onAcceptCallback
   *          the new on password return
   */
  public void setOnPasswordReturn(final OnAcceptCallback onAcceptCallback) {
    this.onAcceptCallback = onAcceptCallback;
  }
}
