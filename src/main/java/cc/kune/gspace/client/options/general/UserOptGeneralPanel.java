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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.gspace.client.i18n.LanguageSelectorPanel;

import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;

public class UserOptGeneralPanel extends EntityOptGeneralPanel implements UserOptGeneralView {

  private static final String LONG_NAME_FIELD = "k-uogp-lname";
  private final CheckBox emailNotifField;
  private final LanguageSelectorPanel langSelector;
  private final TextField<String> longName;

  @Inject
  public UserOptGeneralPanel(final I18nTranslationService i18n, final CoreResources res,
      final MaskWidget maskWidget, final LanguageSelectorPanel langSelector,
      final UserFieldFactory userFieldFactory) {
    super(maskWidget, res.emblemSystem(), i18n.t("General"), i18n.t("Change this values:"));
    this.langSelector = langSelector;
    longName = userFieldFactory.createUserLongName(LONG_NAME_FIELD);
    add(longName);
    langSelector.setLangTitle(i18n.t("Your language"));
    langSelector.setLabelAlign(LabelAlign.LEFT);
    langSelector.setLangSeparator(":");
    add(langSelector);
    emailNotifField = new CheckBox();
    emailNotifField.setFieldLabel(i18n.t("Email notifications"));
    add(emailNotifField);
  }

  @Override
  public I18nLanguageSimpleDTO getLanguage() {
    return langSelector.getLanguage();
  }

  @Override
  public String getLongName() {
    return longName.getValue();
  }

  public boolean isEmailNofifField() {
    return emailNotifField.getValue();
  }

  public void setEmailNofifField(final boolean value) {
    emailNotifField.setValue(value);
  }

  @Override
  public void setLanguage(final I18nLanguageSimpleDTO language) {
    langSelector.setLanguage(language);
  }

  @Override
  public void setLongName(final String longName) {
    this.longName.setValue(longName);
  }

}
