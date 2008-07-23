package org.ourproject.kune.workspace.client.sitebar.login;

import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.VType;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;

public class RegisterForm extends DefaultForm {

    private static final String MUST_BE_BETWEEN_3_AND_15 = "Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes";

    private static final String NICK_FIELD = "nick";
    private static final String EMAIL_FIELD = "email";
    private static final String LONGNAME_FIELD = "long_name";
    private static final String PASSWORD_FIELD = "password";
    private static final String PASSWORD_FIELD_DUP = "passwordDup";
    private static final String LANG_FIELD = "lang";
    private static final String COUNTRY_FIELD = "country";
    private static final String TIMEZONE_FIELD = "timezone";

    private final TextField shortNameRegField;
    private final TextField longNameRegField;
    private final TextField passwdRegField;
    private final TextField passwdRegFieldDup;
    private final TextField emailRegField;
    private final ComboBox languageCombo;
    private final ComboBox countryCombo;
    private final ComboBox timezoneCombo;

    public RegisterForm(final SignInPresenter presenter, final I18nUITranslationService i18n) {
	shortNameRegField = new TextField();
	shortNameRegField.setFieldLabel(i18n.t("Nickname"));
	shortNameRegField.setName(NICK_FIELD);
	shortNameRegField.setWidth(DEF_FIELD_WIDTH);
	shortNameRegField.setAllowBlank(false);
	shortNameRegField.setMinLength(3);
	shortNameRegField.setMaxLength(15);
	shortNameRegField.setRegex("^[a-z0-9_\\-]+$");
	shortNameRegField.setMinLengthText(i18n.t(MUST_BE_BETWEEN_3_AND_15));
	shortNameRegField.setMaxLengthText(i18n.t(MUST_BE_BETWEEN_3_AND_15));
	shortNameRegField.setRegexText(i18n.t(MUST_BE_BETWEEN_3_AND_15));
	shortNameRegField.setValidationEvent(false);
	add(shortNameRegField);

	longNameRegField = new TextField();
	longNameRegField.setFieldLabel(i18n.t("Full Name"));
	longNameRegField.setName(LONGNAME_FIELD);
	longNameRegField.setWidth(DEF_FIELD_WIDTH);
	longNameRegField.setAllowBlank(false);
	longNameRegField.setMinLength(3);
	longNameRegField.setMaxLength(50);
	longNameRegField.setValidationEvent(false);
	add(longNameRegField);

	passwdRegField = new TextField();
	passwdRegField.setFieldLabel(i18n.t("Password"));
	passwdRegField.setName(PASSWORD_FIELD);
	passwdRegField.setPassword(true);
	passwdRegField.setAllowBlank(false);
	passwdRegField.setMaxLength(40);
	passwdRegField.setWidth(DEF_FIELD_WIDTH);
	passwdRegField.setValidationEvent(false);
	add(passwdRegField);

	passwdRegFieldDup = new TextField();
	passwdRegFieldDup.setFieldLabel(i18n.t("Retype password"));
	passwdRegFieldDup.setName(PASSWORD_FIELD_DUP);
	passwdRegFieldDup.setPassword(true);
	passwdRegFieldDup.setAllowBlank(false);
	passwdRegFieldDup.setMinLength(6);
	passwdRegFieldDup.setMaxLength(40);
	passwdRegFieldDup.setWidth(DEF_FIELD_WIDTH);
	passwdRegFieldDup.setInvalidText(i18n.t("Passwords do not match"));
	passwdRegFieldDup.setValidator(new Validator() {
	    public boolean validate(final String value) throws ValidationException {
		return passwdRegField.getValueAsString().equals(passwdRegFieldDup.getValueAsString());
	    }
	});
	passwdRegFieldDup.setValidationEvent(false);
	add(passwdRegFieldDup);

	emailRegField = new TextField();
	emailRegField.setFieldLabel(i18n.t("Email"));
	emailRegField.setName(EMAIL_FIELD);
	emailRegField.setVtype(VType.EMAIL);
	emailRegField.setWidth(DEF_FIELD_WIDTH);
	emailRegField.setAllowBlank(false);
	emailRegField.setValidationEvent(false);

	add(emailRegField);

	final Store langStore = new SimpleStore(new String[] { "abbr", "language" }, presenter.getLanguages());
	langStore.load();

	languageCombo = new ComboBox();
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
	languageCombo.setWidth(186);
	languageCombo.setAllowBlank(false);
	languageCombo.setValueField("abbr");
	languageCombo.setValue(presenter.getCurrentLanguage().getCode());
	languageCombo.setPageSize(7);
	languageCombo.setForceSelection(true);
	languageCombo.setValidationEvent(false);
	add(languageCombo);

	final Store countryStore = new SimpleStore(new String[] { "abbr", "country" }, presenter.getCountries());
	countryStore.load();

	countryCombo = new ComboBox();
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
	countryCombo.setWidth(186);
	countryCombo.setAllowBlank(false);
	countryCombo.setValueField("abbr");
	countryCombo.setPageSize(7);
	countryCombo.setForceSelection(true);
	countryCombo.setValidationEvent(false);
	add(countryCombo);

	final Store timezoneStore = new SimpleStore(new String[] { "id" }, presenter.getTimezones());
	timezoneStore.load();

	timezoneCombo = new ComboBox();
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
	timezoneCombo.setWidth(186);
	timezoneCombo.setAllowBlank(false);
	timezoneCombo.setValueField("id");
	timezoneCombo.setPageSize(7);
	timezoneCombo.setForceSelection(true);
	timezoneCombo.setValidationEvent(false);
	add(timezoneCombo);
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

}
