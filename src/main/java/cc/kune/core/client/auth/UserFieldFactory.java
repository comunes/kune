package cc.kune.core.client.auth;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserFieldFactory {

  private final I18nTranslationService i18n;

  @Inject
  public UserFieldFactory(final I18nTranslationService i18n) {
    this.i18n = i18n;
  }

  public TextField<String> createUserEmail(final String fieldId) {
    // http://www.sencha.com/forum/showthread.php?49702-GXT-Form-Validation
    final TextField<String> emailRegField = new TextField<String>();
    emailRegField.setFieldLabel(i18n.t("Email"));
    emailRegField.setName(fieldId);
    emailRegField.setRegex(TextUtils.EMAIL_REGEXP);
    emailRegField.getMessages().setRegexText(i18n.t("This is not a valid email"));
    emailRegField.setWidth(DefaultForm.DEF_MEDIUM_FIELD_WIDTH);
    emailRegField.setAllowBlank(false);
    emailRegField.setValidationDelay(1000);
    emailRegField.setId(fieldId);
    return emailRegField;
  }

  public TextField<String> createUserLongName(final String fieldId) {
    final TextField<String> longNameRegField = new TextField<String>();
    longNameRegField.setFieldLabel(i18n.t("Name"));
    longNameRegField.setName(fieldId);
    longNameRegField.setWidth(DefaultForm.DEF_FIELD_WIDTH);
    longNameRegField.setAllowBlank(false);
    longNameRegField.setMinLength(3);
    longNameRegField.setMaxLength(50);
    longNameRegField.setId(fieldId);
    return longNameRegField;
  }

  public TextField<String> createUserPasswd(final String fieldId, final String fieldText) {
    final TextField<String> passwdRegField = new TextField<String>();
    passwdRegField.setFieldLabel(fieldText);
    passwdRegField.setName(fieldId);
    passwdRegField.setPassword(true);
    passwdRegField.setAllowBlank(false);
    passwdRegField.setMinLength(6);
    passwdRegField.setMaxLength(40);
    passwdRegField.getMessages().setMinLengthText(i18n.t(CoreMessages.PASSWD_MUST_BE_BETWEEN_6_AND_40));
    passwdRegField.getMessages().setMaxLengthText(i18n.t(CoreMessages.PASSWD_MUST_BE_BETWEEN_6_AND_40));
    passwdRegField.setWidth(DefaultForm.DEF_MEDIUM_FIELD_WIDTH);
    passwdRegField.setValidationDelay(1000);
    passwdRegField.setId(fieldId);
    return passwdRegField;
  }

  public TextField<String> createUserShortName(final String fieldId) {
    final String minMaxText = i18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15);
    final TextField<String> field = new TextField<String>();
    field.setFieldLabel(i18n.t("Username"));
    field.setName(fieldId);
    field.setId(fieldId);
    field.setWidth(DefaultForm.DEF_SMALL_FIELD_WIDTH);
    field.setAllowBlank(false);
    field.setMinLength(3);
    field.setMaxLength(15);
    field.setRegex("^[a-z0-9]+$");
    field.getMessages().setMinLengthText(minMaxText);
    field.getMessages().setMaxLengthText(minMaxText);
    field.getMessages().setRegexText(minMaxText);
    field.setValidationDelay(1000);
    return field;
  }

}
