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
package cc.kune.gspace.client.i18n;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.utils.SimpleCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.inject.Inject;

public class LanguageSelectorPanel extends FormPanel {

  @SuppressWarnings("serial")
  public class LanguageData extends BaseModel {

    private static final String CODE = "code";
    private static final String ENGLISH_NAME = "englishName";

    public LanguageData(final String code, final String englishName) {
      set(CODE, code);
      set(ENGLISH_NAME, englishName);
    }

    public String getCode() {
      return get(CODE);
    }

    public String getEnglishName() {
      return get(ENGLISH_NAME);
    }
  }

  public static final String LANG_FIELD = "k-langsp-lf";

  private final I18nTranslationService i18n;
  private ComboBox<LanguageData> langCombo;
  private final Session session;

  @Inject
  public LanguageSelectorPanel(final I18nTranslationService i18n, final Session session) {
    super();
    this.i18n = i18n;
    this.session = session;
    setBorders(false);
    setFrame(false);
    setHeaderVisible(false);
    createLangCombo();
    super.add(langCombo);
    setBodyBorder(false);
  }

  public void addChangeListener(final SimpleCallback onChange) {
    langCombo.addListener(Events.Select, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        onChange.onCallback();
      }
    });
  }

  private void createLangCombo() {
    // Field.setMsgTarget("side");
    langCombo = new ComboBox<LanguageData>();
    langCombo.setName(LANG_FIELD);
    langCombo.setMinChars(1);
    // langCombo.setMode(ComboBox.LOCAL);
    langCombo.setStore(createStore());
    langCombo.setDisplayField("language");
    langCombo.setTriggerAction(TriggerAction.ALL);
    langCombo.setEmptyText(i18n.t("Enter language"));
    langCombo.setLoadingText(i18n.t("Searching..."));
    langCombo.setDisplayField(LanguageData.ENGLISH_NAME);
    langCombo.setValueField(LanguageData.CODE);
    langCombo.setTypeAhead(true);
    langCombo.setTypeAheadDelay(1000);
    langCombo.setSelectOnFocus(false);
    langCombo.setWidth(140);
    langCombo.setPageSize(7);
    langCombo.setForceSelection(true);
    langCombo.setAllowBlank(false);
  }

  private ListStore<LanguageData> createStore() {
    final ListStore<LanguageData> list = new ListStore<LanguageData>();

    for (final I18nLanguageSimpleDTO lang : session.getLanguages()) {
      list.add(new LanguageData(lang.getCode(), lang.getEnglishName()));
    }
    return list;
  }

  public I18nLanguageSimpleDTO getLanguage() {
    final String langCode = langCombo.getValue().getCode();
    for (final I18nLanguageSimpleDTO lang : session.getLanguages()) {
      if (lang.getCode().equals(langCode)) {
        return lang;
      }
    }
    throw new UIException("Languege not found");
  }

  public String getLanguageCode() {
    return langCombo.getValue().getCode();
  }

  public String getLanguageEnglishName() {
    return langCombo.getValue().getEnglishName();
  }

  @Override
  public void reset() {
    langCombo.reset();
  }

  public void setLangSeparator(final String separator) {
    langCombo.setLabelSeparator(separator);
  }

  public void setLangTitle(final String langFieldTitle) {
    if (langFieldTitle == null) {
      setHideLabels(true);
    } else {
      if (langFieldTitle != null) {
        langCombo.setFieldLabel(langFieldTitle);
      }
    }
  }

  public void setLanguage(final I18nLanguageSimpleDTO language) {
    langCombo.setRawValue(language.getEnglishName());
  }
}
