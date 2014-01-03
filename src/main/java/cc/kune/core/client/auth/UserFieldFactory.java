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

import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.CoreConstants;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating UserField objects.
 */
@Singleton
public class UserFieldFactory {

  /**
   * Creates a new UserField object.
   * 
   * @param fieldId
   *          the field id
   * @return the text field< string>
   */
  public static TextField<String> createUserEmail(final String fieldId) {
    // http://www.sencha.com/forum/showthread.php?49702-GXT-Form-Validation
    final TextField<String> emailRegField = new TextField<String>();
    emailRegField.setFieldLabel(I18n.t("Email"));
    emailRegField.setName(fieldId);
    emailRegField.setRegex(TextUtils.EMAIL_REGEXP);
    emailRegField.getMessages().setRegexText(I18n.t("This is not a valid email"));
    emailRegField.setWidth(DefaultForm.DEF_MEDIUM_FIELD_WIDTH);
    emailRegField.setAllowBlank(false);
    emailRegField.setValidationDelay(1000);
    emailRegField.setId(fieldId);
    return emailRegField;
  }

  /**
   * Creates a new UserField object.
   * 
   * @param fieldId
   *          the field id
   * @return the text field< string>
   */
  public static TextField<String> createUserLongName(final String fieldId) {
    final TextField<String> longNameRegField = new TextField<String>();
    longNameRegField.setFieldLabel(I18n.t("Name"));
    longNameRegField.setName(fieldId);
    longNameRegField.setWidth(DefaultForm.DEF_FIELD_WIDTH);
    longNameRegField.setAllowBlank(false);
    longNameRegField.setMinLength(3);
    longNameRegField.setMaxLength(CoreConstants.MAX_LONG_NAME_SIZE); /*
                                                                      * Same in
                                                                      * User
                                                                      * .java
                                                                      * /longName
                                                                      */

    longNameRegField.setId(fieldId);
    return longNameRegField;
  }

  /**
   * Creates a new UserField object.
   * 
   * @param fieldId
   *          the field id
   * @param fieldText
   *          the field text
   * @return the text field< string>
   */
  public static TextField<String> createUserPasswd(final String fieldId, final String fieldText) {
    final TextField<String> passwdRegField = new TextField<String>();
    passwdRegField.setFieldLabel(fieldText);
    passwdRegField.setName(fieldId);
    passwdRegField.setPassword(true);
    passwdRegField.setAllowBlank(false);
    passwdRegField.setMinLength(6);
    passwdRegField.setMaxLength(40);
    passwdRegField.getMessages().setMinLengthText(I18n.t(CoreMessages.PASSWD_MUST_BE_BETWEEN_6_AND_40));
    passwdRegField.getMessages().setMaxLengthText(I18n.t(CoreMessages.PASSWD_MUST_BE_BETWEEN_6_AND_40));
    passwdRegField.setWidth(DefaultForm.DEF_MEDIUM_FIELD_WIDTH);
    passwdRegField.setValidationDelay(1000);
    passwdRegField.setId(fieldId);
    return passwdRegField;
  }

  /**
   * Creates a new UserField object.
   * 
   * @param fieldId
   *          the field id
   * @return the text field< string>
   */
  public static TextField<String> createUserShortName(final String fieldId) {
    final String minMaxText = I18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_30);
    final TextField<String> field = new TextField<String>();
    field.setFieldLabel(I18n.t("Username"));
    field.setName(fieldId);
    field.setId(fieldId);
    field.setWidth(DefaultForm.DEF_SMALL_FIELD_WIDTH);
    field.setAllowBlank(false);
    field.setMinLength(3);
    field.setMaxLength(CoreConstants.MAX_SHORT_NAME_SIZE); /*
                                                            * Same in
                                                            * User.java/name
                                                            */
    field.setRegex(TextUtils.SHORTNAME_UPPER_REGEXP);
    field.getMessages().setMinLengthText(minMaxText);
    field.getMessages().setMaxLengthText(minMaxText);
    field.getMessages().setRegexText(minMaxText);
    field.setValidationDelay(1000);
    field.addStyleName("k-lower");
    return field;
  }

  /**
   * Gets the register link.
   * 
   * @return the register link
   */
  public static String getRegisterLink() {
    return getRegisterLink(I18n.t("register"), null);
  }

  /**
   * Gets the register link.
   * 
   * @param withText
   *          the with text
   * @param redirect
   *          the redirect
   * @return the register link
   */
  public static String getRegisterLink(final String withText, final String redirect) {
    final String href = "#"
        + HistoryUtils.PREFIX
        + (TextUtils.empty(redirect) ? SiteTokens.REGISTER : TokenUtils.addRedirect(SiteTokens.REGISTER,
            redirect));
    return TextUtils.generateHtmlLink(href, withText, false);
  }

  /**
   * Gets the sign in link.
   * 
   * @return the sign in link
   */
  public static String getSignInLink() {
    return getSignInLink(null);
  }

  /**
   * Gets the sign in link.
   * 
   * @param redirect
   *          the redirect
   * @return the sign in link
   */
  public static String getSignInLink(final String redirect) {
    final String href = "#"
        + HistoryUtils.PREFIX
        + (TextUtils.empty(redirect) ? SiteTokens.SIGN_IN : TokenUtils.addRedirect(SiteTokens.SIGN_IN,
            redirect));
    return TextUtils.generateHtmlLink(href, I18n.t("sign in"), false);
  }

}
