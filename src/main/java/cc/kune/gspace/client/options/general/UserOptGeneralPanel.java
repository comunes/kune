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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.DefaultFormUtils;
import cc.kune.core.shared.dto.EmailNotificationFrequency;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.gspace.client.i18n.LanguageSelectorPanel;

import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class UserOptGeneralPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserOptGeneralPanel extends EntityOptGeneralPanel implements UserOptGeneralView {

  /** The Constant DAILY_TYPE_ID. */
  public static final String DAILY_TYPE_ID = "k-ngp-type_daily";

  /** The Constant EMAIL_FIELD. */
  public static final String EMAIL_FIELD = "k-ngp-emial";

  /** The Constant HOURLY_TYPE_ID. */
  public static final String HOURLY_TYPE_ID = "k-ngp-type_hourly";

  /** The Constant IMMEDIATE_TYPE_ID. */
  public static final String IMMEDIATE_TYPE_ID = "k-ngp-type_immedi";

  /** The Constant LONG_NAME_FIELD. */
  public static final String LONG_NAME_FIELD = "k-uogp-lname";

  /** The Constant NO_TYPE_ID. */
  public static final String NO_TYPE_ID = "k-ngp-type_no";

  /** The Constant TYPEOFEMAILNOTIF_FIELD. */
  public static final String TYPEOFEMAILNOTIF_FIELD = "k-ngp-type_of_email_notif";

  /** The daily radio. */
  private final Radio dailyRadio;

  /** The email. */
  private final TextField<String> email;

  /** The email notif type field set. */
  private final FieldSet emailNotifTypeFieldSet;

  /** The hourly radio. */
  private final Radio hourlyRadio;

  /** The immediate radio. */
  private final Radio immediateRadio;

  /** The lang selector. */
  private final LanguageSelectorPanel langSelector;

  /** The long name. */
  private final TextField<String> longName;

  /** The no radio. */
  private final Radio noRadio;

  /** The not verif label adapter. */
  private final AdapterField notVerifLabelAdapter;

  /** The resend email verif adapter. */
  private final AdapterField resendEmailVerifAdapter;

  /** The resend email verif btn. */
  private final Button resendEmailVerifBtn;

  /**
   * Instantiates a new user opt general panel.
   * 
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param maskWidget
   *          the mask widget
   * @param langSelector
   *          the lang selector
   */
  @Inject
  public UserOptGeneralPanel(final I18nTranslationService i18n, final IconicResources res,
      final MaskWidget maskWidget, final LanguageSelectorPanel langSelector) {
    super(maskWidget, res.equalizerWhite(), i18n.t("General"), i18n.t("You can change these values:"));
    this.langSelector = langSelector;
    longName = UserFieldFactory.createUserLongName(LONG_NAME_FIELD);
    add(longName);
    langSelector.setLangTitle(i18n.t("Your language"));
    langSelector.setLabelAlign(LabelAlign.LEFT);
    langSelector.setLangSeparator(":");
    add(langSelector);

    email = UserFieldFactory.createUserEmail(EMAIL_FIELD);
    add(email);

    emailNotifTypeFieldSet = DefaultFormUtils.createFieldSet(i18n.t("How often do you want to receive email notifications?"));

    immediateRadio = DefaultFormUtils.createRadio(emailNotifTypeFieldSet, i18n.t("almost immediately"),
        TYPEOFEMAILNOTIF_FIELD, i18n.t(
            "you will receive an email with any new messages in your inbox at [%s] almost immediately",
            i18n.getSiteCommonName()), IMMEDIATE_TYPE_ID);
    immediateRadio.setTabIndex(3);
    immediateRadio.setValue(true);

    hourlyRadio = DefaultFormUtils.createRadio(emailNotifTypeFieldSet, i18n.t("at most hourly"),
        TYPEOFEMAILNOTIF_FIELD, i18n.t(
            "you will receive an email with any new messages in your inbox at [%s] at most hourly",
            i18n.getSiteCommonName()), HOURLY_TYPE_ID);
    hourlyRadio.setTabIndex(4);
    hourlyRadio.setValue(false);

    dailyRadio = DefaultFormUtils.createRadio(emailNotifTypeFieldSet, i18n.t("at most daily"),
        TYPEOFEMAILNOTIF_FIELD, i18n.t(
            "you will receive an email with any new messages in your inbox at [%s] at most daily",
            i18n.getSiteCommonName()), DAILY_TYPE_ID);
    dailyRadio.setTabIndex(5);
    dailyRadio.setValue(false);

    noRadio = DefaultFormUtils.createRadio(
        emailNotifTypeFieldSet,
        i18n.t("I don't need email notifications"),
        TYPEOFEMAILNOTIF_FIELD,
        i18n.t("you will no receive an email with any new messages in your inbox at [%s]",
            i18n.getSiteCommonName()), NO_TYPE_ID);
    noRadio.setTabIndex(6);
    noRadio.setValue(false);
    add(emailNotifTypeFieldSet);

    final Label notVerified = new Label(
        i18n.t("Your email is not verified, so you will not receive email notifications"));
    notVerified.setStyleName("oc-user-msg");
    notVerified.addStyleName("k-3corners");
    notVerifLabelAdapter = new AdapterField(notVerified);
    notVerifLabelAdapter.setLabelSeparator("");
    notVerifLabelAdapter.setWidth(DefaultFormUtils.BIG_FIELD_SIZE);
    super.add(notVerifLabelAdapter);

    resendEmailVerifBtn = new Button(i18n.t("Resend verification email"));
    resendEmailVerifBtn.addStyleName("k-button");
    resendEmailVerifAdapter = new AdapterField(resendEmailVerifBtn);
    resendEmailVerifAdapter.setValidateOnBlur(false);
    resendEmailVerifAdapter.setLabelSeparator("");
    resendEmailVerifAdapter.setWidth(DefaultFormUtils.BIG_FIELD_SIZE);
    // resendEmailVerifAdapter.setFieldLabel(i18n.t("Maybe you want receive again our verification email"));
    add(resendEmailVerifAdapter);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.UserOptGeneralView#getEmail()
   */
  @Override
  public String getEmail() {
    return email.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptGeneralView#getEmailNotif()
   */
  @Override
  public EmailNotificationFrequency getEmailNotif() {
    if (immediateRadio.getValue()) {
      return EmailNotificationFrequency.immediately;
    }
    if (hourlyRadio.getValue()) {
      return EmailNotificationFrequency.hourly;
    }
    if (dailyRadio.getValue()) {
      return EmailNotificationFrequency.daily;
    }
    // if (noRadio.getValue())
    return EmailNotificationFrequency.no;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.UserOptGeneralView#getLanguage()
   */
  @Override
  public I18nLanguageSimpleDTO getLanguage() {
    return langSelector.getLanguage();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.UserOptGeneralView#getLongName()
   */
  @Override
  public String getLongName() {
    return longName.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptGeneralView#getResendEmailVerif
   * ()
   */
  @Override
  public HasClickHandlers getResendEmailVerif() {
    return resendEmailVerifBtn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptGeneralView#setEmail(java.
   * lang.String)
   */
  @Override
  public void setEmail(final String email) {
    this.email.setValue(email);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptGeneralView#setEmailNotifChecked
   * (cc.kune.core.shared.dto.EmailNotificationFrequency)
   */
  @Override
  public void setEmailNotifChecked(final EmailNotificationFrequency freq) {
    switch (freq) {
    case no:
      noRadio.setValue(true);
      break;
    case hourly:
      hourlyRadio.setValue(true);
      break;
    case daily:
      dailyRadio.setValue(true);
      break;
    case immediately:
      immediateRadio.setValue(true);
      break;
    default:
      throw new RuntimeException("Unexpected email frequency");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptGeneralView#setEmailVerified
   * (boolean)
   */
  @Override
  public void setEmailVerified(final boolean verified) {
    resendEmailVerifAdapter.setVisible(!verified);
    notVerifLabelAdapter.setVisible(!verified);
    emailNotifTypeFieldSet.setVisible(verified);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptGeneralView#setLanguage(cc
   * .kune.core.shared.dto.I18nLanguageSimpleDTO)
   */
  @Override
  public void setLanguage(final I18nLanguageSimpleDTO language) {
    langSelector.setLanguage(language);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptGeneralView#setLongName(java
   * .lang.String)
   */
  @Override
  public void setLongName(final String longName) {
    this.longName.setValue(longName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.UserOptGeneralView#
   * setResendEmailVerifEnabled(boolean)
   */
  @Override
  public void setResendEmailVerifEnabled(final boolean enabled) {
    resendEmailVerifBtn.setEnabled(enabled);
  }

}
