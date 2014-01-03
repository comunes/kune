/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import java.util.List;

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

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractLanguageSelectorPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractLanguageSelectorPanel extends FormPanel {

  /**
   * The Class LanguageData.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @SuppressWarnings("serial")
  public class LanguageData extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** The Constant CODE. */
    private static final String CODE = "code";

    /** The Constant ENGLISH_NAME. */
    private static final String ENGLISH_NAME = "englishName";

    /**
     * Instantiates a new language data.
     * 
     * @param code
     *          the code
     * @param englishName
     *          the english name
     */
    public LanguageData(final String code, final String englishName) {
      set(CODE, code);
      set(ENGLISH_NAME, englishName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public String getCode() {
      return get(CODE);
    }

    /**
     * Gets the english name.
     * 
     * @return the english name
     */
    public String getEnglishName() {
      return get(ENGLISH_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (getCode() == null ? 0 : getCode().hashCode());
      return result;
    }
  }

  /** The Constant LANG_FIELD. */
  public static final String LANG_FIELD = "k-langsp-lf";

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The lang combo. */
  private ComboBox<LanguageData> langCombo;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new abstract language selector panel.
   * 
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   * @param type
   *          the type
   */
  public AbstractLanguageSelectorPanel(final I18nTranslationService i18n, final Session session,
      final LanguageSelectorType type) {
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
    createLangCombo(type);
    super.add(langCombo);
  }

  /**
   * Adds the change listener.
   * 
   * @param onChange
   *          the on change
   */
  public void addChangeListener(final SimpleCallback onChange) {
    langCombo.addListener(Events.Select, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        onChange.onCallback();
      }
    });
  }

  /**
   * Creates the lang combo.
   * 
   * @param type
   *          the type
   */
  private void createLangCombo(final LanguageSelectorType type) {
    // Field.setMsgTarget("side");
    // NOTE: The Combo box in modal popups fails!
    langCombo = new ComboBox<LanguageData>();
    langCombo.setName(LANG_FIELD);
    langCombo.setMinChars(1);
    // langCombo.setMode(ComboBox.LOCAL);
    langCombo.setStore(createStore(type));
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

  /**
   * Creates the store.
   * 
   * @param type
   *          the type
   * @return the list store
   */
  private ListStore<LanguageData> createStore(final LanguageSelectorType type) {
    final ListStore<LanguageData> list = new ListStore<LanguageData>();
    final List<I18nLanguageSimpleDTO> langs = type == LanguageSelectorType.ONLY_FULL_TRANSLATED ? session.getFullTranslatedLanguages()
        : session.getLanguages();
    for (final I18nLanguageSimpleDTO lang : langs) {
      switch (type) {
      case ALL_EXCEPT_ENGLISH:
      case ONLY_FULL_TRANSLATED:
        if (!lang.getCode().equals("en")) {
          list.add(getLangData(lang));
        }
        break;
      case ALL:
      default:
        list.add(getLangData(lang));
        break;
      }
    }
    return list;
  }

  /**
   * Gets the lang data.
   * 
   * @param lang
   *          the lang
   * @return the lang data
   */
  private LanguageData getLangData(final I18nLanguageSimpleDTO lang) {
    return new LanguageData(lang.getCode(), lang.getEnglishName());
  }

  /**
   * Gets the language.
   * 
   * @return the language
   */
  public I18nLanguageSimpleDTO getLanguage() {
    final LanguageData value = langCombo.getValue();
    if (value == null) {
      return null;
    }
    final String langCode = value.getCode();
    for (final I18nLanguageSimpleDTO lang : session.getLanguages()) {
      if (lang.getCode().equals(langCode)) {
        return lang;
      }
    }
    throw new UIException("Language not found");
  }

  /**
   * Gets the language code.
   * 
   * @return the language code
   */
  public String getLanguageCode() {
    return langCombo.getValue().getCode();
  }

  /**
   * Gets the language english name.
   * 
   * @return the language english name
   */
  public String getLanguageEnglishName() {
    return langCombo.getValue().getEnglishName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.extjs.gxt.ui.client.widget.form.FormPanel#reset()
   */
  @Override
  public void reset() {
    langCombo.reset();
  }

  /**
   * Sets the lang separator.
   * 
   * @param separator
   *          the new lang separator
   */
  public void setLangSeparator(final String separator) {
    langCombo.setLabelSeparator(separator);
  }

  /**
   * Sets the lang title.
   * 
   * @param langFieldTitle
   *          the new lang title
   */
  public void setLangTitle(final String langFieldTitle) {
    if (langFieldTitle == null) {
      setHideLabels(true);
    } else {
      if (langFieldTitle != null) {
        langCombo.setFieldLabel(langFieldTitle);
      }
    }
  }

  /**
   * Sets the language.
   * 
   * @param language
   *          the new language
   */
  public void setLanguage(final I18nLanguageSimpleDTO language) {
    if (language == null) {
      langCombo.clear();
    } else {
      langCombo.setValue(getLangData(language));
    }
  }
}
