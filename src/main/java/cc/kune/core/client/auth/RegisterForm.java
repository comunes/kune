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

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.i18n.I18nTranslationService;

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

  public RegisterForm(final I18nTranslationService i18n, final Session session) {
    super.addStyleName("kune-Margin-Large-l");

    shortNameRegField = new TextField<String>();
    shortNameRegField.setTabIndex(1);
    shortNameRegField.setFieldLabel(i18n.t("Username"));
    shortNameRegField.setName(NICK_FIELD);
    shortNameRegField.setWidth(DEF_SMALL_FIELD_WIDTH);
    shortNameRegField.setAllowBlank(false);
    shortNameRegField.setMinLength(3);
    shortNameRegField.setMaxLength(15);
    shortNameRegField.setRegex("^[a-z0-9]+$");
    shortNameRegField.getMessages().setMinLengthText(i18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15));
    shortNameRegField.getMessages().setMaxLengthText(i18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15));
    shortNameRegField.getMessages().setRegexText(i18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15));
    shortNameRegField.setValidationDelay(1000);
    add(shortNameRegField);

    longNameRegField = new TextField<String>();
    longNameRegField.setTabIndex(2);
    longNameRegField.setFieldLabel(i18n.t("Name"));
    longNameRegField.setName(LONGNAME_FIELD);
    longNameRegField.setWidth(DEF_FIELD_WIDTH);
    longNameRegField.setAllowBlank(false);
    longNameRegField.setMinLength(3);
    longNameRegField.setMaxLength(50);
    // longNameRegField.setValidationEvent(false);
    longNameRegField.setId(LONGNAME_FIELD);
    add(longNameRegField);

    passwdRegField = new TextField<String>();
    passwdRegField.setTabIndex(3);
    passwdRegField.setFieldLabel(i18n.t("Password"));
    passwdRegField.setName(PASSWORD_FIELD);
    passwdRegField.setPassword(true);
    passwdRegField.setAllowBlank(false);
    passwdRegField.setMinLength(6);
    passwdRegField.setMaxLength(40);
    passwdRegField.getMessages().setMinLengthText(i18n.t(CoreMessages.PASSWD_MUST_BE_BETWEEN_6_AND_40));
    passwdRegField.getMessages().setMaxLengthText(i18n.t(CoreMessages.PASSWD_MUST_BE_BETWEEN_6_AND_40));
    passwdRegField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
    passwdRegField.setValidationDelay(1000);
    passwdRegField.setId(PASSWORD_FIELD);
    add(passwdRegField);

    // http://www.sencha.com/forum/showthread.php?49702-GXT-Form-Validation
    emailRegField = new TextField<String>();
    emailRegField.setTabIndex(5);
    emailRegField.setFieldLabel(i18n.t("Email"));
    emailRegField.setName(EMAIL_FIELD);
    emailRegField.setRegex(TextUtils.EMAIL_REGEXP);
    emailRegField.getMessages().setRegexText(i18n.t("This is not a valid email"));
    emailRegField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
    emailRegField.setAllowBlank(false);
    emailRegField.setValidationDelay(1000);
    emailRegField.setId(EMAIL_FIELD);
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
