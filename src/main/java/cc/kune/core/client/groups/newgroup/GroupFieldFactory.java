package cc.kune.core.client.groups.newgroup;

import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GroupFieldFactory {

  private static I18nTranslationService i18n;

  public static TextField<String> createUserLongName(final String fieldId) {
    final TextField<String> field = new TextField<String>();
    field.setFieldLabel(i18n.t("Long name"));
    field.setName(fieldId);
    field.setId(fieldId);
    field.setWidth(DefaultForm.BIG_FIELD_WIDTH);
    field.setAllowBlank(false);
    field.setMinLength(3);
    field.setMaxLength(50); /* Same in Group.java/longName */
    field.setValidationDelay(1000);
    return field;
  }

  public static TextField<String> createUserShortName(final String fieldId) {
    final String minMaxText = i18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15);
    final TextField<String> field = new TextField<String>();
    field.setFieldLabel(i18n.t("Short name"));
    field.setName(fieldId);
    field.setId(fieldId);
    field.setWidth(175);
    field.setMinLength(3);
    field.setMaxLength(15); /* Same in Group.java/shortName */
    field.setAllowBlank(false);
    field.setRegex("^[a-z0-9]+$");
    field.getMessages().setMinLengthText(minMaxText);
    field.getMessages().setMaxLengthText(minMaxText);
    field.getMessages().setRegexText(minMaxText);
    field.setValidationDelay(1000);
    return field;
  }

  @Inject
  public GroupFieldFactory(final I18nTranslationService i18n) {
    GroupFieldFactory.i18n = i18n;
  }
}
