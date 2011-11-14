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
package cc.kune.core.client.groups.newgroup;

import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GroupFieldFactory {

  private final I18nTranslationService i18n;

  @Inject
  public GroupFieldFactory(final I18nTranslationService i18n) {
    this.i18n = i18n;
  }

  public TextField<String> createUserLongName(final String fieldId) {
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

  public TextField<String> createUserShortName(final String fieldId) {
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
}
