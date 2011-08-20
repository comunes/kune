package cc.kune.core.client.auth;

import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserFieldFactory {

  private static I18nTranslationService i18n;

  public static TextField<String> createUserLongName(final String fieldId) {
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

  public static TextField<String> createUserShortName(final String fieldId) {
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

  @Inject
  public UserFieldFactory(final I18nTranslationService i18n) {
    UserFieldFactory.i18n = i18n;
  }

}
