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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.ui.MaskWidget;
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
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

public class UserOptGeneralPanel extends EntityOptGeneralPanel implements UserOptGeneralView {

  public static final String DAILY_TYPE_ID = "k-ngp-type_daily";
  public static final String EMAIL_FIELD = "k-ngp-emial";
  public static final String HOURLY_TYPE_ID = "k-ngp-type_hourly";
  public static final String IMMEDIATE_TYPE_ID = "k-ngp-type_immedi";
  public static final String LONG_NAME_FIELD = "k-uogp-lname";
  public static final String NO_TYPE_ID = "k-ngp-type_no";
  public static final String TYPEOFEMAILNOTIF_FIELD = "k-ngp-type_of_email_notif";

  private final Radio dailyRadio;
  private final TextField<String> email;
  private final FieldSet emailNotifTypeFieldSet;
  private final Radio hourlyRadio;
  private final Radio immediateRadio;
  private final LanguageSelectorPanel langSelector;
  private final TextField<String> longName;
  private final Radio noRadio;
  private final AdapterField notVerifLabelAdapter;
  private final AdapterField resendEmailVerifAdapter;
  private final Button resendEmailVerifBtn;

  @Inject
  public UserOptGeneralPanel(final I18nUITranslationService i18n, final CoreResources res,
      final MaskWidget maskWidget, final LanguageSelectorPanel langSelector,
      final UserFieldFactory userFieldFactory) {
    super(maskWidget, res.emblemSystem(), i18n.t("General"), i18n.t("You can change these values:"));
    this.langSelector = langSelector;
    longName = userFieldFactory.createUserLongName(LONG_NAME_FIELD);
    add(longName);
    langSelector.setLangTitle(i18n.t("Your language"));
    langSelector.setLabelAlign(LabelAlign.LEFT);
    langSelector.setLangSeparator(":");
    add(langSelector);

    email = userFieldFactory.createUserEmail(EMAIL_FIELD);
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

  @Override
  public String getEmail() {
    return email.getValue();
  }

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

  @Override
  public I18nLanguageSimpleDTO getLanguage() {
    return langSelector.getLanguage();
  }

  @Override
  public String getLongName() {
    return longName.getValue();
  }

  @Override
  public HasClickHandlers getResendEmailVerif() {
    return resendEmailVerifBtn;
  }

  @Override
  public void setEmail(final String email) {
    this.email.setValue(email);
  }

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

  @Override
  public void setEmailVerified(final boolean verified) {
    resendEmailVerifAdapter.setVisible(!verified);
    notVerifLabelAdapter.setVisible(!verified);
    emailNotifTypeFieldSet.setVisible(verified);
  }

  @Override
  public void setLanguage(final I18nLanguageSimpleDTO language) {
    langSelector.setLanguage(language);
  }

  @Override
  public void setLongName(final String longName) {
    this.longName.setValue(longName);
  }

  @Override
  public void setResendEmailVerifEnabled(final boolean enabled) {
    resendEmailVerifBtn.setEnabled(enabled);
  }

}
