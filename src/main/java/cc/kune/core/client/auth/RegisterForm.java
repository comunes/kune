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

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.ui.DefaultForm;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;

public class RegisterForm extends DefaultForm {

  public static final String COUNTRY_FIELD = "k-urf-country-f";
  public static final String EMAIL_FIELD = "k-urf-email-f";
  public static final String LANG_FIELD = "k-urf-lang-f";
  public static final String LONGNAME_FIELD = "k-urf-long_name-f";
  public static final String NICK_FIELD = "k-urf-nick-f";
  public static final String NOPERSONALHOMEPAGE_ID = "k-urf-nphp-id";
  public static final String PASSWORD_FIELD = "k-urf-password-f";
  public static final String PASSWORD_FIELD_DUP = "k-urf-passwordDup-f";
  public static final String TIMEZONE_FIELD = "k-urf-timezone-f";
  public static final String WANNAPERSONALHOMEPAGE_ID = "k-urf-wphp-id";
  public static final String WANTHOMEPAGE_FIELD = "k-urf-wphp-f";

  private final TextField<String> emailRegField;
  private final TextField<String> longNameRegField;
  private final TextField<String> passwdRegField;

  private final TextField<String> shortNameRegField;

  public RegisterForm(final I18nTranslationService i18n, final UserFieldFactory userFieldFactory) {
    super.addStyleName("kune-Margin-Large-l");

    shortNameRegField = userFieldFactory.createUserShortName(NICK_FIELD);
    shortNameRegField.setTabIndex(1);
    add(shortNameRegField);

    longNameRegField = userFieldFactory.createUserLongName(LONGNAME_FIELD);
    longNameRegField.setTabIndex(2);
    add(longNameRegField);

    passwdRegField = userFieldFactory.createUserPasswd(PASSWORD_FIELD, i18n.t("Password"));
    passwdRegField.setTabIndex(3);
    add(passwdRegField);

    emailRegField = userFieldFactory.createUserEmail(EMAIL_FIELD);
    emailRegField.setTabIndex(4);
    add(emailRegField);
  }

  public String getEmail() {
    return emailRegField.getValue();
  }

  public String getLongName() {
    return longNameRegField.getValue();
  }

  public String getRegisterPassword() {
    return passwdRegField.getValue();
  }

  public String getShortName() {
    return shortNameRegField.getValue();
  }

  public Field<String> getShortNameField() {
    return shortNameRegField;
  }

  public void setEmailFailed(final String msg) {
    emailRegField.markInvalid(msg);
  }

  public void setLongNameFailed(final String msg) {
    longNameRegField.markInvalid(msg);
  }

  public void setShortNameFailed(final String msg) {
    shortNameRegField.markInvalid(msg);
  }
}
