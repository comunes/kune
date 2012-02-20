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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

public abstract class AbstractLanguageSelectorPanel extends FormPanel {

  @SuppressWarnings("serial")
  public class LanguageData extends BaseModel {

    private static final String CODE = "code";
    private static final String ENGLISH_NAME = "englishName";

    public LanguageData(final String code, final String englishName) {
      set(CODE, code);
      set(ENGLISH_NAME, englishName);
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      return getCode().equals(((LanguageData) obj).getCode());
    }

    public String getCode() {
      return get(CODE);
    }

    public String getEnglishName() {
      return get(ENGLISH_NAME);
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (getCode() == null ? 0 : getCode().hashCode());
      return result;
    }
  }

  public static final String LANG_FIELD = "k-langsp-lf";

  private final I18nTranslationService i18n;
  private ComboBox<LanguageData> langCombo;
  private final Session session;

  public AbstractLanguageSelectorPanel(final I18nTranslationService i18n, final Session session,
      final boolean withEnglish) {
    super();
    this.i18n = i18n;
    this.session = session;
    setBorders(false);
    setFrame(false);
    setHeaderVisible(false);
    setBodyBorder(false);
    // setWidth(300);
    setLabelWidth(DefaultForm.DEF_FIELD_LABEL_WITH + 25);
    setPadding(0);
    createLangCombo(withEnglish);
    super.add(langCombo);
  }

  public void addChangeListener(final SimpleCallback onChange) {
    langCombo.addListener(Events.Select, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        onChange.onCallback();
      }
    });
  }

  private void createLangCombo(final boolean withEnglish) {
    // Field.setMsgTarget("side");
    // NOTE: The Combo box in modal popups fails!
    langCombo = new ComboBox<LanguageData>();
    langCombo.setName(LANG_FIELD);
    langCombo.setMinChars(1);
    // langCombo.setMode(ComboBox.LOCAL);
    langCombo.setStore(createStore(withEnglish));
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

  private ListStore<LanguageData> createStore(final boolean withEnglish) {
    final ListStore<LanguageData> list = new ListStore<LanguageData>();

    for (final I18nLanguageSimpleDTO lang : session.getLanguages()) {
      final boolean isEnglish = lang.getCode().equals("en");
      if (!isEnglish || (isEnglish && withEnglish)) {
        list.add(getLangData(lang));
      }
    }
    return list;
  }

  private LanguageData getLangData(final I18nLanguageSimpleDTO lang) {
    return new LanguageData(lang.getCode(), lang.getEnglishName());
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
    langCombo.setValue(getLangData(language));
  }
}
