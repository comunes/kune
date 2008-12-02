/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.workspace.client.WorkspaceMessages;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.VType;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;

public class RegisterForm extends DefaultForm {

    public static final String NICK_FIELD = "k-urf-nick-f";
    public static final String EMAIL_FIELD = "k-urf-email-f";
    public static final String LONGNAME_FIELD = "k-urf-long_name-f";
    public static final String PASSWORD_FIELD = "k-urf-password-f";
    public static final String PASSWORD_FIELD_DUP = "k-urf-passwordDup-f";
    public static final String LANG_FIELD = "k-urf-lang-f";
    public static final String COUNTRY_FIELD = "k-urf-country-f";
    public static final String TIMEZONE_FIELD = "k-urf-timezone-f";
    public static final String WANNAPERSONALHOMEPAGE_ID = "k-urf-wphp-id";
    public static final String NOPERSONALHOMEPAGE_ID = "k-urf-nphp-id";
    public static final String WANTHOMEPAGE_FIELD = "k-urf-wphp-f";

    private final TextField shortNameRegField;
    private final TextField longNameRegField;
    private final TextField passwdRegField;
    private final TextField passwdRegFieldDup;
    private final TextField emailRegField;
    private final ComboBox languageCombo;
    private final ComboBox countryCombo;
    private final ComboBox timezoneCombo;
    private final Radio wantPersonalHomePage;
    private final Radio noPersonalHomePage;
    private final I18nUITranslationService i18n;

    public RegisterForm(final I18nUITranslationService i18n, Session session) {
        this.i18n = i18n;
        super.addStyleName("kune-Margin-Large-l");

        shortNameRegField = new TextField();
        shortNameRegField.setTabIndex(0);
        shortNameRegField.setFieldLabel(i18n.t("Nickname"));
        shortNameRegField.setName(NICK_FIELD);
        shortNameRegField.setWidth(DEF_SMALL_FIELD_WIDTH);
        shortNameRegField.setAllowBlank(false);
        shortNameRegField.setMinLength(3);
        shortNameRegField.setMaxLength(15);
        shortNameRegField.setRegex("^[a-z0-9_\\-]+$");
        shortNameRegField.setMinLengthText(i18n.t(WorkspaceMessages.MUST_BE_BETWEEN_3_AND_15));
        shortNameRegField.setMaxLengthText(i18n.t(WorkspaceMessages.MUST_BE_BETWEEN_3_AND_15));
        shortNameRegField.setRegexText(i18n.t(WorkspaceMessages.MUST_BE_BETWEEN_3_AND_15));
        shortNameRegField.setValidationEvent(false);
        add(shortNameRegField);

        longNameRegField = new TextField();
        longNameRegField.setTabIndex(1);
        longNameRegField.setFieldLabel(i18n.t("Full Name"));
        longNameRegField.setName(LONGNAME_FIELD);
        longNameRegField.setWidth(DEF_FIELD_WIDTH);
        longNameRegField.setAllowBlank(false);
        longNameRegField.setMinLength(3);
        longNameRegField.setMaxLength(50);
        longNameRegField.setValidationEvent(false);
        longNameRegField.setId(LONGNAME_FIELD);
        add(longNameRegField);

        passwdRegField = new TextField();
        passwdRegField.setTabIndex(2);
        passwdRegField.setFieldLabel(i18n.t("Password"));
        passwdRegField.setName(PASSWORD_FIELD);
        passwdRegField.setPassword(true);
        passwdRegField.setAllowBlank(false);
        passwdRegField.setMaxLength(40);
        passwdRegField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        passwdRegField.setValidationEvent(false);
        passwdRegField.setId(PASSWORD_FIELD);
        add(passwdRegField);

        passwdRegFieldDup = new TextField();
        passwdRegFieldDup.setTabIndex(3);
        passwdRegFieldDup.setFieldLabel(i18n.t("Retype password"));
        passwdRegFieldDup.setName(PASSWORD_FIELD_DUP);
        passwdRegFieldDup.setPassword(true);
        passwdRegFieldDup.setAllowBlank(false);
        passwdRegFieldDup.setMinLength(6);
        passwdRegFieldDup.setMaxLength(40);
        passwdRegFieldDup.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        passwdRegFieldDup.setInvalidText(i18n.t("Passwords do not match"));
        passwdRegFieldDup.setValidator(new Validator() {
            public boolean validate(final String value) throws ValidationException {
                return passwdRegField.getValueAsString().equals(passwdRegFieldDup.getValueAsString());
            }
        });
        passwdRegFieldDup.setValidationEvent(false);
        passwdRegFieldDup.setId(PASSWORD_FIELD_DUP);
        add(passwdRegFieldDup);

        emailRegField = new TextField();
        emailRegField.setTabIndex(4);
        emailRegField.setFieldLabel(i18n.t("Email"));
        emailRegField.setName(EMAIL_FIELD);
        emailRegField.setVtype(VType.EMAIL);
        emailRegField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        emailRegField.setAllowBlank(false);
        emailRegField.setValidationEvent(false);
        emailRegField.setId(EMAIL_FIELD);
        add(emailRegField);

        final Store langStore = new SimpleStore(new String[] { "abbr", "language" }, session.getLanguagesArray());
        langStore.load();

        languageCombo = new ComboBox();
        languageCombo.setTabIndex(5);
        languageCombo.setLazyRender(true);
        languageCombo.setName(LANG_FIELD);
        languageCombo.setMinChars(1);
        languageCombo.setFieldLabel(i18n.t("Language"));
        languageCombo.setStore(langStore);
        languageCombo.setDisplayField("language");
        languageCombo.setMode(ComboBox.LOCAL);
        languageCombo.setTriggerAction(ComboBox.ALL);
        languageCombo.setEmptyText(i18n.t("Enter language"));
        languageCombo.setLoadingText(i18n.t("Searching..."));
        languageCombo.setTypeAhead(true);
        languageCombo.setTypeAheadDelay(1000);
        languageCombo.setSelectOnFocus(false);
        languageCombo.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        languageCombo.setAllowBlank(false);
        languageCombo.setValueField("abbr");
        languageCombo.setValue(session.getCurrentLanguage().getCode());
        languageCombo.setPageSize(6);
        languageCombo.setForceSelection(true);
        languageCombo.setValidationEvent(false);
        // languageCombo.setId(LANG_FIELD);
        add(languageCombo);

        final Store countryStore = new SimpleStore(new String[] { "abbr", "country" }, session.getCountriesArray());
        countryStore.load();

        countryCombo = new ComboBox();
        countryCombo.setTabIndex(7);
        countryCombo.setLazyRender(true);
        countryCombo.setName(COUNTRY_FIELD);
        countryCombo.setMinChars(1);
        countryCombo.setFieldLabel(i18n.t("Country"));
        countryCombo.setStore(countryStore);
        countryCombo.setDisplayField("country");
        countryCombo.setMode(ComboBox.LOCAL);
        countryCombo.setTriggerAction(ComboBox.ALL);
        countryCombo.setEmptyText(i18n.t("Enter your country"));
        countryCombo.setLoadingText(i18n.t("Searching..."));
        countryCombo.setTypeAhead(true);
        countryCombo.setTypeAheadDelay(1000);
        countryCombo.setSelectOnFocus(false);
        countryCombo.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        countryCombo.setAllowBlank(false);
        countryCombo.setValueField("abbr");
        countryCombo.setPageSize(7);
        countryCombo.setForceSelection(true);
        countryCombo.setValidationEvent(false);
        // countryCombo.setId(COUNTRY_FIELD);
        add(countryCombo);

        final Store timezoneStore = new SimpleStore(new String[] { "id" }, session.getTimezones());
        timezoneStore.load();

        timezoneCombo = new ComboBox();
        timezoneCombo.setTabIndex(8);
        timezoneCombo.setLazyRender(true);
        timezoneCombo.setName(TIMEZONE_FIELD);
        timezoneCombo.setMinChars(1);
        timezoneCombo.setFieldLabel(i18n.t("Timezone"));
        timezoneCombo.setStore(timezoneStore);
        timezoneCombo.setDisplayField("id");
        timezoneCombo.setMode(ComboBox.LOCAL);
        timezoneCombo.setTriggerAction(ComboBox.ALL);
        timezoneCombo.setEmptyText(i18n.t("Enter your timezone"));
        timezoneCombo.setLoadingText(i18n.t("Searching..."));
        timezoneCombo.setTypeAhead(true);
        timezoneCombo.setTypeAheadDelay(1000);
        timezoneCombo.setSelectOnFocus(false);
        timezoneCombo.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        timezoneCombo.setAllowBlank(false);
        timezoneCombo.setValueField("id");
        timezoneCombo.setPageSize(7);
        timezoneCombo.setForceSelection(true);
        timezoneCombo.setValidationEvent(false);
        // timezoneCombo.setId(TIMEZONE_FIELD);
        add(timezoneCombo);

        final FieldSet personalSpaceFieldSet = new FieldSet(i18n.t("Do you want a personal homepage?"));
        personalSpaceFieldSet.setWidth(DEF_FIELD_WIDTH + DEF_FIELD_LABEL_WITH);
        wantPersonalHomePage = new Radio();
        wantPersonalHomePage.setTabIndex(9);
        noPersonalHomePage = new Radio();
        noPersonalHomePage.setTabIndex(10);
        personalSpaceFieldSet.setCollapsible(false);
        createRadio(personalSpaceFieldSet, wantPersonalHomePage,
                i18n.t("Yes, I want a homepage for publish my contents."), WANNAPERSONALHOMEPAGE_ID);
        wantPersonalHomePage.setChecked(true);
        createRadio(personalSpaceFieldSet, noPersonalHomePage, i18n.t("No, I don't want. Maybe in the future."),
                NOPERSONALHOMEPAGE_ID);
        add(personalSpaceFieldSet);
    }

    public String getCountry() {
        return countryCombo.getValueAsString();
    }

    public String getEmail() {
        return emailRegField.getValueAsString();
    }

    public String getLanguage() {
        return languageCombo.getValueAsString();
    }

    public String getLongName() {
        return longNameRegField.getValueAsString();
    }

    public String getRegisterPassword() {
        return passwdRegField.getValueAsString();
    }

    public String getRegisterPasswordDup() {
        return passwdRegFieldDup.getValueAsString();
    }

    public String getShortName() {
        return shortNameRegField.getValueAsString();
    }

    public String getTimezone() {
        return timezoneCombo.getValueAsString();
    }

    public boolean wantPersonalHomepage() {
        return wantPersonalHomePage.getValue();
    }

    private void createRadio(final FieldSet fieldSet, final Radio radio, final String radioLabel, final String radioId) {
        radio.setName(WANTHOMEPAGE_FIELD);
        radio.setBoxLabel(i18n.t(radioLabel));
        radio.setAutoCreate(true);
        radio.setHideLabel(true);
        radio.setId(radioId);
        fieldSet.add(radio);
    }

}
