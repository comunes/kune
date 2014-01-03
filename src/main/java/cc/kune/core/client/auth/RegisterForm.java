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

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.ui.DefaultForm;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterForm.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RegisterForm extends DefaultForm {

  /** The Constant COUNTRY_FIELD. */
  public static final String COUNTRY_FIELD = "k-urf-country-f";

  /** The Constant EMAIL_FIELD. */
  public static final String EMAIL_FIELD = "k-urf-email-f";

  /** The Constant LANG_FIELD. */
  public static final String LANG_FIELD = "k-urf-lang-f";

  /** The Constant LONGNAME_FIELD. */
  public static final String LONGNAME_FIELD = "k-urf-long_name-f";

  /** The Constant NICK_FIELD. */
  public static final String NICK_FIELD = "k-urf-nick-f";

  /** The Constant NOPERSONALHOMEPAGE_ID. */
  public static final String NOPERSONALHOMEPAGE_ID = "k-urf-nphp-id";

  /** The Constant PASSWORD_FIELD. */
  public static final String PASSWORD_FIELD = "k-urf-password-f";

  /** The Constant PASSWORD_FIELD_DUP. */
  public static final String PASSWORD_FIELD_DUP = "k-urf-passwordDup-f";

  /** The Constant TIMEZONE_FIELD. */
  public static final String TIMEZONE_FIELD = "k-urf-timezone-f";

  /** The Constant WANNAPERSONALHOMEPAGE_ID. */
  public static final String WANNAPERSONALHOMEPAGE_ID = "k-urf-wphp-id";

  /** The Constant WANTHOMEPAGE_FIELD. */
  public static final String WANTHOMEPAGE_FIELD = "k-urf-wphp-f";

  /** The email reg field. */
  private final TextField<String> emailRegField;

  /** The long name reg field. */
  private final TextField<String> longNameRegField;

  /** The passwd reg field. */
  private final TextField<String> passwdRegField;

  /** The short name reg field. */
  private final TextField<String> shortNameRegField;

  /**
   * Instantiates a new register form.
   * 
   * @param i18n
   *          the i18n
   */
  public RegisterForm(final I18nTranslationService i18n) {
    super.addStyleName("kune-Margin-Large-l");

    longNameRegField = UserFieldFactory.createUserLongName(LONGNAME_FIELD);
    longNameRegField.setTabIndex(1);
    add(longNameRegField);

    shortNameRegField = UserFieldFactory.createUserShortName(NICK_FIELD);
    shortNameRegField.setTabIndex(2);
    add(shortNameRegField);

    passwdRegField = UserFieldFactory.createUserPasswd(PASSWORD_FIELD, i18n.t("Password"));
    passwdRegField.setTabIndex(3);
    add(passwdRegField);

    emailRegField = UserFieldFactory.createUserEmail(EMAIL_FIELD);
    emailRegField.setTabIndex(4);
    add(emailRegField);
  }

  /**
   * Gets the email.
   * 
   * @return the email
   */
  public String getEmail() {
    return emailRegField.getValue();
  }

  /**
   * Gets the long name.
   * 
   * @return the long name
   */
  public String getLongName() {
    return longNameRegField.getValue();
  }

  /**
   * Gets the long name field.
   * 
   * @return the long name field
   */
  public Field<String> getLongNameField() {
    return longNameRegField;
  }

  /**
   * Gets the register password.
   * 
   * @return the register password
   */
  public String getRegisterPassword() {
    return passwdRegField.getValue();
  }

  /**
   * Gets the short name.
   * 
   * @return the short name
   */
  public String getShortName() {
    return shortNameRegField.getValue();
  }

  /**
   * Gets the short name field.
   * 
   * @return the short name field
   */
  public Field<String> getShortNameField() {
    return shortNameRegField;
  }

  /**
   * Sets the email failed.
   * 
   * @param msg
   *          the new email failed
   */
  public void setEmailFailed(final String msg) {
    emailRegField.markInvalid(msg);
  }

  /**
   * Sets the long name failed.
   * 
   * @param msg
   *          the new long name failed
   */
  public void setLongNameFailed(final String msg) {
    longNameRegField.markInvalid(msg);
  }

  /**
   * Sets the short name failed.
   * 
   * @param msg
   *          the new short name failed
   */
  public void setShortNameFailed(final String msg) {
    shortNameRegField.markInvalid(msg);
  }
}
